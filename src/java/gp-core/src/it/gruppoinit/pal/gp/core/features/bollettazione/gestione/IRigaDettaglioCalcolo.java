package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;

public interface IRigaDettaglioCalcolo {

    public Integer getIdAnagrafe();

    public void setIdAnagrafe(Integer idAnagrafe);

    public BigDecimal getImportoTotale();

    public void setImportoTotale(BigDecimal importoTotale);

    public String getDescrizione();

    public void setDescrizione(String descrizione);

    public Integer getIdConto();

    public void setIdConto(Integer idConto);

    public Integer getIdRiferimento();

    public void setIdRiferimento(Integer idRiferimento);
}
