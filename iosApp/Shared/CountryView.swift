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
    var country: String
    
    @State private var willMoveToNextScreen = false
    @State private var myCategory = ""
    
    var body: some View {
        VStack {
            Button(action: {
                myCategory = "life"
                willMoveToNextScreen.toggle()
            }){
                Text("Daily Life")
            }.padding()
            
            Button(action: {
                myCategory = "health"
                willMoveToNextScreen.toggle()
            }){
                Text("Health and Well-Being")
            }.padding()
            
            Button(action: {
                myCategory = "settling"
                willMoveToNextScreen.toggle()
            }){
                Text("Settling In")
            }.padding()
            
            Button(action: {
                myCategory = "migrant"
                willMoveToNextScreen.toggle()
            }){
                Text("Migrant Status")
            }.padding()
            
            Button(action: {
                myCategory = "language"
                willMoveToNextScreen.toggle()
            }){
                Text("Language and Study")
            }.padding()
            
            Button(action: {
                willMoveToNextScreen.toggle()
            }){
                Text("Contact Us")
            }.padding()
        }
        .navigate(to: CategoryView(category: myCategory, country: country), when: $willMoveToNextScreen)
    }
}

extension View {
    /// Navigate to a new view.
    /// - Parameters:
    ///   - view: View to navigate to.
    ///   - binding: Only navigates when this condition is `true`.
    func navigate<NewView: View>(to view: CategoryView, when binding: Binding<Bool>) -> some View {
        NavigationView {
            ZStack {
                self
                NavigationLink(destination: view, isActive: binding) {
                    EmptyView()
                }
            }
        }
        .navigationViewStyle(.stack)
    }
}


//struct CountryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CountryView(country: mainCountry)
//    }
//}
