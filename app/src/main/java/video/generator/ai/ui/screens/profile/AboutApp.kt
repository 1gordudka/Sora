package video.generator.ai.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import video.generator.ai.R
import video.generator.ai.ui.theme.darkGrey

@Composable
fun AboutAppScreen(
    navController: NavController
    ) {


    Column(
        Modifier
            .fillMaxSize()
            .background(darkGrey)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.fillMaxWidth()){
            IconButton({
                navController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, "",
                    tint = Color.White.copy(alpha = 0.8f), modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart))

            }
            Text(
                stringResource(R.string.about), fontSize = 16.sp,
                fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.Center))
        }
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(R.drawable.icon), "",
                Modifier.size(170.dp).clip(RoundedCornerShape(25.dp)))
            Spacer(Modifier.height(10.dp))
            Text("AI Searcher", color = Color.White,
                fontSize = 40.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(30.dp))
            Text(stringResource(R.string.version) + " 1.0.0", fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f))
            Spacer(Modifier.height(5.dp))
            Text("DEVELOPER ORCEL, 2024", fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f))
        }
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.support_email), fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f))
            Spacer(Modifier.height(5.dp))
            Text("support@ai-searcher.app", fontSize = 14.sp,
                color = Color.White)
        }
    }
}