package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upgrUpdateCodammsportellounicoTask")
public class UpdateCodammsportellounicoTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateCodammsportellounicoTask.class);
    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateCodammsportellounicoTask.run: Inizio aggiornamento");
	//	1 )Le configurazioni dei contromovimenti (AMMMOV, AMMRITSU) vanno settate a
	//	null dove sono uguali a quella della configurazione codammsportellounico.
	//	2 ) per i movimenti fatti va settato a null dove codiceamministrazione = a
	//	quella della configurazione codammsportellounico.
	Query query = session.createQuery("From Configurazione conf where conf.id.software = :softwareTT order by conf.id.idcomune");
	query.setString("softwareTT", WebConstants.SOFTWARE_TT);
	List<Configurazione> configuraziones = query.list();
	// QUERY HQL Tipicontromovimento.AMMMOV
	String hqlAmmov = "update Tipicontromovimento tab set tab.amministrazioniTipiMovimentoId = null where "
		+ "tab.amministrazioniTipiMovimentoId = :codammsportellounico and tab.id.idcomune = :idcomune";
	// QUERY HQL Tipicontromovimento.AMMRITSU
	String hqlAmmritsu = "update Tipicontromovimento tab set tab.amministrazioniTipiContromovimentoId = null where "
		+ "tab.amministrazioniTipiContromovimentoId = :codammsportellounico and tab.id.idcomune = :idcomune";
	// QUERY HQL Movimenti.CODICEAMMINISTRAZIONE
	String hqlMovimenti = "update Movimenti tab set tab.amministrazioniId = null where "
		+ "tab.amministrazioniId = :codammsportellounico and tab.id.idcomune = :idcomune";
	// Query hql per eliminare codammsportello unico
	//	String hqlEliminacodammsportellounico = "update Configurazione tab set tab.codammsportellounico = null where "
	//		+ "tab.id.software = :softwareTT and tab.id.idcomune = :idcomune";
	int rowCount = 0;
	for (Configurazione configurazione : configuraziones) {
	    Integer codammsportellounico = configurazione.getCodammsportellounico();
	    String idcomune = configurazione.getId().getIdcomune();
	    activityLogDebug("codammsportellounico [{}], idcomune [{}]", new Object[] { codammsportellounico, idcomune });
	    if (codammsportellounico != null) {
		query = session.createQuery(hqlAmmov);
		query.setInteger("codammsportellounico", codammsportellounico);
		query.setString("idcomune", idcomune);
		rowCount = query.executeUpdate();
		activityLogDebug("Setto a null ammmov. Record modificati {}", new Object[] { rowCount });
		query = session.createQuery(hqlAmmritsu);
		query.setInteger("codammsportellounico", codammsportellounico);
		query.setString("idcomune", idcomune);
		rowCount = query.executeUpdate();
		activityLogDebug("Setto a null ammritsu. Record modificati {}", new Object[] { rowCount });
		query = session.createQuery(hqlMovimenti);
		query.setInteger("codammsportellounico", codammsportellounico);
		query.setString("idcomune", idcomune);
		rowCount = query.executeUpdate();
		activityLogDebug("run: Setto a null codiceamministrazione. Record modificati {}", new Object[] { rowCount });
		session.flush();
		this.commitTransaction();
		session.flush();
		//		query = session.createQuery(hqlEliminacodammsportellounico);
		//		query.setString("softwareTT", WebConstants.SOFTWARE_TT);
		//		query.setString("idcomune", idcomune);
		//		rowCount = query.executeUpdate();
		//		log.debug("run: elimino codammsportellounico. Record modificati {}", rowCount);
	    }
	}
	activityLogInfo("UpdateCodammsportellounicoTask.run: Fine aggiornamento");
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
