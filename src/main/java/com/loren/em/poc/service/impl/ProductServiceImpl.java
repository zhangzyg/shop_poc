package com.loren.em.poc.service.impl;

import com.loren.em.poc.domain.Cart;
import com.loren.em.poc.domain.Product;
import com.loren.em.poc.domain.ProductMetadata;
import com.loren.em.poc.dto.ProductDto;
import com.loren.em.poc.exception.EmBadRequestException;
import com.loren.em.poc.repository.CartRepository;
import com.loren.em.poc.repository.ProductMetadataRepository;
import com.loren.em.poc.repository.ProductRepository;
import com.loren.em.poc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMetadataRepository productMetadataRepository;

    @Override
    public List<ProductDto> addProduct(String productType, String userId) {
        Product product = new Product();
        Optional<ProductMetadata> productMetadataOptional = productMetadataRepository.findByProductCategoryId(productType);
        if (productMetadataOptional.isEmpty()) {
            throw new EmBadRequestException("Illegal Product Type");
        }
        product.setProductCategory(productMetadataOptional.get());
        Optional<Cart> cartOptional = cartRepository.findByUserUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new EmBadRequestException("Cannot find user");
        }
        product.setCart(cartOptional.get());
        productRepository.save(product);
        return convertProductList(productRepository.findByCartUserUserId(userId));
    }

    @Override
    public List<ProductDto> removeProduct(String productType, String userId) {
        Optional<ProductMetadata> productMetadataOptional = productMetadataRepository.findByProductCategoryId(productType);
        if (productMetadataOptional.isEmpty()) {
            throw new EmBadRequestException("Illegal Product Type");
        }
        Optional<Product> productOptional = productRepository.findFirstByProductCategoryProductCategoryId(productType);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        }
        return convertProductList(productRepository.findByCartUserUserId(userId));
    }

    private List<ProductDto> convertProductList(List<Product> productList) {
        return productList.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductType(product.getProductCategory().getProductCategoryId());
            productDto.setPrice(product.getProductCategory().getPrice());
            return productDto;
        }).collect(Collectors.toList());
    }
}
