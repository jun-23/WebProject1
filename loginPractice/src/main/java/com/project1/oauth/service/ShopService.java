package com.project1.oauth.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project1.oauth.domain.ProductDomain;
import com.project1.oauth.repository.ProductRepository;

@Service
public class ShopService {

	@Autowired
	private ProductRepository productRepository;

	public List<ProductDomain> getList() {

		return productRepository.findAll();
	}

	public List<ProductDomain> getListByCategory(String category2) {
		return productRepository.findByCategory2(category2);
	}

	public ProductDomain getDetail(Integer id) {

		return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
	}

	public void upload(MultipartFile img, Integer cost, String item_name) throws IOException {
		ProductDomain product = new ProductDomain();
		product.setImg(img.getBytes().toString());
		product.setItem_name(item_name);
		product.setCost(cost);
		productRepository.save(product);
	}
}
