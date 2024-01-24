package com.bank.backend.service;

import com.bank.backend.dto.BankTransactionCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.BankTransaction;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.BankTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankTransactionService {

    @Autowired
    BankTransactionRepository bankTransactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public BankTransaction addTransaction(BankTransactionCreateDto bankTransactionCreateDto) {
        try {
            BankTransaction bankTransaction = new BankTransaction();

            Account receiverAccount = null;
            Optional<Account> optionalReceiverAccount = accountRepository
                    .findById(
                            bankTransactionCreateDto.getReceiverAccount());
            if (optionalReceiverAccount.isPresent()) {
                receiverAccount = optionalReceiverAccount.get();
            }

            Account senderAccount = null;
            Optional<Account> optionalSenderAccount = accountRepository.findById(
                    bankTransactionCreateDto.getSenderAccount());
            if (optionalSenderAccount.isPresent()) {
                senderAccount = optionalSenderAccount.get();
            }
            if (senderAccount == null || receiverAccount == null) {
                return null;
            }
            if (senderAccount != null) {
                bankTransaction.setSenderAccount(senderAccount);
            }
            if (receiverAccount != null) {
                bankTransaction.setReceiverAccount(receiverAccount);
            }

            senderAccount.setBalance(senderAccount.getBalance() - bankTransactionCreateDto.getAmount());
            receiverAccount.setBalance(receiverAccount.getBalance() + bankTransactionCreateDto.getAmount());

            bankTransaction.setAmount(bankTransactionCreateDto.getAmount());
            bankTransaction.setDescription(bankTransactionCreateDto.getDescription());
            bankTransaction.setAmount(bankTransactionCreateDto.getAmount());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(bankTransactionCreateDto.getTransactionDate());

            bankTransaction.setTransactionDate(date);
            bankTransaction = bankTransactionRepository.save(bankTransaction);
            return bankTransaction;
        } catch (ParseException pe) {
            log.error("ParseException while processing the bank transaction, cause of ParseException " + pe.getCause());
        } catch (Exception e) {
            log.error("Exception while processing the bank transaction, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<BankTransaction> fetchAllTransactions() {
        try {
            return bankTransactionRepository.findAllBankTransactions();
        } catch (Exception e) {
            log.error("Exception while fetching all banktransaction, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<BankTransaction> fetchMyTransactions(Integer accountId) {
        try {
            return bankTransactionRepository.findLoanByAccountId(accountId);
        } catch (Exception e) {
            log.error("Exception while fetching all transaction using accountId, cause of Excpetion " + e.getCause());
        }
        return  null;
    }

}
