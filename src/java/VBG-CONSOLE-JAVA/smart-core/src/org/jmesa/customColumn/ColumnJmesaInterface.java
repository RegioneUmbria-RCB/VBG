package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.jmesa.IJMesaLinkHelper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.view.html.editor.DroplistFilterEditor;

public interface ColumnJmesaInterface {

    /**
     * <pre>
     * 
     * Colonna base proprietà, label
     * 
     * @param path 		: della proprietà Es bean1.proprieta or proprieta 
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param titleHeader	: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 				  il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addBaseColumn(String path, String label, String titleHeader, String width);

    /**
     * <pre>
     * 
     * Colonna base [proprietà, label] con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile
     * 	2- Ordinabile
     * 	3- Esportabile
     * 
     * @param path		: della proprietà Es bean1.proprieta or proprieta che si vuole visualizzare
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param filterable	: se filtrabile
     * @param sortable		: se ordinabile
     * @param export		: se esportabile
     * @param titleHeader	: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 				  il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addBaseColumn(String path, String label, boolean filterable, boolean sortable, boolean export, String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta un link  [proprietà(path), label] con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile
     * 	2- Ordinabile
     * 	3- Esportabile
     * 
     * Il link di destinazione potrà avere un solo parametro e il valore del parametro da passare all' url sarà recuperato
     * dal "pathparametro"
     * 
     * @param request
     * @param path		: della proprietà Es bean1.proprieta or proprieta che si vuole visualizzare
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param pathparametro	: path del parametro che verrà passato al link urlGoTo
     * @param urlGoTo		: link a cui deve portare
     * @param urlback		: link a cui deve tornare con l'history back
     * @param filterable	: se filtrabile
     * @param sortable		: se ordinabile
     * @param export		: se esportabile
     * @param titleHeader	: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 				  il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * 
     * </pre>
     */
    public void addLinkBaseColumn(HttpServletRequest request, String path, String label, String pathparametro, String urlGoTo, String urlback,
	    boolean filterable, boolean sortable, boolean export, String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta un link  con entichetta la label passata con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile 
     * 	2- Ordinabile 
     * 	3- Esportabile
     * 
     * Il link di destinazione potrà avere un solo parametro e il valore del parametro da passare all' url sarà recuperato
     * dal "path"
     * 
     * Di default la colonna non sarà esportabile
     * 
     * @param request
     * @param path		: path del parametro che verrà passato al link urlGoTo
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param nameLink		: nome del link visualizzato sulla tabella 
     * @param urlGoTo		: link a cui deve portare
     * @param urlback		: link a cui deve tornare con l'history back
     * @param filterable	: se filtrabile
     * @param sortable		: se ordinabile
     * @param export		: se esportabile
     * @param			: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 			          il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addLinkLabelBaseColumn(HttpServletRequest request, String path, String label, String nameLink, String urlGoTo, String urlback,
	    boolean filterable, boolean sortable, String titleHeader, String width);

    /**
     * <pre>
     * 	Colonna che rappresenta una proprietà data con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile 
     * 	2- Ordinabile 
     * 	3- Esportabile
     * 
     * 
     * @param path		: della proprietà Es bean1.proprieta or proprieta data che si vuole visualizzare	
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param dateFormat	: formato della data 
     * @param filterable	: se filtrabile
     * @param sortable		: se ordinabile
     * @param export		: se esportabile
     * @param titleHeader	: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 				  il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addDataBaseColumn(String path, String label, String dateFormat, boolean filterable, boolean sortable, boolean export,
	    String titleHeader, String width);

    /**
     * <pre>
     * 	Colonna che rappresenta una proprietà booleana (true,false) con la possibilità di decidere se renderla:
     * 
     *  1- Filtrabile 
     * 	2- Ordinabile 
     * 	3- Esportabile
     * 
     * @param path		: della proprietà Es bean1.proprieta or proprieta data che si vuole visualizzare
     * @param label		: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param filterable	: se filtrabile
     * @param sortable		: se ordinabile
     * @param export		: se esportabile
     * @param titleHeader	: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 				  il mouse sul titolo della colonna)[opzionale]
     * @param width		: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addBooleanColumn(String path, String label, boolean filterable, boolean sortable, boolean export, String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta una proprietà Integer che può assumere valori fissi,i valori saranno passati tramite una mappa 
     * 	a- key	:label
     *  b- value:valore fisso assoaciato
     *  
     *  	1- Filtrabile 
     * 		2- Ordinabile 
     * 		3- Esportabile
     * 
     * 	Es.  
     * 
     * 	Map<String, Integer> mappaLabelValoreTipologia = new HashMap<String, Integer>();
     * mappaLabelValoreTipologia.put("label.si", -1);
     * mappaLabelValoreTipologia.put("label.no", 0);
     * columnJmesa.addIntegerCustomColumn("tipologia", "anagrafe.label.tipologia_anagrafe", mappaLabelValoreTipologia, true, true, true, "4%");
     *  
     *  Questo esempio indica che che la proprietà integer potrà assumere 2 valori fissi :
     *  
     *  	- label.si 	e sarà mappato sul db con -1
     *  	- label.no 	e sarà mappato sul db con 0
     *  
     * @param path			: della proprietà Es bean1.proprieta or proprieta data che si vuole visualizzare
     * @param label 			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param mappaLabelValore		: Mappa che contiene i valori e le etichette che possono assumere i campi della colonna
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @param width			: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addIntegerCustomColumn(String path, String label, Map<String, Integer> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta una proprietà String che può assumere valori fissi,i valori saranno passati tramite una mappa 
     * 	a- key	:label
     *  b- value:valore fisso assoaciato
     *  
     *  	1- Filtrabile 
     * 		2- Ordinabile 
     * 		3- Esportabile
     * 
     * 	Es.
     * 
     * Map<String, String> mappaLabelValore = new HashMap<String, String>();
     * mappaLabelValore.put("list.jmesa.celleditor.persona_fisica", "F");
     * mappaLabelValore.put("list.jmesa.celleditor.persona_giuridica", "G");
     * columnJmesa.addStringCostunColumn("tipoanagrafe", "anagrafe.label.tipo_anagrafe", mappaLabelValore, true, true, true, "6%");
     * 
     * Questo esempio indica che che la proprietà string potrà assumere 2 valori fissi :
     *  
     *  	- list.jmesa.celleditor.persona_fisica 		e sarà mappato sul db con  F
     *  	- list.jmesa.celleditor.persona_giuridica 	e sarà mappato sul db con  G
     * 
     * @param path			: della proprietà Es bean1.proprieta or proprieta data che si vuole visualizzare
     * @param label 			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param mappaLabelValore		: Mappa che contiene i valori e le etichette che possono assumer i campi della colonna
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @param width			: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addStringCostunColumn(String path, String label, Map<String, String> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width);

    /**
     * <pre>
     * 
     * Colonna che rappresenta un link che svolge un azione.
     * 
     * Es. se vogliamo fare una cancellazione standard (chiave dell'oggetto che vogliamo cancellare deve essere singola) sceglieremo 
     * 
     * 	1- ColumnJemesa.DELETE come typeAction. Questo setterà alcune proprietà delle action cancellazione; come l'icona le label etc.
     * 	2- Il path che indica il parametro che definisce univocamente l'oggetto che vogliamo cancellare (bean1.proprietà or proprietà)
     * 	3- L'url che rappresenta l'azione
     * 	4- L'url a cui si vuole tornare (History back se si vuole)
     * 
     *  Di default la colonna non sarà filtrabile,ordinabile ed  esportabile
     * 
     * @param request
     * @param pathparametro		: path del parametro che verrà passato al link urlGoTo.
     * @param label			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param typeAction		: tipo di azione da svolgere (dettaglio,cancellazione, modifica...)
     * @param urlGoTo			: link a cui deve portare. Link del al Controller
     * @param urlback			: link a cui deve tornare con l'history back 
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @param width			: dimensione del della colonna in percentuale 
     * Se urlback viene messo null non fa l'HistorySet
     * 
     * </pre>
     */
    public void addBaseActionColumn(HttpServletRequest request, String pathparametro, String label, String typeAction, String urlGoTo,
	    String urlback, String titleHeader, String width);

    /**
     * <pre>
     * 
     * 	Colonna che rappresenta un link che fa il download dell'oggetto
     * 
     * 
     *  Di default la colonna non sarà filtrabile,ordinabile ed  esportabile
     * 
     * @param request
     * @param pathparametro		: path del parametro che verrà passato al link urlGoTo.
     * @param label			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * 
     * 
     * Se urlback viene messo null non fa l'HistorySet
     * 
     * </pre>
     */
    public void addDownloadActionColumn(HttpServletRequest request, String pathparametro);

    /**
     * <pre>
     * Colonna che rappresenta un link  [proprietà(path), label] con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile
     * 	2- Ordinabile
     * 	3- Esportabile
     * 
     * Il link di destinazione potrà avere un solo parametro e il valore del parametro da passare all' url sarà recuperato
     * dal "pathparametro"
     * 
     * @param request
     * @param path			: della proprietà Es bean1.proprieta or proprieta che si vuole visualizzare
     * @param label			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param labelElementColumn	: label del parametro che verrà stampato sulla lista
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @cellEditor                      : pemrtte di passare un cell Editor ad hoc (Es. un link che richiama un particolare javascrit)
     * @param width			: dimensione del della colonna in percentuale
     * 
     * </pre>
     */
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, String label, String labelElementColumn,
	    AbstractCellEditor cellEditor, boolean filterable, boolean sortable, boolean export, String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta un link  [proprietà(path), label] con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile
     * 	2- Ordinabile
     * 	3- Esportabile
     * 
     * Il link di destinazione potrà avere un solo parametro e il valore del parametro da passare all' url sarà recuperato
     * dal "pathparametro"
     * 
     * @param request
     * @param path			: della proprietà Es bean1.proprieta or proprieta che si vuole visualizzare  
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     *      
     * @cellEditor                      : pemrtte di passare un cell Editor ad hoc (Es. un link che richiama un particolare javascrit)
     * @customHeaderEditor              : permette di fare un header della tabella costum
     * @param width			: dimensione del della colonna in percentuale
     * 
     * </pre>
     */
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, AbstractCellEditor cellEditor,
	    HeaderEditor customHeaderEditor, boolean filterable, boolean sortable, boolean export, String width);

