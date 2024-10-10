package com.example.bottomsheetcontact_22

import UpdateCustomAlert
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bottomsheetcontact_22.ui.component.CustomAlert
import com.example.bottomsheetcontact_22.ui.component.DisplayContactOnScreen
import com.example.bottomsheetcontact_22.ui.model.Contact

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                ContactScreen()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen() {
    //state variable to control add/update
//    var visibleCustomAlert = remember { mutableStateOf(false) }
    var isSheetOpen= remember { mutableStateOf(false) }
    var isUpdateSheetOpen = remember { mutableStateOf(false) }
    var selectedContactIndex = remember { mutableStateOf(-1) }
    var contacts = remember { mutableStateListOf<Contact>() }//allcontact


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

    ) {innerPadding->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {


            // Display contacts or a message if the list is empty
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                if (contacts.isEmpty()) {
                    Text("please click + to add new Contact ", Modifier.padding(32.dp), fontWeight = FontWeight.Bold)
                } else {
                    contacts.forEachIndexed { index, contact ->
                        DisplayContactOnScreen(contact = contact) {
                            // Show update alert when a contact is clicked
                            isUpdateSheetOpen.value = true
                            selectedContactIndex.value = index
                        }
                    }
                }
            }



            // CustomAlert for adding a new contact
            CustomAlert(
                isSheetOpen,
                contacts = contacts,
                onCancel = { isSheetOpen.value = false; }
            )

            if (isUpdateSheetOpen.value) {
                UpdateCustomAlert(
                    isUpdateSheetOpen = isUpdateSheetOpen,
                    contact = contacts[selectedContactIndex.value],
                    indexOf = selectedContactIndex,
                    contacts = contacts,
//                    modifier = Modifier.align(Alignment.Center),
                )
            }



        }
    }


}

