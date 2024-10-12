package com.example.bottomsheetcontact_22.ui.component

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottomsheetcontact_22.ui.convertDateToString
import com.example.bottomsheetcontact_22.ui.isEmailValid
import com.example.bottomsheetcontact_22.ui.isNameValid
import com.example.bottomsheetcontact_22.ui.isPhoneValid
import com.example.bottomsheetcontact_22.model.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAddContact(
    isSheetOpen: MutableState<Boolean>,
    contacts: SnapshotStateList<Contact>,
) {
    //state of inputs
    val nameState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val addressState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    //error state
    val errorName = remember { mutableStateOf(false) }
    val errorPhone = remember { mutableStateOf(false) }
    val errorAddress = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }

    //sheetState
    val sheetState = rememberModalBottomSheetState()

    //timePicker &datePicker  State
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )
    val datePicker = rememberDatePickerState()


    //Date and time Alert Dailog
    val isTimeOpen = remember { mutableStateOf(false) }
    val isDateOpen = remember { mutableStateOf(false) }


    //display ModalBottomSheet when i click FAB Button
    if (isSheetOpen.value) {

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen.value = false
            },
            containerColor = Color.White
        ) {

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Row {
                    Text(
                        "Add contact",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        fontSize = 24.sp
                    )
                    IconButton(onClick = { isDateOpen.value = true }) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                    }

                    IconButton(onClick = { isTimeOpen.value = true }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null)
                    }
                }
                Input(nameState, errorName, label = "name", validation = { it.isNameValid() })
                Input(phoneState, errorPhone, label = "phone", validation = { it.isPhoneValid() })
                Input(
                    addressState,
                    errorAddress,
                    label = "Address",
                    validation = { it.isNameValid() })
                Input(emailState, errorEmail, label = "email", validation = { it.isEmailValid() })


                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    //cancel Adding new Content
                    Button(onClick = { isSheetOpen.value = false; }) { Text("Cancel") }

                    //confirm button
                    Button(onClick = {
                        //add contact after validation
                        if (!errorName.value && !errorEmail.value && !errorPhone.value) {
                            contacts.add(
                                Contact(
                                    nameState.value,
                                    phoneState.value,
                                    addressState.value,
                                    emailState.value,
                                    "${timePickerState.hour}:${timePickerState.minute}",
                                    datePicker.convertDateToString()

                                )
                            )
                            //empty value state after add the contact
                            nameState.value = ""
                            phoneState.value = ""
                            addressState.value = ""
                            emailState.value = ""

                            //hide bottomSheet after insert contact
                            isSheetOpen.value = false

                        }


                    })
                    { Text("Confirm") }


                }


            }
        }
    }

//showTime picker
    if (isTimeOpen.value) {
        TimePickerDialog(
            isTimePickerOpen = isTimeOpen,
            timePickerState = timePickerState,

            )
    }

//showDatePicker
    if (isDateOpen.value) {
        DatePickerDialog(datePicker, isDatePickerOpen = isDateOpen)
    }


}
