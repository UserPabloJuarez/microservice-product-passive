package com.nttdata.asset.service;

import com.nttdata.asset.model.MoneyLoans;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAssetService {

	Mono<MoneyLoans> create(Mono <MoneyLoans> e);
	
	Mono<MoneyLoans> update(String accountNumber, Mono <MoneyLoans> e);
	
	Flux<MoneyLoans> getAllMoneyLoans();
	
	Mono<MoneyLoans> getById(String accountNumber);
	
	Mono<MoneyLoans> deleteEmp(String accountNumber);
	
}
