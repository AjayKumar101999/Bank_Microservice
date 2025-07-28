package com.eazybank.accounts.mapper;

import com.eazybank.accounts.dto.AccountDto;
import com.eazybank.accounts.dto.CustomerDetailsDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;

public class CustomerMapper {
   public static CustomerDto maptoCustomerDto(Customer customer, CustomerDto customerDto) {
       customerDto.setName(customer.getName());
       customerDto.setEmail(customer.getEmail());
       customerDto.setMobile(customer.getMobile());
       return customerDto;
   }

    public static Customer maptoCustomer(CustomerDto customerDto, Customer customer) {
         customer.setName(customerDto.getName());
         customer.setEmail(customerDto.getEmail());
         customer.setMobile(customerDto.getMobile());
         return customer;
    }
    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobile());
        return customerDetailsDto;
    }
}
