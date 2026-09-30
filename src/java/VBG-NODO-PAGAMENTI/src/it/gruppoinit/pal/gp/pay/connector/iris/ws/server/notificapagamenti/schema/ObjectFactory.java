
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IdpInformativaPagamento_QNAME = new QName("http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", "IdpInformativaPagamento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdpVerificaStatoPagamento }
     * 
     */
    public IdpVerificaStatoPagamento createIdpVerificaStatoPagamento() {
        return new IdpVerificaStatoPagamento();
    }

    /**
     * Create an instance of {@link IdpInformativaPagamento }
     * 
     */
    public IdpInformativaPagamento createIdpInformativaPagamento() {
        return new IdpInformativaPagamento();
    }

    /**
     * Create an instance of {@link IdpVerificaStatoPagamento.IdpBody }
     * 
     */
    public IdpVerificaStatoPagamento.IdpBody createIdpVerificaStatoPagamentoIdpBody() {
        return new IdpVerificaStatoPagamento.IdpBody();
    }

    /**
     * Create an instance of {@link IdPagamento }
     * 
     */
    public IdPagamento createIdPagamento() {
        return new IdPagamento();
    }

    /**
     * Create an instance of {@link it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpBody }
     * 
     */
    public it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpBody createIdpBody() {
        return new it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.IdpBody();
    }

    /**
     * Create an instance of {@link Pagamento }
     * 
     */
    public Pagamento createPagamento() {
        return new Pagamento();
    }

    /**
     * Create an instance of {@link RiferimentoDebito }
     * 
     */
    public RiferimentoDebito createRiferimentoDebito() {
        return new RiferimentoDebito();
    }

    /**
     * Create an instance of {@link RiferimentoPagamento }
     * 
     */
    public RiferimentoPagamento createRiferimentoPagamento() {
        return new RiferimentoPagamento();
    }

    /**
     * Create an instance of {@link Transazione }
     * 
     */
    public Transazione createTransazione() {
        return new Transazione();
    }

    /**
     * Create an instance of {@link DettaglioImportoTransato }
     * 
     */
    public DettaglioImportoTransato createDettaglioImportoTransato() {
        return new DettaglioImportoTransato();
    }

    /**
     * Create an instance of {@link Voce }
     * 
     */
    public Voce createVoce() {
        return new Voce();
    }

    /**
     * Create an instance of {@link Pagante }
     * 
     */
    public Pagante createPagante() {
        return new Pagante();
    }

    /**
     * Create an instance of {@link CanalePagamento }
     * 
     */
    public CanalePagamento createCanalePagamento() {
        return new CanalePagamento();
    }

    /**
     * Create an instance of {@link MezzoPagamento }
     * 
     */
    public MezzoPagamento createMezzoPagamento() {
        return new MezzoPagamento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdpInformativaPagamento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInformativaPagamento", name = "IdpInformativaPagamento")
    public JAXBElement<IdpInformativaPagamento> createIdpInformativaPagamento(IdpInformativaPagamento value) {
        return new JAXBElement<IdpInformativaPagamento>(_IdpInformativaPagamento_QNAME, IdpInformativaPagamento.class, null, value);
    }

}
