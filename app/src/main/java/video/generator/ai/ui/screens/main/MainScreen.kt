package video.generator.ai.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import video.generator.ai.R
import video.generator.ai.data.network.api.model.request.GenerateRequest
import video.generator.ai.data.network.api.model.request.Options
import video.generator.ai.data.network.api.model.request.Parameters
import video.generator.ai.navigation.GENERATION
import video.generator.ai.ui.components.noRippleClickable
import video.generator.ai.ui.theme.green
import video.generator.ai.ui.theme.grey
import video.generator.ai.ui.viewmodel.NetworkViewModel

val ratios = listOf("16:9", "9:16", "1:1", "5:2", "4:5", "4:3")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    networkViewModel: NetworkViewModel,
    navController: NavController,
    isSub: Boolean,
    getSub: () -> Unit
) {

    var ratio = remember { mutableStateOf(0) }
    var expanded = remember { mutableStateOf(false) }
    var prompt = remember { mutableStateOf("") }
    val context = LocalContext.current
    val isSuccess = networkViewModel.isSuccess.collectAsState()

    LaunchedEffect(isSuccess.value) {
        if (isSuccess.value == true){
            navController.navigate(GENERATION)
            networkViewModel.isSuccess.value = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Column(
            Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(grey.copy(alpha = 0.3f), grey.copy(alpha = 0f))
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        stringResource(R.string.model_desc), fontSize = 15.sp, fontWeight = FontWeight.Bold,
                        color = Color.White)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.main_desc), fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.3f))
                }
                Spacer(Modifier.weight(1f))
                Column {
                    Row(
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(grey)
                            .padding(horizontal = 15.dp, vertical = 10.dp)
                            .noRippleClickable {
                                expanded.value = true
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${ratios[ratio.value]}", fontSize = 15.sp,
                            color = Color.White)
                    }
                    DropdownMenu(expanded.value, {expanded.value = false},
                        modifier = Modifier.background(grey)
                            .width(100.dp).clip(RoundedCornerShape(16.dp))) {
                        ratios.forEach { item->
                            DropdownMenuItem(
                                text = {Text(item, color = Color.White)},
                                onClick = {
                                    ratio.value = ratios.indexOf(item)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(prompt.value, {prompt.value = it},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = grey,
                    focusedBorderColor = grey,
                    unfocusedBorderColor = grey,
                    cursorColor = Color.White
                ),
                placeholder = {
                    Text(stringResource(R.string.generate_tf), fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.4f))
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp))
            Spacer(Modifier.height(10.dp))
            Button({
                if (isSub){
                    networkViewModel.generateVideo(
                        GenerateRequest(
                            options = Options(
                                frameRate = 24,
                                parameters = Parameters(
                                    guidanceScale = 15,
                                    motion = 2
                                ),
                                aspectRatio = ratios[ratio.value]
                            ),
                            promptText = prompt.value
                        ),
                        context = context
                    )
                }else{
                    getSub()
                }

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = green,
                    contentColor = Color.Black
                )) {
                Icon(ImageVector.vectorResource(R.drawable.magic), "", Modifier.size(16.dp),
                    tint = Color.Black)
                Spacer(Modifier.width(5.dp))
                Text(stringResource(R.string.generate_btn), fontSize = 15.sp)
            }
        }
    }
}
