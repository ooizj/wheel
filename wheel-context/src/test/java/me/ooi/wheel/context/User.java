package me.ooi.wheel.context;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class User {

	private String name ; 
	
	private List<String> label ; 
	
	private Map<String, String> attr ; 
	
	private Department department ; 
	
}
