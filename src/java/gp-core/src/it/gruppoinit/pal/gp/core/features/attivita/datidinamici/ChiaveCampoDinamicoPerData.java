package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.Date;

/*
 * I setter in questa classe non vanno implementati in quanto utilizzata come chiave di mappa. Mettendo i setter il
 * rischio è quello di modificare elementi già aggiunti in precedenza alla mappa con tanto di caso VERIFICATO di mappa
 * con chiavi identiche
 */
public class ChiaveCampoDinamicoPerData {

    private Date dataSnapshot;
    private Integer idCampo;
    private Integer indice;
    private Integer indiceMolteplicita;

    public ChiaveCampoDinamicoPerData(Date dataSnapshot, Integer idCampo, Integer indice, Integer indiceMolteplicita) {

	super();
	this.dataSnapshot = dataSnapshot;
	this.idCampo = idCampo;
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
    }

    public Date getDataSnapshot() {

	return dataSnapshot;
    }

    public Integer getIdCampo() {

	return idCampo;
    }

    public Integer getIndice() {

	return indice;
    }

    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((dataSnapshot == null) ? 0 : dataSnapshot.hashCode());
	result = prime * result + ((idCampo == null) ? 0 : idCampo.hashCode());
	result = prime * result + ((indice == null) ? 0 : indice.hashCode());
	result = prime * result + ((indiceMolteplicita == null) ? 0 : indiceMolteplicita.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ChiaveCampoDinamicoPerData other = (ChiaveCampoDinamicoPerData) obj;
	if (dataSnapshot == null) {
	    if (other.dataSnapshot != null)
		return false;
	} else if (dataSnapshot.compareTo(other.dataSnapshot) != 0)
	    return false;
	if (idCampo == null) {
	    if (other.idCampo != null)
		return false;
	} else if (!idCampo.equals(other.idCampo))
	    return false;
	if (indice == null) {
	    if (other.indice != null)
		return false;
	} else if (!indice.equals(other.indice))
	    return false;
	if (indiceMolteplicita == null) {
	    if (other.indiceMolteplicita != null)
		return false;
	} else if (!indiceMolteplicita.equals(other.indiceMolteplicita))
	    return false;
	return true;
    }
}
