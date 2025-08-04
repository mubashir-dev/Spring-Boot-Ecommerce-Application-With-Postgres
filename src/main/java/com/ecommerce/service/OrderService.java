package com.ecommerce.service;

import com.ecommerce.dto.OrderCreateDto;
import com.ecommerce.dto.OrderDetailCreateDto;
import com.ecommerce.dto.order.OrderResponseDto;
import com.ecommerce.dto.order.OrderUpdateStatusDto;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetail;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.OrderDetailRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Transactional
    public String create(OrderCreateDto orderCreateDto) {

        //Invalid product ids
        List<Long> invalidProductIds = new ArrayList<>();

        //validate order cart data
        for (OrderDetailCreateDto orderDetail : orderCreateDto.getOrderDetail()) {
            Optional<Product> product = productRepository.findById(orderDetail.getProductId());

            if (!product.isPresent()) {
                invalidProductIds.add(orderDetail.getProductId());
            }

            orderDetail.setPrice(product.get().getPrice());
            orderDetail.setTotalPrice(product.get().getPrice() * orderDetail.getQuantity());
        }

        //check if products are valid
        if (invalidProductIds.size() > 0) {
            throw new BadRequestException("Invalid products in the cart");
        }

        //create customer
        Customer customerBuilder = Customer.builder().name(orderCreateDto.getName()).email(orderCreateDto.getEmail()).phoneNumber(orderCreateDto.getPhoneNumber()).build();
        Customer newCustomer = customerRepository.save(customerBuilder);

        //create order
        Order orderBuilder = Order.builder().address(orderCreateDto.getAddress()).notes(orderCreateDto.getNotes()).customer(newCustomer).status(OrderStatus.PLACED).build();
        Order newOrder = orderRepository.save(orderBuilder);

        //create order details
        for (OrderDetailCreateDto orderDetail : orderCreateDto.getOrderDetail()) {
            Product product = productRepository.findById(orderDetail.getProductId()).orElseThrow(() -> new BadRequestException("Product not found with ID: " + orderDetail.getProductId()));

            if (product.getQuantity() <= 0) {
                throw new BadRequestException("No product left with" + orderDetail.getProductId());
            }

            OrderDetail orderDetailBuilder = OrderDetail.builder().order(newOrder).product(product).quantity(orderDetail.getQuantity()).price(orderDetail.getPrice()).totalPrice(orderDetail.getTotalPrice()).build();
            orderDetailRepository.save(orderDetailBuilder);
        }

        return "Order has Successfully been placed";
    }

    public List<OrderResponseDto> find() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
    }

    public OrderResponseDto findOne(UUID uuid) {
        Order order = orderRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Product with " + uuid + "  has not been found"));
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public String updateStatus(UUID uuid, OrderUpdateStatusDto orderUpdateStatusDto) {
        Order order = orderRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Product with " + uuid + "  has not been found"));

        //update order status
        order.setStatus(orderUpdateStatusDto.getStatus());
        orderRepository.save(order);

        //update product quantity
        if (orderUpdateStatusDto.getStatus() == OrderStatus.CONFIRMED) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                productService.updateQuantity(orderDetail.getProduct().getId(), orderDetail.getQuantity());
            }
        }

        return "Order status has been updated successfully";
    }
}
