package com.example.bottomsheetcontact_22.ui.screen

import UpdateCustomAlert
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bottomsheetcontact_22.ui.component.CustomAddContact
import com.example.bottomsheetcontact_22.ui.component.DisplayContactOnScreen
import com.example.bottomsheetcontact_22.model.Contact


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen() {
    //state variable to control add/update
    val isSheetOpen = remember { mutableStateOf(false) }
    val isUpdateSheetOpen = remember { mutableStateOf(false) }
    val selectedContactIndex = remember { mutableIntStateOf(-1) }
    val contacts = remember { mutableStateListOf<Contact>() }//allcontact


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //title
                    Text(
                        "Contact List Application",
                        fontWeight = FontWeight.Bold
                    )
                }
            )

        },
        floatingActionButton = {
            //add new contact
            FloatingActionButton(
                onClick = { isSheetOpen.value = true },//show add contact dailog
                modifier = Modifier
                    .padding(32.dp)
//                    .align(Alignment.BottomEnd)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {


            // Display contacts or a message if the list is empty
            Column(modifier = Modifier) {

                if (contacts.isEmpty()) {
                    Text(
                        "please click + to add new Contact ",
                        Modifier.padding(32.dp),
                        fontWeight = FontWeight.Bold
                    )
                } else {

                    LazyColumn {
                        itemsIndexed(
                            contacts,
                            key = { _, contact -> contact.name }) { index, contact ->
                            DisplayContactOnScreen(contact = contact) {
                                // Show update alert when a contact is clicked
                                isUpdateSheetOpen.value = true
                                selectedContactIndex.intValue = index
                            }
                        }
                    }
                }
            }


            // CustomAlert for adding a new contact
            CustomAddContact(
                isSheetOpen = isSheetOpen,
                contacts = contacts,
            )

            if (isUpdateSheetOpen.value) {
                UpdateCustomAlert(
                    isUpdateSheetOpen = isUpdateSheetOpen,
                    contact = contacts[selectedContactIndex.intValue],
                    indexOf = selectedContactIndex,
                    contacts = contacts,

                    )
            }


        }
    }


}

