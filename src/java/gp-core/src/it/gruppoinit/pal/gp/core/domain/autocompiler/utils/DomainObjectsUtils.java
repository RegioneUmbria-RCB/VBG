package it.gruppoinit.pal.gp.core.domain.autocompiler.utils;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.wsanagrafe2.schema.ElenchiProfessionaliBase;
import it.gruppoinit.wsanagrafe2.schema.FormeGiuridiche;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class DomainObjectsUtils {

    private static final Logger log = LoggerFactory.getLogger(DomainObjectsUtils.class);
    private static final int DEFAULT_MAX_REFLECTION_DEPTH = 2;

    public static final Anagrafe anagrafeAsDomainObject(it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafe) {

	Anagrafe anagrafeOut = null;
	if (anagrafe != null) {
	    anagrafeOut = new Anagrafe();
	    anagrafeOut.setCap(anagrafe.getCAP());
	    anagrafeOut.setCapcorrispondenza(anagrafe.getCAPCORRISPONDENZA());
	    anagrafeOut.setCitta(anagrafe.getCITTA());
	    anagrafeOut.setCittacorrispondenza(anagrafe.getCITTACORRISPONDENZA());
	    Cittadinanza ctdnz = DomainObjectsUtils.cittadinanzaAsDomainObject(anagrafe.getCittadinanza());
	    anagrafeOut.setCittadinanza(ctdnz);
	    anagrafeOut.setCodicefiscale(anagrafe.getCODICEFISCALE());
	    Comuni cmnOut = DomainObjectsUtils.comuniAsDomainObject(anagrafe.getComuneNascita());
	    anagrafeOut.setComuneNascita(cmnOut);
	    anagrafeOut.setComunecorrispondenza(DomainObjectsUtils.comuniAsDomainObject(anagrafe.getComuneCorrispondenza()));
	    anagrafeOut.setComuneResidenza(DomainObjectsUtils.comuniAsDomainObject(anagrafe.getComuneResidenza()));
	    anagrafeOut.setComunecomregditte(DomainObjectsUtils.comuniAsDomainObject(anagrafe.getComuneRegDitte()));
	    anagrafeOut.setComuneregtrib(DomainObjectsUtils.comuniAsDomainObject(anagrafe.getComuneRegTrib()));
	    anagrafeOut.setDataiscrrea(DomainObjectsUtils.dateFromCalendarValue(anagrafe.getDATAISCRREA()));
	    anagrafeOut.setDatanascita(DomainObjectsUtils.dateFromCalendarValue(anagrafe.getDATANASCITA()));
	    anagrafeOut.setDatanominativo(DomainObjectsUtils.dateFromCalendarValue(anagrafe.getDATANOMINATIVO()));
	    anagrafeOut.setDataregditte(DomainObjectsUtils.dateFromCalendarValue(anagrafe.getDATAREGDITTE()));
	    anagrafeOut.setDataregtrib(DomainObjectsUtils.dateFromCalendarValue(anagrafe.getDATAREGTRIB()));
	    anagrafeOut.setEmail(anagrafe.getEMAIL());
	    Elenchiprofessionalibase epb = DomainObjectsUtils.elenchiProfessionaliBaseToDomainObject(anagrafe.getElencoProfessionale());
	    anagrafeOut.setElenchiprofessionalibase(epb);
	    anagrafeOut.setFax(anagrafe.getFAX());
	    //anagrafeOut.setFlagDisabilitato(anagrafe.getFLAG_DISABILITATO())
	    anagrafeOut.setFormagiuridica(DomainObjectsUtils.formeGiuridicheToDomainObject(anagrafe.getFormaGiuridicaClass()));
	    anagrafeOut.setIndirizzo(anagrafe.getINDIRIZZO());
	    anagrafeOut.setIndirizzocorrispondenza(anagrafe.getINDIRIZZOCORRISPONDENZA());
	    //anagrafeOut.setInvioemail(anagrafe.getINVIOEMAIL());
	    anagrafeOut.setNome(anagrafe.getNOME());
	    anagrafeOut.setNominativo(anagrafe.getNOMINATIVO());
	    anagrafeOut.setNote(anagrafe.getNOTE());
	    anagrafeOut.setNumeroelencopro(anagrafe.getNUMEROELENCOPRO());
	    anagrafeOut.setNumiscrrea(anagrafe.getNUMISCRREA());
	    anagrafeOut.setPartitaiva(anagrafe.getPARTITAIVA());
	    anagrafeOut.setPec(anagrafe.getPec());
	    anagrafeOut.setProvincia(anagrafe.getPROVINCIA());
	    anagrafeOut.setProvinciacorrispondenza(anagrafe.getPROVINCIACORRISPONDENZA());
	    anagrafeOut.setProvinciaelencopro(anagrafe.getPROVINCIAELENCOPRO());
	    anagrafeOut.setProvinciarea(anagrafe.getPROVINCIAREA());
	    anagrafeOut.setRegditte(anagrafe.getREGDITTE());
	    anagrafeOut.setRegtrib(anagrafe.getREGTRIB());
	    anagrafeOut.setSesso(anagrafe.getSESSO());
	    anagrafeOut.setTelefono(anagrafe.getTELEFONO());
	    anagrafeOut.setTelefonocellulare(anagrafe.getTELEFONOCELLULARE());
	    anagrafeOut.setTipoanagrafe(anagrafe.getTIPOANAGRAFE());
	    //anagrafeOut.setTipologia(anagrafe.getTIPOLOGIA());
	    anagrafeOut.setTitolo(DomainObjectsUtils.getTitoliFromString(anagrafe.getTITOLO()));
	}
	return anagrafeOut;
    }

    public static final Cittadinanza cittadinanzaAsDomainObject(it.gruppoinit.wsanagrafe2.schema.Cittadinanza ctdnz) {

	Cittadinanza ctdnzOut = null;
	if (ctdnz != null) {
	    ctdnzOut = new Cittadinanza();
	    ctdnzOut.setCf(ctdnz.getCf());
	    ctdnzOut.setCittadinanza(ctdnz.getDescrizione());
	    ctdnzOut.setCodice(ctdnz.getCodice());
	    ctdnzOut.setDisabilitato(ctdnz.getDisabilitato() != null && ctdnz.getDisabilitato().intValue() != 0);
	}
	return ctdnzOut;
    }

    public static final Comuni comuniAsDomainObject(it.gruppoinit.wsanagrafe2.schema.Comuni cmn) {

	Comuni cmnOut = null;
	if (cmn != null) {
	    cmnOut = new Comuni();
	    cmnOut.setCap(cmn.getCAP());
	    cmnOut.setCf(cmn.getCF());
	    cmnOut.setCodicecomune(cmn.getCODICECOMUNE());
	    cmnOut.setCodiceistat(cmn.getCODICEISTAT());
	    cmnOut.setCodiceistatregione(cmn.getCODICEISTATREGIONE());
	    cmnOut.setCodicestatoestero(cmn.getCODICESTATOESTERO());
	    cmnOut.setComune(cmn.getCOMUNE());
	    cmnOut.setProvincia(cmn.getPROVINCIA());
	    cmnOut.setSiglaprovincia(cmn.getSIGLAPROVINCIA());
	    cmnOut.setRegione(cmn.getREGIONE());
	}
	return cmnOut;
    }

    public static final Elenchiprofessionalibase elenchiProfessionaliBaseToDomainObject(ElenchiProfessionaliBase epb) {

	Elenchiprofessionalibase epbout = null;
	if (null != epb) {
	    epbout = new Elenchiprofessionalibase();
	    epbout.setEpDescrizione(epb.getEpDescrizione());
	    epbout.setId(epb.getEpId());
	}
	return epbout;
    }

    public static final Formegiuridiche formeGiuridicheToDomainObject(FormeGiuridiche fg) {

	Formegiuridiche fgout = null;
	if (null != fg) {
	    fgout = new Formegiuridiche();
	    fgout.setFormagiuridica(fg.getFORMAGIURIDICA());
	}
	return fgout;
    }

    public static Titoli getTitoliFromString(String titoloDesc) {

	Titoli tit = null;
	if (StringUtils.isNotBlank(titoloDesc)) {
	    tit = new Titoli();
	    tit.setTitolo(titoloDesc);
	}
	return tit;
    }

    public static final Date dateFromCalendarValue(Calendar calendar) {

	Date date = null;
	if (calendar != null) {
	    date = calendar.getTime();
	}
	return date;
    }

    public static final Date dateFromCalendarValue(XMLGregorianCalendar calendar) {

	Date date = null;
	if (calendar != null) {
	    return dateFromCalendarValue(calendar.toGregorianCalendar());
	}
	return date;
    }

    public static final Comuni comuniAsDomainObject(Comuni cmn) {

	Comuni cmnOut = null;
	if (cmn != null) {
	    cmnOut = new Comuni();
	    cmnOut.setCap(cmn.getCap());
	    cmnOut.setCf(cmn.getCf());
	    cmnOut.setCodicecomune(cmn.getCodicecomune());
	    cmnOut.setCodiceistat(cmn.getCodiceistat());
	    cmnOut.setCodiceistatregione(cmn.getCodiceistatregione());
	    cmnOut.setCodicestatoestero(cmn.getCodicestatoestero());
	    cmnOut.setComune(cmn.getComune());
	    cmnOut.setProvincia(cmn.getProvincia());
	    cmnOut.setSiglaprovincia(cmn.getSiglaprovincia());
	    cmnOut.setRegione(cmn.getRegione());
	}
	return cmnOut;
    }

    public static Object getDomainObjectAsPojo(Object domainObj, Class returnType, boolean excludeCollections) {

	return getDomainObjectAsPojo(domainObj, returnType, excludeCollections, DEFAULT_MAX_REFLECTION_DEPTH);
    }

    public static Object getDomainObjectAsPojo(Object domainObj, Class returnType, boolean excludeCollections, int reflectionDepth) {

	Object retObj = null;
	if (domainObj != null) {
	    retObj = BeanUtils.instantiateClass(returnType);
	    PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(returnType);
	    for (int i = 0; i < pds.length; i++) {
		Class retType = pds[i].getPropertyType();
		//escludo la copia dei valori delle proprietà di tipo byte[]
		boolean copyProp = !byte.class.equals(retType.getComponentType());
		if (copyProp && excludeCollections) {
		    copyProp &= !Collection.class.isAssignableFrom(retType);
		}
		if (copyProp) {
		    try {
			Method reader = pds[i].getReadMethod();
			Method writer = pds[i].getWriteMethod();
			if (reader != null && writer != null) {
			    Object propValue = null;
			    Class valueClass = reader.getReturnType();
			    if (valueClass.getPackage() != null && valueClass.getPackage().equals(Anagrafe.class.getPackage())) {
				//se il valore è a sua volta un oggetto di dominio invoco lo stesso metodo ricorsivamente
				// per un massimo di reflectionDepth livelli di ricorsione
				if(reflectionDepth > 0){
				    propValue = reader.invoke(domainObj);
				    propValue = getDomainObjectAsPojo(propValue, valueClass, excludeCollections, reflectionDepth - 1);
				}
			    }
			    else{
				propValue = reader.invoke(domainObj);
			    }
			    if (propValue != null) {
				writer.invoke(retObj, propValue);
			    }
			}
		    } catch (Exception e) {
			String errMsg = MessageFormat.format(
				"getDomainObjectAsPojo() - errore nella copia della proprietà {0} del bean di classe {1}",
				new Object[] { pds[i].getName(), returnType.getName() });
			log.error(errMsg, e);
		    }
		}
	    }
	}
	return retObj;
    }

    public static List getDomainObjectListAsPojoList(List inputList, Class elementsType, int reflectionDepth) {

	List<Object> retList = new ArrayList<Object>();
	if (inputList != null) {
	    for (Object inputObj : inputList) {
		retList.add(getDomainObjectAsPojo(inputObj, elementsType, true, reflectionDepth));
	    }
	}
	return retList;
    }
    
    public static List getDomainObjectListAsPojoList(List inputList, Class elementsType) {

	return getDomainObjectListAsPojoList(inputList, elementsType, DEFAULT_MAX_REFLECTION_DEPTH);
    }
}
