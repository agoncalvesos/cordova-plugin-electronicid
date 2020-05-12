const fs = require('fs');
const path = require('path');

module.exports = function(context) {

    console.log(context);

    var platformRoot = path.join(context.opts.projectRoot, 'platforms/ios');
    var podFile = path.join(platformRoot, 'Podfile');
    
    console.log("platformRoot: " + platformRoot);

    console.log("=========> Executing hook iOSBeforeInstall.js  <=========");

    console.log("Checking Cordova Podfile");

    console.log("Path do Podfile: " + podFile); 
    if (fs.existsSync(podFile)) {
      console.log("Podfile exists, updating...");
      fs.readFile(podFile, 'utf8', function (err,data) {
        console.log("Data: "+data);
        if (err) {
          throw new Error('Unable to find Podfile: ' + err);
        }
          //data = 'ENV[\'SWIFT_VERSION\'] = \'5\'' + data;

          if (!data.includes("use_frameworks!")){
            data = data.replace(/ do/g, ' do\nuse_frameworks!');
          } 

          if (!data.includes("VideoID")){
              data = data.replace(/end/g, 'pod \'VideoID\', \'7.1.0\'\nend');
            } 

            var result = data;

          console.log("Podfile DEPOIS: \n"+result);

          //console.log("=========> Editing Podfile on iOS <=========");

          fs.writeFile(podFile, result, 'utf8', function (err) {
            if (err) throw new Error('Unable to write into Podfile ' + err);
          });
      });
    } else {
        //create the file
    }
  }