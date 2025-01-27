package com.example.villa.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.villa.R


@Preview(showBackground = true)
@Composable
fun NavbarPreview() {
    Navbar(nama = "Muhammad Dhafa",  showBackButton = true)
}

@Composable
fun Navbar(
    nama: String,
    showBackButton: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp, topStart = 50.dp, topEnd = 50.dp))
            .background(color = Color(0xFF00BFFF))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 20.dp)
            ) {
                Text(
                    text = "Selamat Datang",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = nama,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logooo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()

                )
            }
        }
    }
}