package com.paevolution.appiogateway.core.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessaggiIODTO implements Serializable {

    private Integer timeToLive;
    private String subject;
    private String markdown;
    private Integer amount;
    private String noticeNumber;
    private Boolean invalidAfterDueDate;
    private String nre;
    private String iup;
    private String prescriberFiscalCode;
    private Date dueDate;
    private String email;
    private String fiscalCode;
    private String idTransazione;
    private Boolean senderAllowed;
    private String preferredLanguages;
}
