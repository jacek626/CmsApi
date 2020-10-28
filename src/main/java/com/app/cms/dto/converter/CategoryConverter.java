package com.app.cms.dto.converter;

import com.app.cms.api.CategoryApi;
import com.app.cms.dto.CategoryDto;
import com.app.cms.entity.Category;
import com.app.cms.valueobject.category.Name;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryConverter implements ObjectConverter<Category, CategoryDto> {

    private final ModelMapper modelMapper;

    public CategoryConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto toDto(Category category) {
        var categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setName(category.getName().getValue());

        categoryDto.add(
                linkTo(methodOn(CategoryApi.class).getCategoryById(categoryDto.getId())).withSelfRel(),
                linkTo(methodOn(CategoryApi.class).getCategories(0, 10, null, null)).withRel("categories"));

        return categoryDto;
    }

    @Override
    public Category toEntity(CategoryDto categoryDto) {
        var category = modelMapper.map(categoryDto, Category.class);
        category.setName(Name.of(categoryDto.getName()));

        return category;
    }
}
