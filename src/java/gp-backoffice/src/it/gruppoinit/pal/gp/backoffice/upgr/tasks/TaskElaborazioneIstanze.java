package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrTaskElaborazioneIstanze")
public class TaskElaborazioneIstanze extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(TaskElaborazioneIstanze.class);
    @Autowired
    private IstanzeService istanzeService;

    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name(), false);
	SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	try {
	    activityLogInfo("Inizio la bonifica delle istanze");
	    String sql = "Select i.id.codice,i.id.idcomune,i.software.codice From Istanze i left join i.istanzeTempistica it where it.id.codice is null";
	    Query query = session.createQuery(sql);
	    List list = query.list();
	    for (Object values : list) {
		Object[] vals = (Object[]) values;
		Integer codiceIstanza = (Integer) vals[0];
		String idcomune = (String) vals[1];
		String software = (String) vals[2];
		ORMHelper.setIdcomune(idcomune);
		ORMHelper.setSoftware(software);
		activityLogInfo("Elaboro l'istanza [{}-{}]", new Object[] { codiceIstanza.intValue(), software });
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza.intValue()));
		istanzeService.primaElaborazione(istanza);
		this.commitTransaction();
		session.flush();
		session.clear();
		activityLogInfo("Terminata l'elaborazione dell'istanza: {}", new Object[] { istanza.getId() });
	    }
	    SigeproBusinessRules.buildDefaultRules();
	    activityLogInfo("Terminata l'elaborazione delle istanze");
	} catch (Exception e) {
	    String errorMessage = "";
	    if (e instanceof BusinessValidationException || e instanceof EntityValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    errorMessage += invalidValue.getMessage() + ",[" + invalidValue.getBeanClass() + "],[" + invalidValue.getPropertyPath() + "],["
			    + invalidValue.getPropertyName() + "]," + "\n";
		}
		//log.error("run: BusinessValidationError {}, [{}]", errorMessage, e.getMessage());
	    }
	    if (StringUtils.isBlank(errorMessage)) {
		errorMessage = e.getMessage();
	    }
	    errorMessage = MessageFormat.format("BusinessValidationError {0}, [{1}]", new Object[] { errorMessage, e.getMessage() });
	    handleErrorCondition(e, errorMessage);
	    //throw new RuntimeException(errorMessage, e);
	} finally {
	    ORMHelper.setIdcomune(origIdcomune);
	}
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
