package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

public interface IAssegnazioniService {

    public void assegnaPratica(Integer codiceIstanza, ResponsabileIstanzaEnum responsabileIstanzaEnum, Integer codiceResposabile);
}
