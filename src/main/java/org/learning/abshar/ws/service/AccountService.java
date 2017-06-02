package org.learning.abshar.ws.service;


import org.learning.abshar.ws.model.Account;

public interface AccountService {
    Account findByUsername(String username);
}
