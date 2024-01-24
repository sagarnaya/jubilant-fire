package com.bank.backend.service;

import com.bank.backend.dto.LoanCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.Loan;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    public Loan addLoan(LoanCreateDto loanCreateDto) throws ParseException {
        try {
            Loan loan = new Loan();
            Optional<Account> accountOptional = accountRepository.findById(loanCreateDto.getAccountId());
            Account account = null;
            if (accountOptional.isPresent()) {
                account = accountOptional.get();
            }
            if (account == null) {
                return null;
            }
            loan.setAccountId(account);
            loan.setLoanAmount(loanCreateDto.getLoanAmount());
            loan.setLoanType(loanCreateDto.getLoanType());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(loanCreateDto.getLoanCompletionDate());

            loan.setLoanCompletionDate(date);
            loan.setLoanInterest(loanCreateDto.getLoanInterest());
            loan.setStatus(loanCreateDto.getStatus());
            loan = loanRepository.save(loan);
            return loan;
        } catch (Exception e) {
            log.error("Exception while registering for a loan, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Loan> getmyloans(Integer loanId) {
        try {
            return loanRepository.findLoanByAccountId(loanId);
        } catch (Exception e) {
            log.error("Exception while fetching all loans using loanId, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Loan> fetchAllLoan() {
        try {
            return loanRepository.findAll();
        } catch (Exception e) {
            log.error("Exception while fetching all loan, cause of Exception " + e.getCause());
        }
        return null;
    }

    @Transactional
    public Integer updateLoanStatus(String status, Integer loanId) {
        try {
            return loanRepository.updateLoanStatus(status.toLowerCase(Locale.ROOT), loanId);
        } catch (Exception e) {
            log.error("Exception while updateLoanStatus using loanId, cause of Exception " + e.getCause());
        }
        return null;
    }

}
