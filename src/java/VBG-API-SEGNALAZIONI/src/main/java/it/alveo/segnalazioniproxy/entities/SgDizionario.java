package it.alveo.segnalazioniproxy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sg_dizionario")
public class SgDizionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_sg_configurazione_id", referencedColumnName = "id", nullable = false)
    private SgConfigurazioni configurazione;

    private String descrizione;
    private String annotazioni;
    private Boolean attivabile;
    private Integer ordine;

    @ManyToOne
    @JoinColumn(name = "fk_padre")
    private SgDizionario padre;

    @Column(name = "dt_insert")
    private LocalDateTime dtInsert;

    @PrePersist
    protected void onCreate() {
        dtInsert = LocalDateTime.now();
    }

}
