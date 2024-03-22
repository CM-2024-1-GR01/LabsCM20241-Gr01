package com.cm_20241_gr01.lab1_gui.ui.composables

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputText(
    input: String,
    @DrawableRes icon: Int,
    keyboard: KeyboardType,
    autoCapitalization: KeyboardCapitalization,
    viewModelValue: String,
    onValueChange: (String) -> Unit
) {
    val colorIcon = Color(0xff164583)
    val colorText = Color(0xff043f8a)
    val colorBack = Color(0xffa1cafe)
    val colorLabel = Color(0xFF000000)
    val modifier = when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Modifier
                .padding(8.dp)
                .height(60.dp)
        }

        else -> {
            Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        }
    }

    Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = viewModelValue,
        onValueChange = { onValueChange(it) },
        label = { Text(input) },
        maxLines = 1,
        textStyle = TextStyle(
            color = colorText,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorText,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = colorText,
            containerColor = colorBack,
            focusedLabelColor = colorLabel
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboard,
            imeAction = ImeAction.Next,
            capitalization = autoCapitalization
        ),// Cambia "Enter" a "Siguiente"
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        modifier = modifier
    )
}
