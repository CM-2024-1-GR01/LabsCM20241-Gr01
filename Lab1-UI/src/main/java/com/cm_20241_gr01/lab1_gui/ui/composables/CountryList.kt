package com.cm_20241_gr01.lab1_gui.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cm_20241_gr01.lab1_gui.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

data class CountryData(
    val country: String,
    val cities: List<String>,
)

interface CountryApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("countries")
    suspend fun getAllCountries(): List<CountryData>
}

object CountryApiService {
    private const val BASE_URL = "https://apimocha.com/compumovil-lab-1.3/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val countryApi: CountryApi = retrofit.create(CountryApi::class.java)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDropdownLandscape(
    countryValue: String,
    cityValue: String,
    onChangeCountry: (String) -> Unit,
    onChangeCity: (String) -> Unit
) {
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var cities by remember { mutableStateOf<List<String>>(emptyList()) }
    var isCountryExpanded by remember { mutableStateOf(false) }
    var isCityExpanded by remember { mutableStateOf(false) }
    val colorPrimary = Color(0xFF000000)
    val colorSecundary = Color(0xFFF7D4E8)
    val labelCountry: String
    val labelCity: String
    if (countryValue.isEmpty()) {
        labelCountry = stringResource(id = R.string.country) + "*"
    } else {
        labelCountry = countryValue
    }
    if (cityValue.isEmpty()) {
        labelCity = stringResource(id = R.string.city)
    } else {
        labelCity = cityValue
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                countries = CountryApiService.countryApi.getAllCountries()
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
            }
        }
    }
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.country),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorPrimary
            )
            Spacer(modifier = Modifier.width(5.dp))
            ExposedDropdownMenuBox(
                expanded = isCountryExpanded,
                onExpandedChange = { newValue ->
                    isCountryExpanded = newValue
                }
            ) {
                TextField(
                    value = selectedCountry?.country ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryExpanded)
                    },
                    placeholder = {
                        Text(text = labelCountry)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorPrimary,
                        containerColor = colorSecundary,
                        focusedLabelColor = colorPrimary,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCountryExpanded,
                    onDismissRequest = {
                        isCountryExpanded = false
                    }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Text(country.country)
                            },
                            onClick = {
                                selectedCountry = country
                                isCountryExpanded = false
                                cities = country.cities
                                onChangeCountry(country.country)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.city),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorPrimary
            )
            Spacer(modifier = Modifier.width(5.dp))
            ExposedDropdownMenuBox(
                expanded = isCityExpanded,
                onExpandedChange = { newValue ->
                    isCityExpanded = newValue
                }
            ) {
                TextField(
                    value = selectedCity ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityExpanded)
                    },
                    placeholder = {
                        Text(text = labelCity)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorPrimary,
                        containerColor = colorSecundary,
                        focusedLabelColor = colorPrimary,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCityExpanded,
                    onDismissRequest = {
                        isCityExpanded = false
                    }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = {
                                Text(city)
                            },
                            onClick = {
                                selectedCity = city
                                isCityExpanded = false
                                onChangeCity(city)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDropdownPortrait(
    countryValue: String,
    cityValue: String,
    onChangeCountry: (String) -> Unit,
    onChangeCity: (String) -> Unit
) {
    var selectedCountry by rememberSaveable { mutableStateOf<CountryData?>(null) }
    var selectedCity by rememberSaveable { mutableStateOf<String?>(null) }
    var countries by rememberSaveable { mutableStateOf<List<CountryData>>(emptyList()) }
    var cities by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    var isCountryExpanded by rememberSaveable { mutableStateOf(false) }
    var isCityExpanded by rememberSaveable { mutableStateOf(false) }
    val colorPrimary = Color(0xFF000000)
    val colorSecundary = Color(0xFFF7D4E8)
    val labelCountry: String
    val labelCity: String
    if (countryValue.isEmpty()) {
        labelCountry = stringResource(id = R.string.country)+"*"
    } else {
        labelCountry = countryValue
    }
    if (cityValue.isEmpty()) {
        labelCity = stringResource(id = R.string.city)
    } else {
        labelCity = cityValue
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                countries = CountryApiService.countryApi.getAllCountries()
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
            }
        }
    }
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.country),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorPrimary
            )
            Spacer(modifier = Modifier.width(20.dp))

            ExposedDropdownMenuBox(
                expanded = isCountryExpanded,
                onExpandedChange = { newValue -> isCountryExpanded = newValue })
            {
                TextField(
                    value = selectedCountry?.country ?: "", onValueChange = {}, readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryExpanded) },
                    placeholder = { Text(text = labelCountry) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorPrimary,
                        containerColor = colorSecundary,
                        focusedLabelColor = colorPrimary,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(expanded = isCountryExpanded,
                    onDismissRequest = {
                        isCountryExpanded = false
                    }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Text(country.country)
                            },
                            onClick = {
                                selectedCountry = country
                                isCountryExpanded = false
                                cities = country.cities
                                onChangeCountry(country.country)
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los menús desplegables
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.city),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorPrimary
            )
            Spacer(modifier = Modifier.width(20.dp))
            ExposedDropdownMenuBox(
                expanded = isCityExpanded,
                onExpandedChange = { newValue ->
                    isCityExpanded = newValue
                }
            ) {
                TextField(
                    value = selectedCity ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityExpanded)
                    },
                    placeholder = {
                        Text(text = labelCity)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorPrimary,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorPrimary,
                        containerColor = colorSecundary,
                        focusedLabelColor = colorPrimary,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCityExpanded,
                    onDismissRequest = {
                        isCityExpanded = false
                    }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = {
                                Text(city)
                            },
                            onClick = {
                                selectedCity = city
                                isCityExpanded = false
                                onChangeCity(city)
                            }
                        )
                    }
                }
            }
        }
    }
}