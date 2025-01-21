package video.generator.ai.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import video.generator.ai.navigation.GENERATION
import video.generator.ai.navigation.VIDEO
import video.generator.ai.ui.viewmodel.GenerationViewModel
import video.generator.ai.ui.viewmodel.NetworkViewModel
import video.generator.ai.R
import video.generator.ai.subscription.ChooseSubscription

@Composable
fun GenerationScreen(
    networkViewModel: NetworkViewModel,
    navController: NavController,
    generationViewModel: GenerationViewModel,
) {

    var mm = remember { mutableStateOf(0) }
    val formattedTime = remember(mm.value) {
        formatTime(mm.value)
    }
    val link = networkViewModel.videoLink.collectAsState()
    val status = networkViewModel.generated.collectAsState()


    LaunchedEffect(true) {
        while (status.value != true){
            delay(10)
            mm.value += 10
        }
    }
    if (status.value == true){
        LaunchedEffect(true) {
            delay(1500)
            generationViewModel.addGeneration(networkViewModel._lastGeneration.value)
            navController.navigate(VIDEO){
                popUpTo(GENERATION) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize().background(Color(0xFF0F0F0F)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(){
            if (status.value == true){
                CircularProgressIndicator(
                    strokeWidth = 15.dp,
                    color = Color.White,
                    trackColor = Color.Black,
                    modifier = Modifier.size(350.dp).align(Alignment.Center),
                    strokeCap = StrokeCap.Round,
                    progress = 1f
                )
            }else{
                CircularProgressIndicator(
                    strokeWidth = 15.dp,
                    color = Color.White,
                    trackColor = Color.Black,
                    modifier = Modifier.size(350.dp).align(Alignment.Center),
                    strokeCap = StrokeCap.Round
                )
            }
            Row(
                Modifier.fillMaxWidth().align(Alignment.Center),
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(status.value == true, enter = fadeIn(tween(500))) {
                    Text(androidx.compose.ui.res.stringResource(R.string.ready), fontSize = 60.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                AnimatedVisibility(status.value != true) {
                    Text("$formattedTime", fontSize = 60.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

fun formatTime(milliseconds: Int): String {
    val seconds = (milliseconds / 1000).toInt()
    val millis = (milliseconds % 1000) / 10 // Два знака для миллисекунд (от 00 до 99)
    return String.format("%03d:%02d", seconds, millis)
}