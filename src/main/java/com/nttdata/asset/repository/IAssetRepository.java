package com.nttdata.asset.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.asset.model.MoneyLoans;

@Repository
public interface IAssetRepository extends ReactiveMongoRepository<MoneyLoans, String>{
}
