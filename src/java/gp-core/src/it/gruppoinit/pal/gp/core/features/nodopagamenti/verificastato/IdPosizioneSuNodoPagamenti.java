package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

public class IdPosizioneSuNodoPagamenti implements IIdPosizioneSuNodoPagamenti {

    private String cfEnteCreditore;
    private int idPosizioneDebitoria;

    public IdPosizioneSuNodoPagamenti(String cfEnteCreditore, int idPosizioneDebitoria) {

	super();
	this.cfEnteCreditore = cfEnteCreditore;
	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    @Override
    public String getCfEnteCreditore() {

	return this.cfEnteCreditore;
    }

    @Override
    public int getIdPosizioneDebitoria() {

	return this.idPosizioneDebitoria;
    }
}
