package com.springboot.automobileinsurancesystem.service;

import com.springboot.automobileinsurancesystem.dto.CustomerPolicyResDto;
import com.springboot.automobileinsurancesystem.enums.PolicyStatus;
import com.springboot.automobileinsurancesystem.enums.Role;
import com.springboot.automobileinsurancesystem.exceptions.ResourceNotFoundException;
import com.springboot.automobileinsurancesystem.exceptions.UpdatePermissionException;
import com.springboot.automobileinsurancesystem.mapper.CustomerPolicyMapper;
import com.springboot.automobileinsurancesystem.model.*;
import com.springboot.automobileinsurancesystem.repository.CustomerPolicyRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerPolicyService {

    private final CustomerPolicyRepo customerPolicyRepo;
    private final CustomerService customerService;
    private final OfficerService officerService;
    private final VehicleService vehicleService;
    private final PolicyService policyService;
    private final UserService userService;

    private Map<String, BigDecimal> calculateInsuranceMath(Vehicle vehicle, Policy policy) {
        // 1. Calculate Age
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - vehicle.getMfg_year().getValue();

        // 2. 10% depreciation per year, max 50%
        double depreciationRate = Math.min(age * 0.10, 0.50);

        // Sum Insured (IDV) = Showroom Price * (1 - Depreciation) [Insured Declared Value]
        BigDecimal calculatedSumInsured = vehicle.getOriginal_showroom_price()
                .multiply(BigDecimal.valueOf(1 - depreciationRate));

        // 3. Calculate Premium (Base Premium from Plan + 2% of Sum Insured)
        BigDecimal riskComponent = calculatedSumInsured.multiply(new BigDecimal("0.02"));
        BigDecimal finalPremium = policy.getBase_premium().add(riskComponent);

        // Return values in a simple map
        Map<String, BigDecimal> results = new HashMap<>();
        results.put("sumInsured", calculatedSumInsured);
        results.put("premium", finalPremium);

        return results;
    }

    public void assignPolicyToCustomerByAdmin(long policyId, long customerId, long officerId, long vehicleId) {
        // get all entities from their ids
        Policy policy = policyService.getById(policyId);
        Customer customer = customerService.getByIdEntity(customerId);
        Officer officer = officerService.getById(officerId);
        Vehicle vehicle = vehicleService.getByIdEntity(vehicleId);

        // IDV calculation
        Map<String, BigDecimal> map = calculateInsuranceMath(vehicle, policy);
        CustomerPolicy customerPolicy = new CustomerPolicy();

        // set missing fields
        customerPolicy.setSum_insured(map.get("calculatedSumInsured"));
        customerPolicy.setPremium(map.get("finalPremium"));
        customerPolicy.setPolicyStatus(PolicyStatus.PROPOSAL_SUBMITTED);
        customerPolicy.setNo_of_years(policy.getDuration_years());
        customerPolicy.setCustomer(customer);
        customerPolicy.setOfficer(officer);
        customerPolicy.setVehicle(vehicle);
        customerPolicy.setPolicy(policy);

        // save it
        customerPolicyRepo.save(customerPolicy);
    }

    public void buyPolicyByCustomer(long policyId, String username, long officerId, long vehicleId) {
        // get all entities from their ids
        Policy policy = policyService.getById(policyId);
        Customer customer = customerService.getByUsername(username);
        Officer officer = officerService.getById(officerId);
        Vehicle vehicle = vehicleService.getByIdEntity(vehicleId);

        // IDV calculation
        Map<String, BigDecimal> map = calculateInsuranceMath(vehicle, policy);
        CustomerPolicy customerPolicy = new CustomerPolicy();

        // set missing fields
        customerPolicy.setSum_insured(map.get("calculatedSumInsured"));
        customerPolicy.setPremium(map.get("finalPremium"));
        customerPolicy.setPolicyStatus(PolicyStatus.PROPOSAL_SUBMITTED);
        customerPolicy.setNo_of_years(policy.getDuration_years());

        customerPolicy.setCustomer(customer);
        customerPolicy.setOfficer(officer);
        customerPolicy.setVehicle(vehicle);
        customerPolicy.setPolicy(policy);

        // save it
        customerPolicyRepo.save(customerPolicy);
    }

    public List<CustomerPolicyResDto> getByCustomerId(long customerId) {
        List<CustomerPolicy> list = customerPolicyRepo.getByCustomerId(customerId);
        return list.stream()
                .map(CustomerPolicyMapper:: toDto)
                .toList();
    }

    public CustomerPolicy getByIdEntity(long customerPolicyId){
        return customerPolicyRepo.findById(customerPolicyId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid customer_policy id.."));
    }

    public void updatePolicyStatus(Long customerPolicyId, PolicyStatus policyStatus, String username) {
        // validate policyId and get policy
        CustomerPolicy customerPolicy = getByIdEntity(customerPolicyId);
        User user = (User) userService.loadUserByUsername(username);

        // validate officer who access this method
        if(user.getRole().equals(Role.OFFICER)){
            if(customerPolicy.getOfficer().getUser().getId() != user.getId()){
                throw new UpdatePermissionException("Officer is not assigned to this customer policy");
            }
        }

        // update status
        customerPolicy.setPolicyStatus(policyStatus);

        // save it, save() does both insert and update!
        customerPolicyRepo.save(customerPolicy);
    }

    public void updateOfficer(Long customerPolicyId, Long officerId) {
        CustomerPolicy customerPolicy = getByIdEntity(customerPolicyId);
        Officer officer = officerService.getById(officerId);

        customerPolicy.setOfficer(officer);

        customerPolicyRepo.save(customerPolicy);
    }

    public void approvePolicy(Long customerPolicyId, LocalDate startDate, String username) {
        // get obj
        CustomerPolicy customerPolicy = getByIdEntity(customerPolicyId);
        User user = (User) userService.loadUserByUsername(username);

        // only officer managing this user can access to approve
        if(user.getRole().equals(Role.OFFICER)){
            if(customerPolicy.getOfficer().getUser().getId() != user.getId()){
                throw new UpdatePermissionException("Officer is not assigned to this customer policy");
            }
        }

        // set start_date, expiry_date, policy_status
        customerPolicy.setStart_date(startDate);
        int no_of_yrs = customerPolicy.getNo_of_years();
        customerPolicy.setExpiry_date(startDate.plusYears(no_of_yrs));

        customerPolicy.setPolicyStatus(PolicyStatus.ACTIVE);

        // save it
        customerPolicyRepo.save(customerPolicy);
    }

    public void deleteById(long customerPolicyId) {
        // validate the id
        getByIdEntity(customerPolicyId);

        customerPolicyRepo.deleteById(customerPolicyId);
    }

    public CustomerPolicy getQuote(Long customerPolicyId) {
        // validate
        CustomerPolicy customerPolicy = getByIdEntity(customerPolicyId);

        return customerPolicyRepo.findById(customerPolicy.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid id.."));
    }
}
