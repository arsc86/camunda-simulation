package com.tesis.automation.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utils {
	
	@SuppressWarnings("deprecation")
	public static <T> List<T> mapearListObjDeserializado(Object objDeserializado, Class<T> tipoValueClass) throws Exception {
		List<T> lista = new ArrayList<>();
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			lista = objMapper.readValue(objMapper.writeValueAsString(objDeserializado),
					objMapper.getTypeFactory().constructCollectionType(List.class, tipoValueClass));
		} catch (Exception e) {
			throw new Exception("Error en formato mapearListObjDeserializado " + e.getMessage());
		}
		return lista;
	}
	
	@SuppressWarnings("deprecation")
	public static <T> T mapearObjDeserializado(Object objDeserializado, Class<T> tipoValueClass) {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		objMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(objDeserializado, tipoValueClass);
	}
	

}
