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
import androidx.compose.ui.graphics.Brush
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
}

@Composable
fun Navbar(
    nama: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00BFFF), Color(0xFF87CEFA))
                )
            )
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Texts
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Selamat Datang",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = nama,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logooo),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
