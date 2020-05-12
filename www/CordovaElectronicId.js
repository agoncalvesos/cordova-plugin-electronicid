var exec = require('cordova/exec');

exports.launchElectronicIdProcess = function (arg0, success, error) {
    console.log("lanzando launchElectronicIdProcess");
    exec(success, error, 'CordovaElectronicId', 'launchElectronicIdProcess', [arg0]);
};
