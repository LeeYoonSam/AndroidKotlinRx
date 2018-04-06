package com.ys.rx.example

import android.content.Context
import kotlin.collections.ArrayList


class RestClient(private val mContext: Context) {

    fun getFavoriteTvShows(): List<String> {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return createTvShowList()
    }

    fun getFavoriteTvShowsWithException(): List<String> {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        throw RuntimeException("Failed to load")
    }

    private fun createTvShowList(): List<String> {
        val tvShows = ArrayList<String>()
        tvShows.add("The Joy of Painting")
        tvShows.add("The Simpsons")
        tvShows.add("Futurama")
        tvShows.add("Rick & Morty")
        tvShows.add("The X-Files")
        tvShows.add("Star Trek: The Next Generation")
        tvShows.add("Archer")
        tvShows.add("30 Rock")
        tvShows.add("Bob's Burgers")
        tvShows.add("Breaking Bad")
        tvShows.add("Parks and Recreation")
        tvShows.add("House of Cards")
        tvShows.add("Game of Thrones")
        tvShows.add("Law And Order")
        return tvShows
    }

    fun searchForCity(searchString: String): List<String> {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return getMatchingCities(searchString)
    }

    private fun getMatchingCities(searchString: String): List<String> {
        if (searchString.isEmpty()) {
            return ArrayList()
        }

        val cities = mContext.resources.getStringArray(R.array.city_list)
        return cities.filter { city -> city.toLowerCase().startsWith(searchString.toLowerCase()) }.toList<String?>() as List<String>
    }
}