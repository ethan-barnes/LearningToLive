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
    @State var myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.dailylife)
    
    var body: some View {
        VStack {
            //Text(String(describing: country.name))
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.dailylife)
                setCategoriesFirebase(country: country, category: "life", ref: "headings")
                willMoveToNextScreen.toggle()
            }){
                Text("Daily Life")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.health)
                setCategoriesFirebase(country: country, category: "health", ref: "headings")
                willMoveToNextScreen.toggle()
            }){
                Text("Health and Well-Being")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.settlingin)
                setCategoriesFirebase(country: country, category: "settling", ref: "headings")
                willMoveToNextScreen.toggle()
            }){
                Text("Settling In")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.migrantstatus)
                setCategoriesFirebase(country: country, category: "migrant", ref: "headings")
                willMoveToNextScreen.toggle()
            }){
                Text("Migrant Status")
            }.padding()
            
            Button(action: {
                myCategory = shared.CategoryShared(n: shared.CategoryShared.Name.language)
                setCategoriesFirebase(country: country, category: "language", ref: "headings")
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
    
    func setCategoriesFirebase(country: String, category: String, ref: String) {
        let path = country + "/" + category.lowercased() + "/" + ref
        print("Path: " + path)
        var fbRef: DatabaseReference!
        fbRef = Database.database().reference(withPath: path)
        fbRef.observe(DataEventType.value, with: { snapshot in
            // print(snapshot.value as Any)
            var headings: [String] = []
            var references: [String] = []
            for child in snapshot.children {
                let snap = child as! DataSnapshot
                let val = snap.value as! [String: String?]

                let id = val["id"]!!
                let name = val["name"]!!
                //print("ID: \(id) , NAME: \(name)")
                headings.append(name)
                references.append(id)
                
            }
            print(references)
        })
    }
    
}


//struct CountryView_Previews: PreviewProvider {
//    static var previews: some View {
//        CountryView(country: mainCountry)
//    }
//}
