package com.springboot.automobileinsurancesystem.mapper;

import com.springboot.automobileinsurancesystem.dto.ClaimResDto;
import com.springboot.automobileinsurancesystem.model.Claim;

public class ClaimMapper {

    public static ClaimResDto toDto(Claim claim){
        return new ClaimResDto(
                claim.getCustomerPolicy().getCustomer().getName(),
                claim.getCustomerPolicy().getVehicle().getReg_no(),
                claim.getCustomerPolicy().getVehicle().getVehicleClass(),
                claim.getCustomerPolicy().getPolicy().getPolicyType(),
                claim.getIncident_date(),
                claim.getClaim_amt(),
                claim.getClaimStatus(),
                claim.getCustomerPolicy().getSum_insured()
        );
    }
}
