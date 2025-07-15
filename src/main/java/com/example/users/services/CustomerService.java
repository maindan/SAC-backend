package com.example.users.services;

import com.example.users.DTO.customer.CreateCustomerDTO;
import com.example.users.DTO.customer.CustomerRequestDTO;
import com.example.users.domain.customer.Customer;
import com.example.users.domain.user.User;
import com.example.users.exceptions.NotFoundException;
import com.example.users.repositories.CustomerRepository;
import com.example.users.repositories.UserRepository;
import com.example.users.security.authentication.IdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdCheckService idCheckService;

    public CustomerRequestDTO getCustomerById(Long id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        idCheckService.validateOwner(customer.getUser().getId());
        return convertCustomer(customer);
    }

    public List<CustomerRequestDTO> getAllCustomersByUserId(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("Customers not found");
        }

        List<Customer> customers = this.customerRepository.findAllByUser_Id(id).orElse(List.of());
        return convertCustomersList(customers);
    }

    public CustomerRequestDTO createCustomer(CreateCustomerDTO newCustomer) {
        User user = this.userRepository.findById(newCustomer.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Customer customer = new Customer();
        customer.setName(newCustomer.name());
        customer.setPhoneNumber(newCustomer.phoneNumber());
        customer.setRegisterNumber(newCustomer.registerNumber() != null ? newCustomer.registerNumber() : null);
        customer.setAddress(newCustomer.address() != null ? newCustomer.address() : null);
        customer.setUser(user);
        Customer savedCustomer = this.customerRepository.save(customer);
        return convertCustomer(savedCustomer);
    }

    public CustomerRequestDTO updateCustomer(Long id, CreateCustomerDTO updatedCustomer) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        Optional.ofNullable(updatedCustomer.name()).ifPresent(customer::setName);
        Optional.ofNullable(updatedCustomer.phoneNumber()).ifPresent(customer::setPhoneNumber);
        Optional.ofNullable(updatedCustomer.registerNumber()).ifPresent(customer::setRegisterNumber);
        Optional.ofNullable(updatedCustomer.typeBusiness()).ifPresent(customer::setTypeBusiness);
        Optional.ofNullable(updatedCustomer.address()).ifPresent(customer::setAddress);

        return convertCustomer(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        if(!this.customerRepository.existsById(id)) {
            throw new NotFoundException("Customer not found");
        }
        this.customerRepository.deleteById(id);
    }

    private CustomerRequestDTO convertCustomer(Customer customer) {
        return new CustomerRequestDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getRegisterNumber(),
                customer.getTypeBusiness(),
                customer.getAddress(),
                customer.getUser().getId(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
            );
    }

    private List<CustomerRequestDTO> convertCustomersList(List<Customer> customers) {
        return customers.stream().map(this::convertCustomer).collect(Collectors.toList());
    }
}
