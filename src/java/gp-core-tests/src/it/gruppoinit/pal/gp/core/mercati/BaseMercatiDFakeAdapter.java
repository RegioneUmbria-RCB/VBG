package it.gruppoinit.pal.gp.core.mercati;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseDAOFakeAdapter;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.web.MercatiDFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

public abstract class BaseMercatiDFakeAdapter extends BaseDAOFakeAdapter<MercatiD, PkId> implements MercatiDDAO {

    @Override
    public List<MercatiD> findAllByMercato(Mercati mercati) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiD> findByMercatiD(MercatiD filter) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, Integer posteggioEscluso,
	    PosteggiEnum tipo) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD, boolean isSingoloPosteggio,
	    Integer codiceMercatoUso) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findPosizioneMaxByMercato(Integer codice) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void exportModalitaPentaho(MercatiD mercatiD, Esportazioni esportazioni, String email, TipicontestoesportazioniEnum posteggiMercato,
	    boolean isInvioMail) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<MercatiDDTO> findByMercatiDDTO(MercatiDFilter filter) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public MercatiDDTO findById(Integer codiceposteggio) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Integer> findByCodiceByMercato(Integer codicemercato, PosteggiEnum posteggiEnum) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PosteggioMercatoBean> findByIdGiornata(Integer idGiornata) {

	return null;
    }
}
