package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.CustomerPolicyResDto;
import com.springboot.automobileinsurancesystem.model.CustomerPolicy;

public class CustomerPolicyMapper {

    public static CustomerPolicyResDto toDto(CustomerPolicy customerPolicy){
        return new CustomerPolicyResDto(
                customerPolicy.getId(),
                customerPolicy.getSum_insured(),
                customerPolicy.getStart_date(),
                customerPolicy.getNo_of_years(),
                customerPolicy.getExpiry_date(),
                customerPolicy.getPolicy().getPolicyType(),
                customerPolicy.getCustomer().getName(),
                customerPolicy.getOfficer().getName(),
                customerPolicy.getVehicle().getModel(),
                customerPolicy.getVehicle().getVehicleClass()
        );
    }
}
