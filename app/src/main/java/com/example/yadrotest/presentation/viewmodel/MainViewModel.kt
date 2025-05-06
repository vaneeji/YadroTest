package com.example.yadrotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.yadrotest.presentation.model.ContactViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _contacts: MutableStateFlow<List<ContactViewData>> = MutableStateFlow(emptyList())
    val contacts: StateFlow<List<ContactViewData>> = _contacts

    fun updateContacts(contacts: List<ContactViewData>) {
        _contacts.value = contacts
    }

    fun getPhoneNumberById(id: String): String? =
        _contacts.value
            .singleOrNull { contact ->
                contact.id == id
            }?.phoneNumber
}