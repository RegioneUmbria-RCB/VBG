package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;

public class NoArrotondamentoServiceImpl implements ArrotondamentoService {

    @Override
    public String getDescrizione() {

	return null;
    }

    @Override
    public String getNome() {

	return null;
    }

    @Override
    public BigDecimal arrotondamento(BigDecimal importo) {

	return importo;
    }

    @Override
    public List<ImportoBean> arrotondamento(List<ImportoBean> importi) {

	return importi;
    }
}
