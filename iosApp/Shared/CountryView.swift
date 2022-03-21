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
    
    var body: some View {
        VStack {
            Text(String(describing: country.name))
            Button(action: {
                
            }){
                Text("Daily Life")
            }.padding()
            
            Button(action: {
                
            }){
                Text("Health and Well-Being")
            }.padding()
            
            Button(action: {
                
            }){
                Text("Settling In")
            }.padding()
            
            Button(action: {
                
            }){
                Text("Migrant Status")
            }.padding()
            
            Button(action: {
                
            }){
                Text("Language and Study")
            }.padding()
            
            Button(action: {
                
            }){
                Text("Contact Us")
            }.padding()
        }
    }
}

//struct CountryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CountryView(country: mainCountry)
//    }
//}
