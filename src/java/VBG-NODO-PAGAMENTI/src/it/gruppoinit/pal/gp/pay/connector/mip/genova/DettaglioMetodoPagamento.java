package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum DettaglioMetodoPagamento {

    CCANY("Carta di credito generica"),
    BNCMT("Bancomat"),
    CNTNT("Contante"),
    CMAST("Carta Mastercard"),
    CVISA("Carta Visa"),
    CAMEX("Carta AmericanExpress"),
    CDINR("Carta Diners"),
    RIDO("RID on-line"),
    RIDN("RID Bancario ordinario"),
    RIDPN("RID Postale ordinario"),
    MAVN("MAV normale"),
    MAVP("MAV personalizzato"),
    MAVO("MAV on-line"),
    FIMU("F24 flusso Sogei IMU"),
    FADC("F24 flusso Sogei ADC"),
    FTARE("F24 flusso Sogei TARES"),
    PBOLL("Bollettino postale"),
    TCOOP("Coop"),
    TLOTT("Lottomatica"),
    TSISA("SISAL"),
    CP("Nodo PagoPA - carta di pagamento"),
    BBT("Nodo PagoPA - bonifico bancario"),
    PO("Nodo PagoPA - pagamento presso PSP"),
    BP("Nodo PagoPA - bollettino postale"),
    AD("Nodo PagoPA - addebito diretto"),
    OBEP("Nodo MyBank"),
    N_A("Non applicabile");

    private String desc;

    private DettaglioMetodoPagamento(String desc) {

        this.desc = desc;
    }

    public String description() {

        return desc;
    }
}