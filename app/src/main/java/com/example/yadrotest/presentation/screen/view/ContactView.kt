package com.example.yadrotest.presentation.screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.yadrotest.R
import com.example.yadrotest.presentation.model.ContactViewData
import com.example.yadrotest.presentation.theme.Size64
import com.example.yadrotest.presentation.theme.Space8
import com.example.yadrotest.presentation.theme.Typography

@Composable
fun ContactView(
    contactViewData: ContactViewData,
    onContactClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable {
            onContactClick(contactViewData.id)
        },
        horizontalArrangement = Arrangement.spacedBy(Space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = stringResource(R.string.contact_icon_description),
            modifier = Modifier.size(Size64)
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = contactViewData.name,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodyLarge
            )

            Text(
                text = contactViewData.phoneNumber,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodyLarge
            )
        }
    }
}