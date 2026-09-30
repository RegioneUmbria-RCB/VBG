package com.paevolution.appiogateway.web.controllers.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paevolution.appiogateway.annotations.DateISO8601Constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UIMessageRequest {

    @NotBlank(message = "Il campo idcomune è obbligatorio")
    private String idcomune;
//    @NotBlank(message = "Il campo codicecomune è obbligatorio")
//    private String codicecomune;
//    @NotBlank(message = "Il campo software è obbligatorio")
//    private String software;
    @Schema(description = "This parameter specifies for how long (in seconds) the system will try to deliver the message to the channels configured by the user.", example = "3600", defaultValue = "3600", minimum = "3600", maximum = "6044800")
    private Integer timeToLive = 3600;
    @Schema(description = "The subject of the message - note that only some notification\r\n"
	    + "channels support the display of a subject. When a subject is not provided,\r\n"
	    + "one gets generated from the client attributes.", minLength = 10, maxLength = 120, example = "Welcome new user !")
    @NotBlank(message = "Il campo subject è obbligatorio")
    @Size(min = 10, max = 120, message = "la dimensione del campo subject deve essere compresa tra 10 e 120")
    private String subject;
    @Schema(description = "The full version of the message, in plain text or Markdown format. The\r\n"
	    + "content of this field will be delivered to channels that don't have any\r\n"
	    + "limit in terms of content size (e.g. email, etc...).", minLength = 80, maxLength = 10000, example = "# This is a markdown header to show how easily markdown can be converted to **HTML** Remember: this has to be a long text.")
    @NotBlank(message = "Il campo markdown è obbligatorio")
    @Size(min = 80, max = 10000, message = "la dimensione del campo markdown deve essere compresa tra 80 e 10000")
    private String markdown;
    @Schema(description = "A date-time field in ISO-8601 format and UTC timezone.", example = "2018-10-13T00:00:00.000Z")
    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @DateISO8601Constraint(message = "il campo due_date deve essere nel formato 2018-10-13T00:00:00.000Z")
    private String dueDate;
    @Email(message = "L'email deve essere valida")
    private String email;
    @Schema(description = "User's fiscal code.", example = "SPNDNL80R13C555X")
    @NotBlank(message = "Il campo fiscal_code è obbligatorio")
    @Size(min = 16, max = 16, message = "Il campo fiscal_code deve avere 16 caratteri")
    @JsonProperty("fiscal_code")
    private String fiscalCode;
    @Schema(description = "Identificativo univoco del messaggio fornito dal sistema mittente.")
    @NotNull(message = "il campo id_messaggio_mittente è obbligatorio")
    @JsonProperty("message_id")
    private String messageId;
}
