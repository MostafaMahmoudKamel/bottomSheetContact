package com.example.bottomsheetcontact_22.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottomsheetcontact_22.ui.model.Contact
import com.example.contact_21in.isEmailValid
import com.example.contact_21in.isNameValid
import com.example.contact_21in.isPhoneValid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlert(
    isSheetOpen: MutableState<Boolean>,
    contacts: SnapshotStateList<Contact>,
    onCancel: () -> Unit,

    ) {
    //state of inputs
    var nameState = remember { mutableStateOf("") }
    var phoneState = remember { mutableStateOf("") }
    var addressState = remember { mutableStateOf("") }
    var emailState = remember { mutableStateOf("") }
    //error state
    var errorName = remember { mutableStateOf(true) }
    var errorPhone = remember { mutableStateOf(true) }
    var errorAddress = remember { mutableStateOf(false) }
    var errorEmail = remember { mutableStateOf(true) }

    //sheetState
    val sheetState = rememberModalBottomSheetState()


    //display ModalBottomSheet when i click FAB Button
    if (isSheetOpen.value) {

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen.value = false
            },
            containerColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        "Add contact",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 24.sp
                    )
                    Input(nameState, errorName, label = "name")
                    Input(phoneState, errorPhone, label = "phone")
                    Input(addressState, errorAddress, label = "Address")
                    Input(emailState, errorEmail, label = "email")

                    if ((nameState.value).isNameValid()) {
                        errorName.value = false
                    } else {
                        errorName.value = true
                    }
                    if (emailState.value.isEmailValid()) {
                        errorEmail.value = false
                    } else {
                        errorEmail.value = true
                    }
                    if (phoneState.value.isPhoneValid()) {
                        errorPhone.value = false
                    } else {
                        errorPhone.value = true
                    }

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        //cancel Adding new Content
                        Button(onClick = onCancel) { Text("Cancel") }

                        //confirm button
                        Button(onClick = {
                            //add contact after validation
                            if (!errorName.value && !errorEmail.value && !errorPhone.value) {
                                contacts.add(
                                    Contact(
                                        nameState.value,
                                        phoneState.value,
                                        addressState.value,
                                        emailState.value
                                    )
                                )
                                //empty value state after add the contact
                                nameState.value = ""
                                phoneState.value = ""
                                addressState.value = ""
                                emailState.value = ""
                                isSheetOpen.value = false
                            }

//
                        })
                        { Text("Confirm") }


                    }
                }

            }
        }
    }


}
