package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpgrDuplicatiProgressivoOrdineServiceImpl implements UpgrDuplicatiProgressivoOrdineService {

    @Autowired
    private UpgrIstanzeCollegateDAO upgrIstanzeCollegateDAO;

    @Override
    public void eseguiUpgr() {

	//1. Estrazione dei doppioni a parità di progressivo e ordine da sdoppiare
	List<ProgressivoOrdineDuplicatoBean> doppioni = this.upgrIstanzeCollegateDAO.findProgressivoOrdineDuplicati();
	for (ProgressivoOrdineDuplicatoBean duplicato : doppioni) {
	    //2. Estrazione della lista di istanze di quel collegamento, ordinata secondo codice istanza collegata asc e data validità asc
	    List<IstanzeCollegateBean> collegamenti = this.upgrIstanzeCollegateDAO.findByIdcomuneEProgressivo(duplicato.getIdComune(),
		    duplicato.getProgressivo());
	    //3. Sistemazione dell'ordine
	    int ordine = 1;
	    for (IstanzeCollegateBean collegamento : collegamenti) {
		this.upgrIstanzeCollegateDAO.updateOrdine(collegamento.getIdComune(), collegamento.getId(), ordine);
		this.upgrIstanzeCollegateDAO.commitFlush();
		ordine++;
	    }
	}
    }
}