    /**
     * <pre>
     * Colonna che rappresenta un link  [proprietà(path), label] con la possibilità di decidere se renderla:
     * 
     * 	1- Filtrabile
     * 	2- Ordinabile
     * 	3- Esportabile
     * 
     * Il link di destinazione potrà avere un solo parametro e il valore del parametro da passare all' url sarà recuperato
     * dal "pathparametro"
     * 
     * @param request
     * @param path			: della proprietà Es bean1.proprieta or proprieta che si vuole visualizzare
     * @param label			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param labelElementColumn	: label del parametro che verrà stampato sulla lista
     * @param cellEditor		: definisce il cellEditor custom
     * @param droplistFilterEditor	: definisce il DroplistFilterEditor custom
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @param width			: dimensione del della colonna in percentuale
     * 
     * </pre>
     */
    public void addCellEditorCustomLabelColumn(HttpServletRequest request, String path, String label, String labelElementColumn,
	    AbstractCellEditor cellEditor, DroplistFilterEditor droplistFilterEditor, boolean filterable, boolean sortable, boolean export,
	    String titleHeader, String width);

    /**
     * <pre>
     * Colonna che rappresenta una proprietà Boolean che può assumere di etichetta valori fissi,i valori saranno passati tramite una mappa 
     * 	a- key	: label 
     *  b- value: valore booleano associato alla label
     *  
     *  	1- Filtrabile 
     * 		2- Ordinabile 
     * 		3- Esportabile
     * 
     * 	Es.  
     * 
     * 	Map<String, Boolean> mappaLabelValoreTipologia = new HashMap<String, Integer>();
     *  mappaLabelValoreTipologia.put("label.attiva", true);
     *  mappaLabelValoreTipologia.put("label.cessata", false);
     *  columnJmesa.addBooleanCustomColumn("flagAttiva", "label.stato", mappaLabelValoreTipologia, true, true, true, "4%");
     *  
     *  Questo esempio indica che che la proprietà integer potrà assumere 2 valori fissi :
     *  
     *  	- label.attiva 	e sarà mappato sul db con true
     *  	- label.cessata	e sarà mappato sul db con false
     *  
     * @param path			: della proprietà Es bean1.proprieta or proprieta data che si vuole visualizzare
     * @param label 			: titolo della colonna (label.proprietà) verrà recuperato dalla property.
     * @param mappaLabelValore		: Mappa che contiene i valori e le etichette che possono assumere i campi della colonna
     * @param filterable		: se filtrabile
     * @param sortable			: se ordinabile
     * @param export			: se esportabile
     * @param titleHeader		: title dell' header della colonna (fa comparire una stinga di title di help portando 
     * 					  il mouse sul titolo della colonna)[opzionale]
     * @param width			: dimensione del della colonna in percentuale
     * </pre>
     */
    public void addBooleanCustomColumn(String path, String label, Map<String, Boolean> mappaLabelValore, boolean filterable, boolean sortable,
	    boolean export, String titleHeader, String width);

    public void addLinkColumn(String path, IJMesaLinkHelper linkHelper, String titleKey, String width);

    /**
     * <p>
     * Metodo per la creazione di una cella con valore utilizzato come link html
     * 
     * Possibilità di scegliere se la colonna sia:
     * 
     * 1-filterable 2-sortable 3-export
     * 
     * @param path
     * @param linkHrefValue
     * @param linkHrefPlaceHolders
     * @param titleKey
     * @param width
     *            </p>
     */
    public void addLinkColumn(String path, IJMesaLinkHelper linkHelper, String titleKey, boolean filterable, boolean sortable, boolean export,
	    String width);

    /**
     * <p>
     * Metodo per la creazione di una cella con un icona utilizzata come link
     * 
     * Possibilità di scegliere se la colonna sia:
     * 
     * 1-filterable 2-sortable 3-export
     * 
     * @param path
     * @param linkHrefValue
     * @param linkHrefPlaceHolders
     * @param titleKey
     * @param width
     *            </p>
     */
    public void addLinkWithIconColumn(String classIcon, String title, IJMesaLinkHelper linkHelper, String titleKey, boolean filterable,
	    boolean sortable, boolean export, String width);
}
