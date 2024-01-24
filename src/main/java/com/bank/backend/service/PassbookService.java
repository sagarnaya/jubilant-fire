package com.bank.backend.service;

import com.bank.backend.dto.PassbookCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.Passbook;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.PassbookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PassbookService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PassbookRepository passbookRepository;

    public Passbook createPassbook(PassbookCreateDto passbookCreateDto) {
        try {
            Passbook passbook = new Passbook();
            Account account = null;
            Optional<Account> optionalAccount = accountRepository.findById(passbookCreateDto.getAccountId());
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            }

            if (account == null) {
                return null;
            }

            passbook.setAccountId(account);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(passbookCreateDto.getIssuedDate());

            passbook.setIssuedDate(date);

            passbook = passbookRepository.save(passbook);
            return passbook;
        } catch (Exception e) {
            log.error("Exception while creating passbook, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Passbook> fetchAllPassbook() {
        try {
            return passbookRepository.findAll();
        } catch (Exception e) {
            log.error("Exception while fetching all passbooks, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Passbook> getmyPassbooks(Integer accountId) {
        try {
            return passbookRepository.findPassbookByAccountId(accountId);
        } catch (Exception e) {
            log.error("Exception while fetching passbooks of an account, cause of Exception " + e.getCause());
        }
        return null;
    }
}
