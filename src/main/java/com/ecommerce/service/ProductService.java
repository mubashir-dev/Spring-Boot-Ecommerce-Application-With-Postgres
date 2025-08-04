package com.ecommerce.service;

import com.ecommerce.dto.*;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductResponseDto> find() {
        List<Product> products = productRepository.findAllActive();

        return products.stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
    }

    public ProductResponseDto findOne(UUID uuid) {
        Optional<Product> product = Optional.ofNullable(productRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public ProductStatusResponseDto save(ProductCreateDto productCreateDto) {
        if (productRepository.existsByTitle(productCreateDto.getTitle()).isPresent())
            throw new ResourceAlreadyExistException("Product title '" + productCreateDto.getTitle() + "' is already in use.");

        Optional<Category> category = categoryRepository.findById(productCreateDto.getCategoryId());
        if (category.isEmpty())
            throw new ResourceNotFoundException("Category with '" + productCreateDto.getCategoryId() + "' not found");

        Product product = modelMapper.map(productCreateDto, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductStatusResponseDto.class);
    }

    public ProductStatusResponseDto update(UUID uuid, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Product not found with UUID: " + uuid));

        Product.ProductBuilder productBuilder = product.toBuilder();
        productBuilder.title(productUpdateDto.getTitle() != null ? productUpdateDto.getTitle() : product.getTitle());
        productBuilder.description(productUpdateDto.getDescription() != null ? productUpdateDto.getDescription() : product.getDescription());
        productBuilder.image(productUpdateDto.getImage() != null ? productUpdateDto.getImage() : product.getImage());

        if (productUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productUpdateDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category with ID '" + productUpdateDto.getCategoryId() + "' not found"));
            productBuilder.category(category);
        }

        Product productUpdate = productRepository.save(productBuilder.build());
        return modelMapper.map(productUpdate, ProductStatusResponseDto.class);
    }

    public ProductStatusResponseDto delete(UUID uuid) {
        Product product = productRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Product not found with UUID: " + uuid));
        product.setDeleted(true);
        Product productUpdate = productRepository.save(product);
        return modelMapper.map(productUpdate, ProductStatusResponseDto.class);
    }

    public Boolean updateQuantity(Long id, int quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found with " + id + " ID"));
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return true;
    }
}
