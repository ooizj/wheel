package me.ooi.wheel.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class GsonUtils {

	/**
	 * 改进Gson的反序列化操作（解决Gson在处理数字的时候自动转为了浮点数的问题）
	 * 
	 * @author jun.zhao
	 * @since 1.0
	 */
	public static class OptimizeJsonDeserializer<T> implements JsonDeserializer<T> {

		@SuppressWarnings("unchecked")
		@Override
		public T deserialize(JsonElement json, Type typeOfTemplate, JsonDeserializationContext context)
				throws JsonParseException {
			return (T) decodeJsonElement(json);
		}

		private LinkedTreeMap<String, Object> decodeJsonObject(JsonObject jsonObject) {
			LinkedTreeMap<String, Object> treeMap = new LinkedTreeMap<String, Object>();
			for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				treeMap.put(entry.getKey(), decodeJsonElement(entry.getValue()));
			}
			return treeMap;
		}

		private List<Object> decodeJsonArray(JsonArray jsonArray) {
			List<Object> list = new ArrayList<Object>();
			for (JsonElement jsonElement : jsonArray) {
				list.add(decodeJsonElement(jsonElement));
			}
			return list;
		}

		private Object decodeJsonPrimitive(JsonPrimitive jsonPrimitive) {
			if (jsonPrimitive.isJsonNull()) {
				return null;
			} else {
				return decodeNotNullJsonPrimitive(jsonPrimitive);
			}
		}

		private Object decodeNotNullJsonPrimitive(JsonPrimitive jsonPrimitive) {
			if (jsonPrimitive.isJsonArray()) {
				return decodeJsonArray(jsonPrimitive.getAsJsonArray());
			} else if (jsonPrimitive.isJsonObject()) {
				return decodeJsonObject(jsonPrimitive.getAsJsonObject());
			} else {
				return decodeBaseJsonPrimitive(jsonPrimitive);
			}
		}

		private Object decodeBaseJsonPrimitive(JsonPrimitive jsonPrimitive) {
			if (jsonPrimitive.isBoolean()) {
				return jsonPrimitive.getAsBoolean();
			} else if (jsonPrimitive.isString()) {
				return jsonPrimitive.getAsString();
			} else {
				return decodeBaseJsonPrimitive2(jsonPrimitive);
			}
		}

		private Object decodeBaseJsonPrimitive2(JsonPrimitive jsonPrimitive) {
			if (jsonPrimitive.isNumber()) {
				return decodeNumberJsonPrimitive(jsonPrimitive);
			} else {
				return jsonPrimitive;
			}
		}

		private Object decodeNumberJsonPrimitive(JsonPrimitive jsonPrimitive) {
			if (jsonPrimitive.getAsString().contains(".")) {
				return jsonPrimitive.getAsDouble();
			} else {
				return jsonPrimitive.getAsLong();
			}
		}

		private Object decodeJsonElement(JsonElement jsonElement) {
			Object ret = null;
			if (jsonElement.isJsonArray()) {
				ret = decodeJsonArray(jsonElement.getAsJsonArray());
			} else if (jsonElement.isJsonObject()) {
				ret = decodeJsonObject(jsonElement.getAsJsonObject());
			} else if (jsonElement.isJsonPrimitive()) {
				ret = decodeJsonPrimitive(jsonElement.getAsJsonPrimitive());
			}
			return ret;
		}

	}

	/**
	 * 在使用Gson反序列化时，将需要转为Date类型的字面类型为long的转为Date
	 * 
	 * @author jun.zhao
	 * @since 1.0
	 */
	public static class DateJsonDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement json, Type typeOfTemplate, JsonDeserializationContext context)
				throws JsonParseException {
			return new Date(json.getAsJsonPrimitive().getAsLong());
		}

	}
	
	public static Gson createGson(){
		//处理Gson.fromJson()将数字转为小数问题
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Map.class, new GsonUtils.OptimizeJsonDeserializer<LinkedTreeMap<String, Object>>());
        return builder.create();
	}

}
