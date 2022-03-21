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
        Text(String(describing: country.name))
    }
}

//struct CountryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CountryView(country: mainCountry)
//    }
//}
