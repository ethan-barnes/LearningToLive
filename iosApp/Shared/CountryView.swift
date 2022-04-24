//
//  CountryView.swift
//  iosApp
//
//  Created by Ethan Barnes on 21/03/2022.
//

import SwiftUI
import shared
import FirebaseDatabase

struct CountryView: View {
    @Environment(\.openURL) var openURL
    var country: String
    
    @State private var willMoveToNextScreen = false
    @State private var myCategory = ""
    
    var body: some View {
        VStack {
            NavigationLink(destination: CategoryView(category: "life", country: country)) {
                Label("Daily Life", systemImage: "")
            }.padding()
            NavigationLink(destination: CategoryView(category: "health", country: country)) {
                Label("Health and Well-Being", systemImage: "")
            }.padding()
            NavigationLink(destination: CategoryView(category: "settling", country: country)) {
                Label("Settling In", systemImage: "")
            }.padding()
            NavigationLink(destination: CategoryView(category: "migrant", country: country)) {
                Label("Migrant Status", systemImage: "")
            }.padding()
            NavigationLink(destination: CategoryView(category: "language", country: country)) {
                Label("LanguAage and Study", systemImage: "")
            }.padding()
            
            Button(action: {
                openURL(URL(string: "https://forms.office.com/Pages/ResponsePage.aspx?id=0aJeJGVDJkeAWmMeuT2Tqu8X0dAa6ktFvDvIrgRWdjFUREoxNlNaQ0JCT0dJQVg1MVcxWVJMT1Q1My4u")!)
            }){
                Text("Rate this app")
            }.padding()
        }
    }
}
