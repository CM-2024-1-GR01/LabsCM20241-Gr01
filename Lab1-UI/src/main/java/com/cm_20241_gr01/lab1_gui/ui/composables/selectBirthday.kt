package com.cm_20241_gr01.lab1_gui.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cm_20241_gr01.lab1_gui.R

@Composable
fun SelectDateBirthday() {
    val colorIcon = Color(0xFF000000)
    Icon(
        painter = painterResource(id = R.drawable.calendar),
        contentDescription = null,
        modifier = Modifier.padding(start=15.dp).size(35.dp),
        tint = colorIcon,
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(text = stringResource(id = R.string.birthdate) + "*")
    Spacer(modifier = Modifier.width(16.dp))
    SelectDate();
}