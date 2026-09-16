package com.example.listycity3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

data class City(var name: String)

data class Province(val name: String) {
    private val _cities = mutableStateListOf<City>()
    val cities: List<City> get() = _cities

    fun addCity(city: City) {
        _cities.add(city)
    }
    fun removeCity(oldCity: City) {
        _cities.removeIf { it === oldCity }
    }
}