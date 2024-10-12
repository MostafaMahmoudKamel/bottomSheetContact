import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.example.bottomsheetcontact_22.model.Contact
import com.example.bottomsheetcontact_22.ui.component.Input
import com.example.bottomsheetcontact_22.ui.isEmailValid
import com.example.bottomsheetcontact_22.ui.isNameValid
import com.example.bottomsheetcontact_22.ui.isPhoneValid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCustomAlert(
    isUpdateSheetOpen: MutableState<Boolean>,
    indexOf: MutableState<Int>,
    contact: Contact,
    contacts: SnapshotStateList<Contact>,

    ) {

    val nameState = remember { mutableStateOf(contact.name) }
    val phoneState = remember { mutableStateOf(contact.phone) }
    val addressState = remember { mutableStateOf(contact.address) }
    val emailState = remember { mutableStateOf(contact.email) }

    val errorName = remember { mutableStateOf(false) }
    val errorPhone = remember { mutableStateOf(false) }
    val errorAddress = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            isUpdateSheetOpen.value = false
        },
        containerColor = Color.White
    ) {

        Box(
            modifier = Modifier

                .background(Color.White, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text("update contact?")
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
                    Button(onClick = {

                        isUpdateSheetOpen.value = false

                    }) { Text("Cancel") }

                    Button(onClick = {
                        if (!errorEmail.value && !errorPhone.value && !errorName.value) {
                            contacts[indexOf.value] =
                                Contact(
                                    nameState.value,
                                    phoneState.value,
                                    addressState.value,
                                    emailState.value
                                )
                            isUpdateSheetOpen.value = false

                        }

                    })
                    { Text("Confirm") }

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            contacts.removeAt(indexOf.value)
                            indexOf.value = -1
                            isUpdateSheetOpen.value = false

                        })

                }
            }

        }
    }
}

