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
@Table(name = "sg_pratiche_allegati")
public class SgPraticheAllegati {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @ManyToOne
    @JoinColumn(name = "fk_uuid_pratica", referencedColumnName = "uuid", nullable = false)
    private SgPratiche pratica;

    @Column(name = "nome_file")
    private String nomeFile;
    @Column(name = "riferimento_esterno_uuid")
    private String riferimentoEsternoUuid;
    @Column(name = "dt_insert")
    private LocalDateTime dtInsert;

    @PrePersist
    protected void onCreate() {
        dtInsert = LocalDateTime.now();
    }

}
