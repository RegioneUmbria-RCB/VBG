package com.paevolution.appiogateway.web.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CreatedMessageResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4459822698993761944L;
    @Schema(description = "L'identificativo univoco del messaggio creato.")
    @JsonProperty("message_id")
    private String messageId;
}
