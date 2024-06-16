package com.project1.oauth.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project1.oauth.domain.ProductDomain;
import com.project1.oauth.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Shop", description = "상점 관련 API")
@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ShopController {

	private final ShopService shopservice;

	public ShopController(ShopService shopservice) {
		this.shopservice = shopservice;
	}

	@Operation(summary = "상품 목록")
	@GetMapping("/")
	public List<ProductDomain> getProductList() {
		List<ProductDomain> list = shopservice.getList();
		System.out.println(list);
		return list;

	}
	
	@Operation(summary = "상품 상세")
	@GetMapping("/{id}")
	public ProductDomain getProductList(@PathVariable("id") int id) {
		return shopservice.getDetail(id);

	}
	
	@Operation(summary = "상품 등록")
	@PostMapping("/upload")
	public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("item_name") String item_name,
			@RequestParam("cost") Integer cost) throws IOException {
        // 파일 이름을 파라미터로 받아서 이미지 서비스로 전달
        String fileName = file.getOriginalFilename();
        shopservice.upload(file,cost, item_name);

        return "image-test";

    }

}
