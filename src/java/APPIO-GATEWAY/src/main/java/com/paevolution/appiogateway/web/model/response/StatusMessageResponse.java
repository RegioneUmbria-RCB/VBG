package com.paevolution.appiogateway.web.model.response;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusMessageResponse implements Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = 958746514587035816L;
    private String idcomune;
//    private String codicecomune;
//    private String software;
    @Schema(description = "Identificativo univoco del messaggio fornito dal sistema mittente.")
    @JsonProperty("message_id")
    private String messageId;
    @JsonProperty("fiscal_code")
    private String fiscalCode;
    private Integer timeToLive;
    @Schema(description = "A date-time field in ISO-8601 format and UTC timezone.", example = "2018-10-13T00:00:00.000Z")
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Timestamp createdAt;
    @Schema(description = "The subject of the message - note that only some notification\r\n"
	    + "channels support the display of a subject. When a subject is not provided,\r\n"
	    + "one gets generated from the client attributes.", minLength = 10, maxLength = 120, example = "Welcome new user !")
    private String subject;
    @Schema(description = "The full version of the message, in plain text or Markdown format. The\r\n"
	    + "content of this field will be delivered to channels that don't have any\r\n"
	    + "limit in terms of content size (e.g. email, etc...).", minLength = 80, maxLength = 10000, example = "# This is a markdown header to show how easily markdown can be converted to **HTML** Remember: this has to be a long text.")
    private String markdown;
    @Schema(description = "A date-time field in ISO-8601 format and UTC timezone.", example = "2018-10-13T00:00:00.000Z")
    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Timestamp dueDate;
    @Schema(description = "Identificativo del servizio al quale è registrato il mittente del messaggio.")
    @JsonProperty("sender_service_id")
    private String idServizio;
    @Schema(description = "The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the notification processing; the notification associated with this channel will be delayed for a maximum of 7 days or until the message expires \"EXPIRED\": the message expired before the notification could be sent; this means that the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent to this channel")
    private String emailNotification;
    @Schema(description = "The status of a notification (one for each channel). \"SENT\": the notification was succesfully sent to the channel (ie. email or push notification) \"THROTTLED\": a temporary failure caused a retry during the notification processing; the notification associated with this channel will be delayed for a maximum of 7 days or until the message expires \"EXPIRED\": the message expired before the notification could be sent; this means that the maximum message time to live was reached; no notification will be sent to this channel \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent to this channel")
    private String webhookNotification;
    @Schema(description = "The processing status of a message. \"ACCEPTED\": the message has been accepted and will be processed for delivery; we'll try to store its content in the user's inbox and notify him on his preferred channels \"THROTTLED\": a temporary failure caused a retry during the message processing; any notification associated with this message will be delayed for a maximum of 7 days \"FAILED\": a permanent failure caused the process to exit with an error, no notification will be sent for this message \"PROCESSED\": the message was succesfully processed and is now stored in the user's inbox; we'll try to send a notification for each of the selected channels \"REJECTED\": either the recipient does not exist, or the sender has been blocked")
    private String status;
    @Schema(description = "True in case the service that made the request can send messages to the user identified by this profile (false otherwise).")
    @JsonProperty("sender_allowed")
    private Boolean senderAllowed;
    @Schema(description = "Indicates the User's preferred written or spoken languages in order of preference. Generally used for selecting a localized User interface. Valid values are concatenation of the ISO 639-1 two letter language code, an underscore, and the ISO 3166-1 2 letter country code; e.g., 'en_US' specifies the language English and country US.")
    @JsonProperty("preferred_language")
    private String preferredLanguages;
    @Schema(description = "The identifier of the created message.")
    @JsonProperty("id_transazione")
    private String idTransazione;
}
