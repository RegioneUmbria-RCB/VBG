package it.gruppoinit.pal.gp.pay.parameters;

public class ParametroTipoDocumentoSdi extends ParameterBase {

    public ParametroTipoDocumentoSdi(String descrizione, String help) {

	super(descrizione, help);
    }

    public ParametroTipoDocumentoSdi() {

	this(NOME_PARAMETRO, "");
    }

    private static final String NOME_PARAMETRO = "TIPO_DOCUMENTO_SDI";

    /**
     * Può assumere i valori
     * 
     * <pre>
    &lt;simpleType name="TipiDocumentiSDI">
    &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     &lt;enumeration value="Avviso"/>
     &lt;enumeration value="Fattura"/>
     &lt;enumeration value="PromemoriaDiMancatoPagamento"/>
     &lt;enumeration value="SollecitoNonNotificato"/>
     &lt;enumeration value="SollecitoNotificato"/>
     &lt;enumeration value="IngiunzioneFiscale"/>
     &lt;enumeration value="Accertamento_Liquidazione"/>
     &lt;enumeration value="Accertamento_InfedeleDenuncia"/>
     &lt;enumeration value="Accertamento_OmessaDenuncia"/>
     &lt;enumeration value="Rateizzazione"/>
     &lt;enumeration value="AccertamentoEsecutivo"/>
    &lt;/restriction>
    &lt;/simpleType>
     * </pre>
     */
    @Override
    public String getNomeParametro() {

	return NOME_PARAMETRO;
    }
}
