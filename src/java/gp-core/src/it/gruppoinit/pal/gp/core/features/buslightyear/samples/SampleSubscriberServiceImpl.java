package it.gruppoinit.pal.gp.core.features.buslightyear.samples;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

import org.slf4j.LoggerFactory;

public class SampleSubscriberServiceImpl implements IEventSubscriber<SampleEvent> {

    @Override
    public void onEvent(SampleEvent e) {

	// .... FAI QUALCOSA CON L'EVENTO
	LoggerFactory.getLogger(SampleSubscriberServiceImpl.class).debug("Data operazione {}", e.getDataOperazione());
    }
}
