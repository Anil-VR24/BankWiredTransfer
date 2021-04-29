/**
 * 
 */
package com.anil.banktansferwired.serviceimpl;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.banktansferwired.exception.AccountServiceException;
import com.anil.banktansferwired.exception.ResourceNotFoundException;
import com.anil.banktansferwired.model.Account;
import com.anil.banktansferwired.repository.AccountRepository;
import com.anil.banktansferwired.serviceapi.AccountService;
import com.anil.banktansferwired.util.ApplicationConstants;

/**
 * @author Anil
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Transactional(readOnly = true)
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	@Transactional(isolation = REPEATABLE_READ)
	public Account createAccount(Account aAccount) {
		return accountRepository.save(aAccount);
	}
	
	
	@Transactional(readOnly = true)
	public ResponseEntity<Account> getAccountById(String accountId)
			throws ResourceNotFoundException {
		Account account = getAccount(accountId);
		return ResponseEntity.ok().body(account);
	}
	
	
	public ResponseEntity<Account> updateAccount(String accountId, Account accountDetails) throws ResourceNotFoundException {
		Account account = getAccount(accountId);
		account.setBalance(accountDetails.getBalance());
		accountRepository.save(account);
		return ResponseEntity.ok().body(account);
	}
	
	public ResponseEntity<?> deleteAccount(String accountId) throws ResourceNotFoundException{
		Account account = getAccount(accountId);
		accountRepository.delete(account);
		return ResponseEntity.ok().build();
	}
	

	@Transactional(isolation = REPEATABLE_READ)
	public void internalBankTransfer(String fromAccountId, String toAccountId, String amount)  throws AccountServiceException{
			BigDecimal myAmount = new BigDecimal(amount);
			validateIsNegative(myAmount);
			ResponseEntity<Account> fromAccountResponseEntity = getAccountById(fromAccountId);
			Account fromAccount = fromAccountResponseEntity.getBody();
			ResponseEntity<Account> toAccountResponseEntity = getAccountById(toAccountId);
			Account toAccount = toAccountResponseEntity.getBody();
			if(fromAccount.getBalance().compareTo(myAmount) == -1) {
				throw new AccountServiceException();
			}else {
				fromAccount.setBalance(fromAccount.getBalance().subtract(myAmount));
				toAccount.setBalance(toAccount.getBalance().add(myAmount));
				updateAccount(fromAccountId,fromAccount);
				updateAccount(toAccountId, toAccount);
				/*Audit.setUserId(userId);
				auditService.createAudit(audit);*/
			}
	}

	/* (non-Javadoc)
	 * @see com.anil.banktansferwired.serviceapi.AccountService#externalBankTransfer(java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	@Transactional(isolation = REPEATABLE_READ)
	public void externalBankTransfer(String fromAccount, String toAccount, String amount)  throws AccountServiceException{
		// TODO Auto-generated method stub
		
	}
	
	
	private void validateIsNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountServiceException(ApplicationConstants.ILLEGAL_ARGUMENT);
        }
    }
	
	private Account getAccount(String accountId) throws ResourceNotFoundException{
		Account account = accountRepository.getByAccountNumber(accountId);
		if(account == null) {
			throw new ResourceNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND + accountId);
		}
		return account;
	}

}
