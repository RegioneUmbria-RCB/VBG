/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.domain.PayRegContabiliPagamenti;
import it.gruppoinit.pal.gp.pay.service.PayRegContabiliPagamentiService;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;


/**
 * @author francol
 *
 */
@Service
public class PayRegContabiliPagamentiServiceImpl extends BaseServiceImpl<PayRegContabiliPagamenti, PkId> implements PayRegContabiliPagamentiService {

    @Override
    public void insert(PayRegContabiliPagamenti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(PayRegContabiliPagamenti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(PayRegContabiliPagamenti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<PayRegContabiliPagamenti> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PayRegContabiliPagamenti findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PayRegContabiliPagamenti creaRegistrazioneContabile(RegistrazioneContabileType regCont) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected Class<PayRegContabiliPagamenti> getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }
    
    private void dataIntegration(PayRegContabiliPagamenti entity) {
    }

}
