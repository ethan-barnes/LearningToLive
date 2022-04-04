//
//  LearningToLiveIOSApp.swift
//  Shared
//
//  Created by Ethan Barnes on 14/03/2022.
//

import SwiftUI
import Firebase

@main
struct iosApp: App {
    
    init() {
        FirebaseApp.configure()
      }
    
    var body: some Scene {
        WindowGroup {
            MainView()
        }
    }
}
