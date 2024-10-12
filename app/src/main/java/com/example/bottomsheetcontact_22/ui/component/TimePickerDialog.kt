package com.example.bottomsheetcontact_22.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(timePickerState: TimePickerState, isTimePickerOpen: MutableState<Boolean>) {

    AlertDialog(
        title = {
            Text(text = "Pick Time")
        },
        text = {
            TimePicker(state = timePickerState)
        },
        onDismissRequest = {
            isTimePickerOpen.value = false
        },
        confirmButton = {
            Button(onClick = {
                isTimePickerOpen.value = false
            }) {
                Text("Save Pick Time")

            }
        },
    )


}
