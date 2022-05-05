package com.nttdata.asset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.asset.model.MoneyLoans;
import com.nttdata.asset.repository.IAssetRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class IAssetServiceImpl implements IAssetService{
	
	@Autowired
	private IAssetRepository assetRepository;

	@Autowired
	private static final Logger LOGGER = LoggerFactory.getLogger(IAssetServiceImpl.class);

	@Override
	public Mono<MoneyLoans> create(Mono<MoneyLoans> e){
		return e.flatMap(assetRepository::insert)
				.onErrorResume(x -> {
                    LOGGER.error("[" + getClass().getName() + "][addPerson]" + x);
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request" + x));
                }).switchIfEmpty(
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                );
	}
	
	@Override
	public Mono<MoneyLoans> update(String accountNumber, Mono<MoneyLoans> e){
		return e.flatMap(assetRepository::save).onErrorResume(x -> {
            LOGGER.error("[" + getClass().getName() + "][setUpdatePerson]" + x);
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + x));
        }).switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
	}
	
	@Override
	 public Flux<MoneyLoans> getAllMoneyLoans() {
	    return assetRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][getAllPerson]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "" + e));
        });
	 }
	
	@Override
	 public Mono<MoneyLoans> getById(String accountNumber){
	    return  assetRepository.findById(accountNumber).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][getPersonById]" + e);
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
        }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	 }
	
	@Override
	 public Mono<MoneyLoans> deleteEmp(String accountNumber) {
	    return assetRepository.findById(accountNumber)
	          .flatMap(deleteEmp->assetRepository.delete(deleteEmp)
	          .then(Mono.just(deleteEmp))).onErrorResume(e -> {
                  LOGGER.error("[" + getClass().getName() + "][deletePerson]" + e);
                  return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "" + e));
              });
	 }
}
