package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoServiceImpl;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService;

public class MovimentiZipLogicoServiceImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkIsModificabile_lanciaeccezione_incaso_di_parametro_movimento_nullo() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.checkIsModificabile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIsModificabile_lanciaeccezione_incaso_di_movimento_nullo() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.setMovimentiNoSecurityService(new MovimentiServiceImplAdapterFidByIdReturnNull());
	service.checkIsModificabile(33);
    }

    @Test()
    public void checkIsModificabile_torna_false_se_notificato() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.setMovimentiNoSecurityService(new MovimentiServiceImplAdapterFidByIdReturnMovimentoNotificato());
	Assert.assertEquals("Il movimento è notificato", false, service.checkIsModificabile(33));
    }

    @Test()
    public void checkIsModificabile_torna_false_se_protocollato() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.setMovimentiNoSecurityService(new MovimentiServiceImplAdapterFidByIdReturnMovimentoProtocollato());
	Assert.assertEquals("Il movimento è protocollato", false, service.checkIsModificabile(33));
    }

    @Test()
    public void checkIsModificabile_torna_false_se_protocollato_e_notificato() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.setMovimentiNoSecurityService(new MovimentiServiceImplAdapterFidByIdReturnMovimentoProtocollatoEnotificato());
	Assert.assertEquals("Il movimento è protocollato e notificato", false, service.checkIsModificabile(33));
    }

    @Test()
    public void checkIsModificabile_torna_true_se_non_protocollato_e_non_notificato() {

	MovimentiZipLogicoServiceImpl service = new MovimentiZipLogicoServiceImpl();
	service.setMovimentiNoSecurityService(new MovimentiServiceImplAdapterFidByIdReturnMovimentoNonProtocollatoONotificato());
	Assert.assertEquals("Il movimento non è protocollato o notificato", true, service.checkIsModificabile(33));
    }

    class MovimentiServiceImplAdapterFidByIdReturnNull extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    return null;
	}
    }

    class MovimentiServiceImplAdapterFidByIdReturnMovimentoNonProtocollatoONotificato extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    Movimenti mov = new Movimenti();
	    return mov;
	}
    }

    class MovimentiServiceImplAdapterFidByIdReturnMovimentoProtocollato extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    Movimenti mov = new Movimenti();
	    mov.setNumeroprotocollo("123");
	    mov.setDataprotocollo(Calendar.getInstance().getTime());
	    return mov;
	}
    }

    class MovimentiServiceImplAdapterFidByIdReturnMovimentoProtocollatoEnotificato extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    Movimenti mov = new Movimenti();
	    mov.setNumeroprotocollo("123");
	    mov.setDataprotocollo(Calendar.getInstance().getTime());
	    mov.setInviatoConStc(MovimentiBaseService.STC_INVIATO);
	    return mov;
	}
    }

    class MovimentiServiceImplAdapterFidByIdReturnMovimentoNotificato extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    Movimenti mov = new Movimenti();
	    mov.setInviatoConStc(MovimentiBaseService.STC_INVIATO);
	    mov.setDataprotocollo(Calendar.getInstance().getTime());
	    return mov;
	}
    }

    class MovimentiServiceImplAdapterFidById extends MovimentiServiceImplAdapter {

	@Override
	public Movimenti findById(PkId id) {

	    return new Movimenti();
	}
    }
}
