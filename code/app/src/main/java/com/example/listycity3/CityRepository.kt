package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _provinces = mutableStateListOf<Province>(
        Province("AB"),
        Province("BC"),
        Province("ON")
    )

    // My two-dimensional array solution messes with the simple instantiation used before.
    init {
        _provinces[_provinces.indexOf(Province("AB"))].addCity(City("Edmonton"))
        _provinces[_provinces.indexOf(Province("BC"))].addCity(City("Vancouver"))
        _provinces[_provinces.indexOf(Province("ON"))].addCity(City("Toronto"))
    }

    val cities: List<Province>
        get() = _provinces

    private fun getProvIdx (provinceName: String): Int {
        var idxProv: Int = _provinces.indexOf(Province(provinceName))
        if (idxProv < 0) {
            _provinces.add(Province(provinceName))
            idxProv = _provinces.indexOf(Province(provinceName))
        }
        return idxProv
    }
    fun addCity(city: String, province: String) {
        // It is possible to add a city to a province that doesn't exist.
        _provinces[getProvIdx(province)].addCity(City(city))
    }

    fun removeCity(city: City, province: String) {
        //
        _provinces[getProvIdx(province)].removeCity(city)
    }

    fun updateCityProvince(oldCity: City, newCityName: String, oldProvinceName: String, newProvinceName: String) {
        /* It is possible for both the province and city names to change.
         */
        // First we remove the oldCity, this will be true regardless of whether we are reusing the province.
        removeCity(oldCity, oldProvinceName)
        // Second we add our new city to whichever province it belongs to.
        addCity(newCityName, newProvinceName)
    }
}
