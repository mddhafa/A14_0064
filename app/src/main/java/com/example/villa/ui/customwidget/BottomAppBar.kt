package com.example.villa.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.villa.R

@Composable
fun BottomMenuBar(
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onReservasi: () -> Unit = {},
    onHome: () -> Unit = {}
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .shadow(8.dp),
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF56CCF2), Color(0xFF2F80ED))
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomMenuItem(
                    title = "Villa",
                    iconRes = R.drawable.vila,
                    onClick = onVilla
                )
                BottomMenuItem(
                    title = "Reservasi",
                    iconRes = R.drawable.reservasi,
                    onClick = onReservasi
                )
                BottomMenuItem(
                    title = "Home",
                    iconRes = R.drawable.home,
                    onClick = onHome
                )
                BottomMenuItem(
                    title = "Pelanggan",
                    iconRes = R.drawable.pelanggan,
                    onClick = onPelanggan
                )
                BottomMenuItem(
                    title = "Review",
                    iconRes = R.drawable.review,
                    onClick = onReview
                )
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
