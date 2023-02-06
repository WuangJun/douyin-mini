package com.douyin.exception;

/**
 * @author chendezhi
 */
public enum CommonServerErrorConstants implements LocalError {

	HTTP_400(400, "错误的请求"),
	HTTP_404(404, "资源未找到"),
	HTTP_405(405, "方法不允许"),
	HTTP_406(406, "未接受的媒体类型"),
	HTTP_415(415, "不支持的媒体类型"),
	SERVER_INNER_ERROR(500, "服务器内部错误");

	private Integer code;
	private String message;

	private CommonServerErrorConstants(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "[" + code + "] " + message;
	}
}
