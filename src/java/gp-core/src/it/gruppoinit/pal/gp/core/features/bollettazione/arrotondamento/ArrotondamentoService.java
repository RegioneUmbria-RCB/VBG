package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;

public interface ArrotondamentoService {

    public String getDescrizione();

    public String getNome();

    public BigDecimal arrotondamento(BigDecimal importo);

    public List<ImportoBean> arrotondamento(List<ImportoBean> importi);
}
