package com.yoti.repository;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface HooverRepository extends MongoRepository<RequestResponse, String> {


}
