package com.eazybank.accounts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema ho to hold account information"
)
public class AccountDto {

    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    private Double balance;

    @NotEmpty(message = "Account branch can not be null or empty")
    private String branch;
}
