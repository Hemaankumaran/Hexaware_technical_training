package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.ICVResDto;
import com.springboot.automobileinsurancesystem.enums.FuelType;
import com.springboot.automobileinsurancesystem.enums.ICVBodyType;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.model.ICV;
import com.springboot.automobileinsurancesystem.model.Vehicle;
import com.springboot.automobileinsurancesystem.repository.ICVRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ICVServiceTest {

    @InjectMocks
    private ICVService icvService;
    @Mock
    private ICVRepo icvRepo;

    @Test
    public void getByIdTestWhenExists(){
        // check null for service
        Assertions.assertNotNull(icvService);

        // data
        ICV icv = new ICV();
        icv.setId(5);
        icv.setIcvBodyType(ICVBodyType.BUS);
        icv.setPermit_number("66S35G56");
        icv.setMax_load(8);
        Vehicle vehicle = new Vehicle();
        vehicle.setReg_no("FO25ND902");
        vehicle.setFuelType(FuelType.CNG);
        icv.setVehicle(vehicle);

        ICVResDto icvResDto = new ICVResDto(
                icv.getId(),
                icv.getIcvBodyType(),
                icv.getPermit_number(),
                icv.getMax_load(),
                icv.getVehicle().getReg_no(),
                icv.getVehicle().getFuelType()
        );

        // mock
        Mockito.when(icvRepo.findById(5L)).thenReturn(Optional.of(icv));

        // assert
        Assertions.assertEquals(icvResDto, icvService.getById(5));

        // verify
        Mockito.verify(icvRepo, Mockito.times(1)).findById(5L);
    }

    @Test
    public void getByIdTestWhenNotExists(){
        // mock
        Mockito.when(icvRepo.findById(5L)).thenReturn(Optional.empty());
        // assert
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> icvService.getById(5));
        // verify
        Mockito.verify(icvRepo, Mockito.times(1)).findById(5L);
    }
}
