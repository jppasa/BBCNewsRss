package dev.jpvillegas.bbcnewsrss.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItem

class FeedItemListConverter {

    @TypeConverter
    fun fromString(value: String?): List<FeedItem> =
        if (value == null) {
            emptyList()
        } else {
            val type = object : TypeToken<List<FeedItem>>() {}.type
            Gson().fromJson(value, type)
        }

    @TypeConverter
    fun fromList(list: List<FeedItem>?): String =
        if (list == null) {
            "{}"
        } else {
            Gson().toJson(list)
        }
}