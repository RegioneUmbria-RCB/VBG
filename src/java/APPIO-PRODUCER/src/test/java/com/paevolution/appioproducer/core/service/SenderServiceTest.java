package com.paevolution.appioproducer.core.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.paevolution.appioproducer.core.domain.helper.MessageToSendHelper;

@SpringBootTest
public class SenderServiceTest {

    @Test
    public void testIfMarkdownIsCorrect() {

	//String markdown = generateMarkdown(helper());
	String markdown = generateMarkdownOfMessage(helper());
	assertThat(markdown).isNotEmpty();
    }

    private MessageToSendHelper helper() {

	return MessageToSendHelper.builder().idcomune("E256").codicecomune("C933").software("SS").codiceistanza(8421).codicestato(1)
		.numeroistanza("5/2021").data(new Date(2021, 3, 25, 17, 34, 0)).lavori("Sportello unico - OnLine - Pratiche OnLine")
		.denominazioneServizio("Comune di Test").urlPortaleServizi("https://devel3.init.gruppoinit.it/front/index/E256/SS")
		.destinatario("Vernata Simone").destinatarioCodiceanagrafe(727).destinatarioCodicefiscale("AAAAAA00A00A000A").codicemovimento(79818)
		.movimento("Pubblicita`").build();
    }
    /*    private String generateMarkdown(MessageToSendHelper messageToSendHelper) {
    
    	String DATE_PATTERN = "dd/MM/yyyy";
    	StringBuilder markdown = new StringBuilder();
    	// solo per test
    	String numProt = (StringUtils.isNotBlank(messageToSendHelper.getNumeroprotocollo())) ? messageToSendHelper.getNumeroprotocollo() : "N.D";
    	String dataProt = messageToSendHelper.getDataprotocollo() != null ? formattedDate(messageToSendHelper.getDataprotocollo(), DATE_PATTERN)
    		: "N.D";
    	markdown = markdown.append(new Text("Relativamente alla pratica prot.")).append(new Text(numProt)).append(" ").append(new Text(dataProt))
    		.append(" ")
    		.append(new Text("Istanza n. " + messageToSendHelper.getNumeroistanza() + " del "
    			+ formattedDate(messageToSendHelper.getData(), DATE_PATTERN) + "\\"))
    		.append(new Text("Richiedente: " + messageToSendHelper.getDestinatario() + "-" + messageToSendHelper.getLavori()))
    		.append(new Text("Ci sono comunicazioni da parte del: " + messageToSendHelper.getDenominazioneServizio() + "\\"))
    		.append(new Text("Si prega di provvedere tempestivamente alla consulenza accedendo alla pratica dal Portale SUAPE\\"))
    		.append(new Text(messageToSendHelper.getUrlPortaleServizi()));
    	String md = new String(markdown);
    	return md;
    }
    */
    /*    private String generateMarkdownOfMessage(MessageToSendHelper msgHelper) {
    
    	String DATE_PATTERN = "dd/MM/yyyy";
    	StringBuilder markdown = new StringBuilder();
    	// solo per test
    	String numProt = (StringUtils.isNotBlank(msgHelper.getNumeroprotocollo())) ? msgHelper.getNumeroprotocollo() : "N.D";
    	String dataProt = msgHelper.getDataprotocollo() != null ? formattedDate(msgHelper.getDataprotocollo(), DATE_PATTERN) : "N.D";
    	markdown = markdown.append("Relativamente alla pratica prot.").append(numProt).append(" ").append(dataProt).append(" ");
    	markdown = markdown.append("Istanza: n.").append(msgHelper.getNumeroistanza()).append(" del ")
    		.append(formattedDate(msgHelper.getData(), DATE_PATTERN)).append("\\");
    	markdown = markdown.append("Richiedente: ").append(msgHelper.getDestinatario()).append(" - ").append(msgHelper.getLavori()).append("\\");
    	markdown = markdown.append("Ci sono comunicazioni da parte del: ").append(msgHelper.getDenominazioneServizio()).append("\\");
    	markdown = markdown.append("Si prega di provvedere tempestivamente alla consulenza accedendo alla pratica dal Portale SUAPE").append("\\");
    	markdown = markdown.append(msgHelper.getUrlPortaleServizi());
    	return new String(markdown);
    }*/
    /*    private String generateMarkdownOfMessage(MessageToSendHelper msgHelper) {
    
    	String DATE_PATTERN = "dd/MM/yyyy";
    	StringBuilder markdown = new StringBuilder();
    	// solo per test
    	String numProt = (StringUtils.isNotBlank(msgHelper.getNumeroprotocollo())) ? msgHelper.getNumeroprotocollo() : "N.D";
    	String dataProt = msgHelper.getDataprotocollo() != null ? formattedDate(msgHelper.getDataprotocollo(), DATE_PATTERN) : "N.D";
    	Text bodyTxt1 = new Text("Relativamente alla pratica prot." + numProt + " " + dataProt + " ");
    	Text bodyTxt2 = new Text("Istanza: n." + msgHelper.getNumeroistanza() + " del " + formattedDate(msgHelper.getData(), DATE_PATTERN) + "\\");
    	Text bodTxt3 = new Text("Richiedente: " + msgHelper.getDestinatario() + " - " + msgHelper.getLavori() + "\\");
    	Text bodyTxt4 = new Text("Ci sono comunicazioni da parte del: " + msgHelper.getDenominazioneServizio() + "\\");
    	Text bodyTxt5 = new Text("Si prega di provvedere tempestivamente alla consulenza accedendo alla pratica dal Portale SUAPE&nbsp;&nbsp;");
    	Text bodyTxt6 = new Text(msgHelper.getUrlPortaleServizi());
    	markdown = markdown.append(bodyTxt1).append(bodyTxt2).append(bodTxt3).append(bodyTxt4).append(bodyTxt5).append(bodyTxt6);
    	return new String(markdown);
    }*/

    private String generateMarkdownOfMessage(MessageToSendHelper msgHelper) {

	String DATE_PATTERN = "dd/MM/yyyy";
	StringBuilder markdown = new StringBuilder();
	// solo per test
	String numProt = (StringUtils.isNotBlank(msgHelper.getNumeroprotocollo())) ? msgHelper.getNumeroprotocollo() : "N.D";
	String dataProt = msgHelper.getDataprotocollo() != null ? formattedDate(msgHelper.getDataprotocollo(), DATE_PATTERN) : "N.D";
	markdown = markdown.append("Relativamente alla pratica prot.").append(numProt).append(dataProt).append(" ").append("Istanza: n.")
		.append(msgHelper.getNumeroistanza()).append(" del ").append(formattedDate(msgHelper.getData(), DATE_PATTERN)).append("\n")
		.append("Richiedente: ").append(msgHelper.getDestinatario()).append(" - ").append(msgHelper.getLavori()).append("\n")
		.append("Ci sono comunicazioni da parte del: ").append(msgHelper.getDenominazioneServizio()).append("\n")
		.append("Si prega di provvedere tempestivamente alla consulenza accedendo alla pratica dal Portale SUAPE").append("\n\n")
		.append(msgHelper.getUrlPortaleServizi());
	return new String(markdown);
    }

    private String formattedDate(Date dateToFormat, String pattern) {

	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	return formatter.format(dateToFormat);
    }
}
