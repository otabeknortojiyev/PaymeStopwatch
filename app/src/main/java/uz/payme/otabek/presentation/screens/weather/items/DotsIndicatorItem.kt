package uz.payme.otabek.presentation.screens.weather.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DotsIndicatorItem(pagerState: Int) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .size(8.dp)
            .background(
                color = when (pagerState) {
                    0 -> Color.White
                    else -> Color.Gray
                }, shape = CircleShape
            )
    )
    Box(
        modifier = Modifier
            .padding(start = 4.dp)
            .size(8.dp)
            .background(
                color = when (pagerState) {
                    1 -> Color.White
                    else -> Color.Gray
                }, shape = CircleShape
            )
    )
}