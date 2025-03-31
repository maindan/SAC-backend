package com.example.users.services;

import com.example.users.DTO.serviceOrder.CreateServiceOrderDTO;
import com.example.users.DTO.serviceOrder.ServiceOrderDTO;
import com.example.users.DTO.serviceOrder.UpdateServiceOrderDTO;
import com.example.users.domain.customer.Customer;
import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.serviceOrder.ServiceOrder;
import com.example.users.domain.user.User;
import com.example.users.exceptions.NotFoundException;
import com.example.users.repositories.CustomerRepository;
import com.example.users.repositories.EquipamentRepository;
import com.example.users.repositories.ServiceOrderRepository;
import com.example.users.repositories.UserRepository;
import com.example.users.security.authentication.IdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private EquipamentService equipamentService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EquipamentRepository equipamentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdCheckService idCheckService;

    public ServiceOrderDTO getById(long id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found"));

        idCheckService.validateOwner(serviceOrder.getUser().getId());
        return convertToDTO(serviceOrder);
    }

    public List<ServiceOrderDTO> findAllByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        List<ServiceOrder> list = serviceOrderRepository.findAllByCustomer_Id(id);
        idCheckService.validateOwner(customer.getUser().getId());
        return convertToDTOList(list);
    }

    public List<ServiceOrderDTO> getAllByEquipamentId(Long id) {
        Equipament equipament = equipamentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipament not found"));
        idCheckService.validateOwner(equipament.getCustomer().getUser().getId());

        List<ServiceOrder> list = serviceOrderRepository.findAllByEquipaments_Id(id);
        return convertToDTOList(list);
    }

    public ServiceOrderDTO createServiceOrder(CreateServiceOrderDTO serviceOrderDTO) {
        Customer customer = customerRepository.findById(serviceOrderDTO.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        User user = userRepository.findById(serviceOrderDTO.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        ServiceOrder parentServiceOrder = null;
        if (serviceOrderDTO.parentId() != null) {
            parentServiceOrder = serviceOrderRepository.findById(serviceOrderDTO.parentId())
                    .orElseThrow(() -> new NotFoundException("Parent service order not found"));
        }

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setCustomer(customer);
        serviceOrder.setUser(user);
        serviceOrder.setEntryDate(serviceOrderDTO.entryDate());
        serviceOrder.setType(serviceOrderDTO.serviceOrderType());
        serviceOrder.setStatus(serviceOrderDTO.serviceOrderStatus());

        Optional.ofNullable(serviceOrderDTO.fiscalNumber()).ifPresent(serviceOrder::setFiscalNumber);
        Optional.ofNullable(serviceOrderDTO.observation()).ifPresent(serviceOrder::setObservation);
        Optional.ofNullable(serviceOrderDTO.parentId())
                .flatMap(serviceOrderRepository::findById)
                .ifPresent(serviceOrder::setParentServiceOrder);

        Optional.ofNullable(serviceOrderDTO.equipaments())
                .ifPresent(serviceOrder::setEquipaments);

        ServiceOrder savedServiceOrder = serviceOrderRepository.save(serviceOrder);

        return convertToDTO(savedServiceOrder);
    }

    public ServiceOrderDTO updateServiceOrder(Long id, UpdateServiceOrderDTO serviceOrderDTO) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service Order not found"));

        Optional.ofNullable(serviceOrderDTO.entryDate()).ifPresent(serviceOrder::setEntryDate);
        Optional.ofNullable(serviceOrderDTO.budgetDate()).ifPresent(serviceOrder::setBudgetDate);
        Optional.ofNullable(serviceOrderDTO.budgetValue()).ifPresent(serviceOrder::setBudgetValue);
        Optional.ofNullable(serviceOrderDTO.warrantyClaimDate()).ifPresent(serviceOrder::setWarrantyClaimDate);
        Optional.ofNullable(serviceOrderDTO.warrantyApprovalDate()).ifPresent(serviceOrder::setWarrantyApprovalDate);
        Optional.ofNullable(serviceOrderDTO.removalDate()).ifPresent(serviceOrder::setRemovalDate);
        Optional.ofNullable(serviceOrderDTO.serviceOrderType()).ifPresent(serviceOrder::setType);
        Optional.ofNullable(serviceOrderDTO.serviceOrderStatus()).ifPresent(serviceOrder::setStatus);
        Optional.ofNullable(serviceOrderDTO.fiscalNumber()).ifPresent(serviceOrder::setFiscalNumber);
        Optional.ofNullable(serviceOrderDTO.observation()).ifPresent(serviceOrder::setObservation);

        Optional.ofNullable(serviceOrderDTO.parentId())
                .flatMap(serviceOrderRepository::findById)
                .ifPresent(serviceOrder::setParentServiceOrder);

        Optional.ofNullable(serviceOrderDTO.equipaments()).ifPresent(serviceOrder::setEquipaments);

        serviceOrder.setUpdatedAt(Instant.now());

        ServiceOrder updatedServiceOrder = serviceOrderRepository.save(serviceOrder);
        return convertToDTO(updatedServiceOrder);
    }

    public void deleteServiceOrder(Long id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service Order not found"));
        idCheckService.validateOwner(serviceOrder.getUser().getId());
        serviceOrderRepository.delete(serviceOrder);
    }

    private ServiceOrderDTO convertToDTO(ServiceOrder serviceOrder) {
        return new ServiceOrderDTO(
                serviceOrder.getId(),
                serviceOrder.getCustomer().getId(),
                serviceOrder.getEntryDate(),
                serviceOrder.getBudgetDate(),
                serviceOrder.getBudgetValue(),
                serviceOrder.getWarrantyClaimDate(),
                serviceOrder.getWarrantyApprovalDate(),
                serviceOrder.getRemovalDate(),
                serviceOrder.getType(),
                serviceOrder.getStatus(),
                serviceOrder.getFiscalNumber(),
                serviceOrder.getObservation(),
                serviceOrder.getEquipaments().stream()
                        .map(equipament -> {
                            try {
                                return equipamentService.convertEquipament(equipament);
                            } catch (Exception exception) {
                                throw new RuntimeException(exception);
                            }
                        }).collect(Collectors.toList()),
                serviceOrder.getParentServiceOrder().getId(),
                serviceOrder.getUser().getId(),
                serviceOrder.getCreatedAt(),
                serviceOrder.getUpdatedAt()

        );
    }

    private List<ServiceOrderDTO> convertToDTOList(List<ServiceOrder> serviceOrders) {
        return serviceOrders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
