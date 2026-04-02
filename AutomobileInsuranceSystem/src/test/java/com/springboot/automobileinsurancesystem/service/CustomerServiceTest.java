package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.CustomerResDto;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.Customer;
import com.springboot.automobileinsurancesystem.repository.CustomerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // enables mockito annotations
public class CustomerServiceTest {

    @InjectMocks // where mock is injected
    private CustomerService customerService;
    @Mock // which one to mock
    private CustomerRepo customerRepo;

    @Test
    public void getByIdTestWhenExists(){
        // check null for service
        Assertions.assertNotNull(customerService);

        // create data
        Customer customer = new Customer();
        customer.setId(5);
        customer.setName("Potter");
        customer.setContact("4839729834");
        customer.setLicense_number("SFE8439HF8");
        CustomerResDto dto = new CustomerResDto(
                customer.getId(),
                customer.getName(),
                customer.getContact(),
                customer.getLicense_number()
        );

        // create mock
        Mockito.when(customerRepo.findById(5L)).thenReturn(Optional.of(customer));

        // assert
        Assertions.assertEquals(dto, customerService.getById(5));

        // verify no of times calling, checking performance
        Mockito.verify(customerRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(customerRepo.findById(5L)).thenReturn(Optional.empty());

        // assert
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.getById(5));
        Assertions.assertEquals("Invalid customer id..", e.getMessage());
        Assertions.assertNotEquals("Invalid customer id", e.getMessage());
        // verify
        Mockito.verify(customerRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getAllCustomersTest(){
        // data
        Customer customer1 = new Customer();
        customer1.setId(7);
        customer1.setName("Yang");
        customer1.setContact("098765434");
        customer1.setLicense_number("IU3843D4");
        Customer customer2 = new Customer();
        customer2.setId(5);
        customer2.setName("Potter");
        customer2.setContact("4839729834");
        customer2.setLicense_number("SFE8439HF8");
        List<Customer> list = List.of(customer1, customer2);

        // page obj
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = new PageImpl<>(list);

        // mock
        Mockito.when(customerRepo.findAll(pageable)).thenReturn(customerPage);

        // assert
        Assertions.assertEquals(2, customerService.getAllCustomers(2, 0).data().size());
    }
}
