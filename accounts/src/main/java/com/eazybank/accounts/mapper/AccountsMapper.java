package com.eazybank.accounts.mapper;

import com.eazybank.accounts.dto.AccountDto;
import com.eazybank.accounts.entity.Accounts;

public class AccountsMapper {
    public static AccountDto maptoAccountDto(Accounts accounts, AccountDto accountDto) {
        accountDto.setAccountNumber(accounts.getAccountNumber());
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBalance(accounts.getBalance());
        accountDto.setBranch(accounts.getBranch());
        return accountDto;

    }

    public static Accounts maptoAccounts(AccountDto accountDto, Accounts accounts) {
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setBalance(accountDto.getBalance());
        accounts.setBranch(accountDto.getBranch());
        return accounts;
    }
}
