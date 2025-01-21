package video.generator.ai.ui.screens.profile

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import video.generator.ai.R
import video.generator.ai.navigation.ABOUT
import video.generator.ai.navigation.LANGUAGE
import video.generator.ai.navigation.PLAN
import video.generator.ai.ui.components.noRippleClickable
import video.generator.ai.ui.theme.darkGrey
import video.generator.ai.ui.theme.green
import video.generator.ai.ui.theme.grey

@Composable
fun ProfileScreen(
    navController: NavController,
    isSub: Boolean
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(darkGrey)
            .padding(16.dp)
    ) {
        Column {
            Text(
                stringResource(R.string.profile),
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
        Spacer(Modifier.height(15.dp))
        LazyColumn {
            item{
                if (isSub == false){
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(green)
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.short_plan), color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(7.dp))
                        Text(stringResource(R.string.long_plan), fontSize = 12.sp)
                        Spacer(Modifier.height(7.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button({
                                navController.navigate(PLAN)
                            },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = green,
                                    containerColor = Color.Black
                                )) {
                                Text(stringResource(R.string.upgrade_btn))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(grey)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.noRippleClickable { navController.navigate(PLAN) }
                    ) {
                        Icon(ImageVector.vectorResource(R.drawable.magic), "", tint = green, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(5.dp))
                        Text(stringResource(R.string.ai_video), color = Color.White, fontSize = 13.sp)
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Rounded.KeyboardArrowRight, "", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.noRippleClickable { navController.navigate(LANGUAGE) }
                    ) {
                        Icon(ImageVector.vectorResource(R.drawable.language), "", tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(7.dp))
                        Text(stringResource(R.string.language), color = Color.White, fontSize = 13.sp)
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Rounded.KeyboardArrowRight, "", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(grey)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.noRippleClickable { navController.navigate(ABOUT) }
                    ) {
                        Icon(ImageVector.vectorResource(R.drawable.heart), "", tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(7.dp))
                        Text(stringResource(R.string.about), color = Color.White, fontSize = 13.sp)
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Rounded.KeyboardArrowRight, "", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(ImageVector.vectorResource(R.drawable.message), "", tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(7.dp))
                        Text(stringResource(R.string.support), color = Color.White, fontSize = 13.sp)

                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(ImageVector.vectorResource(R.drawable.thumb), "", tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(7.dp))
                        Text(stringResource(R.string.rate), color = Color.White, fontSize = 13.sp)

                    }
                }
            }
        }
    }
}