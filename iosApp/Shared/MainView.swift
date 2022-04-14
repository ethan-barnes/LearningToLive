//
//  ContentView.swift
//  Shared
//
//  Created by Ethan Barnes on 14/03/2022.
//
//  https://kotlinlang.org/docs/multiplatform-mobile-integrate-in-existing-app.html#use-the-shared-module-from-swift
import SwiftUI
import shared

struct MainView: View {
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            MainView()
        }
    }
    
    @State private var willMoveToNextScreen = false
    @State var myCountry = ""
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack {
                    NavigationLink(destination: CountryView(country: "united_kingdom")) {
                        VStack {
                            Image("uk_flag")
                            Label("United Kingdom", systemImage: "")
                        }
                    }
                    NavigationLink(destination: CountryView(country: "spain")) {
                        VStack {
                            Image("spain_flag")
                            Label("Spain", systemImage: "")
                        }
                    }
                    NavigationLink(destination: CountryView(country: "ireland")) {
                        VStack {
                            Image("ireland_flag")
                            Label("Ireland", systemImage: "")
                        }
                    }
                    NavigationLink(destination: CountryView(country: "slovenia")) {
                        VStack {
                            Image("slovenia_flag")
                            Label("Slovenia", systemImage: "")
                        }
                    }
                    NavigationLink(destination: CountryView(country: "finland")) {
                        VStack {
                            Image("finland_flag")
                            Label("Finland", systemImage: "")
                        }
                    }
                }
            }
        }
    }
}
