//
//  ElectronicID.swift
//  Created by Saul Fernandez Candela on 16/04/2020.
//

import Foundation
import VideoID

@objc(CordovaElectronicId) class CordovaElectronicId: CDVPlugin {
    var eIDWrapper: ElectronicIDWrapper?
    @objc(launchElectronicIdProcess:)
    func launchElectronicIdProcess(command: CDVInvokedUrlCommand) {
        var pluginResult = CDVPluginResult()
        
        // Getting args
        let params = command.arguments[0] as! NSDictionary
        let bearer = params.value(forKey: "bearer") as? String ?? ""
        let rauthority = params.value(forKey: "rAuthority") as? String ?? ""
        let endpoint = params.value(forKey: "endpoint") as? String ?? ""
        let videomode = params.value(forKey: "videoMode") as? String ?? ""
        let videoservice = params.value(forKey: "videoService") as? String ?? ""
        let language = params.value(forKey: "language") as? String ?? "es"
        let doctype = params.value(forKey: "documentType") as? Int ?? 62
        let dic = Bundle.main.object(forInfoDictionaryKey: "eIDCustomization") as! Dictionary<String, AnyObject>
        
        // Styling
        setCustomization(dic)
        
        eIDWrapper = ElectronicIDWrapper(bearer: bearer, rAuthority: rauthority, endpoint: endpoint, present: {
            (uiVC: UIViewController) -> Void in
            self.viewController!.present(uiVC, animated: true, completion: nil)
        }, success: {
            (success: String) -> Void in
            pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: success)
            self.commandDelegate!.send(pluginResult, callbackId: command.callbackId)
        }, failure: {
            (error: String, verbose: String?) -> Void in
            pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: error)
            self.commandDelegate!.send(pluginResult, callbackId: command.callbackId)
        })
        
        eIDWrapper?.startAuthentication(language: language, docType: doctype, mode: stringToMode(mode: videomode.lowercased()), service: stringToService(mode: videoservice.lowercased()))
    }
    
    private func stringToMode(mode: String) -> ElectronicIDMode {
        if mode == "attended" {
            return .attended
        } else if mode == "videoscan" {
            return .videoScan
        } else { return .unattended }
    }
    
    private func stringToService(mode: String) -> VideoID.VideoService {
        if mode == "videoid" {
            return .videoID
        } else if mode == "smileid" {
            return .smileID
        } else { return .videoScan }
    }

    private func stringToFontStyle(style: String) -> Style.FontStyle {
        if style.lowercased() == "italic" {
            return .Italic
        } else if style.lowercased() == "bold" {
            return .Bold
        } else if style.lowercased() == "boldanditalic" {
            return .BoldAndItalic
        } else { return .Normal }
    }

    
    private func setCustomization(_ dic: Dictionary<String, AnyObject>) {
        VideoID.Style.Detection.showDocumentDetection = dic["DetectionView"]!["showDocumentDetection"] as! Bool
        VideoID.Style.Detection.showFaceDetection = dic["DetectionView"]!["showFaceDetection"] as! Bool
        VideoID.Style.Detection.documentLinesColor = UIColor(hexString: dic["DetectionView"]!["documentLinesColor"] as! String)
        VideoID.Style.Detection.documentLinesWidth = dic["DetectionView"]!["documentLinesWidth"] as! CGFloat
        VideoID.Style.Detection.documentBackgroundColor = UIColor(hexString: dic["DetectionView"]!["documentBackgroundColor"] as! String)
        VideoID.Style.Detection.faceLineWidth = dic["DetectionView"]!["faceLineWidth"] as! CGFloat
        VideoID.Style.Detection.faceLinesColor = UIColor(hexString: dic["DetectionView"]!["faceStrokeColor"] as! String)
        
        VideoID.Style.Roi.show = dic["RoiView"]!["show"] as! Bool
        VideoID.Style.Roi.backgroundColor = UIColor(hexString: dic["RoiView"]!["backgroundColor"] as! String)
        VideoID.Style.Roi.linesColor = UIColor(hexString: dic["RoiView"]!["linesColor"] as! String)
        VideoID.Style.Roi.linesColorSuccess = UIColor(hexString: dic["RoiView"]!["linesColorSuccess"] as! String)
        VideoID.Style.Roi.linesWidth = dic["RoiView"]!["linesWidth"] as! CGFloat
        
        VideoID.Style.Tick.show = dic["TickView"]!["show"] as! Bool
        VideoID.Style.Tick.linesColor = UIColor(hexString: dic["TickView"]!["linesColor"] as! String)
        VideoID.Style.Tick.linesWidth = dic["TickView"]!["linesWidth"] as! CGFloat
        VideoID.Style.Tick.durationTime = dic["TickView"]!["durationTime"] as! Double

        VideoID.Style.Audio.backgroundColor = UIColor(hexString: dic["AudioButtonView"]!["backgroundColor"] as! String)
        
        VideoID.Style.Notification.show = dic["NotificationView"]!["show"] as! Bool
        VideoID.Style.Notification.textColor = UIColor(hexString: dic["NotificationView"]!["textColor"] as! String)
        VideoID.Style.Notification.backgroundColor = UIColor(hexString: dic["NotificationView"]!["backgroundColor"] as! String)
        VideoID.Style.Notification.backgroundColorWarningColor = UIColor(hexString: dic["NotificationView"]!["backgroundColorWarningColor"] as! String)
        VideoID.Style.Notification.textSize = dic["NotificationView"]!["textSize"] as! CGFloat
        VideoID.Style.Notification.fullScreen = dic["NotificationView"]!["fullScreen"] as! Bool
        if (dic["NotificationView"]!["textFont"] as! String != "nil") {
            VideoID.Style.Notification.textFont = UIFont(name: dic["NotificationView"]!["textFont"] as! String, size: dic["NotificationView"]!["textSize"] as! CGFloat)
        }
        VideoID.Style.Notification.textFontStyle = stringToFontStyle(style: dic["NotificationView"]!["textFontStyle"] as! String)
        
        VideoID.Style.Waiting.show = dic["WaitingView"]!["show"] as! Bool
        VideoID.Style.Waiting.backgroundColor = UIColor(hexString: dic["WaitingView"]!["backgroundColor"] as! String)
        VideoID.Style.Waiting.textColor = UIColor(hexString: dic["WaitingView"]!["textColor"] as! String)
        VideoID.Style.Waiting.textSize = dic["WaitingView"]!["textSize"] as! CGFloat
        if (dic["WaitingView"]!["textFont"] as! String != "nil") {
            VideoID.Style.Waiting.textFont = UIFont(name: dic["WaitingView"]!["textFont"] as! String, size: dic["WaitingView"]!["textSize"] as! CGFloat)
        }
        VideoID.Style.Notification.textFontStyle = stringToFontStyle(style: dic["WaitingView"]!["textFontStyle"] as! String)
        
        VideoID.Style.Navigation.show = dic["BackButton"]!["show"] as! Bool
        VideoID.Style.Navigation.textColor = UIColor(hexString: dic["BackButton"]!["textColor"] as! String)
        
        VideoID.Style.Preview.backgroundColor = UIColor(hexString: dic["PreviewView"]!["backgroundColor"] as! String)
        
        VideoID.Style.MultimediaNotification.show = dic["MultimediaNotification"]!["show"] as! Bool
        VideoID.Style.MultimediaNotification.titleColor = UIColor(hexString: dic["MultimediaNotification"]!["titleColor"] as! String)
        if (dic["MultimediaNotification"]!["titleFont"] as! String != "nil") {
            VideoID.Style.Notification.textFont = UIFont(name: dic["MultimediaNotification"]!["titleFont"] as! String, size: dic["MultimediaNotification"]!["titleTextSize"] as! CGFloat)
        }
        VideoID.Style.MultimediaNotification.titleTextSize = dic["MultimediaNotification"]!["titleTextSize"] as! CGFloat
        VideoID.Style.MultimediaNotification.messageColor = UIColor(hexString: dic["MultimediaNotification"]!["messageColor"] as! String)
        if (dic["MultimediaNotification"]!["messageFont"] as! String != "nil") {
            VideoID.Style.MultimediaNotification.messageFont = UIFont(name: dic["MultimediaNotification"]!["messageFont"] as! String, size: dic["MultimediaNotification"]!["messageTextSize"] as! CGFloat)
        }
        VideoID.Style.MultimediaNotification.messageTextSize = dic["MultimediaNotification"]!["messageTextSize"] as! CGFloat
        VideoID.Style.MultimediaNotification.inputColor = UIColor(hexString: dic["MultimediaNotification"]!["inputColor"] as! String)
        if (dic["MultimediaNotification"]!["inputFont"] as! String != "nil") {
            VideoID.Style.MultimediaNotification.inputFont = UIFont(name: dic["MultimediaNotification"]!["inputFont"] as! String, size: dic["MultimediaNotification"]!["inputTextSize"] as! CGFloat)
        }
        VideoID.Style.MultimediaNotification.inputTextSize = dic["MultimediaNotification"]!["inputTextSize"] as! CGFloat
        VideoID.Style.MultimediaNotification.buttonTextColor = UIColor(hexString: dic["MultimediaNotification"]!["buttonTextColor"] as! String)
        if (dic["MultimediaNotification"]!["buttonTextFont"] as! String != "nil") {
            VideoID.Style.MultimediaNotification.buttonTextFont = UIFont(name: dic["MultimediaNotification"]!["buttonTextFont"] as! String, size: dic["MultimediaNotification"]!["buttonTitleSize"] as! CGFloat)
        }
        VideoID.Style.MultimediaNotification.buttonTitleSize = dic["MultimediaNotification"]!["buttonTitleSize"] as! CGFloat
        VideoID.Style.MultimediaNotification.buttonBorderColor = UIColor(hexString: dic["MultimediaNotification"]!["buttonBorderColor"] as! String)
        VideoID.Style.MultimediaNotification.buttonBorderWidth = dic["MultimediaNotification"]!["buttonBorderWidth"] as! CGFloat
        VideoID.Style.MultimediaNotification.backgroundColor = UIColor(hexString: dic["MultimediaNotification"]!["backgroundColor"] as! String)
        
    }
}

extension UIColor {
    convenience init(hexString: String, alpha: CGFloat = 1.0) {
        let hexString: String = hexString.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
        let scanner = Scanner(string: hexString)
        if (hexString.hasPrefix("#")) {
            scanner.scanLocation = 1
        }
        var color: UInt32 = 0
        scanner.scanHexInt32(&color)
        let mask = 0x000000FF
        let r = Int(color >> 16) & mask
        let g = Int(color >> 8) & mask
        let b = Int(color) & mask
        let red   = CGFloat(r) / 255.0
        let green = CGFloat(g) / 255.0
        let blue  = CGFloat(b) / 255.0
        if (hexString == "clear") {
            self.init(red:red, green:green, blue:blue, alpha:0.0)
        } else {
            self.init(red:red, green:green, blue:blue, alpha:alpha)
        }
    }
}
