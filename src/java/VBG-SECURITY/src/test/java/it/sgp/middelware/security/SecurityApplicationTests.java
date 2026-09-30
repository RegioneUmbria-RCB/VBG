package it.sgp.middelware.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

import it.sgp.middleware.security.SecurityApplication;
import it.sgp.middleware.security.dao.SigeproBackofficeDAO;
import it.sgp.middleware.security.dao.impl.JDBCSigeproBackofficeDAOImpl;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.domain.ComunisecurityParam;
import it.sgp.middleware.security.domain.ComunisecuritySession;
import it.sgp.middleware.security.domain.ComunisecurityTpartnerapp;
import it.sgp.middleware.security.domain.ContestoEnum;
import it.sgp.middleware.security.domain.InfoUtenteSigepro;
import it.sgp.middleware.security.service.ComunisecurityAppService;
import it.sgp.middleware.security.service.ComunisecurityParamService;
import it.sgp.middleware.security.service.ComunisecurityService;
import it.sgp.middleware.security.service.ComunisecuritySessionService;
import it.sgp.middleware.security.service.ComunisecurityTpartnerappService;
import it.sgp.middleware.security.service.LoginService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

@ContextConfiguration(classes = SecurityApplication.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({ "/test-security.sql" })
class SecurityApplicationTests {

    @Test
    void contextLoads() {

    }

    @Autowired
    private ComunisecurityParamService comunisecurityParamService;
    @Autowired
    private ComunisecurityAppService comunisecurityAppService;
    @Autowired
    private ComunisecuritySessionService comunisecuritySessionService;
    @Autowired
    private ComunisecurityService comunisecurityService;
    @Autowired
    private ComunisecurityTpartnerappService comunisecurityTpartnerappService;
    @Autowired
    private SigeproBackofficeDAO sigeproBackofficeDAO;
    @Autowired
    private LoginService loginService;

    @Test
    void testListaTuttiIParams() {

	List<ComunisecurityParam> all = comunisecurityParamService.findAll();
	assertTrue(!all.isEmpty(), "Il metodo non ha ritornato elementi");
    }

    @Test
    void testFindByTokenMethod() {

	List<ComunisecurityTpartnerapp> byToken = comunisecurityTpartnerappService.findByToken("01d853d4-ef78-4a1a-908d-e5b120c83ff6");
	assertTrue(!byToken.isEmpty(), "Il metodo non ha ritornato elementi");
    }

    @Test
    void testFindByTokenAndSoftwareAndcodicecomuneMethod() {

	List<ComunisecurityTpartnerapp> byToken = comunisecurityTpartnerappService.findByTokenComuneESoftware("01d853d4-ef78-4a1a-908d-e5b120c83ff6",
		"E256", "CO");
	assertTrue(!byToken.isEmpty(), "Il metodo non ha ritornato elementi");
    }

    @Test
    void testInsertTokenPartnerAppMethodThrowsException() {

	ComunisecurityTpartnerapp c = new ComunisecurityTpartnerapp();
	// c.setToken("01d853d4-ef78-4a1a-908d-e5b120c83ff6");
	c.setCodicecomune("G478");
	c.setSoftware("CO");
	Exception e = assertThrows(ConstraintViolationException.class, () -> {
	    comunisecurityTpartnerappService.insert(c);
	});
	assertTrue(e instanceof ValidationException);
    }

    /*
    @Test
    void testInsertTokenPartnerAppMethod() {
    
    	ComunisecurityTpartnerapp c = new ComunisecurityTpartnerapp();
    	c.setToken("01d853d4-ef78-4a1a-908d-e5b120c83ff6");
    	c.setCodicecomune("G478");
    	c.setSoftware("CO");
    	comunisecurityTpartnerappService.insert(c);
    	assertTrue(c.getId() != null, "dato inserito correttamente");
    	comunisecurityTpartnerappService.delete(c);
    }
    */
    @Test
    void testPaginationWithoutParams() {

	List<ComunisecurityApp> all = comunisecurityAppService.findAll(null, null);
	assertTrue(all.size() == 14, "Il conteggio dei record non risulta 14 ma " + all.size());
    }

    @Test
    void testPaginationWithParams() {

	List<ComunisecurityApp> all = comunisecurityAppService.findAll(0, 5);
	assertTrue(all.size() == 5, "Il conteggio dei record non risulta 5 ma " + all.size());
    }

    @Test
    void testPaginationWithParamsReturnExpectedValues() {

	List<String> expectedValues = populateDefaultAppValues();
	List<ComunisecurityApp> all = comunisecurityAppService.findAll(1, 5);
	all.forEach((a) -> {
	    assertTrue(expectedValues.contains(a.getId()), "Valore " + a.getId() + " non trovato in " + expectedValues);
	});
    }

    private static final List<String> populateDefaultAppValues() {

	List<String> ret = new ArrayList<>();
	ret.add("05_BACKOFFICE_2");
	ret.add("06_AREARISERVATA_2");
	ret.add("07_NLA-ENTE_2");
	ret.add("08_NLA-PDD-RI_2");
	ret.add("09_admin_3");
	return ret;
    }
    /*
    @Test
    void testCancellazioneToken() {
    
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DATE, 21);
    	cal.set(Calendar.MONTH, 3);
    	cal.set(Calendar.YEAR, 2024);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	comunisecuritySessionService.deleteBeforeDate(cal.getTime(), false);
    	List<ComunisecuritySession> all = comunisecuritySessionService.findAll();
    	assertTrue(all.size() == 10, "Non sono stati cancellati correttamente " + all.size());
    }*/

    @Test
    void testFindBydescrizioneOrAlias() {

	String g478 = "g478";
	List<Comunisecurity> all = comunisecurityService.findByDescrizioneOrAlias("g478");
	assertTrue(all.size() == 2, "Non sono stati trovati 2 record ma " + all.size() + " record con stringa " + g478);
    }
    /*
    @Test
    void testJdbcDao() {
    
    String token = loginService.login("E256", ContestoEnum.OPE, "admin", "202cb962ac59075b964b07152d234b70", "127.0.0.1", false);
    assertTrue(StringUtils.hasLength(token), "Errore in autenticazione");
    }
    */
}