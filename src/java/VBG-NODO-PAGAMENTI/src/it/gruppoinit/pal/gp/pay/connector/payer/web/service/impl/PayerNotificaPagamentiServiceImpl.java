package it.gruppoinit.pal.gp.pay.connector.payer.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.pay.connector.payer.web.async.PayerNotificaPagamentiAsyncService;
import it.gruppoinit.pal.gp.pay.connector.payer.web.service.PayerNotificaPagamentiService;


@Service
public class PayerNotificaPagamentiServiceImpl implements PayerNotificaPagamentiService{

	private static final Logger log = LoggerFactory.getLogger(PayerNotificaPagamentiServiceImpl.class);
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;
    
	
	@Override
	public void provaAsync() {
		
		try {
		    taskExecutor.execute(new PayerNotificaPagamentiAsyncService(applicationContext));
		} catch (Exception e) {
		    e.printStackTrace();
		}

		
	}


	@Override
	public void avviaSincronizzazionePerCf(String cfEnteCreditore, String cfDebitore) {
		try {
		    taskExecutor.execute(new PayerNotificaPagamentiAsyncService(applicationContext,		    		
		    		cfEnteCreditore,
		    		cfDebitore));
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
		
	}

}
