package video.generator.ai.ui.screens.history

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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import video.generator.ai.ui.theme.darkGrey
import video.generator.ai.ui.viewmodel.GenerationViewModel
import video.generator.ai.ui.viewmodel.NetworkViewModel
import video.generator.ai.R
import video.generator.ai.data.local.model.Generation
import video.generator.ai.navigation.VIDEO
import video.generator.ai.ui.components.noRippleClickable
import video.generator.ai.ui.theme.grey
import video.generator.ai.ui.theme.lightGrey

@Composable
fun HistoryScreen(
    generationViewModel: GenerationViewModel,
    navController: NavController,
    networkViewModel: NetworkViewModel
) {

    val generations = generationViewModel.generations.collectAsState()

    LaunchedEffect(true) {
        generationViewModel.loadGenerations()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(darkGrey)
            .padding(16.dp)
    ) {
        Column {
            Text(stringResource(R.string.history), color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(Modifier.height(5.dp))
            Text(stringResource(R.string.history_desc), color = Color.White.copy(alpha = 0.4f), fontSize = 14.sp)
        }
        if (generations.value.isEmpty()){
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(ImageVector.vectorResource(R.drawable.capture), "", Modifier.size(100.dp), tint = Color.White.copy(alpha = 0.8f))
                    Spacer(Modifier.height(7.dp))
                    Text(stringResource(R.string.no_videos), color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }
        }else{
            LazyColumn {
                item{
                    Spacer(Modifier.height(30.dp))
                }
                items(generations.value){
                    GenerationCard(it) {
                        networkViewModel.videoLink.value = it.videoLink
                        networkViewModel.date.value = it.date
                        networkViewModel.videoHeader.value = it.prompt
                        navController.navigate(VIDEO)
                    }
                }
            }
        }
    }
}

@Composable
fun GenerationCard(
    generation: Generation,
    onClick: () -> Unit
) {


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(generation.posterLink)
            .crossfade(true)
            .build()
    )

    Column(
        Modifier.padding(horizontal = 8.dp, vertical = 12.dp).fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(
            grey
        ).padding(16.dp).noRippleClickable { onClick() },
    ) {
        Image(painter, "",
            Modifier.sizeIn(maxHeight = 400.dp).clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillBounds)
        Spacer(Modifier.height(15.dp))
        Text(generation.prompt, color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(Modifier.height(5.dp))
        Text(generation.date.replace("-", "."), color = lightGrey, fontSize = 14.sp)
    }
}