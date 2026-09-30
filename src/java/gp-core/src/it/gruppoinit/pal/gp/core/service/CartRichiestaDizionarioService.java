package it.gruppoinit.pal.gp.core.service;

import java.rmi.RemoteException;

import org.openspcoop.pdd.services.SPCoopException;

public interface CartRichiestaDizionarioService extends CartBaseService {

    public void inviaRichiestaDizionario() throws SPCoopException, RemoteException;
}
