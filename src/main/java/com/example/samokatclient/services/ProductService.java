package com.example.samokatclient.services;

import com.example.samokatclient.DTO.product.CategoryDto;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.product.Category;
import com.example.samokatclient.entities.product.Product;
import com.example.samokatclient.exceptions.product.CategoryNotFoundException;
import com.example.samokatclient.exceptions.product.ProductNotFoundException;
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

    public Category getCategoryById(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = null;
        if (optionalCategory.isPresent()){
            category = optionalCategory.get();
        }
        else{
            throw new CategoryNotFoundException();
        }
        return category;
    }

    public ProductDto getProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product;
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
        }
        else{
            throw new ProductNotFoundException();
        }
        return new ProductDto(product);
    }

    public List<ProductDto> getAllProductsPage(int pageNumber, int pageSize){
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository
                .findAll(pageable)
                .stream()
                .map(ProductDto::new)
                .toList();
    }

    public List<CategoryDto> getAllMainCategories(){
        return categoryRepository
                .findAllByParentIsNull()
                .stream()
                .map(CategoryDto::new)
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
                .map(ProductDto::new)
                .toList()
                .subList( (pageNumber - 1) * pageSize
                        , Integer.min( (pageNumber - 1) * pageSize + pageSize
                                     , allProducts.size()));
    }

    public List<ProductDto> getAllProductsFromCategory(Long category_id, int pageNumber, int pageSize){
        List<Product> products = new ArrayList<>();
        Category category = getCategoryById(category_id);
        if (category.getHasChildren()){
            List<Category> children = categoryRepository.findCategoriesByParent_Id(category_id);
            for (Category child : children){
                products.addAll(productRepository.findProductsByCategory_Id(child.getId()));
            }

        }
        else {
            products = productRepository.findProductsByCategory_Id(category_id);
        }

        return products
                .stream()
                .map(ProductDto::new)
                .toList()
                .subList((pageNumber - 1) * pageSize, Integer.min((pageNumber - 1) * pageSize + pageSize, products.size()));
    }
}
