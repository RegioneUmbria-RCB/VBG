package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public enum CanalePagamento {

    BANCA("Banca"),
    SOGEI("Sogei"),
    WEB("Portale on-line"),
    KIOSK("Chioschi"),
    RTER("Reti terze"),
    POSTE("Poste Italiane"),
    CASSA("Sportello del comune"),
    NODO("Nodo dei pagamenti PagoPA"),
    N_A("Non applicabile");

    private String desc;

    private CanalePagamento(String desc) {

        this.desc = desc;
    }

    public String description() {

        return desc;
    }
}