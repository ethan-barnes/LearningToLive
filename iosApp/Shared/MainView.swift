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
    @State var myCountry = Country(name: Country.Name.unitedkingdom) // Placeholder country
    
    var body: some View {
        VStack {
            Button(action: {
                myCountry = Country(name: Country.Name.unitedkingdom)
                willMoveToNextScreen.toggle()
            }){
                VStack {
                    Image("uk_flag")
                    Text(String(describing: Country.Name.unitedkingdom))
                }
            }.padding()

            Button(action: {
                myCountry = Country(name: Country.Name.spain)
                willMoveToNextScreen.toggle()
            }){
                Image("spain_flag")
                Text(String(describing: Country.Name.spain))
            }.padding()

            Button(action: {
                myCountry = Country(name: Country.Name.ireland)
                willMoveToNextScreen.toggle()
            }){
                Image("ireland_flag")
                Text(String(describing: Country.Name.ireland))
            }.padding()

            Button(action: {
                myCountry = Country(name: Country.Name.slovenia)
                willMoveToNextScreen.toggle()
            }){
                Image("slovenia_flag")
                Text(String(describing: Country.Name.slovenia))
            }.padding()

            Button(action: {
                myCountry = Country(name: Country.Name.finland)
                willMoveToNextScreen.toggle()
            }){
                Image("finland_flag")
                Text(String(describing: Country.Name.finland))
            }.padding()
        }
        .navigate(to: CountryView(country: myCountry), when: $willMoveToNextScreen)
    }
}

extension View {
    /// Navigate to a new view.
    /// - Parameters:
    ///   - view: View to navigate to.
    ///   - binding: Only navigates when this condition is `true`.
    func navigate<NewView: View>(to view: NewView, when binding: Binding<Bool>) -> some View {
        NavigationView {
            ZStack {
                self
                    //.navigationBarTitle("")
                    //.navigationBarHidden(true)

                NavigationLink(
                    destination: view,
                        //.navigationBarTitle("")
                        //.navigationBarHidden(true),
                    isActive: binding
                ) {
                    EmptyView()
                }
            }
        }
        .navigationViewStyle(.stack)
    }
}
