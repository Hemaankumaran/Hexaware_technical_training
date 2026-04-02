package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.VehicleResDto;
import com.springboot.automobileinsurancesystem.model.Vehicle;

public class VehicleMapper {

    public static VehicleResDto mapToDto(Vehicle vehicle){
        return new VehicleResDto(
                vehicle.getId(),
                vehicle.getReg_no(),
                vehicle.getModel(),
                vehicle.getFuelType(),
                vehicle.getVehicleClass()
        );
    }
}
