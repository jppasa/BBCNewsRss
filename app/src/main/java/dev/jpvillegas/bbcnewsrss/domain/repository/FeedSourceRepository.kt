package dev.jpvillegas.bbcnewsrss.domain.repository

interface FeedSourceRepository {
    fun sourceUrls() : List<String>
}