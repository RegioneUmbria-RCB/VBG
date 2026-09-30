package it.gruppoinit.pal.gp.core.features.sistema.upgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MySQLTipimovimentoUtfBinServiceImpl implements IMySQLTipimovimentoUtfBinService {

    @Autowired
    private IMySQLTipimovimentoUtfBinDAO dao;

    @Override
    public List<String> upgrCollateUtf8() {

	return dao.upgrCollateUtf8();
    }

    @Override
    public List<String> upgrTipiMovimentiDoppi() {

	return dao.upgrTipiMovimentiDoppi();
    }
}
