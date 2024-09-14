package com.programming.productservice;

import com.programming.productservice.dto.ProductRequest;
import com.programming.productservice.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongdb.url", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	@Order(1)
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String stringProductRequest = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stringProductRequest)
		).andExpect(status().isCreated());
		Assertions.assertEquals(1,productRepository.findAll().size());
	}

//    @Test
//	@Order(2)
//	void testGetProduct() throws Exception {
//		mockMvc.perform(
//				MockMvcRequestBuilders.get("/api/product")
//		).andExpect(status().isOk());
//		Assertions.assertEquals(1,productRepository.findAll().size());
//	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iphone13")
				.description("for unit testing")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
