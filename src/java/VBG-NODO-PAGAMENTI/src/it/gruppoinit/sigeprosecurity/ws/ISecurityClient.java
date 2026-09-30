package it.gruppoinit.sigeprosecurity.ws;

import java.util.Map;

import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;

public interface ISecurityClient {

    public String loginAPP(SecurityConfig config);

    public void checkToken(SecurityConfig config, String token);

    public TokenInfoType getTokenInfo(SecurityConfig config, String token);

    public Map<String, String> getParams(SecurityConfig config, String param);

    public Map<String, String> getParams(SecurityConfig config);
}
