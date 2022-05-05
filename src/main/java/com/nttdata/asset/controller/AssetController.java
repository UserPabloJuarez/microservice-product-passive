package com.nttdata.asset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.asset.model.MoneyLoans;
import com.nttdata.asset.service.IAssetService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AssetController {

	@Autowired
	private final IAssetService assetService;
	
	@PostMapping("/create/asset")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MoneyLoans> create(@RequestBody MoneyLoans moneyLoans){
        return assetService.create(Mono.just(moneyLoans));
    }
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(value = "/put/{accountNumber}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Mono<MoneyLoans> update(@PathVariable("accountNumber") String accountNumber, @RequestBody MoneyLoans moneyLoans){
		return assetService.update(accountNumber, Mono.just(moneyLoans));
	}
	
	@GetMapping(value = "/get/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<MoneyLoans> findAll(){
	   return assetService.getAllMoneyLoans();
	}
	
	@GetMapping("/get/{accountNumber}")
	@ResponseBody
	public ResponseEntity<Mono<MoneyLoans>> findById(@PathVariable("accountNumber") String accountNumber){
	    Mono<MoneyLoans> loanMono= assetService.getById(accountNumber);
	    return new ResponseEntity<Mono<MoneyLoans>>(loanMono,loanMono != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{accountNumber}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("accountNumber") String accountNumber){
        return assetService.deleteEmp(accountNumber)
                .map(r->ResponseEntity.ok().<Void> build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
