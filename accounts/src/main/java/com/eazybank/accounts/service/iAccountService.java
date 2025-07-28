package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CustomerDto;

public interface iAccountService {

    /**
     * This method is used to create account
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccounts(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
