package com.eazybank.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("AuditAwareImpl")   //this class is responsible to do auditing
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("Account Microservice");
    }
}
