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

        /*if (data.includes("platform :ios, '11.0'")){
          data = data.replace("platform :ios, '11.0'", "platform :ios, '10.0'");
        } */

        /*data = "\n dynamic_frameworks = ['Alamofire'] \n" + 
              "# make all the other frameworks into static frameworks by overriding the static_framework? function to return true \n" + 
              "pre_install do |installer| \n" + 
              "   installer.pod_targets.each do |pod| \n" + 
              "     if !dynamic_frameworks.include?(pod.name) \n" + 
              "       puts \"Overriding the static_framework? method for #{pod.name}\" \n" + 
              "      def pod.static_framework?; \n" + 
              "        true \n" + 
              "      end \n" + 
              "     end \n" + 
              "   end \n" + 
              " end\n" + data;*/

          /*data= "\n $static_framework = ['VideoID'] \n" + 
                 "pre_install do |installer| \n"+
                 "installer.pod_targets.each do |pod| \n" +
                 "if $static_framework.include?(pod.name) \n" +
                 "puts \"Overriding the static_framework? method for #{pod.name}\" \n" + 
                 "def pod.build_as_static_framework?; \n" +
                 "true \n" +
                 "end \n"+
                "end \n"+
              "end \n"+ 
            "end \n" + data;*/

            /*data = "\n post_install do |installer| \n" + 
            "installer.pods_project.targets.each do |pod| \n" + 
              "if ['AlamofireImage'].include?(pod.name) \n" + 
                "pod.build_configurations.each do |config| \n" + 
                  "puts \"Overriding SWIFT_VERSION for #{pod.name}\" \n" + 
                  "config.build_settings['SWIFT_VERSION'] = '5.0' \n" +
                "end \n" + 
              "end \n" + 
            "end \n" +
          "end \n" + data;*/

          /*data = "\n workspace 'EverisTest.xcworkspace' \n pre_install do |installer| \n" + 
                 "installer.analysis_result.specifications.each do |s| \n" +
                 "puts \"Overriding SWIFT_VERSION for #{s.name}\" \n" + 
                 "s.swift_version = '5.0' unless s.swift_version \n" +
                 "end \n" + 
                 "end \n" + data;*/

            /*data+= "\n $static_framework = ['FLEX', 'CocoaLumberjack', 'PureeOS'] \n" + 
                   "pre_install do |installer| \n" + 
                   "installer.pod_targets.each do |pod| \n" + 
                   "if $static_framework.include?(pod.name) \n" + 
                   "puts \"Overriding the static_framework? method for #{pod.name}\" \n" + 
                   "def pod.build_type; \n" + 
                   "Pod::Target::BuildType.static_framework \n" +
                "end \n"+
              "end \n" +
            "end \n" + 
          "end";*/

          /*if (data.includes("VideoID")){
            console.log("inside includes");
            data = data.replace("pod \'VideoID\', \'7.0.11\'", "pod \'VideoID\', \'7.0.11\', :modular_headers => true");
          }*/

          //data = "use_modular_headers!\n" + data;

          /*data = 'ENV[\'SWIFT_VERSION\'] = \'5\'' + data;

          if (data.includes('pod \'FLEX\', :git => \'https://github.com/OutSystems/FLEX/\', :tag => \'3.0.0-OS3\', :configurations => [\'Debug\']')){
            var lines = data.split('\n');
            delete lines[6];
            data = lines.join('\n');
          }

          if (!data.includes("use_frameworks!")){
            data = data.replace(/ do/g, ' do\nuse_frameworks!');
          } 

          if (!data.includes("VideoID")){
              data = data.replace(/end/g, 'pod \'VideoID\', \'7.0.11\'\nend');
            } */

            /*data="pre_install do |installer| \n"+
                 "installer.analysis_result.specifications.each do |s| \n" + 
                 //"if s.name == 'AlamofireImage' \n" + 
                    "s.swift_version = '5' \n" + 
                 //"end \n" + 
              "end \n" + 
            "end \n" + data;*/

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
