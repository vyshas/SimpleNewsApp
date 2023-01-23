package com.vyshas.newsapp.core.data.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import kotlinx.datetime.Instant

class InstantJsonAdapter : JsonAdapter<Instant>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Instant {
        return Instant.parse(reader.nextString())
    }

    override fun toJson(writer: JsonWriter, value: Instant?) {
        writer.value(value.toString())
    }
}
