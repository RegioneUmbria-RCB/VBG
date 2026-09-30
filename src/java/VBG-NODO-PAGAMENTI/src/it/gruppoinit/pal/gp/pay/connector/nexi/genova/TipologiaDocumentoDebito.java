package it.gruppoinit.pal.gp.pay.connector.nexi.genova;

public enum TipologiaDocumentoDebito {

    MAV(0, 1),
    MAV_PERSONALIZZATI(1, 1),
    RID_SDD(2, 1),
    ON_LINE(3, 1),
    BOLLETTINO_POSTALE(4, 1),
    BONIFICO(5, 1),
    AVVISO_PAGOPA(6, 2),
    BOLLETTINO_POSTALE_PAGOPA(7, 1),
    RID_SDD_MAV(8, 1);

    private int posizioneFlag = -1;
    private int valoreFlag = -1;

    private TipologiaDocumentoDebito(int pos, int val) {

        this.posizioneFlag = pos;
        this.valoreFlag = val;
    }

    public int getPosizioneFlag() {

        return posizioneFlag;
    }

    public int getValoreFlag() {

        return valoreFlag;
    }
}