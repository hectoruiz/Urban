package com.urban.androidhomework

import retrofit2.Response

class Utils {
    companion object {
        fun getName(characterResponse: Response<Character>): List<String> {
            val names = mutableListOf<String>()
            characterResponse.body()?.let { characters ->
                for (character in characters.results) {
                    names.add(character.name)
                }
            }
            return names
        }
    }
}