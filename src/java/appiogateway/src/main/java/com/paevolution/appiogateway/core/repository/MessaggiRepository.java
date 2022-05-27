package com.paevolution.appiogateway.core.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.domain.StatoMessaggio;

@Repository
public interface MessaggiRepository extends PagingAndSortingRepository<Messaggi, Long> {

    Optional<Messaggi> findByIdTransazione(String idTransazione);

    Optional<Messaggi> findByFiscalCodeAndId(String fiscalCode, Long id);

    Optional<Messaggi> findByIdcomuneAndCodicecomuneAndSoftware(String idcomune, String codicecomune, String software);

    Optional<Messaggi> findByMessageId(String messageId);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.idTransazione = :idTransazione where msg.id = :id")
    void updateIdTransazione(@Param(value = "id") Long id, @Param(value = "idTransazione") String idTransazione);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.senderAllowed = :senderAllowed where msg.id = :id")
    void updateSenderAllowed(@Param(value = "id") Long id, @Param(value = "senderAllowed") Boolean senderAllowed);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.preferredLanguages = :preferredLanguages where msg.id = :id")
    void updatePreferredLanguages(@Param(value = "id") Long id, @Param(value = "preferredLanguages") String preferredLanguages);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.createdAt = :createdAt where msg.id = :id")
    void updateCreatedAt(@Param(value = "id") Long id, @Param(value = "createdAt") Timestamp createdAt);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.webhookNotification = :webhookNotification where msg.id = :id")
    void updateWebhookNotification(@Param(value = "id") Long id, @Param(value = "webhookNotification") String webhookNotification);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.emailNotification = :emailNotification where msg.id = :id")
    void updateEmailNotification(@Param(value = "id") Long id, @Param(value = "emailNotification") String emailNotification);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.status = :status where msg.id = :id")
    void updateStatus(@Param(value = "id") Long id, @Param("status") String status);

    List<Messaggi> findByStatoMessaggio(StatoMessaggio statoMessaggio);

    @Transactional
    @Modifying
    @Query("update Messaggi msg set msg.statoMessaggio = :statoMessaggio where msg.id = :id")
    void updateStatoMessaggio(@Param(value = "id") Long id, @Param(value = "statoMessaggio") StatoMessaggio statoMessaggio);
}
