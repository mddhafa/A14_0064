package com.example.villa.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.foundation.shape.CircleShape
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
            .fillMaxHeight(0.1f)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .shadow(8.dp),
        containerColor = Color.Transparent,
        contentPadding = PaddingValues(0.dp),
        windowInsets = WindowInsets(0),

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
                    onClick = onVilla,
                    isActive = false
                )
                BottomMenuItem(
                    title = "Reservasi",
                    iconRes = R.drawable.reservasi,
                    onClick = onReservasi,
                    isActive = false
                )
                BottomMenuItem(
                    title = "Home",
                    iconRes = R.drawable.home,
                    onClick = onHome,
                    isActive = true // Highlighted item
                )
                BottomMenuItem(
                    title = "Pelanggan",
                    iconRes = R.drawable.pelanggan,
                    onClick = onPelanggan,
                    isActive = false
                )
                BottomMenuItem(
                    title = "Review",
                    iconRes = R.drawable.review,
                    onClick = onReview,
                    isActive = false
                )
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    title: String,
    iconRes: Int,
    onClick: () -> Unit,
    isActive: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = if (isActive) {
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            } else {
                Modifier.size(48.dp)
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = if (isActive) Color(0xFF2F80ED) else Color.White
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            color = if (isActive) Color.White else Color.White.copy(alpha = 0.7f),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}