package it.alveo.segnalazioniproxy.servicies;

import it.alveo.segnalazioniproxy.clients.SigeproSecurityClient;
import it.alveo.sigeproSecurity.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SigeproSecurityService {
    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityService.class);

    private final SigeproSecurityClient sigeproSecurityClient;

    @Autowired
    public SigeproSecurityService(SigeproSecurityClient sigeproSecurityClient) {
        this.sigeproSecurityClient = sigeproSecurityClient;
    }

    @Transactional
    public String getToken(String alias, String software, String uuidPratica) throws Exception {
        log.info("Inizio metodo getToken dal servizio di security");
        LoginResponse response = sigeproSecurityClient.login(alias, software, uuidPratica);
        return response.getToken();
    }
}
