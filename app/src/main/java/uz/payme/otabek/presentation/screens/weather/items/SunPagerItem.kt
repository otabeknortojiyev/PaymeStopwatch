package uz.payme.otabek.presentation.screens.weather.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.payme.otabek.R
import uz.payme.otabek.utils.convertUnixTimestampToTime

@Composable
fun SunPagerItem(time: Long?, text1: String, text2: String, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val sunTime = convertUnixTimestampToTime(time)
        Column(
            modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Image(
                    painter = painterResource(R.drawable.sun), contentDescription = stringResource(R.string.sun)
                )
                Text(
                    text = text1, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.ExtraLight
                )
            }
            Text(
                text = "$text2 в\n $sunTime",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(icon), contentDescription = stringResource(R.string.sunrise)
            )
            Text(
                text = sunTime, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Normal
            )
        }
    }
}