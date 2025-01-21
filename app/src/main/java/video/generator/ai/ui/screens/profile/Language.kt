package video.generator.ai.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import video.generator.ai.ui.components.noRippleClickable
import video.generator.ai.ui.theme.darkGrey
import video.generator.ai.ui.theme.grey
import video.generator.ai.R

data class Language(
    val name: String,
    val second: String,
)

val languages = listOf(
    Language("English", "English"),
    Language("Russian", "Русский")
)

@Composable
fun LanguageScreen(
    navController: NavController
) {

    var chosen = remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(darkGrey)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.fillMaxWidth()) {
            IconButton({
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Rounded.ArrowBack, "",
                    tint = Color.White.copy(alpha = 0.8f), modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )
            }
            Text(
                stringResource(R.string.language), fontSize = 16.sp,
                fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(grey)
                .padding(16.dp)
        ) {
            item{
                LanguageCard(languages[0], chosen.value == 0) {
                    chosen.value = 0
                }
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.2f),
                    thickness = 0.5.dp
                )
                LanguageCard(languages[1], chosen.value == 1) {
                    chosen.value= 1
                }
            }
        }
    }
}

@Composable
fun LanguageCard(
    language: Language,
    chosen: Boolean,
    choose: () -> Unit
) {
    Row(
        Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .noRippleClickable { choose() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(language.name, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            Spacer(Modifier.height(3.dp))
            Text(language.name, fontSize = 14.sp, color = Color.White.copy(alpha = 0.5f))
        }
        Spacer(Modifier.weight(1f))
        if (chosen){
            Icon(ImageVector.vectorResource(R.drawable.tick), "",
                Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        }
    }
}