package com.example.userapp.core.network.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import timber.log.Timber
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val DATE_FORMAT = "dd.MM.yyyy" // Формат для сериализации
private const val ISO_DATETIME_FORMAT =
    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX" // Формат для десериализации

object DateTimeTypeAdapter : JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
    override fun serialize(
        src: Calendar,
        typeOfSrc: Type,
        context: JsonSerializationContext?,
    ): JsonElement {
        val dateTimeFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return JsonPrimitive(dateTimeFormat.format(src.timeInMillis))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Calendar? {
        if (json == null) return null
        val jsonString = json.asJsonPrimitive.asString

        try {
            val formatter = SimpleDateFormat(ISO_DATETIME_FORMAT, Locale.getDefault())
            val formattedDate = formatter.parse(jsonString)
            val calendar = Calendar.getInstance()
            if (formattedDate != null) {
                calendar.time = formattedDate
            }
            return calendar
        } catch (e: ParseException) {
            Timber.tag("DateTimeTypeAdapter").e("Deserialize ParseException: ${e.message}")
        }
        return null
    }
}