package video.generator.ai.navigation

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.appmetrica.analytics.AppMetrica
import video.generator.ai.subscription.ChooseSubscription
import video.generator.ai.subscription.WEEKLY
import video.generator.ai.subscription.WEEKLY_TRIAL
import video.generator.ai.subscription.YEARLY
import video.generator.ai.subscription.YEARLY_TRIAL
import video.generator.ai.ui.components.BottomBar
import video.generator.ai.ui.screens.history.HistoryScreen
import video.generator.ai.ui.screens.main.GenerationScreen
import video.generator.ai.ui.screens.main.MainScreen
import video.generator.ai.ui.screens.main.VideoScreen
import video.generator.ai.ui.screens.plan.PlanScreen
import video.generator.ai.ui.screens.profile.AboutAppScreen
import video.generator.ai.ui.screens.profile.LanguageScreen
import video.generator.ai.ui.screens.profile.ProfileScreen
import video.generator.ai.ui.theme.darkGrey
import video.generator.ai.ui.viewmodel.GenerationViewModel
import video.generator.ai.ui.viewmodel.NetworkViewModel
import video.generator.ai.ui.viewmodel.PreferencesViewModel

@Composable
fun Nav(
    networkViewModel: NetworkViewModel = hiltViewModel(),
    generationViewModel: GenerationViewModel = hiltViewModel(),
    appPreferencesViewModel: PreferencesViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    val screensWithBottomBar = listOf(CAPTURE, HISTORY, PROFILE)
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val status = networkViewModel.status.collectAsState()

    val context = LocalContext.current as Activity


    val pay = appPreferencesViewModel.pay.collectAsState()
    val launch = appPreferencesViewModel.launch.collectAsState()

   if (pay.value != null){
       val chooseSubscription = remember {
           ChooseSubscription(context, pay = pay.value == true, changePay = {
               appPreferencesViewModel.changePay()
           })
       }

       LaunchedEffect(launch.value) {
           if (launch.value != null){
               if (launch.value == false){
                   AppMetrica.reportEvent("first-launch")
                   appPreferencesViewModel.launchApp()
               }
           }
       }

       LaunchedEffect(key1 = true) {
           chooseSubscription.billingSetup()
           chooseSubscription.hasSubscription()
       }
       val currentSubscription by chooseSubscription.subscriptions.collectAsState()
       val availablePlans by chooseSubscription.availablePlans.collectAsState()

       Scaffold(
           bottomBar = {
               AnimatedVisibility(
                   visible = currentDestination in screensWithBottomBar,
                   modifier = Modifier.background(darkGrey),
                   enter = EnterTransition.None,
                   exit = ExitTransition.None
               ) {
                   BottomBar(navController)
               }
           },
           modifier = Modifier
               .background(if (currentDestination == CAPTURE || currentDestination == PLAN) Color.Black else darkGrey)
               .systemBarsPadding()
       ) { paddingValues ->

           NavHost(
               navController,
               startDestination = if (currentSubscription.contains(WEEKLY) || currentSubscription.contains(
                       YEARLY
                   ) || currentSubscription.contains(WEEKLY_TRIAL) || currentSubscription.contains(
                       YEARLY_TRIAL
                   )
               ) CAPTURE else PLAN,
               modifier = Modifier
                   .padding(paddingValues)
                   .background(darkGrey)
           )
           {

               composable(CAPTURE) {
                   MainScreen(navController = navController, networkViewModel = networkViewModel,
                       isSub = currentSubscription.contains(WEEKLY) || currentSubscription.contains(
                           YEARLY
                       ) || currentSubscription.contains(WEEKLY_TRIAL) || currentSubscription.contains(
                           YEARLY_TRIAL
                       ),
                       getSub = {
                           navController.navigate(PLAN)
                       })
               }
               composable(GENERATION) {
                   GenerationScreen(
                       navController = navController,
                       networkViewModel = networkViewModel,
                       generationViewModel = generationViewModel,

                       )
               }
               composable(VIDEO) {
                   VideoScreen(navController = navController, networkViewModel = networkViewModel)
               }
               composable(HISTORY) {
                   HistoryScreen(generationViewModel, navController, networkViewModel)
               }
               composable(PROFILE) {
                   ProfileScreen(navController, isSub =
                   currentSubscription.contains(WEEKLY) || currentSubscription.contains(
                       YEARLY
                   ) || currentSubscription.contains(WEEKLY_TRIAL) || currentSubscription.contains(
                       YEARLY_TRIAL
                   ))
               }
               composable(ABOUT) {
                   AboutAppScreen(navController)
               }
               composable(PLAN) {
                   if (status.value != "" && status.value != "error") {
                       PlanScreen(navController, chooseSubscription, status.value, availablePlans, isSub = currentSubscription.contains(WEEKLY) || currentSubscription.contains(
                           YEARLY
                       ) || currentSubscription.contains(WEEKLY_TRIAL) || currentSubscription.contains(
                           YEARLY_TRIAL
                       ))
                   } else {
                       Column(
                           Modifier
                               .fillMaxSize()
                               .background(Color.Black),
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           CircularProgressIndicator(
                               strokeCap = StrokeCap.Round,
                               color = Color.White,
                               trackColor = Color.Black
                           )
                       }
                   }
               }
               composable(LANGUAGE) {
                   LanguageScreen(navController)
               }
           }

       }
   }

}