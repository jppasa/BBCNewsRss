package dev.jpvillegas.bbcnewsrss.presentation

sealed class Screen(val route: String) {
    object RssFeedListScreen : Screen("rss_feed_list_screen")
    object RssFeedScreen : Screen("rss_feed_screen")
}
