package com.cm_20241_gr01.lab1_gui.ui.composables

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun schoolDropdownMenu(DataViewModel: DataViewModel = viewModel()) {
    val infoUiState by DataViewModel.uiState.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var grade by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    )
    {
        val colorText = Color(0xFF000000)
        val colorBack = Color(0xFFF7D4E8)
        val colorLabel = Color(0xFF000000)
        val label: String
        if (infoUiState.scholarship.isNullOrEmpty()) {
            label = stringResource(id = R.string.scolarity)
        } else {
            label = infoUiState.scholarship
        }
        TextField(
            value = grade,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = label)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = colorText,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = colorText,
                containerColor = colorBack,
                focusedLabelColor = colorLabel,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            val items = listOf(
                stringResource(id = R.string.scolarity_primary),
                stringResource(id = R.string.scolarity_secundary),
                stringResource(id = R.string.scolarity_bachelor),
                stringResource(id = R.string.scolarity_other)
            )
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        grade = item
                        isExpanded = false
                        DataViewModel.setScholarship(item)
                    },
                    text = {
                        Text(text = item)
                    }
                )
            }
        }
    }
}