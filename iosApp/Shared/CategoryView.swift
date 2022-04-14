//
//  CategoryView.swift
//  iosApp
//
//  Created by Ethan Barnes on 28/03/2022.
//

import SwiftUI
import shared
import FirebaseDatabase

struct CategoryView: View {
    @Environment(\.openURL) var openURL
    var category: String
    var country: String
    
    @State private var myHeadings: [MenuItem] = [MenuItem(name: "No Content Found")]
    @State private var links = [String : String]()
    
    var body: some View {
        VStack {
            Text("").onAppear(perform: setCategoriesFirebase)
            
            List(myHeadings, children: \.subMenuItems) { item in
                HStack {
                    Button(action: {
                        if(item.subMenuItems == nil) { // If we're clicking on a submenuitem
                            if (links[item.name] != nil) {                                
                                let url = links[item.name]!
                                openURL(URL(string: url)!)
                            }
                        }
                    }){
                        Text(item.name)
                    }.padding()
                }
            }
            .listStyle(.plain)
        }
    }
    
    private func setCategoriesFirebase() {
        let path = country + "/" + category.lowercased() + "/" + "headings"
        var fbRef: DatabaseReference!
        fbRef = Database.database().reference(withPath: path)
        fbRef.observe(DataEventType.value, with: { snapshot in
            var headings: [String] = []
            var references: [String] = []
            for child in snapshot.children {
                let snap = child as! DataSnapshot
                let val = snap.value as! [String: String?]

                let id = val["id"]!!
                let name = val["name"]!!
                
                headings.append(name)
                references.append(id)
                
            }
            makeListArray(refs: references, names: headings, country: country, category: category.lowercased())
        })
    }
    
    private func makeListArray(refs: [String], names: [String], country: String, category: String) {
        var headings: [MenuItem] = []
        
        
        var fbRef: DatabaseReference!
        for i in 0...refs.count-1 {
            var entries: [MenuItem] = []
            
            let path = country + "/" + category.lowercased() + "/" + refs[i]
            fbRef = Database.database().reference(withPath: path)
            fbRef.observe(DataEventType.value, with: { snapshot in
                
                for child in snapshot.children {
                    let snap = child as! DataSnapshot
                    let key = snap.key
                    let value = snap.value!

                    links[key] = value as? String
                    entries.append(MenuItem(name: key))
                }
                
                if(!entries.isEmpty) {
                    headings.append(MenuItem(name: names[i], subMenuItems: entries))
                    setHeadings(headings: headings)
                }
            })
        }
    }
    
    private func setHeadings(headings: [MenuItem]) {
        myHeadings = headings
    }
}



//struct CategoryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CategoryView()
//    }
//}
