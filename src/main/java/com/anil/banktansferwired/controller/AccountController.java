package com.anil.banktansferwired.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anil.banktansferwired.exception.ResourceNotFoundException;
import com.anil.banktansferwired.model.Account;
import com.anil.banktansferwired.serviceapi.AccountService;
import com.anil.banktansferwired.util.ApplicationConstants;

@RestController
@RequestMapping("api/v1")
public class AccountController implements AccountBaseController{

	@Autowired
	private AccountService accountService;
		
	
	/*@Autowired
	private AuditService auditService;
	*/
	@GetMapping("accounts")
	public List<Account> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	
	@PostMapping("/accounts")
	public Account createAccount(@RequestBody Account aAccount) {
		return accountService.createAccount(aAccount);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable String id)
			throws ResourceNotFoundException {
		ResponseEntity<Account> responseEntity = accountService.getAccountById(id);
		return ResponseEntity.ok().body(responseEntity.getBody());
	}
	
	@PostMapping("/account/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable String id, @RequestBody Account accountDetails) throws ResourceNotFoundException {
		ResponseEntity<Account> responseEntity = accountService.getAccountById(id);
		ResponseEntity<Account> myresponseEntity = accountService.updateAccount(id,responseEntity.getBody());
		return ResponseEntity.ok().body(myresponseEntity.getBody());
	}
	
	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable String id) throws ResourceNotFoundException{
		ResponseEntity<?> myResponseEntity = accountService.deleteAccount(id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/accounts/moneyTransfer/{userId}/{fromAccount}/{toAccount}/{amount}")
	public ResponseEntity<String> internalBankTransfer(@PathVariable String userId, @PathVariable String fromAccount, @PathVariable String toAccount, @PathVariable String amount) {
		accountService.internalBankTransfer(fromAccount,toAccount , amount);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ApplicationConstants.AMOUNT);
		stringBuilder.append(amount);
		stringBuilder.append(ApplicationConstants.BENIFICIARY);
		stringBuilder.append(toAccount);
		stringBuilder.append(ApplicationConstants.SUCCESSFULLY);
		return ResponseEntity.ok(stringBuilder.toString());
	}
	
}
