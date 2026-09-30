package com.paevolution.appioproducer.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appioproducer.core.domain.AppIoCodaStati;
import com.paevolution.appioproducer.core.domain.AppIoCodaStatiId;

@Repository
public interface AppIoCodaStatiRepository extends PagingAndSortingRepository<AppIoCodaStati, AppIoCodaStatiId> {

}
