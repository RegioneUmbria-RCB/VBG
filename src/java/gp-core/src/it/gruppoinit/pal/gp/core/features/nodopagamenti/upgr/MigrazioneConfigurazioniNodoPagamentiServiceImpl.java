package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigrazioneConfigurazioniNodoPagamentiServiceImpl implements IMigrazioneConfigurazioniNodoPagamentiService {

    @Autowired
    private IMigrazioneConfigurazioniDAO migrazioneConfigurazioniDAO;

    @Override
    public List<String> migraConfigurazioniDaContiAParametri() {

	return migrazioneConfigurazioniDAO.eseguiMigrazione();
    }

    @Override
    public List<String> upgrCodiceVersamento() {

	return migrazioneConfigurazioniDAO.upgrCodiceVersamentoPerCausaliSingole();
    }
}
