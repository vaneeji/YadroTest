package com.example.yadrotest.presentation.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.yadrotest.R
import com.example.yadrotest.presentation.model.ContactViewData
import com.example.yadrotest.presentation.screen.MainScreen
import com.example.yadrotest.presentation.theme.YadroTestTheme
import com.example.yadrotest.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForPermissions()
        enableEdgeToEdge()
        setContent {
            YadroTestTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {}
                        )
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        viewModel = viewModel,
                        onContactClick = ::onContactClick,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            updateContacts()
        }
    }

    private fun checkForPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE),
                1
            )
        }
    }

    private fun updateContacts() {
        val contactsMap = linkedMapOf<Long, ContactViewData>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getLong(idIndex)
                if (contactsMap.contains(id)) continue

                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)

                val contact = ContactViewData(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    phoneNumber = number
                )

                contactsMap[id] = contact
            }
        }

        viewModel.updateContacts(contactsMap.values.toList())
    }

    private fun onContactClick(id: String) {
        val phoneNumber = viewModel.getPhoneNumberById(id)

        val intent = Intent().apply {
            action = Intent.ACTION_CALL
            data = Uri.parse("tel:$phoneNumber")
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
            PackageManager.PERMISSION_GRANTED || phoneNumber == null) {
            Toast.makeText(
                /* context = */ this,
                /* text = */ resources.getString(R.string.cannot_call_error),
                /* duration = */ Toast.LENGTH_SHORT
            ).show()
            return
        }

        startActivity(intent)
    }
}
