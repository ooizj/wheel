package me.ooi.wheel.context.json;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class JsonBeanDefinition {
	
	private String name ; 
	@SerializedName("class")
	private String type ; 
	private String scope ; 
	private Map<String, Object> properties ; 

}
