//
//  ExpandableList.swift
//  iosApp
//
//  Created by Ethan Barnes on 28/03/2022.
//

import Foundation

struct MenuItem: Identifiable {
    var id = UUID()
    var name: String
    var subMenuItems: [MenuItem]?
}

let sampleMenuItems = [ MenuItem(name: "Espresso Machines", subMenuItems: espressoMachineMenuItems),
                        MenuItem(name: "Grinders", subMenuItems: grinderMenuItems),
                        MenuItem(name: "Other Equipment", subMenuItems: otherMenuItems)
                    ]

// Sub-menu items for Espressco Machines
let espressoMachineMenuItems = [ MenuItem(name: "Leva", subMenuItems: [ MenuItem(name: "Leva X"), MenuItem(name: "Leva S") ]),
                                 MenuItem(name: "Strada", subMenuItems: [ MenuItem(name: "Strada EP"), MenuItem(name: "Strada AV"), MenuItem(name: "Strada MP"), MenuItem(name: "Strada EE") ]),
                                 MenuItem(name: "KB90"),
                                 MenuItem(name: "Linea", subMenuItems: [ MenuItem(name: "Linea PB X"), MenuItem(name: "Linea PB"), MenuItem(name: "Linea Classic") ]),
                                 MenuItem(name: "GB5"),
                                 MenuItem(name: "Home", subMenuItems: [ MenuItem(name: "GS3"), MenuItem(name: "Linea Mini") ])
                                ]

// Sub-menu items for Grinder
let grinderMenuItems = [ MenuItem(name: "Swift"),
                         MenuItem(name: "Vulcano"),
                         MenuItem(name: "Swift Mini"),
                         MenuItem(name: "Lux D")
                        ]

// Sub-menu items for other equipment
let otherMenuItems = [ MenuItem(name: "Espresso AV"),
                         MenuItem(name: "Espresso EP"),
                         MenuItem(name: "Pour Over"),
                         MenuItem(name: "Steam")
                        ]
