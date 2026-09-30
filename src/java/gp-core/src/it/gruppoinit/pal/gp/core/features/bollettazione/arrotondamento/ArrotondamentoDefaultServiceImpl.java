package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;

@Service
public class ArrotondamentoDefaultServiceImpl implements ArrotondamentoService {

    @Override
    public BigDecimal arrotondamento(BigDecimal importo) {

	return importo.setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescrizione() {

	return "Arrotondamento all'intero più vicino. ";
    }

    @Override
    public String getNome() {

	return ArrotondamentoEnum.STANDARD.name();
    }

    @Override
    public List<ImportoBean> arrotondamento(List<ImportoBean> importi) {

	List<ImportoBean> listaConImportoArrotondato = new ArrayList<ImportoBean>();
	for (ImportoBean importoBean : importi) {
	    ImportoBean ib = new ImportoBean(this.arrotondamento(importoBean.getImporto()), importoBean.getMappaturaNodoPag());
	    listaConImportoArrotondato.add(ib);
	}
	return listaConImportoArrotondato;
    }
}
