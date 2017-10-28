var exec = require('cordova/exec');

//init exports
module.exports = {
  launchanyconnect : function(arg0, success, error) {
    console.log("Interfacing with AnyConnect");
    exec(success, error, 'AnyConnectClient', 'launchanyconnect', [arg0]);
  }
};
