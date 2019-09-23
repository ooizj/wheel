package me.ooi.wheel.requesthandler;

/**
 * “HTTP请求” -> “处理HTTP请求的方法” 映射关系配置文档
 * @author jun.zhao
 * @since 1.0
 */
public interface RequestHandlerMappingDocumentProvider {
	
	void init(RequestHandlerMappingDocument document) ; 

}
