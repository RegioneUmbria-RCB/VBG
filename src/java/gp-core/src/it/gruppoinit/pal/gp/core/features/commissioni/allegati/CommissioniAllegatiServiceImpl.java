package it.gruppoinit.pal.gp.core.features.commissioni.allegati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdilizieAllegatiFirmeModel;

@Service
public class CommissioniAllegatiServiceImpl implements ICommissioniAllegatiService {

    @Autowired
    private ICommissioniAllegatiDAO commissioniAllegatiDAO;

    @Override
    public long countFirmePerAllegato(Integer idAllegato) {

	return commissioniAllegatiDAO.countFirmePerAllegato(idAllegato);
    }

    @Override
    public List<CommissioniEdilizieAllegatiFirmeModel> findFirmePerAllegato(Integer idAllegato) {

	return commissioniAllegatiDAO.findFirmePerAllegato(idAllegato);
    }

    @Override
    public void eliminaFirmePerAllegato(Integer idAllegato) {

	commissioniAllegatiDAO.eliminaFirmePerAllegato(idAllegato);
    }
}
