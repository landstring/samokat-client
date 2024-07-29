package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.cart.CartItem;
import com.example.samokatclient.DTO.cart.OrderCartItem;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.redis.Cart;
import com.example.samokatclient.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class CartMapper {

    private final ProductService productService;

    public CartDto cartToDto(Cart cart) {
        long totalCost = 0L;
        CartItem cartItem;
        ProductDto product;
        Map<Long, Integer> productsTreeMap = cart.getProducts();
        List<CartItem> cartItemList = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productsTreeMap.entrySet()) {
            product = productService.getProductById(entry.getKey());

            cartItem = CartItem.builder()
                    .product(product)
                    .count(entry.getValue())
                    .build();

            totalCost += product.price * entry.getValue();
            cartItemList.add(cartItem);
        }
        return new CartDto(cartItemList, totalCost);
    }

    public CartDto listOrderCartItemToDto(List<OrderCartItem> orderCartItemList) {
        long totalCost = 0L;
        CartItem cartItem;
        ProductDto product;
        List<CartItem> cartItemList = new ArrayList<>();

        for (OrderCartItem orderCartItem : orderCartItemList) {
            product = productService.getProductById(orderCartItem.getProductId());

            cartItem = CartItem.builder()
                    .product(product)
                    .count(orderCartItem.getCount())
                    .build();

            totalCost += product.price * orderCartItem.getCount();
            cartItemList.add(cartItem);
        }
        return new CartDto(cartItemList, totalCost);
    }

    public List<OrderCartItem> toListOrderCartItem(CartDto cartDto) {
        List<OrderCartItem> orderCartItemList = new ArrayList<>();

        for (CartItem cartItem : cartDto.getCartItemList()) {
            orderCartItemList.add(new OrderCartItem(cartItem.getProduct().id, cartItem.getCount()));
        }

        return orderCartItemList;
    }
}