package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;
import org.jmesa.web.SpringWebContext;

public class ImportoOnereMulticomuneCellRenderer extends HtmlCellRendererImpl {

    private static final String DETAIL_LINK_URL = "../oneri/view.htm?id={0}&idcomune={1}";
    private static final String CREATE_NEW_LINK_URL = "../oneri/create.htm?idco={0}&idcomuneco={1}&idip={2}&idcomuneip={3}&codcomune={4}";
    private List<Responsabilicomuni> comuniGruppo = new ArrayList<Responsabilicomuni>();

    public ImportoOnereMulticomuneCellRenderer() {

	super();
    }

    public ImportoOnereMulticomuneCellRenderer(List<Responsabilicomuni> comuniGruppo) {

	this();
	this.comuniGruppo = comuniGruppo;
    }

    public List<Responsabilicomuni> getComuniGruppo() {

	return comuniGruppo;
    }

    public void setComuniGruppo(List<Responsabilicomuni> comuniGruppo) {

	this.comuniGruppo = comuniGruppo;
    }

    @Override
    public Object render(Object item, int rowcount) {

	String propName = getColumn().getProperty();
	OneriPerCausaleHelper opch = (OneriPerCausaleHelper) item;
	Map<String, Inventarioprocedimentioneri> propVal = (Map<String, Inventarioprocedimentioneri>) getCellEditor().getValue(item, propName,
		rowcount);
	HtmlBuilder html = new HtmlBuilder();
	html.td(1);
	html.close();
	List<Responsabilicomuni> resComList = new ArrayList<Responsabilicomuni>(getComuniGruppo().size() + 1);
	resComList.add(null);
	resComList.addAll(getComuniGruppo());
	Iterator<Responsabilicomuni> iterCom = resComList.iterator();
	html.ul().styleClass("inline-paragraph-list").close();
	//TODO collegamento alla configurazione di default del gruppo
	//itero su tutto l'elenco dei comuni del gruppo
	while (iterCom.hasNext()) {
	    Responsabilicomuni resCom = iterCom.next();
	    String codCom = null;
	    if (resCom != null) {
		codCom = resCom.getComune().getCodicecomune();
	    }
	    Inventarioprocedimentioneri onere = propVal.get(codCom);
	    //impostazione locale esistente: link alla pagina di modifica
	    String listUrl = opch.getUrlListaOneri();
	    String detUrl = opch.getUrlOnerePerComune(codCom);
	    //String tuttiComuni = UtilityJmesa.getLabel("inventarioprocedimentioneri.label.tuttiicomuni", (SpringWebContext) getWebContext());
	    String tuttiComuni = "tutti i comuni";
	    String descComune = resCom != null && resCom.getComune() != null ? resCom.getComune().getComune() : tuttiComuni;
	    StringBuilder text = new StringBuilder(descComune).append(": ");
	    BigDecimal importo = BigDecimal.ZERO;
	    String desc = "";
	    if (onere != null) {
		importo = onere.getImporto();
	    }
	    //impostazione locale inesistente: render del valore come link al dettaglio alla pagina per la creazione di un nuovo record
	    //viene visualizzato l'importo di default recuperato dall'impostazione di default del gruppo o da quella regionale se presenti
	    else {
		onere = propVal.get(null);
		if (onere != null) {
		    importo = onere.getImporto();
		    desc = " (default gruppo)";
		}
	    }
	    if (onere == null) {
		onere = opch.getOnereComuneBase();
		if (onere != null) {
		    importo = onere.getImporto();
		    desc = " (dafault regionale)";
		}
	    }
	    String style = "lista-compatta ";
	    if (onere != null) {
		if (BooleanUtils.isTrue(onere.getFlagDisattivo())) {
		    style += "onere-disattivo";
		}
	    }
	    text.append(Utilities.formatImporto(importo, 2, 2, false)).append(desc);
	    //MessageFormat mf = MessageFormat.format(pattern, );
	    StringBuilder sbHref = new StringBuilder("javascript:historySet(URLEncode('").append(listUrl);
	    sbHref.append("'),'").append(detUrl).append("','');");
	    html.li().styleClass(style).close();
	    html.a().href(sbHref.toString()).close();
	    html.append(text);
	    html.aEnd();
	    html.liEnd();
	}
	html.ulEnd();
	html.tdEnd();
	return html.toString();
    }
}
