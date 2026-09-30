package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class CommissioniDettaglioDocumentiPratica {

    public static class DatiPraticaBreve {

	private Integer id;
	private String numero;
	private String dataPresentazione;
	private String numeroProtocollo;
	private String dataProtocollo;
	private String richiedente;
	private String oggetto;
	private String intervento;

	public static DatiPraticaBreve daIstanza(Istanze istanza) {

	    Integer codiceIstanza = istanza.getId().getCodice();
	    String numero = istanza.getNumeroistanza();
	    Date data = istanza.getData();
	    String richiedente = istanza.getRichiedente().getDescrizioneRichiedenteBreve();
	    String oggetto = istanza.getLavori();
	    String intervento = istanza.getAlberoproc().getDescrizioneCompleta();
	    String numeroProtocollo = istanza.getNumeroprotocollo();
	    Date dataProtocollo = istanza.getDataprotocollo();
	    return new DatiPraticaBreve(codiceIstanza, numero, data, numeroProtocollo, dataProtocollo, richiedente, oggetto, intervento);
	}

	public DatiPraticaBreve(Integer codiceIstanza, String numero, Date dataIstanza, String numeroProtocollo, Date dataProtocollo,
		String richiedente, String oggetto, String intervento) {

	    super();
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	    DateFormat dateFormatProtocollo = new SimpleDateFormat("dd/MM/yyyy");
	    this.id = codiceIstanza;
	    this.numero = numero;
	    this.dataPresentazione = dateFormat.format(dataIstanza);
	    this.richiedente = richiedente;
	    this.oggetto = oggetto;
	    this.intervento = intervento;
	    this.numeroProtocollo = numeroProtocollo;
	    this.dataProtocollo = dataProtocollo == null ? "" : dateFormatProtocollo.format(dataProtocollo);
	}

	public String getDataPresentazione() {

	    return dataPresentazione;
	}

	public String getRichiedente() {

	    return richiedente;
	}

	public String getOggetto() {

	    return oggetto;
	}

	public String getIntervento() {

	    return intervento;
	}

	public String getNumero() {

	    return numero;
	}

	public Integer getId() {

	    return id;
	}

	public String getNumeroProtocollo() {

	    return numeroProtocollo;
	}

	public String getDataProtocollo() {

	    return dataProtocollo;
	}
    }

    public static class DatiCommissioneBreve {

	private Integer id;
	private Integer idDettaglioCommissione;
	private String numero;
	private String data;
	private String descrizione;
	private boolean aperta;

	public static DatiCommissioneBreve daCommissioneT(CommissioniedilizieT commissione, int idDettaglioCommissione) {

	    Integer id = commissione.getId().getCodice();
	    String numero = commissione.getNumprotocollo();
	    Date data = commissione.getData();
	    String descrizione = commissione.getDescrizione();
	    boolean aperta = BooleanUtils.isTrue(commissione.getFlagaperta());
	    // TODO Auto-generated method stub
	    return new DatiCommissioneBreve(id, idDettaglioCommissione, numero, data, descrizione, aperta);
	}

	public DatiCommissioneBreve(int id, int idDettaglioCommissione, String numero, Date data, String descrizione, boolean aperta) {

	    super();
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	    this.id = id;
	    this.idDettaglioCommissione = idDettaglioCommissione;
	    this.numero = numero;
	    if (data != null) {
		this.data = dateFormat.format(data);
	    }
	    this.descrizione = descrizione;
	    this.aperta = aperta;
	}

	public String getDescrizione() {

	    return descrizione;
	}

	public String getData() {

	    return data;
	}

	public String getNumero() {

	    return numero;
	}

	public Integer getId() {

	    return id;
	}

	public Integer getIdDettaglioCommissione() {

	    return idDettaglioCommissione;
	}

	public boolean isAperta() {

	    return aperta;
	}

	public void setAperta(boolean aperta) {

	    this.aperta = aperta;
	}
    }

    public static class RiferimentiDocumento {

	private Integer fkId;
	private String descrizione;
	private String nomeFile;
	private Integer codiceOggetto;
	private Boolean selezionato = Boolean.FALSE;

	public RiferimentiDocumento(Integer fkId, String descrizione, String nomeFile, Integer codiceOggetto) {

	    super();
	    this.fkId = fkId;
	    this.descrizione = descrizione;
	    this.nomeFile = nomeFile;
	    this.codiceOggetto = codiceOggetto;
	}

	public String getDescrizione() {

	    return descrizione == null ? "" : descrizione;
	}

	public String getNomeFile() {

	    return nomeFile == null ? "" : nomeFile;
	}

	public Integer getCodiceOggetto() {

	    return codiceOggetto;
	}

	public Boolean getSelezionato() {

	    return selezionato;
	}

	public void seleziona() {

	    this.selezionato = Boolean.TRUE;
	}

	public void deseleziona() {

	    this.selezionato = Boolean.FALSE;
	}

	public Integer getFkId() {

	    return fkId;
	}
    }

    public static class RaggruppamentoDocumenti {

	private String categoria;
	private String titolo;
	private List<RiferimentiDocumento> documenti = new ArrayList<CommissioniDettaglioDocumentiPratica.RiferimentiDocumento>();

	public RaggruppamentoDocumenti(String categoria, String titolo) {

	    this.categoria = categoria;
	    this.titolo = titolo;
	}

	public String getTitolo() {

	    return titolo;
	}

	public String getCategoria() {

	    return categoria;
	}

	public List<RiferimentiDocumento> getDocumenti() {

	    return documenti;
	}

	public void aggiungiDocumento(RiferimentiDocumento doc) {

	    this.documenti.add(doc);
	}
    }

    private DatiPraticaBreve pratica;
    private DatiCommissioneBreve commissione;
    private RaggruppamentoDocumenti documentiGenerali = new RaggruppamentoDocumenti("documentiGenerali", "Documenti generali");
    private List<RaggruppamentoDocumenti> documentiEndoprocedimenti = new ArrayList<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti>();
    private List<RaggruppamentoDocumenti> documentiMovimenti = new ArrayList<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti>();

    public CommissioniDettaglioDocumentiPratica(DatiPraticaBreve pratica, DatiCommissioneBreve commissione) {

	this.pratica = pratica;
	this.commissione = commissione;
    }

    public DatiPraticaBreve getPratica() {

	return pratica;
    }

    public DatiCommissioneBreve getCommissione() {

	return commissione;
    }

    public RaggruppamentoDocumenti getDocumentiGenerali() {

	return documentiGenerali;
    }

    public List<RaggruppamentoDocumenti> getDocumentiEndoprocedimenti() {

	return documentiEndoprocedimenti;
    }

    public List<RaggruppamentoDocumenti> getDocumentiMovimenti() {

	return documentiMovimenti;
    }

    public List<RaggruppamentoDocumenti> getTuttiDocumenti() {

	List<RaggruppamentoDocumenti> docs = new ArrayList<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti>();
	docs.add(this.getDocumentiGenerali());
	docs.addAll(this.getDocumentiEndoprocedimenti());
	docs.addAll(this.getDocumentiMovimenti());
	return docs;
    }

    public void aggiungiDocumentiEndo(RaggruppamentoDocumenti documenti) {

	this.getDocumentiEndoprocedimenti().add(documenti);
    }

    public void aggiungiDocumentiMovimento(RaggruppamentoDocumenti documenti) {

	this.getDocumentiMovimenti().add(documenti);
    }

    public void aggiungiDocumentoGenerale(RiferimentiDocumento dto) {

	this.getDocumentiGenerali().getDocumenti().add(dto);
    }

    public void setDocumentiSelezionati(RiferimentiDocumentiSelezionati documentiSelezionati) {

	// Complessità smodata!!!!!
	for (Integer id : documentiSelezionati.getDocumentiIstanza()) {
	    for (RiferimentiDocumento doc : this.getDocumentiGenerali().getDocumenti()) {
		if (doc.getFkId().equals(id)) {
		    doc.seleziona();
		}
	    }
	}
	for (Integer id : documentiSelezionati.getDocumentiEndo()) {
	    for (RaggruppamentoDocumenti documentiEndo : this.getDocumentiEndoprocedimenti()) {
		for (RiferimentiDocumento doc : documentiEndo.getDocumenti()) {
		    if (doc.getFkId().equals(id)) {
			doc.seleziona();
		    }
		}
	    }
	}
	for (Integer id : documentiSelezionati.getDocumentiMovimenti()) {
	    for (RaggruppamentoDocumenti documentiMov : this.getDocumentiMovimenti()) {
		for (RiferimentiDocumento doc : documentiMov.getDocumenti()) {
		    if (doc.getFkId().equals(id)) {
			doc.seleziona();
		    }
		}
	    }
	}
    }
}
