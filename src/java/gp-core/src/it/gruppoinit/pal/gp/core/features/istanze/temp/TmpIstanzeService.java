package it.gruppoinit.pal.gp.core.features.istanze.temp;

import java.util.List;

public interface TmpIstanzeService {

    void insert(String sessionId, List<String> uuidIstanze);
}
