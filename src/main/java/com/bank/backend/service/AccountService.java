package com.bank.backend.service;

import com.bank.backend.dto.AccountCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.User;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    public Account createAccount(AccountCreateDto accountCreateDto) {
        try {
            Account account = new Account();
            account.setAccounttype(accountCreateDto.getAccounttype());
            User user = userRepository.findByEmail(accountCreateDto.getEmail());
            account.setUser(user);
            account.setBalance(accountCreateDto.getBalance());
            account.setStatus(accountCreateDto.getStatus());
            account = accountRepository.save(account);
            log.info("Account is created successfull with accountId" + account.getAccountId());
            return account;
        } catch (Exception e) {
            log.error("Exception while creating the account " + e.getCause());
        }
        return null;
    }

    public List<Account> getmyaccounts(String email) {
        try {
            return accountRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("Exception while fetching accounts of email " + email + " with cause " + e.getCause());
        }
        return null;
    }

    public Account findAccount(Integer accountId) {
        try {
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                return optionalAccount.get();
            }
        } catch (Exception e) {
            log.error("Exception while finding account with id " + accountId + ", Cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Account> fetchAllAccounts() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            log.error("Exception while fetching all accounts, cause of exception " + e.getCause());
        }
        return null;
    }

    @Transactional
    public Integer updateAccountStatus(String status, Integer accountId) {
        try {
            return accountRepository.updateAccountStatus(status.toLowerCase(Locale.ROOT), accountId);
        } catch (Exception e) {
            log.error("Exception while updating the account status, cause of exception " + e.getCause());
        }
        return null;
    }
}
