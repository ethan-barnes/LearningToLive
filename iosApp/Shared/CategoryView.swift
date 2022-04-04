//
//  CategoryView.swift
//  iosApp
//
//  Created by Ethan Barnes on 28/03/2022.
//

import SwiftUI
import shared

struct CategoryView: View {
    var category: shared.CategoryShared
    var body: some View {
        VStack {
            List(sampleMenuItems, children: \.subMenuItems) { item in
                HStack {
                    Text(item.name)
                        .font(.system(.title3, design: .rounded))
                        .bold()
                }
            }
            .listStyle(.plain)
            
//            Button(action: {
//                getFbData(country: "united_kingdom", category: "health", ref: "headings")
//            }){
//                Text("Health and Well-Being")
//            }.padding()
        }
        
    }
}



//struct CategoryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CategoryView()
//    }
//}
