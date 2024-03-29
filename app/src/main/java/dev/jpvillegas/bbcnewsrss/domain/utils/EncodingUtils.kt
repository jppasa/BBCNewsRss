package dev.jpvillegas.bbcnewsrss.domain.utils

import dev.jpvillegas.bbcnewsrss.domain.model.FeedItem
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

fun FeedItem.encodedLink(): String? {
    return if (link == null) null
    else try {
        URLEncoder.encode(link, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        null
    }
}

fun String.decoded(): String? {
    return try {
        URLDecoder.decode(this, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        null
    }
}