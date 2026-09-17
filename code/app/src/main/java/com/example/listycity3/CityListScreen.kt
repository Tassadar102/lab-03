package com.example.listycity3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.graphics.Color

@Composable
fun CityListScreen(
    provinces: List<Province>,
    modifier: Modifier = Modifier,
    onAddCity: (String, String) -> Unit,
    onUpdateCity: (City, String, String, String) -> Unit
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var showButtonSymbol by remember { mutableStateOf("+") }
    var showUpdateCityFields by remember { mutableStateOf(false) }
    var updateCityName by remember { mutableStateOf("") }
    var updateProvinceName by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf(City("")) } // Default to empty city.
    var selectedProvince by remember { mutableStateOf("") }

    if (showUpdateCityFields) showUpdateCityFields = false
    for (p in provinces) {
        if (p.cities.indexOfFirst { it === selectedCity } >= 0) {
            showUpdateCityFields = true
        }
    }

    Column(modifier = modifier.fillMaxHeight()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                    showButtonSymbol = if (showButtonSymbol == "+") "-" else "+"
                }
            ) {
                Text(showButtonSymbol)
            }
        }

        if (showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(newCityName, newProvinceName)
                            newCityName = ""
                            newProvinceName = ""
                            showButtonSymbol = "+"
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
        }

        if (showUpdateCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = updateCityName,
                    onValueChange = { updateCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = updateProvinceName,
                    onValueChange = { updateProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (updateCityName.isNotBlank() && updateProvinceName.isNotBlank()) {
                            onUpdateCity(selectedCity, updateCityName, selectedProvince, updateProvinceName)
                            updateCityName = ""
                            updateProvinceName = ""
                            selectedCity = City("")
                            selectedProvince = ""
                            showUpdateCityFields = false
                        }
                    }
                ) {
                    Text("Update City")
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(provinces) { idxProv, province ->
                province.cities.forEachIndexed { idxCity, city ->
                    CityRow(
                        city,
                        province,
                        selectedCity,
                        selectedProvince,
                        onSelected = { selectedCity = it },
                        updateSelectedProvince = {selectedProvince = it})
                    if (idxCity < province.cities.lastIndex) {
                        HorizontalDivider()
                    }
                }

                if (idxProv < provinces.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    province: Province,
    selectedCity: City,
    selectedProvince: String,
    onSelected: (City) -> Unit,
    updateSelectedProvince: (String) -> Unit) {
    //

    val selectedState = selectedCity === city
    val backgroundColor = if (selectedState) Color.LightGray else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .selectable(
            selected = selectedState,
            onClick = {
                onSelected(if (selectedState) City("") else city)
                updateSelectedProvince(if (selectedProvince == province.name) "" else province.name)
            })
    )
             {
                Text(
                    text = city.name,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .weight(1f)
                        .background(backgroundColor)
                )

                Text(
                    text = province.name,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .weight(1f)
                        .background(backgroundColor)
                )
            }
    //return selectedCity
}

/*@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
    CityListScreen(
        cities = listOf(
            City("Edmonton", "AB"),
            City("Vancouver", "BC"),
            City("Calgary", "AB")
            ),
            onAddCity = {}
        )
    }
}
*/