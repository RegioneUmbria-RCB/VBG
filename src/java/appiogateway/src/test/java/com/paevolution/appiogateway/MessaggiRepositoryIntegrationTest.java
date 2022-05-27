package com.paevolution.appiogateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.repository.MessaggiRepository;

@SpringBootTest
class MessaggiRepositoryIntegrationTest {

    @Autowired
    private MessaggiRepository messaggiRepository;

    @Test
    void testFindAllMethod() {

	List<Messaggi> allMessages = (List<Messaggi>) messaggiRepository.findAll();
	assertAll("allMessages", () -> allMessages.forEach(message -> assertThat(message.getMarkdown()).isInstanceOf(String.class)));
    }

    @Test
    void populateClobToString() {

	Messaggi msg = new Messaggi();
	/*msg.setMarkdown(
		ClobProxy.generateProxy("dfvhodifhihvioihui ghuosducigdsiufvgwdviugasdfvgsdgbgfhnghfu ik6uio6uyokl68 mcasuhg guigsdofsdv cv b vdfg"));*/
	msg.setMarkdown("dfvhodifhihvioihui ghuosducigdsiufvgwdviugasdfvgsdgbgfhnghfu ik6uio6uyokl68 mcasuhg guigsdofsdv cv b vdfg");
	ModelMapper modelMapper = new ModelMapper();
	MessaggiDTO messaggiDTO = modelMapper.map(msg, MessaggiDTO.class);
	assertThat(messaggiDTO.getMarkdown())
		.isEqualTo("dfvhodifhihvioihui ghuosducigdsiufvgwdviugasdfvgsdgbgfhnghfu ik6uio6uyokl68 mcasuhg guigsdofsdv cv b vdfg");
    }
}
