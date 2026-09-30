package it.gruppoinit.pal.gp.core.features.infrastructure.security;

import java.util.List;

import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;

public interface SigeproSecurityUtilsService {

    List<String> getAliasAttivi();

    void setORMHelper(String alias) throws SecurityException;

    void setORMHelper(String alias, String software) throws SecurityException;

    void resetThreadLocalVars();

    CheckTokenResponse infoToken(String token);
}
