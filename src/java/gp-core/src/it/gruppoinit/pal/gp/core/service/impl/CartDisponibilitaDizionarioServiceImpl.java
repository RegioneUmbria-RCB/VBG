package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaDizionarioService;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184DisponibilitaDizionarioService;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDisponibilitaDizionarioServiceImpl extends CartBaseServiceEROImpl implements CartDisponibilitaDizionarioService {

    //private CartRfc184DisponibilitaDizionarioService disponibilitaDizionarioCartService;
    @Autowired
    public void setDisponibilitaDizionarioService(CartRfc184DisponibilitaDizionarioService service) {

	this.cartService = service;
	//this.disponibilitaDizionarioCartService = service;
    }

    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	// SPCoopMessage message = getMessage(idEgov);
	this.deleteMessage(idEgov);
    }

    /**
     * Sono utilizzati nel service che gestisce l'elaborazione del dizionario
     */
    @Override
    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper) throws RemoteException, SPCoopException {

	// TODO Medoti che non devo essere implementati
    }

    @Override
    public CartInfoDizionarioHelper preElaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	// TODO Medoti che non devo essere implementati
	return null;
    }
}
