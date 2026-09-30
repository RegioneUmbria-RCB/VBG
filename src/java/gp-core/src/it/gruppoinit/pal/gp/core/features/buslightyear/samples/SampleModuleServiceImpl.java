package it.gruppoinit.pal.gp.core.features.buslightyear.samples;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleModuleServiceImpl extends EventBusModule {

    @Autowired
    public SampleModuleServiceImpl(IEventPublisher publisher) {

	super(publisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(SampleEvent.class, SampleSubscriberServiceImpl.class);
    }
}
