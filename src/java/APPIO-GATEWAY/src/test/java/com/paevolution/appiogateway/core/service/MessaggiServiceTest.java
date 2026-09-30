package com.paevolution.appiogateway.core.service;

import static org.mockito.Mockito.mock;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.paevolution.appiogateway.core.repository.MessaggiRepository;
import com.paevolution.appiogateway.core.service.impl.MessaggiServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MessaggiServiceTest {

    MessaggiRepository messaggiRepository = mock(MessaggiRepository.class);
    MessaggiService messaggiService = new MessaggiServiceImpl(messaggiRepository);

//    @Test
//    public void ReturnOggettoClasseMessaggiDTO_Se_Presente() throws Exception {
//
//	Optional<Messaggi> optMsg = Optional.of(new Messaggi());
//	when(messaggiRepository.findById(1L)).thenReturn(optMsg);
//	MessaggiRestBean result = messaggiService.findById(1L);
//	assertTrue(result instanceof MessaggiRestBean);
//    }
//
//    @Test
//    public void ReturnNull_Se_OggettoNonPresente() throws Exception {
//
//	Optional<Messaggi> optMsg = Optional.empty();
//	when(messaggiRepository.findById(1L)).thenReturn(optMsg);
//	MessaggiRestBean result = messaggiService.findById(1L);
//	assertNull(result);
//    }

//    @Test
//    public void Save_Return_OggettoMessaggi() throws Exception {
//
//	Messaggi msg = new Messaggi();
//	MessaggiRestBean msgRestBean = new MessaggiRestBean();
//	when(messaggiRepository.save(msg)).thenReturn(new Messaggi());
//	Messaggi result = messaggiService.save(msgRestBean);
//	assertTrue(result instanceof Messaggi);
//    }
}
