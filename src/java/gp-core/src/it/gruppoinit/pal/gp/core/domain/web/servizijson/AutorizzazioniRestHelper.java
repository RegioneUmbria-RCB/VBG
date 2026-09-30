package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;

public class AutorizzazioniRestHelper {

    private Integer idAutorizzazione;
    private String autoriznumero;
    private Date autorizdata;
    private Date dataCessazione;
    private Integer codiceOccupante;
    private String noteSistema;
    private String note;
    private String autorignumero;
    private Date autorigdata;
    private String autorigcomune;
    private String autorizcomune;
    private String nominativo;
    private String nome;
    private String codicefiscale;
    private String partitaiva;
    private String indirizzo;
    private String citta;
    private String cap;
    private String provincia;
    private String numiscrrea;
    private Date dataiscrrea;
    private String provinciarea;
    private String telefono;
    private String email;
    private Date dataInizioAttivita;
    private String codicefiltracategoria;
    private String descrizionefiltracategoria;
    private Integer numeropresenze;
    // indica se in una particolare giornata lo spuntista con questa autorizzazione 
    // ha effettuato il pagamento del posteggio
    private Boolean pagamentoEffettuato;
    private Integer numeropresenzeoggi;
    private Integer idposteggiorinunciato;
    private String posteggiorinunciato;
    private Boolean presenzaRinunciata;
    // Campi autorizzazioni csi
    private Integer idautorizzazionicsi;
    private String statowarning;
    private String statoAutorizzazione;
    private Date dataSospDa;
    private Date dataSospA;
    private Date dataFineGerenza;
    private String causaleSospensione;
    private Boolean validaSpunta;
    private Date dataInizioGerenza;
    private String autPrecedenteNumero;
    private Date autPrecedenteData;
    private String autPrecComune;
    private String numeroProtocolloAut;
    private Date dataProtocolloAut;
    // gerente
    private Integer gerentecodice;
    private String gerenteNominativo;
    private String gerenteNome;
    private String gerentePartitaiva;
    private String gerenteCodicefiscale;
    private String gerenteEmail;
    private String gerenteFormagiuridica;
    // COADIUVANTE
    private Integer coadiuvantecodice;
    private String coadiuvanteNominativo;
    private String coadiuvanteNome;
    private String coadiuvantePartitaiva;
    private String coadiuvanteCodicefiscale;
    private String coadiuvanteEmail;
    private String coadiuvanteFormagiuridica;
    // battitori
    private String tipologiaBattitori;
    // posteggio occupato
    private Integer posteggioOccupato;
    private Boolean flagAttiva;
    private Integer posdebspunt;
    // titolare
    private Integer codiceTitolare;
    private String titNominativo;
    private String titNome;
    private String titCodicefiscale;
    private String titPartitaiva;
    private String titIndirizzo;
    private String titCitta;
    private String titCap;
    private String titProvincia;
    private String titNumiscrrea;
    private Date titDataiscrrea;
    private String titProvinciarea;
    private String titTelefono;
    private String titEmail;
    private Date titDataInizioAttivita;
    private Date titDataregditte;
    // end titolare
    private Date autDataAnzianita;
    private Date dataregditte;

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    public String getAutoriznumero() {

	return autoriznumero;
    }

    public void setAutoriznumero(String autoriznumero) {

	this.autoriznumero = autoriznumero;
    }

    public Date getAutorizdata() {

	return autorizdata;
    }

