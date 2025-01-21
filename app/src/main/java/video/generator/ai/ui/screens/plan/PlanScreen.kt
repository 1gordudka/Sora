package video.generator.ai.ui.screens.plan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.billingclient.api.ProductDetails
import video.generator.ai.data.network.server.statusYES
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.impl.fa
import video.generator.ai.R
import video.generator.ai.navigation.CAPTURE
import video.generator.ai.navigation.PLAN
import video.generator.ai.subscription.ChooseSubscription
import video.generator.ai.subscription.WEEKLY
import video.generator.ai.subscription.WEEKLY_TRIAL
import video.generator.ai.subscription.YEARLY
import video.generator.ai.subscription.YEARLY_TRIAL
import video.generator.ai.ui.theme.green
import video.generator.ai.ui.theme.lightGrey


@Composable
fun PlanScreen(
    navController: NavController,
    chooseSubscription: ChooseSubscription,
    status: String,
    availablePlans: List<ProductDetails.SubscriptionOfferDetails>,
    isSub: Boolean
) {

    var plan = remember { mutableStateOf(-1) }
    val bonuses = listOf(
        stringResource(R.string.bonus1),
        stringResource(R.string.bonus2),
        stringResource(R.string.bonus3),
        stringResource(R.string.bonus4),
        stringResource(R.string.bonus5)
    )


    Column (
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.weight(1f))
            if (status == statusYES){
                IconButton({
                    navController.navigate(CAPTURE){
                        popUpTo(PLAN){
                            inclusive = true
                        }
                    }
                }) {
                    Icon(Icons.Rounded.Close, "",
                        Modifier.size(24.dp), tint = Color.White)
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Image(painterResource(R.drawable.icon), "",
                    Modifier.size(55.dp).clip(RoundedCornerShape(16.dp)))
                Spacer(Modifier.width(10.dp))
                Text("AI Searcher", color = Color.White.copy(alpha = 0.8f),
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 8.dp))
            }
            Spacer(Modifier.height(7.dp))
            Text(
                stringResource(R.string.by_purchasing),
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp)
            Spacer(Modifier.height(10.dp))
            bonuses.forEach{bonus->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Box(modifier = Modifier.size(24.dp).clip(RoundedCornerShape(16.dp)).background(green), contentAlignment = Alignment.Center){
                        Icon(ImageVector.vectorResource(R.drawable.tick), "", tint = Color.White,
                            modifier = Modifier.size(16.dp))
                    }
                    Spacer(Modifier.width(7.dp))
                    if (bonus == stringResource(R.string.bonus5) && availablePlans.isNotEmpty()){
                        Text(bonus + " " + availablePlans[0].pricingPhases.pricingPhaseList[0].formattedPrice,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp)
                    }else{
                        Text(bonus,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            if (status == statusYES){
                if (availablePlans.isNotEmpty() && isSub == false){
                    OutlinedButton({
                        plan.value = 0
                        AppMetrica.reportEvent("open-paywall")
                        chooseSubscription.checkSubscriptionStatus(YEARLY)

                    },
                        modifier = Modifier.width(250.dp)
                            .border(1.5.dp, if (plan.value == 0) green else Color.White, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        )) {
                        Row(
                            Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(stringResource(R.string.annually),
                                    fontSize = 13.sp,
                                    color = Color.Black.copy(alpha = 0.7f))
                                Text(
                                    availablePlans[3].pricingPhases.pricingPhaseList[0].formattedPrice, fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    OutlinedButton({
                        plan.value = 1
                        AppMetrica.reportEvent("open-paywall")
                        chooseSubscription.checkSubscriptionStatus(WEEKLY)

                    },
                        modifier = Modifier.width(250.dp)
                            .border(1.5.dp, if (plan.value == 1) green else Color.White, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        )) {
                        Row(
                            Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(stringResource(R.string.weekly),
                                    fontSize = 13.sp,
                                    color = Color.Black.copy(alpha = 0.7f))
                                Text(availablePlans[1].pricingPhases.pricingPhaseList[0].formattedPrice, fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        if (isSub == false){
            Button({
                if (status == statusYES){
                    navController.navigate(CAPTURE){
                        popUpTo(PLAN){
                            inclusive = true
                        }
                    }

                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = green
                )
            ) {
                Text(if (status == statusYES) stringResource(R.string.continue_for_free) else stringResource(R.string.continuee), fontSize = 15.sp)
            }
        }
        Spacer(Modifier.height(15.dp))
        Row(
            Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.restore), color = lightGrey, fontSize = 12.sp)
            Spacer(Modifier.weight(1f))
            Text(stringResource(R.string.terms), color = lightGrey, fontSize = 12.sp)
            Spacer(Modifier.weight(1f))
            Text(stringResource(R.string.privacy_policy), color = lightGrey, fontSize = 12.sp)
        }
    }
}