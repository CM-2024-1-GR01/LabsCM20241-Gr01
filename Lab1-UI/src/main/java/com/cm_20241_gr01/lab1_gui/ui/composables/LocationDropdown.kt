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
import androidx.compose.ui.tooling.preview.Preview
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
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val countryApi: CountryApi = retrofit.create(CountryApi::class.java)
}

@Composable
fun LocationDropdownPortrait(
    countryValue: String,
    cityValue: String,
    onChangeCountry: (String) -> Unit,
    onChangeCity: (String) -> Unit
) {
    var countries by rememberSaveable { mutableStateOf<List<CountryData>>(emptyList()) }
    var cities by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    val colorPrimary = Color(0xFF000000)
    val colorSecondary = Color(0xFFF7D4E8)
    val labelCountry: String = countryValue.ifEmpty {
        stringResource(id = R.string.country) + "*"
    }
    val labelCity: String = cityValue.ifEmpty {
        stringResource(id = R.string.city)
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
            cities = countryDropdown(
                colorPrimary,
                labelCountry,
                colorSecondary,
                countries,
                onChangeCountry
            )
        }
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los menús desplegables
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            CityDropdown(
                colorPrimary,
                labelCity,
                colorSecondary,
                cities,
                onChangeCity
            )
        }
    }
}

@Composable
fun LocationDropdownLandscape(
    countryValue: String,
    cityValue: String,
    onChangeCountry: (String) -> Unit,
    onChangeCity: (String) -> Unit
) {
    var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var cities by remember { mutableStateOf<List<String>>(emptyList()) }
    val colorPrimary = Color(0xFF000000)
    val colorSecondary = Color(0xFFF7D4E8)
    val labelCountry: String = countryValue.ifEmpty {
        stringResource(id = R.string.country) + "*"
    }
    val labelCity: String = cityValue.ifEmpty {
        stringResource(id = R.string.city)
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
            cities = countryDropdown(
                colorPrimary,
                labelCountry,
                colorSecondary,
                countries,
                onChangeCountry
            )
            Spacer(modifier = Modifier.width(15.dp))
            CityDropdown(
                colorPrimary,
                labelCity,
                colorSecondary,
                cities,
                onChangeCity
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CityDropdown(
    colorPrimary: Color,
    labelCity: String,
    colorSecondary: Color,
    cities: List<String>,
    onChangeCity: (String) -> Unit
) {
    val selectedCity by remember { mutableStateOf<String?>(null) }
    val isCityExpanded by remember { mutableStateOf(false) }
    var isCityExpanded1 = isCityExpanded
    var selectedCity1 = selectedCity
    Icon(
        painter = painterResource(id = R.drawable.city),
        contentDescription = null,
        modifier = Modifier
            .padding(start = 15.dp)
            .size(35.dp),
        tint = colorPrimary
    )
    Spacer(modifier = Modifier.width(15.dp))
    ExposedDropdownMenuBox(
        expanded = isCityExpanded1,
        onExpandedChange = { newValue ->
            isCityExpanded1 = newValue
        }
    ) {
        TextField(
            value = selectedCity1 ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityExpanded1)
            },
            placeholder = {
                Text(text = labelCity)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = colorPrimary,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = colorPrimary,
                containerColor = colorSecondary,
                focusedLabelColor = colorPrimary,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isCityExpanded1,
            onDismissRequest = {
                isCityExpanded1 = false
            }
        ) {
            cities.forEach { city ->
                DropdownMenuItem(
                    text = {
                        Text(city)
                    },
                    onClick = {
                        selectedCity1 = city
                        isCityExpanded1 = false
                        onChangeCity(city)
                    }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun countryDropdown(
    colorPrimary: Color,
    labelCountry: String,
    colorSecondary: Color,
    countries: List<CountryData>,
    onChangeCountry: (String) -> Unit
) : List<String> {
    var isCountryExpanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    Icon(
        painter = painterResource(id = R.drawable.country),
        contentDescription = null,
        modifier = Modifier
            .padding(start = 15.dp)
            .size(35.dp),
        tint = colorPrimary
    )
    Spacer(modifier = Modifier.width(15.dp))
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
                containerColor = colorSecondary,
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
                        onChangeCountry(country.country)
                    }
                )
            }
        }
    }
    return selectedCountry?.cities ?: emptyList()
}

@Preview
@Composable
fun LocationDropdownPortraitPreview() {
    LocationDropdownPortrait(
        countryValue = "",
        cityValue = "",
        onChangeCountry = {},
        onChangeCity = {})
}

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun LocationDropdownLandscapePreview() {
    LocationDropdownLandscape(
        countryValue = "",
        cityValue = "",
        onChangeCountry = {},
        onChangeCity = {})
}