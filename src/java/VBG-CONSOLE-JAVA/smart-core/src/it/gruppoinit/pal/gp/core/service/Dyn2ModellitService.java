package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellitDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SchedaHelper;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;

public interface Dyn2ModellitService extends BaseService<Dyn2Modellit, PkId> {

    public static enum TipoControlloEnum {

	Bottone("Bottone"),
	Checkbox("Casella di spunta"),
	Data("Data"),
	RadioButtons("Gruppo di radio button"),
	MultiLista("Lista multivalore"),
	Lista("Lista valori"),
	ListaSIGePro("Lista valori da database"),
	Localizzazione("Localizzazione"),
	NumericoDouble("Numero decimale"),
	NumericoIntero("Numero intero"),
	Ricerca("Ricerca da database"),
	Testo("Testo"),
	Upload("Upload"),
	Link("Link");

	private String value;

	private TipoControlloEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public enum ProprietaCampi {
	AllowedExtensions,
	/**
	 */
	CampiSelect,
	/**
	*/
	CampoRicercaCodice,
	/**
	*/
	CampoRicercaDescrizione,
	/**
	*/
	Columns,
	/**
	*/
	CompletionSetCount,
	/**
	*/
	CondizioniJoin,
	/**
	*/
	CondizioniWhere,
	/**
	*/
	DescriptionBoxColumns,
	/**
	 */
	DigitalSignatureRequired,
	/**
	*/
	ElementiLista,
	/**
	 * 
	 */
	EspressioneRegolare,
	/*
	 * 
	 */
	ForzaValoriEsistenti,
	/**
	*/
	IgnoraErroriBinding,
	/**
	*/
	MaxLength,
	/**
	*/
	MultiLine,
	/**
	*/
	Multiselezione,
	/**
	*/
	NomeCampoTesto,
	/**
	*/
	NomeCampoValore,
	/**
	*/
	Obbligatorio,
	/**
	*/
	ReadOnly,
	/**
	*/
	Rows,
	/**
	*/
	TabelleSelect,
	/**
	*/
	Testo,
	/**
	*/
	TipoRicerca,
	/**
	*/
	Url,
	/**
	*/
	ValidationMaxValue,
	/**
	*/
	ValidationMinValue,
	/**
	*/
	Valore,
	/**
	*/
	ValoreFalse,
	/**
	*/
	ValoreTrue,
	/**
	*/
	ValueBoxColumns
    }

    public static enum CampiTestoEnum {

	ReadOnly("label.solo_lettura#select#false"),
	MaxLength("label.lunghezza_massima#input#99999"),
	Columns("label.larghezza_visualizzata#input#40")
	/**
	 * 
	 */
	,MultiLine("label.multi_riga#select#false"),
	Rows("label.num_riga_se_multiple#input#1"),
	EspressioneRegolare("label.espressione_regolare_validazione#input#");

	private String value;

