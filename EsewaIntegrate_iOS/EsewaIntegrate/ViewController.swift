//
//  ViewController.swift
//  EsewaIntegrate
//
//  Created by Roshan on 7/25/19.
//  Copyright © 2019 KandK Tech Pvt Ltd. All rights reserved.
//

import UIKit
import EsewaSDK
import SwiftyJSON


class ViewController: UIViewController, EsewaSDKPaymentDelegate {
    
    let testButton = UIButton()
    
    var sdk: EsewaSDK!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addSubview(testButton)
        testButton.translatesAutoresizingMaskIntoConstraints = false
        testButton.backgroundColor = UIColor.darkGray
        testButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        testButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
        testButton.heightAnchor.constraint(equalToConstant: 60).isActive = true
        testButton.widthAnchor.constraint(equalToConstant: 180).isActive = true
        testButton.setTitle("Test SDK", for: .normal)
        testButton.addTarget(self, action: #selector(onTestButtonTap), for: .touchUpInside)
        
        sdk = EsewaSDK(inViewController: self, environment: .development, delegate: self)
    }
    
    @objc func onTestButtonTap() {
        
        sdk.initiatePayment(merchantId: "JB0BBQ4aD0UqIThFJwAKBgAXEUkEGQUBBAwdOgABHD4DChwUAB0R", merchantSecret: "BhwIWQQADhIYSxILExMcAgFXFhcOBwAKBgAXEQ==", productName: "Test Product", productAmount: "100", productId: "10", callbackUrl: "https://ir-user.esewa.com.np/epay/main")
    }
    
    func onEsewaSDKPaymentSuccess(info: [String : Any]) {
        
            let json = JSON(info)
        
            if  let productId = json["productID"].string,
                let totalAmount = json["totalAmount"].string, let message = json["message"]["successMessage"].string ,let referenceId = json["transactionDetails"]["referenceId"].string {
                print("ProductId : \(productId) \nTotalAmount: \(totalAmount) \nMessage : \(message) \nReferenceId: \(referenceId)")
            }
        
        
    }
    
    func onEsewaSDKPaymentError(errorDescription: String) {
        
        print("Failed")
    }
    
}


