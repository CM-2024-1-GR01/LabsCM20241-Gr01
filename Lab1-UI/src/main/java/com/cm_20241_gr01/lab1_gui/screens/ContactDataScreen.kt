package com.cm_20241_gr01.lab1_gui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R
import com.cm_20241_gr01.lab1_gui.ui.composables.InputField
import com.cm_20241_gr01.lab1_gui.ui.composables.LocationDropdownLandscape
import com.cm_20241_gr01.lab1_gui.ui.composables.LocationDropdownPortrait

@Composable
fun ContactDataScreen(viewModel: DataViewModel) {
    val dataState by viewModel.uiState.collectAsState()
    var validation by remember {
        mutableStateOf(false)
    }
    DisposableEffect(dataState.phone, dataState.email, dataState.country) {
        validation =
            dataState.phone.isNotEmpty() and dataState.email.isNotEmpty() and dataState.country.isNotEmpty()
        onDispose { }
    }
    val colorTittle = Color(0xFF000000)
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            ContactDataLandscape(viewModel, colorTittle)
        }

        else -> {
            ContactDataPortrait(viewModel, colorTittle)
        }
    }
}

@Composable
fun ContactDataLandscape(dataViewModel: DataViewModel, colorTittle: Color) {
    val dataState by dataViewModel.uiState.collectAsState()
    val screenTitle = stringResource(id = R.string.contact_data_title)
    val phone = stringResource(id = R.string.phone)
    val email = stringResource(id = R.string.email)
    val country = stringResource(id = R.string.country)
    val city = stringResource(id = R.string.city)
    val address = stringResource(id = R.string.address)
    val validation by remember {
        mutableStateOf(false)
    }
    val colorBackground = Color(0xFFFFFFFF)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = screenTitle,
            fontSize = 28.sp,
            color = colorTittle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            InputField(
                "$phone *",
                R.drawable.phone,
                KeyboardType.Number,
                KeyboardCapitalization.None,
                dataState.phone,
                onValueChange = { dataViewModel.setPhone(it) }
            )
            Spacer(modifier = Modifier.width(15.dp))
            InputField(
                "$email *",
                R.drawable.email,
                KeyboardType.Email,
                KeyboardCapitalization.None,
                dataState.email,
                onValueChange = { dataViewModel.setEmail(it) }
            )
        }
        LocationDropdownLandscape(
            dataState.country,
            dataState.city,
            onChangeCountry = { dataViewModel.setCountry(it) },
            onChangeCity = { dataViewModel.setCity(it) },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            InputField(
                address,
                R.drawable.address,
                KeyboardType.Text,
                KeyboardCapitalization.Sentences,
                dataState.address,
                onValueChange = { dataViewModel.setAddress(it) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.required_fields),
                fontSize = 11.sp,
                color = colorTittle,
                modifier = Modifier
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        )
        {
            val colorBack = Color(0xFF000000)
            val colorText = Color(0xFF000000)
            Button(
                enabled = validation,
                onClick = {
                    Log.i(
                        "", "${screenTitle.uppercase()}\n" +
                                "---------------------------------------\n" +
                                "$phone = ${dataState.phone}\n" +
                                "$email = ${dataState.email}\n" +
                                "$country = ${dataState.country}\n" +
                                if (dataState.city.isNotEmpty()) {
                                    "$city = ${dataState.city}\n"
                                } else {
                                    ""
                                } +
                                if (dataState.address.isNotEmpty()) {
                                    "$address = ${dataState.address}\n"
                                } else {
                                    ""
                                }
                    )
                },
                modifier = Modifier.padding(end = 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorBack)
            ) {
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(id = R.string.save_button),
                    color = colorText,
                )
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "ArrowForward",
                    tint = colorText
                )
            }
        }
    }
}


@Composable
fun ContactDataPortrait(infoViewModel: DataViewModel = viewModel(), colorTittle: Color) {
    val dataState by infoViewModel.uiState.collectAsState()
    val screenTitle = stringResource(id = R.string.contact_data_title)
    val phone = stringResource(id = R.string.phone)
    val email = stringResource(id = R.string.email)
    val country = stringResource(id = R.string.country)
    val city = stringResource(id = R.string.city)
    val address = stringResource(id = R.string.address)
    val validation by remember {
        mutableStateOf(false)
    }
    val colorBackground = Color(0xFFFFFFFF)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = screenTitle,
            fontSize = 28.sp,
            color = colorTittle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                "$phone*",
                R.drawable.phone,
                KeyboardType.Number,
                KeyboardCapitalization.None,
                dataState.phone,
                onValueChange = { infoViewModel.setPhone(it) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                "$email*",
                R.drawable.email,
                KeyboardType.Email,
                KeyboardCapitalization.None,
                dataState.email,
                onValueChange = { infoViewModel.setEmail(it) }
            )
        }
        Spacer(modifier = Modifier.width(20.dp))

        LocationDropdownPortrait(
            dataState.country,
            dataState.city,
            onChangeCountry = { infoViewModel.setCountry(it) },
            onChangeCity = { infoViewModel.setCity(it) },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputField(
                address,
                R.drawable.address,
                KeyboardType.Text,
                KeyboardCapitalization.Sentences,
                dataState.address,
                onValueChange = { infoViewModel.setAddress(it) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.required_fields),
                fontSize = 11.sp,
                color = colorTittle,
                modifier = Modifier
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(Modifier.weight(1f))
            val colorBack = Color(0xFF000000)
            val colorText = Color(0xFF000000)
            Button(
                enabled = validation,
                onClick = {
                    Log.i(
                        "DATA", "${screenTitle.uppercase()}\n" +
                                "---------------------------------------\n" +
                                "$phone = ${dataState.phone}\n" +
                                "$email = ${dataState.email}\n" +
                                "$country = ${dataState.country}\n" +
                                if (dataState.city.isNotEmpty()) {
                                    "$city = ${dataState.city}\n"
                                } else {
                                    ""
                                } +
                                if (dataState.address.isNotEmpty()) {
                                    "$address = ${dataState.address}\n"
                                } else {
                                    ""
                                }
                    )
                },
                modifier = Modifier.padding(30.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                colors = ButtonDefaults.buttonColors(containerColor = colorBack)
            ) {
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(id = R.string.save_button),
                    color = colorText,
                )
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "ArrowForward",
                    tint = colorText
                )
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun ContactDataPortraitPreview() {
    ContactDataScreen(viewModel())
}