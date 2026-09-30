package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

public class ElementoBlackListDaChiudereBean {

    private Integer idBlackListMotivi;
    private Integer idMercatiPresenzeD;
    private Integer idAutBlackList;
    private Integer idAutMercatiPresenzeD;
    private Integer idBlackListSrcPDebSp;
    private Integer idDettPosizioneDebitoria;
    private String stato;

    public Integer getIdBlackListMotivi() {

	return idBlackListMotivi;
    }

    public void setIdBlackListMotivi(Integer idBlackListMotivi) {

	this.idBlackListMotivi = idBlackListMotivi;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }

    public void setIdMercatiPresenzeD(Integer idMercatiPresenzeD) {

	this.idMercatiPresenzeD = idMercatiPresenzeD;
    }

    public Integer getIdAutBlackList() {

	return idAutBlackList;
    }

    public void setIdAutBlackList(Integer idAutBlackList) {

	this.idAutBlackList = idAutBlackList;
    }

    public Integer getIdAutMercatiPresenzeD() {

	return idAutMercatiPresenzeD;
    }

    public void setIdAutMercatiPresenzeD(Integer idAutMercatiPresenzeD) {

	this.idAutMercatiPresenzeD = idAutMercatiPresenzeD;
    }

    public Integer getIdBlackListSrcPDebSp() {

	return idBlackListSrcPDebSp;
    }

    public void setIdBlackListSrcPDebSp(Integer idBlackListSrcPDebSp) {

	this.idBlackListSrcPDebSp = idBlackListSrcPDebSp;
    }

    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public void setIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @Override
    public String toString() {

	StringBuilder s = new StringBuilder();
	s.append("blacklist_motivi.id:").append(this.getIdBlackListMotivi());
	s.append("\n");
	s.append("blacklist_src_p_deb_sp.id:").append(this.getIdBlackListSrcPDebSp());
	s.append("\n");
	s.append("dett_posizione_debitoria.id:").append(this.getIdDettPosizioneDebitoria());
	s.append("\n");
	s.append("dett_posizione_debitoria.stato:").append(this.getStato());
	return s.toString();
    }
}
