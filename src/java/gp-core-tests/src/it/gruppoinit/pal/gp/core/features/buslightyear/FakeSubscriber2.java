package it.gruppoinit.pal.gp.core.features.buslightyear;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

public class FakeSubscriber2 implements IEventSubscriber<FakeEvent2> {

    public int onEventFakeEventInvocato = 0;

    @Override
    public void onEvent(FakeEvent2 e) {

	this.onEventFakeEventInvocato++;
    }
}
