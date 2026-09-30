package it.sgp.middleware.security.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.sgp.middleware.security.domain.CheckTokenResult;
import it.sgp.middleware.security.domain.ComuniSecuritySessMetadati;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.rest.schema.ChangeTokenRequest;
import it.sgp.middleware.security.rest.schema.ChangeTokenResponse;
import it.sgp.middleware.security.rest.schema.LetturaMetadatiResponse;
import it.sgp.middleware.security.rest.schema.Metadati;
import it.sgp.middleware.security.rest.schema.SalvataggioMetadatiRequest;
import it.sgp.middleware.security.service.ComuniSecuritySessMetadatiService;
import it.sgp.middleware.security.service.ComunisecurityService;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import it.sgp.middleware.security.service.LoginService;

@RestController
@RequestMapping("/rest-services")
public class TokenRestController {

	private static final Logger log = LoggerFactory.getLogger(TokenRestController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ComuniSecuritySessMetadatiService comuniSecuritySessMetadatiService;
	
	@Autowired
	ComunisecuritySessionService comunisecuritySessionService;
	
	@GetMapping("/v1/security/token/metadati/{tkn}")
	public LetturaMetadatiResponse getTokenMetadati(@PathVariable String tkn) {
		
		if(StringUtils.isBlank(tkn)) {
			throw  new RuntimeException("Token can not be empty");
		}
		
		CheckTokenResult result = loginService.checkToken(tkn);
		if(!result.isValid()) {
			throw  new RuntimeException("Il token passato non risulta valido");
		}
		
		List<ComuniSecuritySessMetadati> entities = comuniSecuritySessMetadatiService.findAllByToken(tkn);
		List<Metadati> metadati = new ArrayList<>();
		for(ComuniSecuritySessMetadati entity : entities) {
			Metadati mdati = new Metadati();
			mdati.setNome(entity.getChiave());
			mdati.setValore(entity.getValore());
			metadati.add(mdati);
		}
		
		LetturaMetadatiResponse response = new LetturaMetadatiResponse();
		response.setMetadati(metadati);
		
		return response;
	}
	
	
	@PostMapping("/v1/security/token/metadati")
	public String setTokenMetadati(@RequestBody SalvataggioMetadatiRequest request) {
		
		if(StringUtils.isBlank(request.getToken())) {
			throw  new RuntimeException("Token can not be empty");
		}
		
		CheckTokenResult result = loginService.checkToken(request.getToken());
		if(!result.isValid()) {
			throw  new RuntimeException("Il token passato non risulta valido");
		}
		
		List<ComuniSecuritySessMetadati> entities = new ArrayList<>();
		for(Metadati metadati : request.getMetadati()) {
			ComuniSecuritySessMetadati entity = new ComuniSecuritySessMetadati();
			entity.setToken(request.getToken());
			entity.setChiave(metadati.getNome());
			entity.setValore(metadati.getValore());
			entities.add(entity);
		}
		
		comuniSecuritySessMetadatiService.bulkInsert(entities);
		
		return "OK";
	}
	
	@PostMapping("/v1/security/changetoken")
	public ChangeTokenResponse changeToken(@RequestBody ChangeTokenRequest request) {
		
		if(StringUtils.isBlank(request.getToken())) {
			throw  new RuntimeException("Token can not be empty");
		}
		
		CheckTokenResult result = loginService.checkToken(request.getToken());
		if(!result.isValid()) {
			throw  new RuntimeException("Il token passato non risulta valido");
		}
		
		ComunisecuritySession sessentity = comunisecuritySessionService.findById(request.getToken());
		List<ComuniSecuritySessMetadati> entities = comuniSecuritySessMetadatiService.findAllByToken(request.getToken());
		
		
		//NUOVO RECORD
		ComunisecuritySession newEntity = new ComunisecuritySession();
		String newtoken = UUID.randomUUID().toString();

        newEntity.setId(newtoken);
        newEntity.setContesto(sessentity.getContesto());
        newEntity.setClientIp(sessentity.getClientIp());
        newEntity.setAlias(sessentity.getAlias());
        newEntity.setIdcomune(sessentity.getIdcomune());
        newEntity.setFirstrequest(Calendar.getInstance().getTime());
        newEntity.setLastrequest(newEntity.getFirstrequest());
        newEntity.setUserid(sessentity.getUserid());
        newEntity.setValid(sessentity.getValid());
        newEntity.setTokenPartnerApp(sessentity.getTokenPartnerApp());
        newEntity.setAuthLevel(sessentity.getAuthLevel());
		
		//COPIA METADATI
		List<ComuniSecuritySessMetadati> newentities = new ArrayList<>();
		for(ComuniSecuritySessMetadati metadati : entities) {
			ComuniSecuritySessMetadati entity = new ComuniSecuritySessMetadati();
			entity.setToken(newtoken);
			entity.setChiave(metadati.getChiave());
			entity.setValore(metadati.getValore());
			newentities.add(entity);
		}
		
		comuniSecuritySessMetadatiService.insertNewTokenWithMetadati(sessentity, newEntity, newentities);
		
		ChangeTokenResponse response = new ChangeTokenResponse();
		response.setToken(newtoken);
		
		return response;
	}
}
