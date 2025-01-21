package video.generator.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import video.generator.ai.R
import video.generator.ai.navigation.CAPTURE
import video.generator.ai.navigation.HISTORY
import video.generator.ai.navigation.PROFILE
import video.generator.ai.ui.theme.darkGrey
import video.generator.ai.ui.theme.green
import video.generator.ai.ui.theme.grey
import video.generator.ai.ui.theme.lightGrey


data class BottomNavItem(val route: String, val title: String, val icon: Int)

@Composable
fun BottomBar(
    navController: NavController
) {

    val items = listOf(
        BottomNavItem(HISTORY, stringResource(R.string.history), R.drawable.history),
        BottomNavItem(CAPTURE, stringResource(R.string.capture), R.drawable.capture),
        BottomNavItem(PROFILE, stringResource(R.string.profile), R.drawable.profile)
    )


    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        Modifier
            .background(if (currentDestination == CAPTURE) Color.Black else darkGrey)
            .padding(horizontal = 25.dp)
            .padding(bottom = 25.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(grey)
            .padding(4.dp)
    ) {
        items.forEach(){item->
            Box(Modifier.weight(1f)
                .fillMaxHeight().clip(RoundedCornerShape(16.dp))
                .background(if (currentDestination == item.route) green.copy(alpha = 0.3f) else green.copy(alpha = 0f))
                .noRippleClickable{
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }){
                Column (
                    modifier = Modifier.align(Alignment.Center).fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Icon(ImageVector.vectorResource(item.icon), "",
                        Modifier.size(30.dp), tint = if (currentDestination == item.route) green else lightGrey
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(item.title, fontSize = 9.sp, color = if (currentDestination == item.route) green else lightGrey)
                }
            }
        }
    }
}