package com.cm_20241_gr01.lab1_gui.screens

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R
import java.util.Date
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cm_20241_gr01.lab1_gui.ui.composables.inputText
import com.cm_20241_gr01.lab1_gui.ui.composables.radioGender
import com.cm_20241_gr01.lab1_gui.ui.composables.selectBirthday
import com.cm_20241_gr01.lab1_gui.ui.composables.selectStudy


@Composable
fun PersonalDataScreen(onNextButton: (Int) -> Unit) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            PersonalDataLandscapeLayout(onNextButton)
        }
        else -> {
            personalDataPortraitLayout(onNextButton)
        }
    }
}

@Composable
fun PersonalDataLandscapeLayout(
    onNextButton: (Int) -> Unit,
    DataViewModel: DataViewModel = viewModel()
) {
    val infoUiState by DataViewModel.uiState.collectAsState()
    val screenTitle = stringResource(id = R.string.personal_data_title)
    val firstname = stringResource(id = R.string.firstname)
    val lastName = stringResource(id = R.string.lastname)
    val gender = stringResource(id = R.string.gender)
    val birthdate = stringResource(id = R.string.birthdate)
    val scholarship = stringResource(id = R.string.scolarity)
    var validation by remember {
        mutableStateOf(false)
    }
    DisposableEffect(infoUiState.firstName, infoUiState.lastName, infoUiState.birthdate) {
        validation =
            !infoUiState.firstName.isNullOrEmpty() and !infoUiState.lastName.isNullOrEmpty() and !infoUiState.birthdate.isNullOrEmpty()
        onDispose { }
    }
    BoxWithConstraints {
        val colorBackground = Color(0xFF53041D)
        val colorTittle = Color(0xff164583)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
        ) {
            Text(
                text = stringResource(id = R.string.personal_data_title),
                fontSize = 28.sp,
                color = colorTittle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, bottom = 1.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.firstname) + "*",
                    R.drawable.name,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.firstName,
                    onValueChange = {DataViewModel.setFirstName(it) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                inputText(
                    stringResource(id = R.string.lastname) + "*",
                    R.drawable.name,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.lastName,
                    onValueChange = { DataViewModel.setLastName(it) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

                ) {
                radioGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                selectBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy()
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
            ) {
                val colorBack = Color(0xffa1cafe)
                val colorText = Color(0xff043f8a)
                Button(
                    enabled = validation,
                    onClick = {
                        onNextButton(2)
                        Log.i(
                            "", "${screenTitle.uppercase()}\n" +
                                    "---------------------------------------\n" +
                                    "$firstname= ${infoUiState.firstName}\n" +
                                    "$lastName = ${infoUiState.lastName}\n" +
                                    if (!infoUiState.gender.isEmpty()) {
                                        "$gender = ${infoUiState.gender}\n"
                                    } else {
                                        ""
                                    }
                                    + "$birthdate = ${infoUiState.birthdate}\n" +
                                    if (!infoUiState.scholarship.isEmpty()) {
                                        "$scholarship = ${infoUiState.scholarship}\n"
                                    } else {
                                        ""
                                    }
                        )
                    },
                    modifier = Modifier.padding(end = 100.dp, bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorBack)
                ) {
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        stringResource(R.string.next_button),
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
}

@Composable
fun personalDataPortraitLayout(
    onNextButton: (Int) -> Unit,
    DataViewModel: DataViewModel = viewModel()
) {
    val infoUiState by DataViewModel.uiState.collectAsState()

    val screenTitle = stringResource(id = R.string.personal_data_title)
    val firstname = stringResource(id = R.string.firstname)
    val lastName = stringResource(id = R.string.lastname)
    val gender = stringResource(id = R.string.gender)
    val birthdate = stringResource(id = R.string.birthdate)
    val scholarship = stringResource(id = R.string.scolarity)
    var validation by remember {
        mutableStateOf(false)
    }
    DisposableEffect(infoUiState.firstName, infoUiState.lastName, infoUiState.birthdate) {
        validation =
            !infoUiState.firstName.isNullOrEmpty() and !infoUiState.lastName.isNullOrEmpty() and !infoUiState.birthdate.isNullOrEmpty()
        onDispose { }
    }

    BoxWithConstraints {
        val colorBackground = Color(0xFFFFFFFF)
        val colorTittle = Color(0xFF000000)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
        ) {
            Text(
                text = stringResource(id = R.string.personal_data_title),
                fontSize = 28.sp,
                color = colorTittle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.firstname) + "*",
                    R.drawable.name,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.firstName,
                    onValueChange = { DataViewModel.setFirstName(it) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.lastname) + "*",
                    R.drawable.name,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.lastName,
                    onValueChange = { DataViewModel.setLastName(it) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                radioGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = stringResource(id = R.string.required_fields),
                    fontSize = 11.sp,
                    color = colorTittle,
                    modifier = Modifier
                        .fillMaxWidth()
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
                    onClick = {
                        onNextButton(2)
                        Log.i(
                            "", "${screenTitle.uppercase()}\n" +
                                    "---------------------------------------\n" +
                                    "$firstname = ${infoUiState.firstName}\n" +
                                    "$lastName = ${infoUiState.lastName}\n" +
                                    if (!infoUiState.gender.isEmpty()) {
                                        "$gender = ${infoUiState.gender}\n"
                                    } else {
                                        ""
                                    }
                                    + "$birthdate = ${infoUiState.birthdate}\n" +
                                    if (!infoUiState.scholarship.isEmpty()) {
                                        "$scholarship = ${infoUiState.scholarship}\n"
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
                    colors = ButtonDefaults.buttonColors(containerColor = colorBack),
                    enabled = validation
                ) {
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        stringResource(R.string.next_button),
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
}




@Preview
@Composable
fun PersonalDataScreenPreview(){
    val navController: NavHostController = rememberNavController()
    personalDataPortraitLayout(onNextButton = {
        navController.navigate("contactData")
    })
}