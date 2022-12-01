package dev.jpvillegas.bbcnewsrss.presentation

sealed class Screen(val route: String) {
    object RssFeedListScreen : Screen("rss_feed_list_screen")
    object RssFeedScreen : Screen("rss_feed_screen")
    object WebViewScreen : Screen("web_view_screen")

    companion object {
        const val PARAM_FEED_ID = "feedId"
        const val PARAM_FEED_ITEM_LINK = "feedItemLink"
    }
}
