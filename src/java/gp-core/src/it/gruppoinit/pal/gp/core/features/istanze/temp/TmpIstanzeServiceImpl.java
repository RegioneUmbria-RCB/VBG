package it.gruppoinit.pal.gp.core.features.istanze.temp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TmpIstanzeServiceImpl implements TmpIstanzeService {

    private TmpIstanzeDAO tmpIstanzeDAO;

    @Autowired
    public void setTmpIstanzeDAO(TmpIstanzeDAO tmpIstanzeDAO) {

	this.tmpIstanzeDAO = tmpIstanzeDAO;
    }

    @Override
    public void insert(String sessionId, List<String> uuidIstanze) {

	this.tmpIstanzeDAO.insert(sessionId, uuidIstanze);
    }
}
