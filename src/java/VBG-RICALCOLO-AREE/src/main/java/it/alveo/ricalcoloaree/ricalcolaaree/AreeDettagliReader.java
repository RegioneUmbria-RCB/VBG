package it.alveo.ricalcoloaree.ricalcolaaree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.micrometer.common.util.StringUtils;
import it.alveo.ricalcoloaree.bean.AreeDettagliBean;
import it.alveo.ricalcoloaree.dao.custom.AreeDettagliDAOCustom;
import it.alveo.ricalcoloaree.datilocalizzativi.RicalcolooRequest;
import it.alveo.ricalcoloaree.entities.AreeDettagli;
import it.alveo.ricalcoloaree.entities.IstanzeStradario;
import jakarta.persistence.EntityManager;

public class AreeDettagliReader {

    public List<AreeDettagliBean> getAreeDettagliFromIstanzeStradario(IstanzeStradario istanzestradario, String idcomune, EntityManager em) {

	RicalcolooRequest request = RicalcolooRequest.fromIstanzestradario(istanzestradario);
	//1. Se la request non ha civico e km, provo a calcolare le zone dallo stradrio
	if (StringUtils.isBlank(request.getCivico()) && StringUtils.isBlank(request.getKm())) {
	    AreeDettagliBean area = this.getAreeDettagliInBaseAllArea(request, idcomune, em);
	    if (area == null) {
		return new ArrayList<>();
	    }
	    List<AreeDettagliBean> lista = new ArrayList<>();
	    lista.add(area);
	    return lista;
	}
	List<AreeDettagliBean> retVal = new ArrayList<>();
	//2. Se è presente il civico, provo a calcolare le aree in base al civico
	if (!StringUtils.isBlank(request.getCivico())) {
	    List<AreeDettagliBean> listaDaCivico = this.getAreeDettagliInBaseAlCivico(request, idcomune, em);
	    retVal.addAll(listaDaCivico);
	}
	//3. Se è presente il km, provo a calcolare le aree in base al km
	if (!StringUtils.isBlank(request.getKm())) {
	    List<AreeDettagliBean> listaDaKm = this.getAreeDettagliInBaseAlKm(request, idcomune, em);
	    retVal.addAll(listaDaKm);
	}
	return retVal;
    }

    private List<AreeDettagliBean> getAreeDettagliInBaseAlCivico(RicalcolooRequest request, String idcomune, EntityManager em) {

	if (StringUtils.isBlank(request.getCivico())) {
	    return new ArrayList<>();
	}
	Integer civico = null;
	try {
	    civico = Integer.parseInt(checkCivicoNumeric(request.getCivico()));
	} catch (Exception e) {
	    return new ArrayList<>();
	}
	List<AreeDettagli> areelist = AreeDettagliDAOCustom.findByCustomCriteriaCivico(idcomune, request.getCodiceStradario(), civico, em);
	if (areelist.isEmpty()) {
	    return new ArrayList<>();
	}
	boolean isDispari = civico % 2 != 0;
	List<AreeDettagliBean> retVal = new ArrayList<>();
	for (AreeDettagli areedettagli : areelist) {
	    Boolean pariDispari = areedettagli.getParidispari();
	    // faccio attenzione a pari dispari
	    if (pariDispari == null || pariDispari.booleanValue() != isDispari) {
		AreeDettagliBean bean = new AreeDettagliBean();
		bean.setAreeDettagli(areedettagli);
		bean.setCodiceIstanza(request.getCodiceIstanza());
		bean.setIdcomune(idcomune);
		bean.setIstanza(request.getIstanza());
		retVal.add(bean);
	    }
	}
	return retVal;
    }

    private List<AreeDettagliBean> getAreeDettagliInBaseAlKm(RicalcolooRequest request, String idcomune, EntityManager em) {

	if (StringUtils.isBlank(request.getKm())) {
	    return new ArrayList<>();
	}
	BigDecimal km = null;
	try {
	    km = new BigDecimal(request.getKm().replace(",", "."));
	} catch (Exception e) {
	    return new ArrayList<>();
	}
	List<AreeDettagli> aree = AreeDettagliDAOCustom.findByCustomCriteriaKm(idcomune, request.getCodiceStradario(), km, em);
	if (aree.isEmpty()) {
	    return new ArrayList<>();
	}
	List<AreeDettagliBean> retVal = new ArrayList<>();
	for (AreeDettagli dettaglio : aree) {
	    AreeDettagliBean bean = new AreeDettagliBean();
	    bean.setAreeDettagli(dettaglio);
	    bean.setCodiceIstanza(request.getCodiceIstanza());
	    bean.setIdcomune(idcomune);
	    bean.setIstanza(request.getIstanza());
	    retVal.add(bean);
	}
	return retVal;
    }

    private AreeDettagliBean getAreeDettagliInBaseAllArea(RicalcolooRequest request, String idcomune, EntityManager em) {

	//Il metodo torna l'area solamente se ce n'è una sola collegata allo stradario
	Integer codiceStradario = request.getCodiceStradario();
	List<AreeDettagli> areelist = AreeDettagliDAOCustom.findByCustomCriteriaStradario(idcomune, codiceStradario, em);
	if (areelist.isEmpty() || areelist.size() > 1) {
	    return null;
	}
	AreeDettagliBean bean = new AreeDettagliBean();
	bean.setAreeDettagli(areelist.get(0));
	bean.setCodiceIstanza(request.getCodiceIstanza());
	bean.setIdcomune(idcomune);
	bean.setIstanza(request.getIstanza());
	return bean;
    }

    private String checkCivicoNumeric(String civico) {

	String answer = civico;
	String patternStr = "^([0-9]+)";
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(civico);
	if (matcher.find()) {
	    answer = (matcher.group());
	}
	return answer;
    }
}
