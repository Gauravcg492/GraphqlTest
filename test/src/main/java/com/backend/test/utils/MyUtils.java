package com.backend.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MyUtils {
	
	public String fileToString(String filePath) throws IOException {
		File file = ResourceUtils.getFile(filePath);
	    InputStream in = new FileInputStream(file);
	    return IOUtils.toString(in, StandardCharsets.UTF_8);
	}
	
	public <T> T jsonFileToType(String filePath, Class<T> type) throws IOException {
		String jsonStr = fileToString(filePath);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, type);
		
	}

}
