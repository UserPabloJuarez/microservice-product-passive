package com.nttdata.asset.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//4xxxxxxx --> prestamo personal
//5xxxxxxx --> prestamo empresarial
//6xxxxxxx --> tarjeta credito
//64xxxxxx --> tarjeta credito(personal)
//65xxxxxx --> tarjeta credito(empresarial)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "MoneyLoans")
public class MoneyLoans {

	private String id;
	@Id
	private String accountNumber;
	private Float amount;
	private Float withdrawal;
	private Float share;
	private Float balance;
	
	public MoneyLoans(String accountNumber, Float amount, Float withdrawal, Float share, Float balance) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.withdrawal = withdrawal;
        this.share = share;
        this.balance = balance;
    }
	
}
