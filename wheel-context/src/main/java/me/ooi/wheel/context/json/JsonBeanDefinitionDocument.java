package me.ooi.wheel.context.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class JsonBeanDefinitionDocument {
	
	@SerializedName("beans")
	private List<JsonBeanDefinition> jsonBeanDefinitions ; 

}
