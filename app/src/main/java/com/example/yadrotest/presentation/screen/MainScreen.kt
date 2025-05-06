package com.example.yadrotest.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.yadrotest.presentation.screen.view.ContactView
import com.example.yadrotest.presentation.theme.Space20
import com.example.yadrotest.presentation.theme.Space8
import com.example.yadrotest.presentation.theme.Typography
import com.example.yadrotest.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onContactClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val contacts by viewModel.contacts.collectAsState()
    var letter: Pair<Char, Boolean> = contacts[0].name[0] to false

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = contacts, key = { it.id } ) { contact ->
                if (letter.first != contact.name[0]) {
                    letter = contact.name[0] to false
                }

                if (letter.first == contact.name[0] && !letter.second) {
                    letter = letter.first to true

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Space20, vertical = Space8)
                    ) {
                        Text(
                            text = letter.first.uppercase(),
                            color = Color.Black,
                            style = Typography.titleLarge
                        )
                    }
                }

                ContactView(
                    contactViewData = contact,
                    onContactClick = onContactClick,
                    modifier = Modifier.padding(horizontal = Space20)
                )
            }
        }
    }
}