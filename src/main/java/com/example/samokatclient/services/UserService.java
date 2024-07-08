package com.example.samokatclient.services;

import com.example.samokatclient.DTO.order.AddressDto;
import com.example.samokatclient.DTO.order.OrderDto;
import com.example.samokatclient.DTO.order.PaymentDto;
import com.example.samokatclient.DTO.session.UserDto;
import com.example.samokatclient.entities.user.Address;
import com.example.samokatclient.entities.user.Payment;
import com.example.samokatclient.entities.user.SamokatUser;
import com.example.samokatclient.exceptions.order.AddressNotFoundException;
import com.example.samokatclient.exceptions.order.PaymentNotFoundException;
import com.example.samokatclient.exceptions.user.UserNotFoundException;
import com.example.samokatclient.mappers.AddressMapper;
import com.example.samokatclient.mappers.OrderMapper;
import com.example.samokatclient.mappers.PaymentMapper;
import com.example.samokatclient.repositories.user.AddressRepository;
import com.example.samokatclient.repositories.user.OrderRepository;
import com.example.samokatclient.repositories.user.PaymentRepository;
import com.example.samokatclient.repositories.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    public SamokatUser getUserById(String user_id){
        Optional<SamokatUser> optionalUser = userRepository.findById(user_id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public void authorizeUser(UserDto userDto){
        Optional<SamokatUser> optionalUser = userRepository.findById(userDto.phone_number);
        if (optionalUser.isEmpty()) {
            SamokatUser samokatUser = new SamokatUser(userDto.phone_number, userDto.name);
            userRepository.save(samokatUser);
        }
    }

    public void setUserName(String user_id, String name){
        Optional<SamokatUser> optionalUser = userRepository.findById(user_id);
        if (optionalUser.isPresent()){
            SamokatUser samokatUser = optionalUser.get();
            samokatUser.setName(name);
            userRepository.save(samokatUser);
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public List<OrderDto> getUserOrders(String user_id){
        return orderRepository.findByUserId(user_id).stream().map(orderMapper::toDto).toList();
    }

    public List<AddressDto> getUserAddresses(String user_id){
        return addressRepository.findByUserId(user_id).stream().map(addressMapper::toDto).toList();
    }

    public List<PaymentDto> getUserPayments(String user_id){
        return paymentRepository.findByUserId(user_id).stream().map(paymentMapper::toDto).toList();
    }

    public AddressDto getAddress(String address_id){
        Optional<Address> optionalAddress = addressRepository.findById(address_id);
        if (optionalAddress.isPresent()){
            return addressMapper.toDto(optionalAddress.get());
        }
        else{
            throw new AddressNotFoundException();
        }
    }

    public PaymentDto getPayment(String payment_id){
        Optional<Payment> optionalPayment = paymentRepository.findById(payment_id);
        if (optionalPayment.isPresent()){
            return paymentMapper.toDto(optionalPayment.get());
        }
        else{
            throw new PaymentNotFoundException();
        }
    }

    public void createNewAddress(String user_id, AddressDto addressDto){
        Address address = addressMapper.fromDto(addressDto);
        address.setUserId(user_id);
        addressRepository.save(address);
    }

    public void createNewPayment(String user_id, PaymentDto paymentDto){
        Payment payment = paymentMapper.fromDto(paymentDto);
        payment.setUserId(user_id);
        paymentRepository.save(payment);
    }
}
