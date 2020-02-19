package itemsstuff

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import itemsstuff.cards.ArcheType
import itemsstuff.cards.RaceType
import itemsstuff.cards.TypeType
import okhttp3.OkHttpClient


/**
 * converts [this] to a Json string
 */
fun Any?.toJson(): String = Gson().toJson(this)

/**
 * converts [this] to a Json string but its formatted nicely
 */
fun Any?.toPrettyJson(): String = GsonBuilder().setPrettyPrinting().create().toJson(this)

fun <T> customListOf(count: Int, block: (Int) -> T) = mutableListOf<T>().apply { repeat(count) { this += block(it) } }.toList()

/**
 * Takes [this] and coverts it to an object
 */
/*inline fun <reified T> String?.fromJson(): T? = try {
    Gson().fromJson(this, object : TypeToken<T>() {}.type)
} catch (e: Exception) {
    null
}*/

val gson: Gson = GsonBuilder()
    .registerTypeAdapter(RaceType::class.java,
        JsonDeserializer<RaceType?> { json, _, _ -> RaceType(json.asString) })
    .registerTypeAdapter(TypeType::class.java,
        JsonDeserializer<TypeType?> { json, _, _ -> TypeType(json.asString) })
    .registerTypeAdapter(ArcheType::class.java,
        JsonDeserializer<ArcheType?> { json, _, _ -> ArcheType(json.asString) })
    .create()

inline fun <reified T> String?.fromJson(): T? = try {
    gson.fromJson(this, object : TypeToken<T>() {}.type)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun getApi(url: String): String? {
    val request = okhttp3.Request.Builder()
        .url(url)
        .get()
        .build()
    val response = OkHttpClient().newCall(request).execute()
    return if (response.code() == 200) response.body()!!.string() else null
}
