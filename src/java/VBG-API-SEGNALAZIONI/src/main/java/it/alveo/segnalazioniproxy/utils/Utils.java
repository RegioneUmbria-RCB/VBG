package it.alveo.segnalazioniproxy.utils;

import it.alveo.segnalazioniproxy.exceptions.ErrorResponse;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import org.apache.tika.Tika;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static final String STANDARD_ERROR_TXT = "Parametro di configurazione non valorizzato nella configurazione E256-TT";

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String extractBase64String(String base64) {
        if (StringUtils.hasText(base64) && base64.contains(",")) {
            return base64.substring(base64.indexOf(",") + 1);
        }
        return base64;
    }

    private static byte[] decodeBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String getMimeType(String base64) {
        try {
            String base64Data = extractBase64String(base64);
            byte[] data = decodeBase64(base64Data);

            // Using Apache Tika to detect MIME type
            Tika tika = new Tika();
            return tika.detect(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to determine MIME type", e);
        }
    }

    public static DataHandler convertToDataHandler(String fileBase64, String mimeType) {
        try {
            byte[] data = Base64.getDecoder().decode(fileBase64);

            // Create DataSource from byte array
            DataSource dataSource = new ByteArrayDataSource(data, mimeType);

            // Create DataHandler from DataSource
            return new DataHandler(dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert base64 string to DataHandler", e);
        }
    }

    /**
     * Crea una <code>ResponseEntity</code> con un messaggio JSON preformattato contenente come dettaglio l'errore che è stato generato
     *
     * @param e the error
     * @return the KO reason via <code>ResponseEntity</code> in JSON format with 3 fields
     */
    public static ResponseEntity<?> getEsitoKO(IllegalArgumentException e) {
        JSONObject esitoKO = new JSONObject();
        esitoKO.put("codice", HttpStatus.BAD_REQUEST.value());
        esitoKO.put("messaggio", "Errore nella request");
        esitoKO.put("dettaglio", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(esitoKO.toString());
    }

    /**
     * Verifica se i parametri comuni: <code>alias</code>, <code>software</code> e <code>clientId</code> sono valorizzati
     *
     * @param alias    the alias
     * @param software the software
     * @param clientId the client id
     * @return La response d'errore se non sono validi, altrimenti null
     */
    public static ResponseEntity<?> checkBaseParams(String alias, String software, String clientId) {
        if (Utils.isNullOrEmpty(alias)
                || Utils.isNullOrEmpty(software)) {
            ErrorResponse errorResponse = new ErrorResponse(
                    String.valueOf(HttpStatus.BAD_REQUEST),
                    STANDARD_ERROR_TXT,
                    "Dettaglio errore: ...\n"
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }

        if (Utils.isNullOrEmpty(clientId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utente non autorizzato.");
        }

        return null;
    }

    // Metodo per convertire un DataHandler in Base64
    public static String convertDataHandlerToBase64(DataHandler dataHandler) {
        String base64String = "";
        try (InputStream inputStream = dataHandler.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Convert byte array to Base64 string
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64String = Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException e) {
            log.error("Errore nella conversione da DataHandler a Base64! ", e); // Handle exceptions appropriately
        }
        return base64String;
    }

    public static WebServiceTemplate getStcWebServiceTemplate(ApplicationContext applicationContext, String stcUrl) {
        return (WebServiceTemplate) applicationContext.getBean("stcWebServiceTemplate", stcUrl);
    }

    public static WebServiceTemplate getSecurityWebServiceTemplate(ApplicationContext applicationContext, String securityUrl) {
        return (WebServiceTemplate) applicationContext.getBean("securityFileWebServiceTemplate", securityUrl);
    }

    // Non usato, ma pronto per gestire dinamicamente anche l'URL degli oggetti
    public static WebServiceTemplate getGestoreFileWebServiceTemplate(ApplicationContext applicationContext, String gestoreFileUrl) {
        return (WebServiceTemplate) applicationContext.getBean("gestoreFileWebServiceTemplate", gestoreFileUrl);
    }

}
