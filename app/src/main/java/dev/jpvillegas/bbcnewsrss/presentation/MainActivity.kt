package dev.jpvillegas.bbcnewsrss.presentation

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jpvillegas.bbcnewsrss.domain.utils.decoded
import dev.jpvillegas.bbcnewsrss.domain.utils.encodedLink
import dev.jpvillegas.bbcnewsrss.presentation.rss_feed.RssFeedScreen
import dev.jpvillegas.bbcnewsrss.presentation.rss_list.RssFeedListScreen
import dev.jpvillegas.bbcnewsrss.presentation.ui.theme.BBCNewsRssTheme
import dev.jpvillegas.bbcnewsrss.presentation.web_view_screen.WebViewScreen
import java.net.URLDecoder
import java.net.URLEncoder

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
                    // Improvement: Use Google's Accompanist lib to add screen transitions
                    // https://google.github.io/accompanist/navigation-animation/
                    
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RssFeedListScreen.route
                    ) {
                        composable(
                            route = Screen.RssFeedListScreen.route
                        ) {
                            RssFeedListScreen(onFeedClick = { feed ->
                                navController.navigate(Screen.RssFeedScreen.route + "/${feed.id}")
                            })
                        }
                        composable(
                            route = Screen.RssFeedScreen.route + "/{${Screen.PARAM_FEED_ID}}"
                        ) {
                            RssFeedScreen(
                                onBackClicked = {
                                    navController.popBackStack()
                                },
                                onFeedItemClicked = { rssFeedItem ->
                                    navController.navigate(Screen.WebViewScreen.route + "/${rssFeedItem.encodedLink()}")
                                }
                            )
                        }
                        composable(
                            route = Screen.WebViewScreen.route + "/{${Screen.PARAM_FEED_ITEM_LINK}}",
                        ) { navBackStackEntry ->
                            navBackStackEntry.arguments
                                ?.getString(Screen.PARAM_FEED_ITEM_LINK)
                                ?.decoded()?.let { link ->
                                    WebViewScreen(context = this@MainActivity, url = link)
                                }
                        }
                    }
                }
            }
        }
    }

}
