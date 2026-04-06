package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.OfficerResDto;
import com.springboot.automobileinsurancesystem.enums.OfficerDesignation;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.mapper.OfficerMapper;
import com.springboot.automobileinsurancesystem.model.Officer;
import com.springboot.automobileinsurancesystem.repository.OfficerRepo;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
        OfficerResDto dto = new OfficerResDto(
                officer.getId(),
                officer.getName(),
                officer.getOfficerDesignation(),
                officer.getContact()
        );
        Officer officer1 = new Officer();
        officer1.setId(5);
        officer1.setName("Ken");
        officer1.setOfficerDesignation(OfficerDesignation.GENERAL_SUPPORT);
        officer1.setContact("593749");
        OfficerResDto dto1 = new OfficerResDto(
                officer1.getId(),
                officer1.getName(),
                officer1.getOfficerDesignation(),
                officer1.getContact()
        );

        // mock
        Mockito.when(officerRepo.findById(5L)).thenReturn(Optional.of(officer));
        // assert
        Assertions.assertEquals(dto, officerService.getById(5));
        Assertions.assertNotEquals(dto1, officerService.getById(5));
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

    @Test
    public void getByDesignationWhenExists(){
        // data
        Officer officer1 = new Officer();
        officer1.setName("Officer1");
        officer1.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer1.setContact("9634578345");
        Officer officer2 = new Officer();
        officer2.setName("Officer1");
        officer2.setOfficerDesignation(OfficerDesignation.SALES_SUPPORT);
        officer2.setContact("9634578345");

        List<Officer> list = List.of(officer1, officer2);
        List<OfficerResDto> listDto = list.stream()
                .map(OfficerMapper :: toDto).toList();

        // mock
        Mockito.when(officerRepo.getByDesignation(OfficerDesignation.SALES_SUPPORT)).thenReturn(list);

        // assert
        Assertions.assertEquals(listDto, officerService.getByDesignation(OfficerDesignation.SALES_SUPPORT));

        // verify
        Mockito.verify(officerRepo, Mockito.times(1)).getByDesignation(OfficerDesignation.SALES_SUPPORT);
    }

    @Test
    public void getByDesignationWhenNotExists(){
        // data
        List<Officer> list = List.of();
        List<OfficerResDto> listDto = List.of();
        // mock
        Mockito.when(officerRepo.getByDesignation(OfficerDesignation.TECHNICAL_SUPPORT)).thenReturn(list);
        // assert
        Assertions.assertEquals(listDto, officerService.getByDesignation(OfficerDesignation.TECHNICAL_SUPPORT));
    }
}
