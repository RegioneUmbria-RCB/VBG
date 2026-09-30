package com.paevolution.appiogateway.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROBLEM")
public class Problem implements Serializable {

    private static final long serialVersionUID = 653530591022300433L;
    @Id
    private Long id;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "STATUS", precision = 3, scale = 0)
    private Integer statusCode;
    @Column(name = "DETAIL")
    private String detail;
    @Column(name = "INSTANCE")
    private String instance;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Messaggi messaggi;
}
