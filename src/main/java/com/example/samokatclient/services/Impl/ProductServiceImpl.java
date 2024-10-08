package com.example.samokatclient.services.Impl;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;
import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.product.Category;
import com.example.samokatclient.entities.product.Product;
import com.example.samokatclient.enums.CurrentOrderStatus;
import com.example.samokatclient.exceptions.product.CategoryNotFoundException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
import com.example.samokatclient.mappers.CategoryMapper;
import com.example.samokatclient.mappers.ProductMapper;
import com.example.samokatclient.repositories.product.CategoryRepository;
import com.example.samokatclient.repositories.product.ProductRepository;
import com.example.samokatclient.services.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final KafkaTemplate<String, NewStatusDto> statusKafkaTemplate;

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Такого продукта не существует")
        );
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("Такой категории не существует")
        );
    }

    @Override
    public ProductDto getProduct(Long productId) {
        return productMapper.toDto(getProductById(productId));
    }

    @Override
    public List<ProductDto> getAllProductsPage(int pageNumber, int pageSize) {
        statusKafkaTemplate.send("newStatus", NewStatusDto.builder()
                .orderId("5143c601-1a86-4227-b14c-a2bce72fda51")
                .status(CurrentOrderStatus.CANCELED)
                .build());
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository
                .findAll(pageable)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository
                .findAllByParentIsNull()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> searchProductsByKeywords(String keywords, int pageNumber, int pageSize) {
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
                .subList((pageNumber - 1) * pageSize
                        , Integer.min((pageNumber - 1) * pageSize + pageSize
                                , allProducts.size()));
    }

    @Override
    public List<ProductDto> getAllProductsFromCategory(Long categoryId, int pageNumber, int pageSize) {
        Category category = getCategoryById(categoryId);
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository
                .findProductsByCategory_Id(category.getId(), pageable)
                .map(productMapper::toDto)
                .toList();
    }
}
