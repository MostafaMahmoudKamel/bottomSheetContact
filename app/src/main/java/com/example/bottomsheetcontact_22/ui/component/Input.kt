package com.example.bottomsheetcontact_22.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Input(
    stateInput: MutableState<String>,
    errorState: MutableState<Boolean>,
    label: String,
    validation: (String) -> Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = stateInput.value,
        onValueChange = {
            stateInput.value = it
            errorState.value = !validation(it)//manage error state inside onValueChange
        },
        label = { Text(text = label) },
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .wrapContentHeight()
        ),
        isError = errorState.value
    )
}
