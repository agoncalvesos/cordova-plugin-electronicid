//
//  ElectronicIDWrapper.swift
//  ElectronicIDWrapper
//
//  Copyright © 2020 Jose Servet Font. All rights reserved.
//

import Foundation
import UIKit
import Alamofire
import ObjectMapper
import VideoID

enum ElectronicIDMode: Int {
    case unattended = 0
    case attended
    case videoScan
}

class ElectronicIDWrapper: NSObject {
    let bearer: String
    let rAuthority: String
    let endpoint: String
    let present: (UIViewController) -> Void
    let success: (String) -> Void
    let failure: (String, String?) -> Void

    init(bearer: String,
         rAuthority: String,
         endpoint: String,
         present: @escaping (UIViewController) -> Void,
         success: @escaping (String) -> Void,
         failure: @escaping (String, String?) -> Void) {
        self.bearer = bearer
        self.rAuthority = rAuthority
        if endpoint.hasSuffix("/") {
            self.endpoint = String(endpoint.prefix(endpoint.count - 1))
        } else {
            self.endpoint = endpoint
        }
        print(self.endpoint)
        self.present = present
        self.success = success
        self.failure = failure
    }

    public func startAuthentication(language: String = "es", docType: Int = 62, mode: ElectronicIDMode = .unattended, service: VideoID.VideoService = .videoID) -> Void {

        createAuthorization(mode: mode,
                            service: service,
                            onResult: { authorization in
                                guard let authorization = authorization else {
                                    self.failure("Authorization error", nil)
                                    return
                                }
                                let environment: VideoID.Environment = Environment(url: self.endpoint, autorization: authorization)
                                let videoViewController = self.createViewController(for: mode, environment: environment, language: language, docType: docType)
                                videoViewController.modalPresentationStyle = .fullScreen
                                self.present(videoViewController)
        },
                            onError: { error in
                                self.failure(error?.localizedDescription ?? "Authorization error", nil)
        })


    }

    private func createAuthorization(mode: ElectronicIDMode, service: VideoID.VideoService, onResult: @escaping (String?) -> Void, onError: @escaping (Error?) -> Void) {

        //let headers = ["Authorization": "Bearer " + self.bearer, "Accept": "application/json"]
        let serviceURL = getServiceUrl(for: mode, service: service)
        let process = getProcess(for: mode)
        let request = AuthorizationRequest(tenantId: "", process: process, rauthorityId: self.rAuthority)
        let path = endpoint + serviceURL
        let cleanPath = path.replacingOccurrences(of: "//", with: "/")

        let headers : HTTPHeaders = [
            "Authorization": "Bearer " + self.bearer,
            "Accept" : "application/json"
        ]
        AF.request(cleanPath, method: .post, parameters: request.toJSON(),
                   encoding: JSONEncoding.default, headers: headers).validate().responseJSON { (response: AFDataResponse<Any>) in
                    let value = response.value;
                    switch response.result {
                    case .success:
                        if let dict : Dictionary = value as? Dictionary<String,String> {
                            onResult(dict.description)
                            print("Sucesso")
                        }
                    case .failure(let error):
                        onError(error)
                    }
        }

        /*Alamofire.request(cleanPath, method: .post, parameters: request.toJSON(), encoding: JSONEncoding.default, headers: headers).validate().responseObject { (response: DataResponse<AuthorizationResponse>) in

            switch response.result {
            case .success:
                onResult(response.result.value?.authorization)
            case .failure(let error):
                onError(error)
            }
        }*/
    }

    private func getServiceUrl(for mode: ElectronicIDMode, service: VideoID.VideoService) -> String {
        switch mode {
        case .unattended, .attended:
            return getDefaultServiceUrl(for: service)
        case .videoScan:
            return getDefaultServiceUrl(for: .videoScan)
        }
    }

    private func getDefaultServiceUrl(for service: VideoID.VideoService) -> String {
        let videoIdRequest = "/videoid.request"
        let smileIdRequest = "/smileid.request"
        let videoScanRequest = "/videoscan.request"

        switch service {
        case .videoID:
            return videoIdRequest
        case .smileID:
            return smileIdRequest
        case .videoScan:
            return videoScanRequest
        }
    }

    private func getProcess(for mode: ElectronicIDMode) -> VideoID.Process {
        switch mode {
        case .unattended:
            return .unattended
        case .attended:
            return .attended
        case .videoScan:
            return .unattended
        }
    }

    private func createViewController(for mode: ElectronicIDMode, environment: Environment, language: String, docType: Int) -> UIViewController {
        switch mode {
        case .unattended, .attended, .videoScan:
            let viewController = VideoIDViewController(environment: environment, language: language, docType: docType)
            viewController.delegate = self
            return viewController
        }
    }

    private class AuthorizationRequest: Mappable {

        var tenantId: String?
        var process: VideoID.Process?
        var rauthorityId: String?

        init(tenantId: String?, process: VideoID.Process?, rauthorityId: String) {
            self.tenantId = tenantId
            self.process = process
            self.rauthorityId = rauthorityId
        }

        required init?(map: Map) {}

        public func mapping(map: Map) {
            tenantId <- map["tenantId"]
            process <- map["process"]
            rauthorityId <- map["rauthorityId"]
        }
    }

    private class AuthorizationResponse: Mappable {

        var id: String?
        var authorization: String?

        required init?(map: Map) {}

        public func mapping(map: Map) {
            id <- map["id"]
            authorization <- map["authorization"]
        }
    }
}

extension ElectronicIDWrapper: VideoDelegate {
    func onComplete(videoID: String) {
        self.success(videoID)
    }

    func onError(code: String, message: String?) {
        self.failure(code, message)
    }
}
