import Foundation


@objc(AnyConnectClient) class AnyConnectClient : CDVPlugin {

   //this function will validate if a certificate is installed within the app file structure or in the keychain of the device
   @objc(launchanyconnect:)
   func launchanyconnect(_ command: CDVInvokedUrlCommand) {

    //get the vpn details
    let address = ((command.argument(at: 0) as! [String:Any])["url"] as! String)
    let userName = ((command.argument(at: 0) as! [String:Any])["username"] as! String)
    let userPassword = ((command.argument(at: 0) as! [String:Any])["password"] as! String)
    print("Url Received - " + address)
    print("Username Received - " + userName)
    print("Password Received - " + userPassword)

    //init the url and launch anyconnect
    let url = URL(string: "anyconnect://connect?host=" + address + "&prefill_username=" + userName + "&prefill_password=" + userPassword)
    if #available(iOS 10.0, *) {
        UIApplication.shared.open(url!, options: [:], completionHandler: nil);
    }
    else {
        UIApplication.shared.openURL(url!)
    }
    print("AnyConnect launched")

     //init plugin result
    let response: Dictionary = ["success" : true] as [String : Any]
     let pluginResult = CDVPluginResult(
         status: CDVCommandStatus_OK,
         messageAs: response
     )

     //send the callback object back
     print("Sending back cordova response")
     self.commandDelegate!.send(
       pluginResult,
       callbackId: command.callbackId
     )
   }
}
