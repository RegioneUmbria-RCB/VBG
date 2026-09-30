package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.Map;

import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.ISecurityClient;
import it.gruppoinit.sigeprosecurity.ws.SecurityConfig;

public class FakeSecurityClient implements ISecurityClient {

    private String token;

    public FakeSecurityClient(String token) {

	this.token = token;
    }

    @Override
    public String loginAPP(SecurityConfig config) {

	return this.token;
    }

    @Override
    public void checkToken(SecurityConfig config, String token) {

	//non serve
    }

    @Override
    public TokenInfoType getTokenInfo(SecurityConfig config, String token) {

	//non serve
	return null;
    }

    @Override
    public Map<String, String> getParams(SecurityConfig config, String param) {

	//non serve
	return null;
    }

    @Override
    public Map<String, String> getParams(SecurityConfig config) {

	//non serve
	return null;
    }
}
