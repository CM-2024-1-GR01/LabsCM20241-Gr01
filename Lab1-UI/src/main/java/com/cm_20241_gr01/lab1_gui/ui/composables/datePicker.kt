package com.cm_20241_gr01.lab1_gui.ui.composables

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cm_20241_gr01.lab1_gui.DataViewModel
import com.cm_20241_gr01.lab1_gui.R
import java.util.Date

@Composable
fun SelectDate(DataViewModel: DataViewModel = viewModel()) {
    val infoUiState by DataViewModel.uiState.collectAsState()

    val colorIcon = Color(0xFFF7D4E8)
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val labelBirthdate: String
    if (infoUiState.birthdate.isNullOrEmpty()) {
        labelBirthdate = stringResource(id = R.string.select_button)
    } else {
        labelBirthdate = infoUiState.birthdate
    }

    val mDate = remember { mutableStateOf(value = labelBirthdate) }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            DataViewModel.setBirthdate(mDate.value)
        }, mYear, mMonth, mDay
    )
    OutlinedButton(onClick = {
        mDatePickerDialog.show()
    }, colors = ButtonDefaults.buttonColors(containerColor = colorIcon)) {
        Text(labelBirthdate, color = Color.Black)
    }
}

