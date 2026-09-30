package it.gruppoinit.pal.gp.core.features.istanze.temp;

import java.util.List;

public interface TmpIstanzeDAO {

    void insert(String sessionId, List<String> uuidIstanze);
}
