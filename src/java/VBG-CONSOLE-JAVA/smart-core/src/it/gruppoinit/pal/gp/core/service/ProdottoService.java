package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Prodotto;

public interface ProdottoService extends BaseService<Prodotto, String> {

    public Prodotto findProdotto();

    public String getIdentificativoProdotto(Alberoproc alberoproc);

    public String getIdentificativoFromPratica(Integer fodomandeId);

    public String findIdentificativoProdottoInstallazione();
}
