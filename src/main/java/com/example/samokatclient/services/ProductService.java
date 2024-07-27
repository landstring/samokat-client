package com.example.samokatclient.services;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.product.Category;
import com.example.samokatclient.entities.product.Product;
import com.example.samokatclient.exceptions.product.CategoryNotFoundException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
import com.example.samokatclient.mappers.CategoryMapper;
import com.example.samokatclient.mappers.ProductMapper;
import com.example.samokatclient.repositories.product.CategoryRepository;
import com.example.samokatclient.repositories.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;


    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Продукт не найден")
        );
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImage_url(product.getImage_url())
                .category(categoryMapper.toDto(product.getCategory()))
                .build();
    }

    public List<ProductDto> getAllProductsPage(int pageNumber, int pageSize){
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository
                .findAll(pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<CategoryDto> getAllCategories(){
        return categoryRepository
                .findAllByParentIsNull()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public List<ProductDto> searchProductsByKeywords(String keywords, int pageNumber, int pageSize){
        String[] keywordArray = keywords.toLowerCase().split("\\s+");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        for (String keyword : keywordArray) {
            Predicate predicate = cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword + "%")
            );
            predicates.add(predicate);
        }
        Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
        query.where(finalPredicate);
        List<Product> allProducts = entityManager.createQuery(query).getResultList();
        return allProducts
                .stream()
                .map(productMapper::toDto)
                .toList()
                .subList( (pageNumber - 1) * pageSize
                        , Integer.min( (pageNumber - 1) * pageSize + pageSize
                                     , allProducts.size()));
    }

    public List<ProductDto> getAllProductsFromCategory(Long category_id, int pageNumber, int pageSize){
        if (categoryRepository.existsById(category_id)) {
            throw new CategoryNotFoundException("Категория не найдена");
        }
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository
                .findProductsByCategory_Id(category_id, pageable)
                .map(productMapper::toDto)
                .toList();
    }
}
