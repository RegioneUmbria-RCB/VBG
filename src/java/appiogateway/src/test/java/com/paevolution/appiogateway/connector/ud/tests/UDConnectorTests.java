package com.paevolution.appiogateway.connector.ud.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.paevolution.appiogateway.connector.ioitalia.client.model.NewMessage;
import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;

@SpringBootTest
public class UDConnectorTests {

    @Test
    public void populateNewMessageUsingObjectMapper() {

	/*MessaggiDTO messaggiDTO = new MessaggiDTO();
	messaggiDTO = modelMapper.map(messaggioJPA, MessaggiDTO.class);
	NewMessageDTO newMessageDTO = modelMapper.map(messaggioJPA, NewMessageDTO.class);
	assertThat(newMessageDTO.getMarkdown()).isNotBlank();
	//NewMessage messageToSend = modelMapper.map(newMessageDTO, NewMessage.class);
	ObjectMapper objMapper = new ObjectMapper();
	NewMessage messageToSend = objMapper.convertValue(newMessageDTO, NewMessage.class);
	assertThat(messageToSend.getContent()).isNotNull();*/
	/*assertThat(messageToSend.getContent()).isNotNull();
	assertThat(messageToSend.getContent().getMarkdown()).isEqualTo(new String(
		"jbjdbjbjbjkjbsdfsdgfuigfyusdgfuisguifguwegroiuwvgfugrfuiegfiuwgiugweiufgui gugusgdiufgiusg iugweiufgwuig uguiguigsiufg uiguigiusrgfiwefguiowehgrufiwheui ghuiwgiugheruifweiug huhiughiuheirgiweh iohoihoihiohweorigy8wegv89g 89g98g89g"));*/
    }

    @Test
    public void populateNewMessageWithModelMapper() {

	Messaggi messaggiodomain = new Messaggi();
	/* Messaggi messaggiodomain = Messaggi.builder().idcomune("E256").codicecomune("E256").software("SS").timeToLive(3600).subject("TEST MESSAGE")
		.markdown(
			"jbjdbjbjbjkjbsdfsdgfuigfyusdgfuisguifguwegroiuwvgfugrfuiegfiuwgiugweiufgui gugusgdiufgiusg iugweiufgwuig uguiguigsiufg uiguigiusrgfiwefguiowehgrufiwheui ghuiwgiugheruifweiug huhiughiuheirgiweh iohoihoihiohweorigy8wegv89g 89g98g89g")
		.email("test@example.com").dueDate(new Date()).build();*/
	ModelMapper modelMapper = new ModelMapper();
	MessaggiDTO messaggiDTO = modelMapper.map(messaggiodomain, MessaggiDTO.class);
	assertThat(messaggiDTO.getEmail()).isEqualTo("test@example.com");
	byte[] serializedDTO = SerializationUtils.serialize(messaggiDTO);
	NewMessage nmsg = SerializationUtils.deserialize(serializedDTO);
	assertThat(nmsg.getContent()).isNotNull();
	//assertThat(messageContent).isNotNull();
	/*assertThat(messageContent.getMarkdown()).isEqualTo(
		"jbjdbjbjbjkjbsdfsdgfuigfyusdgfuisguifguwegroiuwvgfugrfuiegfiuwgiugweiufgui gugusgdiufgiusg iugweiufgwuig uguiguigsiufg uiguigiusrgfiwefguiowehgrufiwheui ghuiwgiugheruifweiug huhiughiuheirgiweh iohoihoihiohweorigy8wegv89g 89g98g89g");*/
    }
}
