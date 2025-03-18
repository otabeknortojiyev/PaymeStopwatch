package uz.payme.otabek.presentation.screens.weather.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.payme.otabek.R

@Composable
fun SmallIcons(speed: Double?, humidity: Int?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(color = Color(0xFF528fd7), shape = RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(painter = painterResource(R.drawable.wind), contentDescription = "Ветер")
                Text(
                    text = "Ветер", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Light
                )
            }
            Text(
                text = speed.toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .background(color = Color(0xFF528fd7), shape = RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(painter = painterResource(R.drawable.humidity), contentDescription = "Влажность")
                Text(
                    text = "Влажность", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Light
                )
            }
            Text(
                text = "$humidity%", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal
            )
        }
    }
}