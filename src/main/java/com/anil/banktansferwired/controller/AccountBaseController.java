/**
 * 
 */
package com.anil.banktansferwired.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.anil.banktansferwired.model.Account;

/**
 * @author Anil
 *
 */
public interface AccountBaseController {

	List<Account> getAllAccounts();
	Account createAccount(Account aAccount);
	ResponseEntity<Account> getAccountById(String accountId);
	ResponseEntity<Account> updateAccount(String accountId, Account accountDetails);
	ResponseEntity<?> deleteAccount(String accountId);
	ResponseEntity<String> internalBankTransfer(String userId, String fromAccountId, String toAccountId, String balance);
}
