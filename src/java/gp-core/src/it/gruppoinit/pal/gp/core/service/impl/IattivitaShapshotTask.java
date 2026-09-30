package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

import org.springframework.beans.factory.annotation.Autowired;

// public class IattivitaShapshotTask implements Runnable {
public class IattivitaShapshotTask extends Thread {

    private IAttivitaService iAttivitaService;
    private IstanzeService istanzeService;
    private IAttivitaSnapshotService iAttivitaSnapshotService;
    private Integer codiceIstanza;
    private String idcomunealias;
    private String idcomune;
    private String software;
    //private String helperHibernateSFKey;
    private String token;
    private boolean isScollega;

    public IattivitaShapshotTask(IAttivitaService iAttivitaService, IstanzeService istanzeService, IAttivitaSnapshotService iAttivitaSnapshotService,
	    boolean isScollega, Integer codiceIstanza, String idcomunealias, String idcomune, String software, String token) {

	super();
	this.idcomunealias = idcomunealias;
	this.idcomune = idcomune;
	this.software = software;
	//this.helperHibernateSFKey = helperHibernateSFKey;
	this.token = token;
	this.iAttivitaService = iAttivitaService;
	this.istanzeService = istanzeService;
	this.iAttivitaSnapshotService = iAttivitaSnapshotService;
	this.codiceIstanza = codiceIstanza;
	this.isScollega = isScollega;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    /**
     * 
     * <pre>
     * Il metodo esegue su un Thread diverso o l'operazione di iAttivitaService.updateSnapShot(codiceIstanza) 
     * o di iAttivitaService.updateSnapShotCopia(iAttivita.getIstanza(), iAttivita) secondo la logica:
     * 
     * 	1- Controllo se "codiceIstanza" è diverso da null"
     * 		1.1 codiceIstanza != null : eseguo updateSnapShot(codiceIstanza)
     *          1.2 codiceIstanza == null : eseguo updateSnapShotCopia(iAttivita.getIstanza(), iAttivita)
     * 
     * </pre>
     * 
     */
    @Override
    public void run() {

	//		ORMHelper.setHibernateSFKey(helperHibernateSFKey);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	ORMHelper.setSoftware(software);
	ORMHelper.setToken(token);
	try {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (EntityUtils.getNestedProperty(istanza.getAttivita(), "id.codice") != null) {
		Integer codiceAttivita = istanza.getAttivita().getId().getCodice();
		IAttivita iAttivita = iAttivitaService.findById(new PkId(codiceAttivita));
		if (!(iAttivita.getIstanza().getId().getCodice().equals(codiceIstanza))) {
		    iAttivitaService.updateSnapShot(codiceIstanza);
		}
		iAttivitaService.updateSnapShotCopia(iAttivita);
	    }
	    if (isScollega) {
		iAttivitaSnapshotService.deleteByIstanza(codiceIstanza);
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }
}
