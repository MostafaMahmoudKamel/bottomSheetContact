package com.example.bottomsheetcontact_22.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(datePicker:DatePickerState,isDatePickerOpen: MutableState<Boolean>) {
//    var datePicker = rememberDatePickerState()
//    val datePicker=datePicker
    AlertDialog(
        title = {
            Text(text = "Pick Date")
        },
        text = {
            DatePicker(state = datePicker)

        },
        onDismissRequest = {
            isDatePickerOpen.value = false
        },
        confirmButton = {
            Button(onClick = {
                isDatePickerOpen.value = false
            }) {
                Text("Save Pick Time")

            }
        },

//            dismissButton = { isTimePickerOpen.value = false }
    )
}