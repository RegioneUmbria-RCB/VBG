package com.paevolution.appioproducer.core.repository;

import org.springframework.stereotype.Repository;

import com.paevolution.appioproducer.core.domain.AppIoCoda;
import com.paevolution.appioproducer.core.domain.AppIoCodaId;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AppIoCodaRepository extends PagingAndSortingRepository<AppIoCoda, AppIoCodaId> {
    
    @Query("From AppIoCoda aioc where aioc.stato = 'DA_PROCESSARE' and aioc.dataPrevistaElaborazione <= :dataPrevistaElaborazione Order by aioc.ordine desc")
    public List<AppIoCoda> findAllMessageToSend(@Param("dataPrevistaElaborazione") Date dataPrevistaElaborazione);
    @Query("From AppIoCoda aioc where aioc.stato in ('INVIATA_A_GATEWAY','NOTIFICATA_APPIO','IN_LAVORAZIONE')")
    public List<AppIoCoda> findAllMessageToNotify();
}
