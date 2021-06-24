package com.ian.payback.image.util

import com.google.gson.GsonBuilder
import com.ian.payback.image.domain.model.ImageInfo
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type


class CustomJsonConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return JsonConverter.INSTANCE
    }

    internal class JsonConverter : Converter<ResponseBody, List<ImageInfo>> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): List<ImageInfo> {
            try {

                val response = JSONObject(responseBody.string())
                val imageInfoList = response.getJSONArray("hits")
                val gson = GsonBuilder().create()

                return gson.fromJson(imageInfoList.toString(), Array<ImageInfo>::class.java)
                    .toList()

            } catch (e: JSONException) {
                throw IOException("Failed to parse JSON", e)
            }

        }

        companion object {
            val INSTANCE = JsonConverter()
        }
    }
}