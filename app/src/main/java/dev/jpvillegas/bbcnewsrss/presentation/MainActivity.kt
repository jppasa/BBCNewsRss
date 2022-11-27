package dev.jpvillegas.bbcnewsrss.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jpvillegas.bbcnewsrss.presentation.rss_list.RssFeedListScreen
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBCNewsRssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RssFeedListScreen.route
                    ) {
                        composable(
                            route = Screen.RssFeedListScreen.route
                        ) {
                            RssFeedListScreen(onFeedClick = {
                                //navController.navigate()
                            })
                        }
                        composable(
                            route = Screen.RssFeedScreen.route + "/{coinId}"
                        ) {
                            //CoinDetailScreen()
                        }
                    }
                }
            }
        }
    }
}
