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
    
    var body: some View {
        VStack {
            Button(action: {
                selectCountry(country: Country(name: Country.Name.unitedkingdom))
            }){
                VStack {
                    Image("uk_flag")
                    Text(String(describing: Country.Name.unitedkingdom))
                }
            }.padding()

            Button(action: {
                selectCountry(country: Country(name: Country.Name.spain))
            }){
                Image("spain_flag")
                Text(String(describing: Country.Name.spain))
            }.padding()
                        
            Button(action: {
                selectCountry(country: Country(name: Country.Name.ireland))
            }){
                Image("ireland_flag")
                Text(String(describing: Country.Name.ireland))
            }.padding()
            
            Button(action: {
                selectCountry(country: Country(name: Country.Name.slovenia))
            }){
                Image("slovenia_flag")
                Text(String(describing: Country.Name.slovenia))
            }.padding()
            
            Button(action: {
                selectCountry(country: Country(name: Country.Name.finland))
            }){
                Image("finland_flag")
                Text(String(describing: Country.Name.finland))
            }.padding()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}

func selectCountry(country: Country) {
    print("country is " + String(describing: country.name))
}
