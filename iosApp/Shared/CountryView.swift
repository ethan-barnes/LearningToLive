//
//  CountryView.swift
//  iosApp
//
//  Created by Ethan Barnes on 21/03/2022.
//

import SwiftUI
import shared


struct CountryView: View {
    var country: Country
    
    @State private var willMoveToNextScreen = false
    @State var myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.dailylife)
    
    var body: some View {
        VStack {
            Text(String(describing: country.name))
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.dailylife)
                willMoveToNextScreen.toggle()
            }){
                Text("Daily Life")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.health)
                willMoveToNextScreen.toggle()
            }){
                Text("Health and Well-Being")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.settlingin)
                willMoveToNextScreen.toggle()
            }){
                Text("Settling In")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.migrantstatus)
                willMoveToNextScreen.toggle()
            }){
                Text("Migrant Status")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.language)
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
        .navigate(to: CategoryView(category: myCategory), when: $willMoveToNextScreen)
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
