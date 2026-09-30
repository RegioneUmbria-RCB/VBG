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
@Table(name = "sg_configurazioni_enti")
public class SgConfigurazioniEnti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_sg_configurazione_id", referencedColumnName = "id", nullable = false)
    private SgConfigurazioni configurazione;

    @Column(name = "codice_comune")
    private String codiceComune;
    @Column(name = "descrizione_comune")
    private String descrizioneComune;
    @Column(name = "dest_id_nodo")
    private String destIdNodo;
    @Column(name = "dest_id_ente")
    private String destIdEnte;
    @Column(name = "dest_id_sportello")
    private String destIdSportello;
    @Column(name = "ws_servizio_oggetti_alias")
    private String wsServizioOggettiAlias;
    @Column(name = "dt_insert")
    private LocalDateTime dtInsert;

    @PrePersist
    protected void onCreate() {
        dtInsert = LocalDateTime.now();
    }

}