	private CampiTestoEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiUploadEnum {

	AllowedExtensions("label.estensioni_consentite#input#"),
	DigitalSignatureRequired("label.richiede_firma#select#false");

	private String value;

	private CampiUploadEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiDataEnum {

	ReadOnly("label.solo_lettura#select#false");

	private String value;

	private CampiDataEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiInteroEnum {

	ReadOnly("label.solo_lettura#select#false"),
	MaxLength("label.lunghezza_massima#input#99999"),
	Columns("label.larghezza_visualizzata#input#10")
	/**
	 * 
	 */
	,ValidationMinValue("label.valore_minimo#input#0"),
	ValidationMaxValue("label.valore_massimo#input#99999");

	private String value;

	private CampiInteroEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiDecimaliEnum {

	ReadOnly("label.solo_lettura#select#false"),
	MaxLength("label.lunghezza_massima#input#99999"),
	Columns("label.larghezza_visualizzata#input#10")
	/**
	 * 
	 */
	,ValidationMinValue("label.valore_minimo#input#0"),
	ValidationMaxValue("label.valore_massimo#input#99999");

	private String value;

	private CampiDecimaliEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiCheckboxEnum {

	Testo("label.testo_checkbox#input#"),
	ValoreTrue("label.valore_spuntato#input#1"),
	ValoreFalse("label.valore_non_spuntato#input#0");

	private String value;

	private CampiCheckboxEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiListaEnum {

	ElementiLista("label.elementi_lista_tipologia_campo_lista#input#"),
	IgnoraErroriBinding("label.ignora_errori_binding#select#false");

	private String value;

	private CampiListaEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiListaSigeproEnum {

	CondizioneJoin("label.condizioni_join#input#"),
	CondizioniWhere("label.condizioni_where#input#"),
	NomeCampoValore("label.nome_campo_valore#input#"),
	/**
	 * 
	 */
	NomeCampoTesto("label.nome_campo_testo#input#");

	private String value;

	private CampiListaSigeproEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiMultiListaEnum {

	ElementiLista("label.elementi_lista_tipologia_campo_lista#input#"),
	IgnoraErroriBinding("label.ignora_errori_binding#select#false"),
	Multiselezione("label.abilita_multiselezione#select#false");

	private String value;

	private CampiMultiListaEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiRicercaEnum {

	ValueBoxColumns("label.larghezza_campo_codice#input#6"),
	DescriptionBoxColumns("label.larghezza_campo_descrizione#input#40"),
	/**
	 * 
	 */
	TipoRicerca("label.tipo_ricerca#selectTipoRicerca#0"),
	CampiSelect("label.campi_select#input#"),
	TabelleSelect("label.tabelle_select#input#"),
	CondizioniJoin("label.condizioni_join#input#"),
	/**
	 * 
	 */
	CondizioniWhere("label.condizioni_where#input#"),
	NomeCampoValore("label.nome_campo_valore#input#"),
	NomeCampoTesto("label.nome_campo_testo#input#"),
	CampoRicercaCodice("label.campo_ricerca_valore#input#"),
	/**
	 * 
	 */
	CampoRicercaDescrizione("label.campo_ricerca_descrizione#input#"),
	CompletionSetCount("label.numero_massimo_righe_ritornate#input#40"),
	ForzaValoriEsistenti("label.forza_valori_esistenti#select#true");

	private String value;

	private CampiRicercaEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiBottoneEnum {

	Testo("label.testo_bottone#input#");

	private String value;

	private CampiBottoneEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiRadioButtonsEnum {

	ElementiLista("label.elementi_lista_tipologia_campo_lista#input#"),
	IgnoraErroriBinding("label.ignora_errori_binding#select#false");

	private String value;

	private CampiRadioButtonsEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public static enum CampiLinkEnum {

	Testo("label.testo_link#input#"),
	Url("label.url_link#input#"),
	Valore("label.valore_link#input#");

	private String value;

	private CampiLinkEnum(String v) {

	    value = v;
	}

	public String value() {

	    return value;
	}
    }

    public List<Dyn2Modellit> findByDescrizione(Dyn2Modellit entity, boolean isComuneBase);

    /**
     * @see Dyn2ModellitDAO#findAllByDescrizione(String)
     */
    public List<Dyn2Modellit> findAllByDescrizione(String descrizione);

    /**
     * @see Dyn2ModellitDAO#findByDescrizioneAndSoftware(Dyn2Modellit entity, String codicesoftware)
     */
    public List<Dyn2Modellit> findByDescrizioneAndSoftware(Dyn2Modellit entity, String codicesoftware);

    public ModellidinamiciHelper populateModellodinamicoForAnagrafe(int codiceModello, Integer codiceAnagrafe);

    public ModellidinamiciHelper populateModellodinamicoForPreview(String idcomune, int codiceModello);

    public ModellidinamiciHelper populateModellodinamicoForStar(String idcomune, int codiceModello, DatiDomandaCart datiDomandaStar);

    /**
     * Renderizza il modello in html
     * 
     * @param helper
     * @return
     */
    public String render(ModellidinamiciHelper helper, Boolean flagDomandaDinamica);

    /**
     * 
     * @param codiceModello
     * @param codiceCampo
     */
    public boolean checkCampoUsedForModello(Integer codiceModello, Integer codiceCampo);

    public void aggiungiBloccoAScheda(String idcomuneModello, ModellidinamiciHelper helperScheda, Integer codiceModello, Integer numeroRiga,
	    Integer indice, Integer indiceMolteplicita);

    public void eliminaBloccoDaScheda(String idcomuneModello, ModellidinamiciHelper helperScheda, Integer numeroRiga, Integer indice,
	    Integer indiceMolteplicita);

    public String validaCampo(String idcomuneModello, Integer codiceModello, Integer codiceCampo, String valore);

    public String validaCampo(String idcomuneModello, ModellidinamiciCampoHelper campoHelper, String valore);

    /**
     * Ritorna una lista di modelli filtrata per descrizione (ilike), software e contesto del modello
     * (Istanze[IS],Attivita[AT],Anagrafe[AN]). Se non viene passato il codice software filtra per il software corrente
     * 
     * @param descrizione
     *            : Obbligatorio
     * @param codicesoftware
     *            :opzionale
     * @param contesto
     *            : obbligatorio
     * @return
     */
    public List<Dyn2Modellit> findByDescrizioneAndSoftwareAndContesto(String descrizione, String codicesoftware, String contesto);

    public String renderCampo(CustomHtmlBuilder html, TipoControlloEnum tipodato, Dyn2Campi d2c, ModellidinamiciCampoHelper campo);

    /**
     * Cerca per il campo codicescheda e software corrente
     * 
     * @param idModello
     * @return
     */
    public Dyn2Modellit findByCodiceScheda(String idcomuneModello, String idModello);

    public ModellidinamiciHelper populateScheda(String idcomune, SchedaHelper schedaH);

    public ModellidinamiciHelper populateSchedaAsTemplate(String idcomune, SchedaHelper schedaH, String codScheda);

    public Dyn2Modellit copiaModello(Dyn2Modellit copiaDa);

    public void riordinaRigheModello(String idcomuneModello, Integer codiceModello);
}
