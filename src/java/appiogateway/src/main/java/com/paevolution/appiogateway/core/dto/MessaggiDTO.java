package com.paevolution.appiogateway.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessaggiDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5318529628581595582L;
    private Long id;
    private String idcomune;
    private String codicecomune;
    private String software;
    private Integer timeToLive;
    private String subject;
    private String markdown;
    private Integer amount;
    private String noticeNumber;
    private Boolean invalidAfterDueDate;
    private String nre;
    private String iup;
    private String prescriberFiscalCode;
    private Timestamp dueDate;
    private String email;
    private String fiscalCode;
    private String idTransazione;
    private Boolean senderAllowed;
    private String preferredLanguages;
    private Timestamp createdAt;
    private String connettore;
    private String messageId;
    private String idServizio;
    private String notificationEmail;
    private String notificationWebhook;
    private String status;
    private String chiavePrimaria;
    private String chiaveScondaria;
    private String statoMessaggio;
    private Integer statusCode;
    private String detail;
    private String title;
}
