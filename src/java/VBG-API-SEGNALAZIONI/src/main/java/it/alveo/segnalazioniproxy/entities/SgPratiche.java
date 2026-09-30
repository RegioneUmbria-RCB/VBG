package it.alveo.segnalazioniproxy.entities;

import it.alveo.segnalazioniproxy.enums.Stato;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sg_pratiche")
public class SgPratiche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @ManyToOne
    @JoinColumn(name = "fk_sg_configurazione_id", referencedColumnName = "id", nullable = false)
    private SgConfigurazioni configurazione;

    @OneToOne
    @JoinColumn(name = "fk_sg_dizionario_id", referencedColumnName = "id", nullable = false)
    private SgDizionario dizionario;

    @Column(name = "codice_comune")
    private String codiceComune;

    @Enumerated(EnumType.STRING)
    private Stato stato;

    @Column(length = 2000)
    private String oggetto;

    @Column(length = 4000)
    private String testo;

    private String utente;
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    @Column(name = "data_invio")
    private LocalDateTime dataInvio;
    @Column(name = "data_ricezione")
    private LocalDateTime dataRicezione;
    @Column(name = "numero_protocollo")
    private String numeroProtocollo;
    @Column(name = "data_protocollo")
    private LocalDateTime dataProtocollo;
    @Column(name = "riferimento_ente")
    private String riferimentoEnte;
    @Column(name = "stato_avanzamento_ente")
    private String statoAvanzamentoEnte;
    @Column(name = "x_client_id")
    private String xClientId;
    @Column(name = "id_pratica_destinataria")
    private String idPraticaDestinataria;
    @Column(name = "dt_insert")
    private LocalDateTime dtInsert;

    @PrePersist
    protected void onCreate() {
        dataCreazione = LocalDateTime.now();
        dtInsert = LocalDateTime.now();
    }

}
