package video.generator.ai.ui.screens.main

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import video.generator.ai.R
import kotlinx.coroutines.launch
import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.primex.core.ExperimentalToolkitApi
import video.generator.ai.ui.theme.green
import video.generator.ai.ui.viewmodel.NetworkViewModel
import video.generator.ai.utils.VideoCacheManager
import video.generator.ai.utils.saveVideoFromUrl

@Composable
fun VideoScreen(
    networkViewModel: NetworkViewModel,
    navController: NavController
) {

    val link = networkViewModel.videoLink.collectAsState()
    val date = networkViewModel.date.collectAsState()
    val header = networkViewModel.videoHeader.collectAsState()
//    val header = remember { mutableStateOf("Cat head in the green glasses") }
//    val date = remember { mutableStateOf("17.11.2024") }
//    val link = remember { mutableStateOf(TEST_VIDEO) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                coroutineScope.launch{
                    saveVideoFromUrl(context, link.value)
                }
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Box(Modifier.fillMaxSize()){
        BlurredVideo(link.value)
        Column (
            Modifier
                .fillMaxSize()
                .background(Color(0xFF0F0F0F).copy(alpha = 0f))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LazyColumn {
                item{
                    Row (Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                        IconButton({
                            networkViewModel.generated.value = false
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Rounded.ArrowBack, "",
                                tint = Color.White)
                        }
                        Spacer(Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(20.dp))
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(header.value, fontSize = 22.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(3.dp))
                        Text(date.value.replace("-", "."), fontSize = 13.sp, color = Color.White.copy(alpha = 0.3f))
                    }
                    Spacer(Modifier.height(35.dp))
                    Exoplayer(link.value)
                    Spacer(Modifier.height(10.dp))
                    Row (
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Button({
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
                            } else {
                                coroutineScope.launch{
                                    saveVideoFromUrl(context, link.value)
                                }
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = green
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(6.dp).weight(1f)) {
                            Icon(ImageVector.vectorResource(R.drawable.save_btn), "",
                                Modifier.size(16.dp))
                            Spacer(Modifier.width(5.dp))
                            Text(stringResource(R.string.save_btn), fontSize = 15.sp)
                        }
                        Button({
                            shareContent(context, link.value)
                        },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = green
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(6.dp).weight(1f)) {
                            Icon(ImageVector.vectorResource(R.drawable.share_btn), "",
                                Modifier.size(16.dp))
                            Spacer(Modifier.width(5.dp))
                            Text(stringResource(R.string.save_btn), fontSize = 15.sp)
                        }
                    }
                }
            }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalToolkitApi::class)
@Composable
fun BlurredVideo(
    link: String
) {

    val context = LocalContext.current


    val mediaItem = MediaItem.Builder()
        .setUri(link)
        .build()
    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(VideoCacheManager.getDataSourceFactory(context)))
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            }
    }

    Box(Modifier.fillMaxSize()){
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    useController = false // Отключаем контролы
                }
            },
            modifier = Modifier
                .fillMaxSize()

        )
        Box(Modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))){
        }
    }

}



@Composable
fun Exoplayer(
    link: String
) {

    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri(link)
        .build()
    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(VideoCacheManager.getDataSourceFactory(context)))
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            }
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false // Отключаем контролы
                    background = ColorDrawable(android.graphics.Color.TRANSPARENT)

                }
            },
            modifier = Modifier
                .sizeIn(maxHeight = 400.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    }

}

fun shareContent(context: Context, content: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}