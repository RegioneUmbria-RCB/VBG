package it.alveo.ricalcoloaree.utils;

import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecUsernameToken;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;
import org.w3c.dom.Document;

import java.io.IOException;

public class SecurityHeaderCallback implements WebServiceMessageCallback {

    private final String username;
    private final String password;

    public SecurityHeaderCallback(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException {
        SoapMessage soapMessage = (SoapMessage) message;
        try {
            Document doc = soapMessage.getDocument();
            WSSecHeader secHeader = new WSSecHeader(doc);
            secHeader.insertSecurityHeader();

            WSSecUsernameToken usernameToken = new WSSecUsernameToken(secHeader);
            usernameToken.setPasswordType(WSConstants.PASSWORD_DIGEST);
            usernameToken.setUserInfo(username, password);
            usernameToken.build();

        } catch (Exception e) {
            throw new IOException("Error while adding WS-Security header", e);
        }
    }

}
