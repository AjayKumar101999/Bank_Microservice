package com.eazybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema ho to hold customer and account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of customer"
    )
    @NotEmpty(message = "Name can not be empty")
    @Size(min = 5, max = 20)
    private String name;

    @Email(message = "email should be valid email")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digits")
    private String mobile;


    private AccountDto accountDto;
}
