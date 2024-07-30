package vtt.asm.rest.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.websocket.server.PathParam;
import vtt.asm.service.ParamService;

@RestController
public class UploadRestController {
	@Autowired
	ParamService paramService;

	@PostMapping("/rest/upload")
	public JsonNode upload(@PathParam("file") MultipartFile file) {
		String folder = "/img/product/";
		File saveFile = paramService.save(file, folder);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", saveFile.getName());
		node.put("size", saveFile.length());
		return node;
	}
}
