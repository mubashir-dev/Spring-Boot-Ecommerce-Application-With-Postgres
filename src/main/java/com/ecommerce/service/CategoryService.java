package com.ecommerce.service;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.exception.ResourceAlreadyExistException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.converters.models.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PageResponse<CategoryDto> find(Pageable pageable, String search) {
        Page<Category> categories = categoryRepository.findAll(pageable, search);
        List<CategoryDto> content = categories.getContent().stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        return new PageResponse<>("Categories Fetched Successfully", content, categories.getNumber(), categories.getSize(), categories.getTotalElements(), categories.getTotalPages(), categories.isLast());
    }

    public CategoryDto findOne(UUID uuid) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        return modelMapper.map(category, CategoryDto.class);
    }

    public CategoryDto save(CategoryDto categoryDto) {
        if (categoryRepository.existsByTitle(categoryDto.getTitle())) {
            throw new ResourceAlreadyExistException("Category title '" + categoryDto.getTitle() + "' is already in use.");
        }

        Category category = modelMapper.map(categoryDto, Category.class);

        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDto.class);
    }

    public CategoryDto update(UUID uuid, CategoryDto categoryDto) {
        Category category = categoryRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Category not found with UUID: " + uuid));

        Category categoryBuilder = category.toBuilder().title(categoryDto.getTitle() != null ? categoryDto.getTitle() : category.getTitle()).description(categoryDto.getDescription() != null ? categoryDto.getDescription() : category.getDescription()).image(categoryDto.getImage() != null ? categoryDto.getImage() : category.getImage()).build();
        Category updatedCategory = categoryRepository.save(categoryBuilder);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }


    public PageResponse<String> delete(UUID uuid) {
        Category category = categoryRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Category not found with UUID: " + uuid));
        categoryRepository.delete(category);
        return new PageResponse("Category record has been deleted");
    }

}
