package it.gruppoinit.pal.gp.core.features.buslightyear;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

public class FakeSubscriber3 implements IEventSubscriber<FakeEvent> {

    public int onEventFakeEventInvocato = 0;

    @Override
    public void onEvent(FakeEvent e) {

	this.onEventFakeEventInvocato++;
    }
}