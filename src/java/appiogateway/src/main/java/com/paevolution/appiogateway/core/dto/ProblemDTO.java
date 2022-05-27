package com.paevolution.appiogateway.core.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4923298535878921749L;
    private Long id;
    private String type;
    private String title;
    private Integer statusCode;
    private String detail;
    private String instance;
}
