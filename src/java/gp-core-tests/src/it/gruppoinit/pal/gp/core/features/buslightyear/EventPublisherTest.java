package it.gruppoinit.pal.gp.core.features.buslightyear;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

import org.junit.Assert;
import org.junit.Test;

public class EventPublisherTest {

    @Test
    public void aggiuntaDiUnHandler() {

	FakeKernel fakeKernel = new FakeKernel();
	IEventPublisher pub = new EventPublisherServiceImpl(fakeKernel);
	pub.add(FakeEvent.class, FakeSubscriber.class);
    }

    @Test
    public void publishDiUnEventoInvocaHandler() {

	FakeSubscriber sub = new FakeSubscriber();
	FakeKernel fakeKernel = new FakeKernel();
	fakeKernel.beansregistrati.put(sub.getClass().getName(), sub);
	IEventPublisher pub = new EventPublisherServiceImpl(fakeKernel);
	pub.add(FakeEvent.class, FakeSubscriber.class);
	pub.publish(new FakeEvent());
	Assert.assertEquals(1, sub.onEventFakeEventInvocato);
    }

    @Test
    public void aggiuntaPiuDiUnHandlerPerEventiSeparati() {

	FakeSubscriber sub = new FakeSubscriber();
	FakeSubscriber2 sub2 = new FakeSubscriber2();
	FakeKernel fakeKernel = new FakeKernel();
	fakeKernel.beansregistrati.put(sub.getClass().getName(), sub);
	fakeKernel.beansregistrati.put(sub2.getClass().getName(), sub2);
	IEventPublisher pub = new EventPublisherServiceImpl(fakeKernel);
	pub.add(FakeEvent.class, FakeSubscriber.class);
	pub.add(FakeEvent2.class, FakeSubscriber2.class);
	pub.publish(new FakeEvent());
	Assert.assertEquals(1, sub.onEventFakeEventInvocato);
	Assert.assertEquals(0, sub2.onEventFakeEventInvocato);
    }

    @Test
    public void aggiuntaPiuDiUnHandlerPerLoStessoEvento() {

	FakeSubscriber sub = new FakeSubscriber();
	FakeSubscriber2 sub2 = new FakeSubscriber2();
	FakeSubscriber3 sub3 = new FakeSubscriber3();
	FakeKernel fakeKernel = new FakeKernel();
	fakeKernel.beansregistrati.put(sub.getClass().getName(), sub);
	fakeKernel.beansregistrati.put(sub2.getClass().getName(), sub2);
	fakeKernel.beansregistrati.put(sub3.getClass().getName(), sub3);
	IEventPublisher pub = new EventPublisherServiceImpl(fakeKernel);
	pub.add(FakeEvent.class, FakeSubscriber.class);
	pub.add(FakeEvent2.class, FakeSubscriber2.class);
	pub.add(FakeEvent.class, FakeSubscriber3.class);
	pub.publish(new FakeEvent());
	Assert.assertEquals(1, sub.onEventFakeEventInvocato);
	Assert.assertEquals(0, sub2.onEventFakeEventInvocato);
	Assert.assertEquals(1, sub3.onEventFakeEventInvocato);
    }

    @Test
    public void classeNonregistrataNonsollevaEccezione() {

	FakeKernel fakeKernel = new FakeKernel();
	IEventPublisher pub = new EventPublisherServiceImpl(fakeKernel);
	pub.add(FakeEvent.class, FakeSubscriber.class);
	pub.publish(new FakeEvent());
    }
}
