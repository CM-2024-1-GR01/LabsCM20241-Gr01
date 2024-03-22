package com.cm_20241_gr01.lab1_gui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cm_20241_gr01.lab1_gui.ui.composables.InputField
import com.cm_20241_gr01.lab1_gui.ui.composables.RadioButtonGender
import com.cm_20241_gr01.lab1_gui.ui.composables.SelectDateBirthday
import com.cm_20241_gr01.lab1_gui.ui.composables.SelectStudyGrade


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
                InputField(
                    stringResource(id = R.string.firstname) + "*",
                    R.drawable.name,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.firstName,
                    onValueChange = {DataViewModel.setFirstName(it) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                InputField(
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
                RadioButtonGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SelectDateBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                SelectStudyGrade()
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
                val colorBack = Color(0xFF000000)
                val colorText = Color(0xFF000000)
                OutlinedButton(
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
                InputField(
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
                InputField(
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
                RadioButtonGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectDateBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                SelectStudyGrade()
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




@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun PersonalDataScreenPreview(){
    val navController: NavHostController = rememberNavController()
    PersonalDataLandscapeLayout(onNextButton = {
        navController.navigate("contactData")
    })
}

