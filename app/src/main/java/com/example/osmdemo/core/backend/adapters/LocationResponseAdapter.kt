import com.example.osmdemo.map.data.model.LocationResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class LocationResponseAdapter : JsonDeserializer<LocationResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocationResponse {
        val jsonObject = json.asJsonObject

        return if (jsonObject.has("StopLocation")) {
            context.deserialize(jsonObject.get("StopLocation"), LocationResponse.StopLocation::class.java)
        } else if (jsonObject.has("CoordLocation")) {
            context.deserialize(jsonObject.get("CoordLocation"), LocationResponse.CoordLocation::class.java)
        } else {
            throw JsonParseException("Unknown type of location")
        }
    }
}
