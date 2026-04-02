package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.Officer;
import com.springboot.automobileinsurancesystem.repository.OfficerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OfficerServiceTest {

    @InjectMocks
    private OfficerService officerService;
    @Mock
    private OfficerRepo officerRepo;

    @Test
    public void getByIdTestWhenExists(){
        // null check
        Assertions.assertNotNull(officerService);

        // data
        Officer officer = new Officer();
        officer.setId(5);
        officer.setName("Yin");
        officer.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer.setContact("4820482404");
        Officer officer1 = new Officer();
        officer.setId(5);
        officer.setName("Yin");
        officer.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer.setContact("593749");

        // mock
        Mockito.when(officerRepo.findById(5L)).thenReturn(Optional.of(officer));
        // assert
        Assertions.assertEquals(officer, officerService.getById(5));
        Assertions.assertNotEquals(officer1, officerService.getById(5));
        // verify
        Mockito.verify(officerRepo, Mockito.times(2)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(officerRepo.findById(5L)).thenReturn(Optional.empty());
        // assert
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> officerService.getById(5));
        // verify
        Mockito.verify(officerRepo, Mockito.times(1)).findById(5L);
    }
}
