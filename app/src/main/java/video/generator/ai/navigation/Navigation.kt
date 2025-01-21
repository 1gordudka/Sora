package video.generator.ai.navigation

import androidx.compose.runtime.Composable

//val HISTORY = 0
//val CAPTURE = 1
//val PROFILE = 2
//val GENERATION = -1
//val VIDEO = -2

@Composable
fun Navigation() {
//
//    var screen = remember { mutableStateOf(CAPTURE) }
//
//    Box(Modifier.fillMaxSize().background(darkGrey)){
//        AnimatedVisibility(screen.value == CAPTURE,
//            enter = fadeIn(tween(500))
//        ) {
//            MainScreen { prompt, ratio->
//                screen.value = GENERATION
//            }
//        }
//
//        AnimatedVisibility(screen.value == GENERATION,
//            enter = fadeIn(tween(500))
//        ) {
//            GenerationScreen() {
//                screen.value = VIDEO
//            }
//        }
//        AnimatedVisibility(screen.value == VIDEO,
//            enter = fadeIn(tween(500))
//        ) {
//            VideoScreen{
//                screen.value = CAPTURE
//            }
//        }
//        AnimatedVisibility(screen.value == PROFILE,
//            enter = fadeIn(tween(500))
//        ) {
//
//        }
//        AnimatedVisibility(screen.value == HISTORY,
//            enter = fadeIn(tween(500))
//        ) {
//
//        }
//        AnimatedVisibility(screen.value == CAPTURE || screen.value == HISTORY || screen.value == PROFILE,
//            enter = fadeIn(tween(500)), exit = fadeOut(tween(500))
//        ) {
//            BottomBar(screen.value) {
//                screen.value = it
//            }
//        }
//    }
}