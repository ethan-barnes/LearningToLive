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
        List(sampleMenuItems, children: \.subMenuItems) { item in
            HStack {
                Text(item.name)
                    .font(.system(.title3, design: .rounded))
                    .bold()
            }
        }.listStyle(.plain)
    }
}

//struct CategoryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CategoryView()
//    }
//}
