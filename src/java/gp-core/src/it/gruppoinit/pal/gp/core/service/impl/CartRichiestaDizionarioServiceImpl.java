package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.CartRichiestaDizionarioService;
import it.gruppoinit.sigepro.cart.service.fru.CartRfc183RichiestaDizionarioServiceClient;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartRichiestaDizionarioServiceImpl extends CartBaseServiceImpl implements CartRichiestaDizionarioService {

    //private static final Logger log = LoggerFactory.getLogger(CartRichiestaDizionarioServiceImpl.class);
    
    private CartRfc183RichiestaDizionarioServiceClient cartRichiestaDizionarioService;
    
    @Autowired
    public void setCartRichiestaDizionarioService(CartRfc183RichiestaDizionarioServiceClient cartService){
	this.cartRichiestaDizionarioService = cartService;
	this.cartService = cartService;
    }

    @Override
    public void inviaRichiestaDizionario() throws RemoteException, SPCoopException {

	cartRichiestaDizionarioService.inviaRichiestaDizionario();
    }
}
