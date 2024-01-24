package com.bank.backend.service;

import com.bank.backend.dto.BeneficiaryCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.Beneficiary;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.BeneficiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BeneficiaryService {

    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Autowired
    AccountRepository accountRepository;

    public Beneficiary addBeneficiary(BeneficiaryCreateDto beneficiaryCreateDto) {
        Beneficiary beneficiary = new Beneficiary();

        Account beneficialAccount = null;
        Account senderAccount = null;

        Optional<Account> optionalBeneficialAccount = accountRepository
                .findById(beneficiaryCreateDto.getBeneficiaryAccount());
        if (optionalBeneficialAccount.isPresent()) {
            beneficialAccount = optionalBeneficialAccount.get();
            if (!beneficialAccount.getUser().getEmail().equalsIgnoreCase(beneficiaryCreateDto.getBeneficiaryEmail())) {
                return null;
            }
        }

        Optional<Account> optionalSenderAccount = accountRepository
                .findById(beneficiaryCreateDto.getSenderAccount());
        if (optionalSenderAccount.isPresent()) {
            senderAccount = optionalSenderAccount.get();
        }
        if (beneficialAccount == null || senderAccount == null) {
            return null;
        }

        beneficiary.setBeneficiaryName(beneficiaryCreateDto.getBeneficiaryName());
        beneficiary.setBeneficiaryEmail(beneficiaryCreateDto.getBeneficiaryEmail());
        beneficiary.setBeneficiaryAccount(beneficialAccount);
        beneficiary.setSenderAccount(senderAccount);
        beneficiaryRepository.save(beneficiary);
        return beneficiary;
    }

    public List<Beneficiary> fetchAllBeneficiary() {
        try {
            return beneficiaryRepository.findAll();
        }catch (Exception e){
            log.error("Exception while fetching all beneficiaries, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Beneficiary> findMyBeneficiaries(Integer accountId) {
        try {
            return beneficiaryRepository.findBeneficiaryByAccountId(accountId);
        }catch (Exception e){
            log.error("Exception while fetching all beneficiaries using accountId, cause of Exception " + e.getCause());
        }
        return  null;
    }

}
