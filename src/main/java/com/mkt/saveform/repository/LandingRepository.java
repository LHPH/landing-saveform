package com.mkt.saveform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mkt.core.entity.Landing;

public interface LandingRepository extends JpaRepository<Landing,Integer>{
	
	
	@Query(value="SELECT 1 FROM DATALANDING",nativeQuery=true)
	public Object testConnection();

}
