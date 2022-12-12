package dev.jpvillegas.bbcnewsrss.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItemEntity

class FeedItemListConverter {

    @TypeConverter
    fun fromString(value: String?): List<FeedItemEntity> =
        if (value == null) {
            emptyList()
        } else {
            val type = object : TypeToken<List<FeedItemEntity>>() {}.type
            Gson().fromJson(value, type)
        }

    @TypeConverter
    fun fromList(list: List<FeedItemEntity>?): String =
        if (list == null) {
            "{}"
        } else {
            Gson().toJson(list)
        }
}