package com.basta.guessemoji.data.local

import androidx.compose.ui.graphics.Color
import com.basta.guessemoji.domain.model.GameEntry

object GameColor {
    val singleColorList = listOf<GameEntry>(
        GameEntry(
            colors = listOf(Color.Yellow),
            characters = listOf(
                "🐥",   // Baby Chick
                "🧀",   // Cheese Wedge
                "🌼",   // Blossom
                "🌟",   // Glowing Star
                "🍌",   // Banana
                "🌤️",   // Sun Behind Small Cloud
                "🐝",   // Honeybee
                "🍯",   // Honey Pot
            ),
        ),
        GameEntry(
            colors = listOf(Color.Red),
            characters = listOf(
                "🍒",   // Cherries
                "🌹",   // Rose
                "🍓",   // Strawberry
                "🎈",   // Balloon
                "🍅",   // Tomato
                "🚩",   // Triangular Flag
                "❤️",   // Red Heart
                "📌",   // Pushpin
                "🎀",   // Ribbon
                "👠",   // High-Heeled Shoe
                "🥩",   // Cut of Meat
                "🧨",   // Firecracker
                "🏮",   // Red Lantern
                "🦐",   // Shrimp
                "🦩",   // Flamingo
                "💔",   // Broken Heart
            ),
        ),
        GameEntry(
            colors = listOf(Color.Blue),
            characters = listOf(
                "🐳",   // Whale
                "🐋",   // Whale
                "🐟",   // Fish
                "🐬",   // Dolphin
                "🦋",   // Butterfly
                "🌀",   // Cyclone
                "🌊",   // Water Wave
                "💧",   // Droplet
                "🌐",   // Globe with Meridians
                "🧊",   // Ice Cube
                "💎",   // Gem Stone
                "🪣",   // Bucket ?
                "💦",   // Sweat Droplets
                "🧿",   // Nazar Amulet (blue)
                "👖",   // Jeans
                "🥶",   // Cold Face

            ),
        ),
        GameEntry(
            colors = listOf(Color.Green),
            characters = listOf(
                "🐢",  // Turtle
                "🐊",  // Crocodile
                "🐍",  // Snake
                "🦖",  // T-Rex
                "🌵",  // Cactus
                "🥝",  // Kiwi
                "🥒",  // Cucumber
                "🥬",  // Leafy Green
                "🥦",  // Broccoli
                "🌿",  // Herb
                "🍀",  // Four Leaf Clover
                "🌲",  // Evergreen Tree
                "🌳",  // Deciduous Tree
                "🪴",  // Potted Plant
                "🦎",  // Lizard
                "🦖",  // T-Rex
                "🧩",  // puzzle
                "🦠",   // Microbe
                "🪲",  // Beetle
                "🫛",   // Peas
                "🌱",   // Seedling
                "🍃",   // Leaf
                "🐉",   // dragon
            ),
        ),
    )
    val excludedColorList = listOf<GameEntry>( // all emoji which dont have yellow, ..
        GameEntry(
            colors = listOf(Color.Yellow),
            characters = listOf(

            ),
        ),
        GameEntry(
            colors = listOf(Color.Red),
            characters = listOf(

            ),
        ),
        GameEntry(
            colors = listOf(Color.Blue),
            characters = listOf(
                "👑",   // Crown
                "🚦",   // Traffic Light
            ),
        ),
        GameEntry(
            colors = listOf(Color.Green),
            characters = listOf(

            ),
        ),
    )
}