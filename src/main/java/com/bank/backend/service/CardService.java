package com.bank.backend.service;

import com.bank.backend.dto.CardCreateDto;
import com.bank.backend.model.Account;
import com.bank.backend.model.Card;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    public Card addCard(CardCreateDto cardCreateDto) {
        try {
            Card card = new Card();
            Account account = null;
            Optional<Account> optionalAccount =
                    accountRepository.findById(cardCreateDto.getAccountId());
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            }
            if (account == null) {
                return null;
            }
            card.setCardType(cardCreateDto.getCardType());
            card.setAccountId(account);
            card = cardRepository.save(card);
            return card;
        } catch (Exception e) {
            log.error("Exception while registering card, cause of Exception " + e.getCause());
        }
        return null;
    }


    public List<Card> fetchAllCards() {
        try {
            return cardRepository.findAll();
        } catch (Exception e) {
            log.error("Exception while fetching all cards, cause of Exception " + e.getCause());
        }
        return null;
    }

    public List<Card> fetchMyCards(Integer accountId) {
        try {
            return cardRepository.findByAccountId(accountId);
        } catch (Exception e) {
            log.error("Exception while fetching all cards using accountId, cause of Exception " + e.getCause());
        }
        return null;
    }

}
