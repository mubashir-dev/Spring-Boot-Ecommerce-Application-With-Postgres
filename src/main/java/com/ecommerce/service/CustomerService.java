package com.ecommerce.service;

import com.ecommerce.dto.customer.CustomerResponseDto;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PageResponse<CustomerResponseDto> find(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);

        List<CustomerResponseDto> content = customers.getContent()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseDto.class))
                .toList();

        return new PageResponse<>(
                "Customers Fetched Successfully",
                content,
                customers.getNumber(),
                customers.getSize(),
                customers.getTotalElements(),
                customers.getTotalPages(),
                customers.isLast()
        );
    }

    public CustomerResponseDto findOne(UUID uuid) {
        Customer customer = customerRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found with " + uuid + " ID"));
        return modelMapper.map(customer, CustomerResponseDto.class);
    }

    public String delete(UUID uuid) {
        Customer customer = customerRepository.findByUUID(uuid).orElseThrow(() -> new ResourceNotFoundException("Customer Not Found with " + uuid + " ID"));
        customerRepository.delete(customer);
        return "Customer record has been deleted successfully";
    }
}
