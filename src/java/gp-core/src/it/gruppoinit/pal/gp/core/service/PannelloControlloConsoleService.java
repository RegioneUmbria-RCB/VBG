package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;

public interface PannelloControlloConsoleService {

    public CartInfoDizionarioHelper preElaboraMessaggio();

    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper);

    public String statoElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper);
}
