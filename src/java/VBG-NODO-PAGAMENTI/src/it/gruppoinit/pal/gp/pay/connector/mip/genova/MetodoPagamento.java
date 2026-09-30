package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum MetodoPagamento {

    CC("Carta di credito"),
    BNCMT("Bancomat"),
    CNTNT("Contante"),
    BMAV("MAV"),
    BRID("RID Bancario"),
    PRID("RID Postale"),
    F24("F24"),
    PBOLL("Bollettino postale"),
    TCOOP("Coop"),
    TLOTT("Lottomatica"),
    TSISA("SISAL"),
    PNODO("PSP Postale"),
    BNODO("PSP Bancario"),
    N_A("Non applicabile");

    private String desc;

    private MetodoPagamento(String desc) {

        this.desc = desc;
    }

    public String description() {

        return desc;
    }
}