package com.cm_20241_gr01.lab1_gui.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cm_20241_gr01.lab1_gui.R

@Composable
fun SelectStudyGrade() {
    val colorIcon = Color(0xFF000000)
    Icon(
        painter = painterResource(id = R.drawable.study),
        contentDescription = null,
        modifier = Modifier.padding(start=15.dp).size(35.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    schoolDropdownMenu();
    Spacer(modifier = Modifier.width(10.dp))
}