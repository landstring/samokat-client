package com.example.samokatclient.mappers;

import com.example.samokatclient.DTO.cart.CartDto;
import com.example.samokatclient.DTO.cart.CartItemDto;
import com.example.samokatclient.DTO.cart.OrderCartItemDto;
import com.example.samokatclient.entities.user.OrderCartItem;
import com.example.samokatclient.DTO.product.ProductDto;
import com.example.samokatclient.entities.session.Cart;
import com.example.samokatclient.entities.session.CartItem;
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
    private final ProductMapper productMapper;

    public CartDto cartToDto(Cart cart) {
        long totalPrice = 0L;
        Map<Long, CartItem> productsTreeMap = cart.getProducts();
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for (Map.Entry<Long, CartItem> entry : productsTreeMap.entrySet()) {
            ProductDto productDto = productMapper.toDto(entry.getValue().getProduct());
            CartItemDto cartItemDto = CartItemDto.builder()
                    .productDto(productDto)
                    .count(entry.getValue().getCount())
                    .build();
            totalPrice += productDto.getPrice() * entry.getValue().getCount();
            cartItemDtoList.add(cartItemDto);
        }
        return new CartDto(cartItemDtoList, totalPrice);
    }

    public CartDto listOrderCartItemToDto(List<OrderCartItem> orderCartItemList) {
        long totalPrice = 0L;
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for (OrderCartItem orderCartItem : orderCartItemList) {
            ProductDto productDto = productMapper.toDto(productService.getProductById(orderCartItem.getProductId()));
            CartItemDto cartItemDto = CartItemDto.builder()
                    .productDto(productDto)
                    .count(orderCartItem.getCount())
                    .build();

            totalPrice += productDto.getPrice() * orderCartItem.getCount();
            cartItemDtoList.add(cartItemDto);
        }
        return CartDto.builder()
                .cartItemDtoList(cartItemDtoList)
                .totalPrice(totalPrice)
                .build();
    }

    public List<OrderCartItemDto> toListOrderCartItem(CartDto cartDto) {
        List<OrderCartItemDto> orderCartItemList = new ArrayList<>();
        for (CartItemDto cartItemDto : cartDto.getCartItemDtoList()) {
            orderCartItemList.add(
                    OrderCartItemDto.builder()
                            .productId(cartItemDto.getProductDto().getId())
                            .count(cartItemDto.getCount())
                            .build());
        }
        return orderCartItemList;
    }
}