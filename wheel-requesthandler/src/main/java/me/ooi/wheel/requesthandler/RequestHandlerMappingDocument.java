package me.ooi.wheel.requesthandler;

import java.util.List;

import lombok.Data;

/**
 * “HTTP请求” -> “处理HTTP请求的方法” 映射关系配置文档
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class RequestHandlerMappingDocument {
	
	private List<RequestHandlerMapping> requestHandlerMappings ; 

}
