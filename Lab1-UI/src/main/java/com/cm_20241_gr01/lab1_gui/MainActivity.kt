package com.cm_20241_gr01.lab1_gui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cm_20241_gr01.lab1_gui.screens.PersonalDataScreen
import com.cm_20241_gr01.lab1_gui.ui.theme.Lab1GUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactApp()
        }
    }
}

enum class AppScreen(@StringRes val title: Int) {
    ContactData(title = R.string.contact_data_title),
    PersonalData(title = R.string.personal_data_title)
}

@Composable
fun ContactApp(
    navController: NavHostController = rememberNavController()
) {
    val dataViewModel : DataViewModel = viewModel()
    NavHost(
        navController, startDestination = "personalData", modifier = Modifier.padding(
            PaddingValues(
                start = 0.dp,
                top = 25.dp,
                end = 0.dp,
                bottom = 0.dp
            )
        )
    ) {
        composable("personalData") {
            PersonalDataScreen(onNextButton = {
                navController.navigate("contactData")
            })
        }
    }
}