package dev.jpvillegas.bbcnewsrss.data.repository

import dev.jpvillegas.bbcnewsrss.common.Constants
import dev.jpvillegas.bbcnewsrss.domain.repository.FeedSourceRepository

class FeedSourceRepositoryImpl : FeedSourceRepository{
    override fun sourceUrls(): List<String> {
        return Constants.rssFeedUrls
    }
}