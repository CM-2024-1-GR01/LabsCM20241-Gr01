package com.cm_20241_gr01.lab1_gui

import androidx.lifecycle.ViewModel
import com.cm_20241_gr01.lab1_gui.screens.DataState
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DataViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DataState())
    val uiState: StateFlow<DataState> = _uiState.asStateFlow()

    fun setFirstName(firstName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                firstName = firstName,
            )
        }
    }

    fun setLastName(lastName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName,
            )
        }
    }

    fun setEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun setAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
                address = address,
            )
        }
    }

    fun setPhone(phone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phone = phone,
            )
        }
    }

    fun setGender(gender: String) {
        _uiState.update { currentState ->
            currentState.copy(
                gender = gender,
            )
        }
    }

    fun setBirthdate(birthdate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                birthdate = birthdate,
            )
        }
    }

    fun setScholarship(scholarship: String) {
        _uiState.update { currentState ->
            currentState.copy(
                scholarship = scholarship,
            )
        }
    }

    fun setCountry(country: String) {
        Log.i("INF", country)
        _uiState.update { currentState ->
            currentState.copy(
                country = country,
            )
        }
    }

    fun setCity(city: String) {
        Log.i("INF", city)
        _uiState.update { currentState ->
            currentState.copy(
                city = city,
            )
        }
    }
}