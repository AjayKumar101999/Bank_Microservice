package com.eazybank.accounts.service.impl;

import com.eazybank.accounts.constants.AccountsContants;
import com.eazybank.accounts.dto.AccountDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;
import com.eazybank.accounts.exception.CustomerAlreadyExistException;
import com.eazybank.accounts.exception.ResourceNotFoundException;
import com.eazybank.accounts.mapper.AccountsMapper;
import com.eazybank.accounts.mapper.CustomerMapper;
import com.eazybank.accounts.repository.AccountRepository;
import com.eazybank.accounts.repository.CustomerRepository;
import com.eazybank.accounts.service.iAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements iAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        customerRepository.findByMobile(customerDto.getMobile())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistException("Customer already exists with mobile number: " + customer.getMobile());
                });
        Customer customer = CustomerMapper.maptoCustomer(customerDto, new Customer());
        Customer savedCustomerID = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomerID));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobile(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "CustomerID", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.maptoCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.maptoAccountDto(accounts, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccounts(CustomerDto customerDto) {
       boolean isUpdated=false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto!=null){
            Accounts accounts = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "AccountNUmber", accountDto.getAccountNumber().toString())
            );
            AccountsMapper.maptoAccounts(accountDto, accounts);
            accountRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
            );
            CustomerMapper.maptoCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated=true;

        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobile(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private Accounts createAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long accountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountsContants.SAVINGS);
        newAccount.setBalance(2000.00);
        newAccount.setBranch(AccountsContants.ADDRESS);
        return newAccount;

    }
}