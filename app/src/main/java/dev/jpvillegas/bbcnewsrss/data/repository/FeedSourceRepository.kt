package dev.jpvillegas.bbcnewsrss.data.repository

import dev.jpvillegas.bbcnewsrss.common.Constants
import dev.jpvillegas.bbcnewsrss.domain.repository.SourceRepository

class FeedSourceRepository : SourceRepository {
    override fun sources(): List<String> {
        return Constants.rssFeedUrls
    }
}