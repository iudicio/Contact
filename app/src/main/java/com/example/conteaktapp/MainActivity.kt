package com.example.conteaktapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Contact(
    val name: String,
    val surname: String? = null,
    val familyName: String,
    @DrawableRes val imageRes: Int? = null,
    val isFavorite: Boolean = false,
    val phone: String,
    val address: String,
    val email: String? = null,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactApp {
                ContactDetails(contact = previewContactWithoutPhoto)
            }
        }
    }
}

@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppHeader()
        Spacer(modifier = Modifier.height(26.dp))
        ContactAvatar(contact = contact)
        Spacer(modifier = Modifier.height(26.dp))
        ContactName(contact = contact)
        Spacer(modifier = Modifier.height(76.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 54.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            InfoRow(label = stringResource(R.string.phone), value = contact.phone)
            InfoRow(label = stringResource(R.string.address), value = contact.address)
            contact.email?.let {
                InfoRow(label = stringResource(R.string.email), value = it)
            }
        }
    }
}

@Composable
private fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 0.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF7B00F5),
        ) {}
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = 20.dp),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun ContactAvatar(contact: Contact) {
    if (contact.imageRes != null) {
        Image(
            painter = painterResource(contact.imageRes),
            contentDescription = null,
            modifier = Modifier
                .width(132.dp)
                .height(76.dp),
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.circle),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = Color(0xFFD0D0D0),
            )
            Text(
                text = "${contact.name.take(1)}${contact.familyName.take(1)}",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun ContactName(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = contact.fullName(),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp,
        )
        if (contact.isFavorite) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontStyle = FontStyle.Italic,
            fontSize = 17.sp,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = Color(0xFF333333),
            fontSize = 16.sp,
            lineHeight = 20.sp,
        )
    }
}

@Composable
private fun ContactApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
        ) {
            content()
        }
    }
}

private fun Contact.fullName(): String =
    listOfNotNull(name, surname, familyName).joinToString(" ")

private val previewContactWithoutPhoto = Contact(
    name = "Евгений",
    surname = "Андреевич",
    familyName = "Лукашин",
    isFavorite = true,
    phone = "+7 495 495 95 95",
    address = "г. Москва, 3-я улица\nСтроителей, д. 25, кв. 12",
    email = "ELukashin@practicum.ru",
)

private val previewContactWithPhoto = Contact(
    name = "Василий",
    familyName = "Кузякин",
    imageRes = R.drawable.contact_photo,
    phone = "---",
    address = "Ивановская область, дер.\nКрутово, д. 4",
)

@Preview(showBackground = true, name = "ProfileWithoutPhotoPreview")
@Composable
private fun ProfileWithoutPhotoPreview() {
    ContactApp {
        ContactDetails(contact = previewContactWithoutPhoto)
    }
}

@Preview(showBackground = true, name = "ProfileWithPhotoPreview")
@Composable
private fun ProfileWithPhotoPreview() {
    ContactApp {
        ContactDetails(contact = previewContactWithPhoto)
    }
}
