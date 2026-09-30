package it.gruppoinit.service.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.constants.AvvisoPagamentoCostanti;
import it.gruppoinit.constants.AvvisoPagamentoProperties;
import it.gruppoinit.domain.AvvisoPagamentoInput;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;
import it.gruppoinit.service.AvvisoPagamentoPdfService;
import it.gruppoinit.service.DatamatrixService;
import it.gruppoinit.service.QRCodeService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class AvvisoPagamentoPdfserviceImpl implements AvvisoPagamentoPdfService {

    private static final Logger log = LoggerFactory.getLogger(AvvisoPagamentoPdfserviceImpl.class);
    private static JAXBContext jaxbContext = null;
    private DatamatrixService datamatrixService;
    private QRCodeService qrCodeService;

    @Autowired
    public void setDatamatrixService(DatamatrixService datamatrixService) {

	this.datamatrixService = datamatrixService;
    }

    @Autowired
    public void setQrCodeService(QRCodeService qrCodeService) {

	this.qrCodeService = qrCodeService;
    }

    @Override
    public File generaAvvisatuaPagoPa(AvvisoPagamentoInput input) {

	File tempFile = null;
	try {
	    log.debug("generaAvvisatuaPagoPa# Start...");
	    log.debug("generaAvvisatuaPagoPa# AvvisoPagamentoProperties.getInstance()");
	    AvvisoPagamentoProperties.newInstance("/var/govpay"); // FIXME mi puo non servire
	    AvvisoPagamentoProperties avProperties = AvvisoPagamentoProperties.getInstance();
	    // Ad oggi il dominio non è gestito, si potrebbe aggiungere un parametro alla request del WS per 
	    // passare il dominio e caricare il file di properties specifico per ente 
	    String codDominio = null;
	    //Properties propertiesAvvisoPerDominio = avProperties.getPropertiesPerDominio(codDominio);
	    log.debug("generaAvvisatuaPagoPa# Carico le properties per dominio = {}",
		    StringUtils.defaultIfEmpty(codDominio, "Dominio non passato (Ad oggi non gestito)"));
	    Properties propertiesAvvisoPerDominio = avProperties.getPropertiesPerDominio(codDominio);
	    log.debug("generaAvvisatuaPagoPa# Carico il template da stampare da file di properties...");
	    String template = (String) propertiesAvvisoPerDominio.get(AvvisoPagamentoCostanti.AVVISO_PAGAMENTO_TEMPLATE_JASPER);
	    log.debug("generaAvvisatuaPagoPa# Nome template = {}", template);
	    InputStream jasperTemplateInputStream = getClass().getClassLoader().getResourceAsStream(template);
	    Map<String, Object> parameters = new HashMap<String, Object>();
	    if (StringUtils.isBlank(input.getLogoEnte())) {
		log.debug("generaAvvisatuaPagoPa# Logo ente non passato, recupero la stringa presente sul file di properties");
		this.caricaLoghiAvviso(input, propertiesAvvisoPerDominio);
	    }
	    log.debug("generaAvvisatuaPagoPa# creaXmlDataSource....");
	    JRDataSource dataSource = this.creaXmlDataSource(input);
	    log.debug("generaAvvisatuaPagoPa# creaJasperPrintAvviso....");
	    JasperPrint jasperPrint = this.creaJasperPrintAvviso(input, propertiesAvvisoPerDominio, jasperTemplateInputStream, dataSource,
		    parameters);
	    log.debug("generaAvvisatuaPagoPa# Trasformo il report in jasper in pdf");
	    tempFile = File.createTempFile("Bollettino", "");
	    String path = tempFile.getPath();
	    log.debug("generaAvvisatuaPagoPa# File temp = {}", path);
	    JasperExportManager.exportReportToPdfFile(jasperPrint, path);
	    log.debug("generaAvvisatuaPagoPa# Stampa pdf creata .. ");
	    log.debug("generaAvvisatuaPagoPa# End...");
	    return tempFile;
	} catch (Exception e) {
	    log.error("generaAvvisatuaPagoPa# E = {} ", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void populateAvvisoPagamentoInput(BollettinopagopaRequest bollettinopagopaRequest, AvvisoPagamentoInput avvisoPagamentoInput) {

	if (avvisoPagamentoInput == null) {
	    avvisoPagamentoInput = new AvvisoPagamentoInput();
	}
	log.debug("populateAvvisoPagamentoInput# Start ....");
	avvisoPagamentoInput.setLogoEnte(bollettinopagopaRequest.getLogoEnte());
	// oggetto da stampare sui bollettini
	avvisoPagamentoInput.setOggettoDelPagamento(bollettinopagopaRequest.getOggettoDelPagamento());
	avvisoPagamentoInput.setOggettoDelPagamentoRata(bollettinopagopaRequest.getOggettoDelPagamentoRata());
	avvisoPagamentoInput.setOggettoDelPagamentoBollettino(bollettinopagopaRequest.getOggettoDelPagamentoBollettino());
	// informazioni ente
	avvisoPagamentoInput.setCfEnte(bollettinopagopaRequest.getCfEnte());
	avvisoPagamentoInput.setEnteCreditore(bollettinopagopaRequest.getEnteCreditore());
	avvisoPagamentoInput.setSettoreEnte(bollettinopagopaRequest.getSettoreEnte());
	avvisoPagamentoInput.setInfoEnte(bollettinopagopaRequest.getInfoEnte());
	avvisoPagamentoInput.setDelTuoEnte(bollettinopagopaRequest.getDelTuoEnte());
	avvisoPagamentoInput.setDiPoste(bollettinopagopaRequest.getDiPoste());
	// informazioni debitore
	avvisoPagamentoInput.setCfDestinatario(bollettinopagopaRequest.getCfDestinatario());
	avvisoPagamentoInput.setNomeCognomeDestinatario(bollettinopagopaRequest.getNomeCognomeDestinatario());
	avvisoPagamentoInput.setIndirizzoDestinatario1(bollettinopagopaRequest.getIndirizzoDestinatario1());
	avvisoPagamentoInput.setIndirizzoDestinatario2(bollettinopagopaRequest.getIndirizzoDestinatario2());
	String _importo = "";
	if (StringUtils.isNotBlank(bollettinopagopaRequest.getImporto())) {
	    String d = StringUtils.replace(bollettinopagopaRequest.getImporto(), ",", ".");
	    _importo = StringUtils.replace(d, ".", "");
	    avvisoPagamentoInput.setImporto(new Double(d));
	}
	avvisoPagamentoInput.setData(bollettinopagopaRequest.getData());
	avvisoPagamentoInput.setCbill(bollettinopagopaRequest.getCbill());
	String codiceAvviso = "";
	if (StringUtils.isNotBlank(bollettinopagopaRequest.getCodiceAvviso())) {
	    for (int i = 0; i <= bollettinopagopaRequest.getCodiceAvviso().length() / 4; i++) {
		// split.add(r.substring(i * 4, Math.min((i + 1) * 4, r.length())));
		codiceAvviso += bollettinopagopaRequest.getCodiceAvviso().substring(i * 4,
			Math.min((i + 1) * 4, bollettinopagopaRequest.getCodiceAvviso().length())) + " ";
	    }
	    codiceAvviso = codiceAvviso.trim();
	    avvisoPagamentoInput.setCodiceAvvisoPostale(codiceAvviso);
	    avvisoPagamentoInput.setCodiceAvviso(codiceAvviso);
	}
	//
	String codiceAvvisoPostale = "";
	if (StringUtils.isNotBlank(bollettinopagopaRequest.getCodiceAvvisoPostale())) {
	    for (int i = 0; i <= bollettinopagopaRequest.getCodiceAvvisoPostale().length() / 4; i++) {
		// split.add(r.substring(i * 4, Math.min((i + 1) * 4, r.length())));
		codiceAvvisoPostale += bollettinopagopaRequest.getCodiceAvvisoPostale().substring(i * 4,
			Math.min((i + 1) * 4, bollettinopagopaRequest.getCodiceAvvisoPostale().length())) + " ";
	    }
	    codiceAvvisoPostale = codiceAvvisoPostale.trim();
	    avvisoPagamentoInput.setCodiceAvvisoPostale(codiceAvvisoPostale);
	}
	avvisoPagamentoInput.setNumeroCcPostale(bollettinopagopaRequest.getNumeroCcPostale());
	avvisoPagamentoInput.setIntestatarioContoCorrentePostale(bollettinopagopaRequest.getIntestatarioContoCorrentePostale());
	avvisoPagamentoInput.setAutorizzazione(bollettinopagopaRequest.getAutorizzazione());
	if (StringUtils.isNoneBlank(bollettinopagopaRequest.getNumeroCcPostale())) {
	    if (StringUtils.isNotBlank(bollettinopagopaRequest.getDataMatrix())) {
		avvisoPagamentoInput.setDataMatrix(bollettinopagopaRequest.getDataMatrix());
	    } else {
		log.debug("populateAvvisoPagamentoInput# datamatrix non passato. Eseguo il calcolo con i dati inviati..");
		String datamatrix = datamatrixService.generaStringaDatamatrixPoste(bollettinopagopaRequest.getCodiceAvvisoPostale(),
			bollettinopagopaRequest.getNumeroCcPostale(), _importo, bollettinopagopaRequest.getCfEnte(),
			bollettinopagopaRequest.getCfDestinatario(), bollettinopagopaRequest.getNomeCognomeDestinatario(),
			bollettinopagopaRequest.getOggettoDelPagamentoBollettino());
		avvisoPagamentoInput.setDataMatrix(datamatrix);
	    }
	}
	if (StringUtils.isNotBlank(bollettinopagopaRequest.getQrCode())) {
	    avvisoPagamentoInput.setQrCode(bollettinopagopaRequest.getQrCode());
	} else {
	    log.debug("populateAvvisoPagamentoInput# datamatrix non passato. Eseguo il calcolo con i dati inviati..");
	    String qrcode = qrCodeService.generaStringaQRCode(bollettinopagopaRequest.getCodiceAvviso(), _importo,
		    bollettinopagopaRequest.getCfEnte());
	    avvisoPagamentoInput.setQrCode(qrcode);
	}
	log.debug("populateAvvisoPagamentoInput# End ....");
    }

    public static void main(String[] args) {

	String r1 = "";
	String r = "301188340000064356";
	System.out.println(r.length() / 4);
	System.out.println(r.length() % 4);
	ArrayList<String> split = new ArrayList<String>();
	for (int i = 0; i <= r.length() / 4; i++) {
	    // split.add(r.substring(i * 4, Math.min((i + 1) * 4, r.length())));
	    r1 += r.substring(i * 4, Math.min((i + 1) * 4, r.length())) + " ";
	}
	r1 = r1.trim();
	System.out.println(r1);
    }

    //    }
    private JasperPrint creaJasperPrintAvviso(AvvisoPagamentoInput input, Properties propertiesAvvisoPerDominio,
	    InputStream jasperTemplateInputStream, JRDataSource dataSource, Map<String, Object> parameters) throws Exception {

	log.debug("creaJasperPrintAvviso# Load template...");
	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperTemplateInputStream);
	log.debug("creaJasperPrintAvviso# Creo il report...");
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
	return jasperPrint;
    }

    private JRDataSource creaXmlDataSource(AvvisoPagamentoInput input) throws JRException, JAXBException {

	//	WriteToSerializerType serType = WriteToSerializerType.XML_JAXB;
	log.debug("creaXmlDataSource# Create JAXBContext per il Marshaller della classe = AvvisoPagamentoInput.class");
	jaxbContext = JAXBContext.newInstance(AvvisoPagamentoInput.class);
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	//jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	JAXBElement<AvvisoPagamentoInput> jaxbElement = new JAXBElement<AvvisoPagamentoInput>(new QName("", "input"), AvvisoPagamentoInput.class,
		null, input);
	jaxbMarshaller.marshal(jaxbElement, baos);
	log.debug("creaXmlDataSource# Genero il JRDataSource ...");
	JRDataSource dataSource = new JRXmlDataSource(new ByteArrayInputStream(baos.toByteArray()),
		AvvisoPagamentoCostanti.AVVISO_PAGAMENTO_ROOT_ELEMENT_NAME);
	return dataSource;
    }

    private void caricaLoghiAvviso(AvvisoPagamentoInput input, Properties propertiesAvvisoPerDominio) {

	// valorizzo la sezione loghi
	if (input.getLogoEnte() == null) {
	    String property = propertiesAvvisoPerDominio.getProperty(AvvisoPagamentoCostanti.LOGO_ENTE + "." + input.getCfEnte());
	    if (property == null) {
		property = propertiesAvvisoPerDominio.getProperty(AvvisoPagamentoCostanti.LOGO_ENTE);
	    }
	    input.setLogoEnte(property);
	}
    }
}
