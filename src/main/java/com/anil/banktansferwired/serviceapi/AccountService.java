package com.anil.banktansferwired.serviceapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.anil.banktansferwired.exception.AccountServiceException;
import com.anil.banktansferwired.exception.ResourceNotFoundException;
import com.anil.banktansferwired.model.Account;

@Service
public interface AccountService {

	List<Account> getAllAccounts();
	Account createAccount(Account aAccount);
	ResponseEntity<Account> getAccountById(String accountId) throws ResourceNotFoundException;
	ResponseEntity<Account> updateAccount(String accountId, Account accountDetails) throws ResourceNotFoundException;
	ResponseEntity<?> deleteAccount(String accountId) throws ResourceNotFoundException;
	void internalBankTransfer(String fromAccount, String toAccount, String amount) throws AccountServiceException;
	void externalBankTransfer(String fromAccount, String toAccount, String amount) throws AccountServiceException;
	
}
