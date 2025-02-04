package com.basta.guessemoji.navigation

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val name: String
    val arguments: List<NamedNavArgument>
}

object Directions {
    val menu = object : NavigationCommand {
        override val name: String
            get() = "menu"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
//                listOf(
//                navArgument("type") {
//                    type = NavType.StringType
//                }
//            )
    }
    val earn = object : NavigationCommand {
        override val name: String
            get() = "earn"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
    val settings = object : NavigationCommand {
        override val name: String
            get() = "settings"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
    val home = object : NavigationCommand {
        override val name: String
            get() = "home"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
    val game1 = object : NavigationCommand {
        override val name: String
            get() = "game1"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
    val game2 = object : NavigationCommand {
        override val name: String
            get() = "game2"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
    val profile = object : NavigationCommand {
        override val name: String
            get() = "profile"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
}