    public void setAutorizdata(Date autorizdata) {

	this.autorizdata = autorizdata;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public Integer getCodiceOccupante() {

	return codiceOccupante;
    }

    public void setCodiceOccupante(Integer codiceOccupante) {

	this.codiceOccupante = codiceOccupante;
    }

    public String getNoteSistema() {

	return noteSistema;
    }

    public void setNoteSistema(String noteSistema) {

	this.noteSistema = noteSistema;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getAutorizcomune() {

	return autorizcomune;
    }

    public void setAutorizcomune(String autorizcomune) {

	this.autorizcomune = autorizcomune;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCitta() {

	return citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public String getNumiscrrea() {

	return numiscrrea;
    }

    public void setNumiscrrea(String numiscrrea) {

	this.numiscrrea = numiscrrea;
    }

    public Date getDataiscrrea() {

	return dataiscrrea;
    }

    public void setDataiscrrea(Date dataiscrrea) {

	this.dataiscrrea = dataiscrrea;
    }

    public String getProvinciarea() {

	return provinciarea;
    }

    public void setProvinciarea(String provinciarea) {

	this.provinciarea = provinciarea;
    }

    public String getTelefono() {

	return telefono;
    }

    public void setTelefono(String telefono) {

	this.telefono = telefono;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public Date getDataInizioAttivita() {

	return dataInizioAttivita;
    }

    public void setDataInizioAttivita(Date dataInizioAttivita) {

	this.dataInizioAttivita = dataInizioAttivita;
    }

    public String getCodicefiltracategoria() {

	return codicefiltracategoria;
    }

    public void setCodicefiltracategoria(String codicefiltracategoria) {

	this.codicefiltracategoria = codicefiltracategoria;
    }

    public String getDescrizionefiltracategoria() {

	return descrizionefiltracategoria;
    }

    public void setDescrizionefiltracategoria(String descrizionefiltracategoria) {

	this.descrizionefiltracategoria = descrizionefiltracategoria;
    }

    public String getAutorignumero() {

	return autorignumero;
    }

    public void setAutorignumero(String autorignumero) {

	this.autorignumero = autorignumero;
    }

    public Date getAutorigdata() {

	return autorigdata;
    }

    public void setAutorigdata(Date autorigdata) {

	this.autorigdata = autorigdata;
    }

    public String getAutorigcomune() {

	return autorigcomune;
    }

    public void setAutorigcomune(String autorigcomune) {

	this.autorigcomune = autorigcomune;
    }

    public Integer getNumeropresenze() {

	return numeropresenze;
    }

    public void setNumeropresenze(Integer numeropresenze) {

	this.numeropresenze = numeropresenze;
    }

    public Boolean getPagamentoEffettuato() {

	return pagamentoEffettuato;
    }

    public void setPagamentoEffettuato(Boolean pagamentoEffettuato) {

	this.pagamentoEffettuato = pagamentoEffettuato;
    }

    public Integer getNumeropresenzeoggi() {

	return numeropresenzeoggi;
    }

    public void setNumeropresenzeoggi(Integer numeropresenzeoggi) {

	this.numeropresenzeoggi = numeropresenzeoggi;
    }

    public Integer getIdposteggiorinunciato() {

	return idposteggiorinunciato;
    }

    public void setIdposteggiorinunciato(Integer idposteggiorinunciato) {

	this.idposteggiorinunciato = idposteggiorinunciato;
    }

    public String getPosteggiorinunciato() {

	return posteggiorinunciato;
    }

    public void setPosteggiorinunciato(String posteggiorinunciato) {

	this.posteggiorinunciato = posteggiorinunciato;
    }

    public Boolean getPresenzaRinunciata() {

	return presenzaRinunciata;
    }

    public void setPresenzaRinunciata(Boolean presenzaRinunciata) {

	this.presenzaRinunciata = presenzaRinunciata;
    }

    // autorizzazioni_csi
    public Integer getIdautorizzazionicsi() {

	return idautorizzazionicsi;
    }

    public void setIdautorizzazionicsi(Integer idautorizzazionicsi) {

	this.idautorizzazionicsi = idautorizzazionicsi;
    }

    public String getStatowarning() {

	return statowarning;
    }

    public void setStatowarning(String statowarning) {

	this.statowarning = statowarning;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    public Date getDataSospDa() {

	return dataSospDa;
    }

    public void setDataSospDa(Date dataSospDa) {

	this.dataSospDa = dataSospDa;
    }

    public Date getDataSospA() {

	return dataSospA;
    }

    public void setDataSospA(Date dataSospA) {

	this.dataSospA = dataSospA;
    }

    public Date getDataFineGerenza() {

	return dataFineGerenza;
    }

    public void setDataFineGerenza(Date dataFineGerenza) {

	this.dataFineGerenza = dataFineGerenza;
    }

    public String getCausaleSospensione() {

	return causaleSospensione;
    }

    public void setCausaleSospensione(String causaleSospensione) {

	this.causaleSospensione = causaleSospensione;
    }

    public Boolean getValidaSpunta() {

	return validaSpunta;
    }

    public void setValidaSpunta(Boolean validaSpunta) {

	this.validaSpunta = validaSpunta;
    }

    public Date getDataInizioGerenza() {

	return dataInizioGerenza;
    }

    public void setDataInizioGerenza(Date dataInizioGerenza) {

	this.dataInizioGerenza = dataInizioGerenza;
    }

    public String getAutPrecedenteNumero() {

	return autPrecedenteNumero;
    }

    public void setAutPrecedenteNumero(String autPrecedenteNumero) {

	this.autPrecedenteNumero = autPrecedenteNumero;
    }

    public Date getAutPrecedenteData() {

	return autPrecedenteData;
    }

    public void setAutPrecedenteData(Date autPrecedenteData) {

	this.autPrecedenteData = autPrecedenteData;
    }

    public String getAutPrecComune() {

	return autPrecComune;
    }

    public void setAutPrecComune(String autPrecComune) {

	this.autPrecComune = autPrecComune;
    }

    public String getNumeroProtocolloAut() {

	return numeroProtocolloAut;
    }

    public void setNumeroProtocolloAut(String numeroProtocolloAut) {

	this.numeroProtocolloAut = numeroProtocolloAut;
    }

    public Date getDataProtocolloAut() {

	return dataProtocolloAut;
    }

    public void setDataProtocolloAut(Date dataProtocolloAut) {

	this.dataProtocolloAut = dataProtocolloAut;
    }

    public Integer getGerentecodice() {

	return gerentecodice;
    }

    public void setGerentecodice(Integer gerentecodice) {

	this.gerentecodice = gerentecodice;
    }

    public String getGerenteNominativo() {

	return gerenteNominativo;
    }

    public void setGerenteNominativo(String gerenteNominativo) {

	this.gerenteNominativo = gerenteNominativo;
    }

    public String getGerenteNome() {

	return gerenteNome;
    }

    public void setGerenteNome(String gerenteNome) {

	this.gerenteNome = gerenteNome;
    }

    public String getGerentePartitaiva() {

	return gerentePartitaiva;
    }

    public void setGerentePartitaiva(String gerentePartitaiva) {

	this.gerentePartitaiva = gerentePartitaiva;
    }

    public String getGerenteCodicefiscale() {

	return gerenteCodicefiscale;
    }

    public void setGerenteCodicefiscale(String gerenteCodicefiscale) {

	this.gerenteCodicefiscale = gerenteCodicefiscale;
    }

    public String getGerenteEmail() {

	return gerenteEmail;
    }

    public void setGerenteEmail(String gerenteEmail) {

	this.gerenteEmail = gerenteEmail;
    }

    public String getGerenteFormagiuridica() {

	return gerenteFormagiuridica;
    }

    public void setGerenteFormagiuridica(String gerenteFormagiuridica) {

	this.gerenteFormagiuridica = gerenteFormagiuridica;
    }

    public Integer getCoadiuvantecodice() {

	return coadiuvantecodice;
    }

    public void setCoadiuvantecodice(Integer coadiuvantecodice) {

	this.coadiuvantecodice = coadiuvantecodice;
    }

    public String getCoadiuvanteNominativo() {

	return coadiuvanteNominativo;
    }

    public void setCoadiuvanteNominativo(String coadiuvanteNominativo) {

	this.coadiuvanteNominativo = coadiuvanteNominativo;
    }

    public String getCoadiuvanteNome() {

	return coadiuvanteNome;
    }

    public void setCoadiuvanteNome(String coadiuvanteNome) {

	this.coadiuvanteNome = coadiuvanteNome;
    }

    public String getCoadiuvantePartitaiva() {

	return coadiuvantePartitaiva;
    }

    public void setCoadiuvantePartitaiva(String coadiuvantePartitaiva) {

	this.coadiuvantePartitaiva = coadiuvantePartitaiva;
    }

    public String getCoadiuvanteCodicefiscale() {

	return coadiuvanteCodicefiscale;
    }

    public void setCoadiuvanteCodicefiscale(String coadiuvanteCodicefiscale) {

	this.coadiuvanteCodicefiscale = coadiuvanteCodicefiscale;
    }

    public String getCoadiuvanteEmail() {

	return coadiuvanteEmail;
    }

    public void setCoadiuvanteEmail(String coadiuvanteEmail) {

	this.coadiuvanteEmail = coadiuvanteEmail;
    }

    public String getCoadiuvanteFormagiuridica() {

	return coadiuvanteFormagiuridica;
    }

    public void setCoadiuvanteFormagiuridica(String coadiuvanteFormagiuridica) {

	this.coadiuvanteFormagiuridica = coadiuvanteFormagiuridica;
    }

    public String getTipologiaBattitori() {

	return tipologiaBattitori;
    }

    public void setTipologiaBattitori(String tipologiaBattitori) {

	this.tipologiaBattitori = tipologiaBattitori;
    }

    public Integer getPosteggioOccupato() {

	return posteggioOccupato;
    }

    public void setPosteggioOccupato(Integer posteggioOccupato) {

	this.posteggioOccupato = posteggioOccupato;
    }

    public Boolean getFlagAttiva() {

	return flagAttiva;
    }

    public void setFlagAttiva(Boolean flagAttiva) {

	this.flagAttiva = flagAttiva;
    }

    public Integer getPosdebspunt() {

	return posdebspunt;
    }

    public void setPosdebspunt(Integer posdebspunt) {

	this.posdebspunt = posdebspunt;
    }

    public Integer getCodiceTitolare() {

	return codiceTitolare;
    }

    public void setCodiceTitolare(Integer codiceTitolare) {

	this.codiceTitolare = codiceTitolare;
    }

    public String getTitNominativo() {

	return titNominativo;
    }

    public void setTitNominativo(String titNominativo) {

	this.titNominativo = titNominativo;
    }

    public String getTitNome() {

	return titNome;
    }

    public void setTitNome(String titNome) {

	this.titNome = titNome;
    }

    public String getTitCodicefiscale() {

	return titCodicefiscale;
    }

    public void setTitCodicefiscale(String titCodicefiscale) {

	this.titCodicefiscale = titCodicefiscale;
    }

    public String getTitPartitaiva() {

	return titPartitaiva;
    }

    public void setTitPartitaiva(String titPartitaiva) {

	this.titPartitaiva = titPartitaiva;
    }

    public String getTitIndirizzo() {

	return titIndirizzo;
    }

    public void setTitIndirizzo(String titIndirizzo) {

	this.titIndirizzo = titIndirizzo;
    }

    public String getTitCitta() {

	return titCitta;
    }

    public void setTitCitta(String titCitta) {

	this.titCitta = titCitta;
    }

    public String getTitCap() {

	return titCap;
    }

    public void setTitCap(String titCap) {

	this.titCap = titCap;
    }

    public String getTitProvincia() {

	return titProvincia;
    }

    public void setTitProvincia(String titProvincia) {

	this.titProvincia = titProvincia;
    }

    public String getTitNumiscrrea() {

	return titNumiscrrea;
    }

    public void setTitNumiscrrea(String titNumiscrrea) {

	this.titNumiscrrea = titNumiscrrea;
    }

    public Date getTitDataiscrrea() {

	return titDataiscrrea;
    }

    public void setTitDataiscrrea(Date titDataiscrrea) {

	this.titDataiscrrea = titDataiscrrea;
    }

    public String getTitProvinciarea() {

	return titProvinciarea;
    }

    public void setTitProvinciarea(String titProvinciarea) {

	this.titProvinciarea = titProvinciarea;
    }

    public String getTitTelefono() {

	return titTelefono;
    }

    public void setTitTelefono(String titTelefono) {

	this.titTelefono = titTelefono;
    }

    public String getTitEmail() {

	return titEmail;
    }

    public void setTitEmail(String titEmail) {

	this.titEmail = titEmail;
    }

    public Date getTitDataInizioAttivita() {

	return titDataInizioAttivita;
    }

    public void setTitDataInizioAttivita(Date titDataInizioAttivita) {

	this.titDataInizioAttivita = titDataInizioAttivita;
    }

    public Date getTitDataregditte() {

	return titDataregditte;
    }

    public void setTitDataregditte(Date titDataregditte) {

	this.titDataregditte = titDataregditte;
    }

    public Date getAutDataAnzianita() {

	return autDataAnzianita;
    }

    public void setAutDataAnzianita(Date autDataAnzianita) {

	this.autDataAnzianita = autDataAnzianita;
    }

    public Date getDataregditte() {

	return dataregditte;
    }

    public void setDataregditte(Date dataregditte) {

	this.dataregditte = dataregditte;
    }

    public RuoloAutorizzazioneEnum individuaRuolo(String cfriferimento) {


	if (cfriferimento.equalsIgnoreCase(this.getTitCodicefiscale())) {
	    // proprietario	
	    if (cfriferimento.equalsIgnoreCase(this.getCodicefiscale())) {
		return RuoloAutorizzazioneEnum.TitolareEOccupante;
	    } else {
		return RuoloAutorizzazioneEnum.DataInAffitto;
	    }
	} else if (cfriferimento.equalsIgnoreCase(this.getCodicefiscale())) {
	    //affittuario
	    return RuoloAutorizzazioneEnum.PresaInAffitto;
	    //PER CUI DEVEE ESSERE PER FORZA AFFITTUARIO
	} else if (cfriferimento.equalsIgnoreCase(this.getGerenteCodicefiscale())) {
	    //affittuario == gerente
	    return RuoloAutorizzazioneEnum.PresaInAffitto;
	} else {
	    return RuoloAutorizzazioneEnum.TitolareEOccupante;
	}
    }
}
