package com.paevolution.appioproducer.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioni;
import com.paevolution.appioproducer.core.domain.MovimentiIoComunicazioniId;

@Repository
public interface MovimentiIoComunicazioniRepository extends PagingAndSortingRepository<MovimentiIoComunicazioni, MovimentiIoComunicazioniId> {

    public List<MovimentiIoComunicazioni> findAllByFlagCompletatoIsFalse();

    public List<MovimentiIoComunicazioni> findAllByFlagCompletatoIsFalseAndErroreIsNull();

    @Query("From MovimentiIoComunicazioni mioc where (mioc.flagCompletato = false and errore is null) OR (mioc.flagCompletato = true and mioc.notificationStatus in ('ACCEPTED','THROTTLED') and errore is null) OR (mioc.flagCompletato = true and (mioc.notificationEmail in ('THROTTLED') OR mioc.notificationWebhook in ('THROTTLED')) and errore is null)")
    public List<MovimentiIoComunicazioni> findAllMessageToGetNotification();
}
