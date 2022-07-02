package com.amazonaws.lambda.ec2manager.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class CommonUtility {

	public static ObjectMapper getObjectMapper() {

		return JsonMapper.builder().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).enable(SerializationFeature.INDENT_OUTPUT)
				.build();
	}
	

	public static String convertEntity2Json(Object entity) {

		if (entity == null) {
			return null;
		}

		try {
			return getObjectMapper().writer().writeValueAsString(entity);
		} catch (Exception e) {
			throw new RuntimeException("Error converting object to Json", e);
		}
	}

	public static <T> T parseJsonString(InputStream inputStream, Class<?> clazz) {

		try {

			ObjectMapper mapper = getObjectMapper();
			return mapper.readerFor(clazz).readValue(toString(inputStream));

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T parseJsonString(String jsonStr, Class<?> clazz) {

		try {

			ObjectMapper mapper = getObjectMapper();
			return mapper.readerFor(clazz).readValue(jsonStr);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseJsonString(String jsonStr, TypeReference<T> requiredType) {

		try {

			ObjectMapper mapper = getObjectMapper();
			return mapper.readerFor(requiredType).readValue(jsonStr);

		} catch (JsonProcessingException e) {
			throw new RuntimeException( e);
		}
	}


	public static String toString(InputStream inputStream) {

		// IOUtils.toString(is, Charset.defaultCharset());
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
	}
	
//	public static HttpRequestBase prepareRequest(HttpEntityEnclosingRequestBase httpRequest, String requestUrl,
//			Object entityValue) {
//
//		prepareRequest(httpRequest, requestUrl);
//		setRequestEntity(httpRequest, entityValue);
//		return httpRequest;
//	}
//
//	public static HttpRequestBase prepareRequest(HttpRequestBase httpRequest, String requestUrl) {
//
//		setRequestUrl(httpRequest, requestUrl);
//
//		httpRequest.setHeader("Content-Type", "application/json");
//		httpRequest.setHeader("Accept", "application/json");
//
//
//		return httpRequest;
//	}
//
//	public static void setRequestEntity(HttpEntityEnclosingRequestBase httpRequest, Object entityValue) {
//
//		if (entityValue == null) {
//			return;
//		}
//
//		String entityJsonString = "";
//		if ((entityValue instanceof String)) {
//			entityJsonString = (String) entityValue;
//		} else {
//			entityJsonString = convertEntity2Json(entityValue);
//		}
//		httpRequest.setEntity(new StringEntity(entityJsonString, StandardCharsets.ISO_8859_1));
//	}
//
//
//	public static HttpRequestBase setRequestUrl(HttpRequestBase httpRequest, String requestUrl) {
//
//		if (StringUtils.isEmpty(requestUrl)) {
//			throw new RuntimeException("Invalid request URL");
//		}
//
//		try {
//			httpRequest.setURI(new URI(requestUrl));
//			return httpRequest;
//		} catch (URISyntaxException e) {
//			throw new RuntimeException("Error setting HTTP request URL", e);
//		}
//	}
//	
//
//	public static CloseableHttpClient getHttpClient(int timeoutInSecond) {
//
//		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeoutInSecond * 1000)
//				.setConnectionRequestTimeout(timeoutInSecond * 1000).setSocketTimeout(timeoutInSecond * 1000).build();
//		return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//	}

}
