package it.alveo.segnalazioniproxy.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sg_configurazioni")
public class SgConfigurazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String alias;
    private String software;
    private String descrizione;

    @Column(name = "mitt_id_nodo")
    private String mittIdNodo;
    @Column(name = "mitt_id_ente")
    private String mittIdEnte;
    @Column(name = "mitt_id_sportello")
    private String mittIdSportello;
    @Column(name = "stc_ws_url")
    private String stcWsUrl;
    @Column(name = "stc_username")
    private String stcUsername;
    @Column(name = "stc_password")
    private String stcPassword;
    @Column(name = "security_ws_url")
    private String securityWsUrl;
    @Column(name = "security_ws_username")
    private String securityWsUsername;
    @Column(name = "security_ws_password")
    private String securityWsPassword;
    @Column(name = "dt_insert")
    private LocalDateTime dtInsert;

    // Relazione uno-a-molti con la tabella sg_configurazioni_enti
    @OneToMany(mappedBy = "configurazione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SgConfigurazioniEnti> configurazioniEnti;

    @OneToMany(mappedBy = "configurazione", cascade = CascadeType.ALL)
    private List<SgDizionario> dizionari;

    @PrePersist
    protected void onCreate() {
        dtInsert = LocalDateTime.now();
    }


    // Personalizzato perché quello generato da @Data da problemi per le 2 liste configurazioniEnti e dizionari
    @Override
    public String toString() {
        return "SgConfigurazioni{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                ", software='" + software + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", mittIdNodo='" + mittIdNodo + '\'' +
                ", mittIdEnte='" + mittIdEnte + '\'' +
                ", mittIdSportello='" + mittIdSportello + '\'' +
                ", stcWsUrl='" + stcWsUrl + '\'' +
                ", stcUsername='" + stcUsername + '\'' +
                ", stcPassword='" + stcPassword + '\'' +
                ", securityWsUrl='" + securityWsUrl + '\'' +
                ", securityWsUsername='" + securityWsUsername + '\'' +
                ", securityWsPassword='" + securityWsPassword + '\'' +
                ", dtInsert='" + dtInsert + '\'' +
                ", configurazioniEnti= ..." +
                ", dizionari= ..." +
                '}';
    }

    // Personalizzato per escludere dal confronto configurazioniEnti e dizionari, perché non necessari
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SgConfigurazioni that = (SgConfigurazioni) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(software, that.software) &&
                Objects.equals(descrizione, that.descrizione) &&
                Objects.equals(mittIdNodo, that.mittIdNodo) &&
                Objects.equals(mittIdEnte, that.mittIdEnte) &&
                Objects.equals(mittIdSportello, that.mittIdSportello) &&
                Objects.equals(stcWsUrl, that.stcWsUrl) &&
                Objects.equals(stcUsername, that.stcUsername) &&
                Objects.equals(stcPassword, that.stcPassword) &&
                Objects.equals(securityWsUrl, that.securityWsUrl) &&
                Objects.equals(securityWsUsername, that.securityWsUsername) &&
                Objects.equals(securityWsPassword, that.securityWsPassword) &&
                Objects.equals(dtInsert, that.dtInsert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alias, software, descrizione, mittIdNodo, mittIdEnte,
                mittIdSportello, stcWsUrl, stcUsername, stcPassword,
                securityWsUrl, securityWsUsername, securityWsPassword, dtInsert);
    }

}
