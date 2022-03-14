//
//  ContentView.swift
//  Shared
//
//  Created by Ethan Barnes on 14/03/2022.
//
//  https://kotlinlang.org/docs/multiplatform-mobile-integrate-in-existing-app.html#use-the-shared-module-from-swift
import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        Text(/*String(describing: Country.Name.unitedkingdom)*/ "test")
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
