package com.eazybank.accounts.controller;

import com.eazybank.accounts.constants.AccountsContants;
import com.eazybank.accounts.dto.AccountsContactInfoDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.dto.ErrorResponseDto;
import com.eazybank.accounts.dto.ResponseDto;
import com.eazybank.accounts.service.iAccountService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD Rest API for Accounts in EazyBank",
        description = "CRUD rest API in EazyBank to Create, Update, Fetch and Delete account Details"
)
public class AccountController {

    private iAccountService accountService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
     summary = "create a new Account ",
     description = "Rest API to create new customer and account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status created"
    )
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        ResponseDto responseDto = new ResponseDto(AccountsContants.STATUS_201,AccountsContants.MESSAGE_201);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @Operation(
            summary = "get customer details ",
            description = "Rest API to fetch customer and account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status created"
    )
    @GetMapping(value = "/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        ResponseDto responseDto = new ResponseDto(AccountsContants.STATUS_201,AccountsContants.MESSAGE_201);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "update the details ",
            description = "Rest API to update customer and account"
    )
    @ApiResponses({
            @ApiResponse(
            responseCode = "201",
            description = "Http status updated"
    ),
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )})
    @PutMapping(value = "/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccounts(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsContants.STATUS_201,AccountsContants.MESSAGE_201));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsContants.STATUS_500,AccountsContants.MESSAGE_500));
        }
   }

    @Operation(
            summary = "delete customer",
            description = "Rest API to delete customer by given mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )})
    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digits")
                                                         String mobile) {
        boolean isUpdated = accountService.deleteAccount(mobile);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsContants.STATUS_200, AccountsContants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsContants.STATUS_500, AccountsContants.MESSAGE_500));
        }
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

    @Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        log.info("getBuildInfo() method Invoked");
        throw new RuntimeException();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("1.1");
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        log.info("getBuildInfoFallback() method Invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.9");
    }

}
