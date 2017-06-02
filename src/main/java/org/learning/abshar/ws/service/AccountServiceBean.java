package org.learning.abshar.ws.service;

import org.learning.abshar.ws.model.Account;
import org.learning.abshar.ws.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceBean implements AccountService {
    @Autowired
    AccountRepository repository;
    @Override
    public Account findByUsername(String username) {
        Account account = repository.findByUsername(username);
        return account;
    }
}
