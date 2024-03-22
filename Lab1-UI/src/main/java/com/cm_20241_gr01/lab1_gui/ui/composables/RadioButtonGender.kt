package com.cm_20241_gr01.lab1_gui.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R

@Composable
fun RadioButtonGender(DataViewModel: DataViewModel = viewModel()) {
    val infoUiState by DataViewModel.uiState.collectAsState()

    val colorIcon = Color(0xFF000000)
    val genderFemale = stringResource(R.string.gender_female)
    val genderMale = stringResource(R.string.gender_male)

    Icon(
        painter = painterResource(id = R.drawable.gender),
        contentDescription = null,
        modifier = Modifier.padding(start=15.dp).size(35.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(text = stringResource(id = R.string.gender))
    Spacer(modifier = Modifier.width(16.dp))
    //var sex by remember { mutableStateOf(genderFemale) }
    RadioButton(
        selected = infoUiState.gender === genderFemale,
        onClick = { DataViewModel.setGender(genderFemale) }
    )
    Text(text = stringResource(id = R.string.gender_female))
    RadioButton(
        selected = infoUiState.gender === genderMale,
        onClick = { DataViewModel.setGender(genderMale) }
    )
    Text(text = stringResource(id = R.string.gender_male))
}
