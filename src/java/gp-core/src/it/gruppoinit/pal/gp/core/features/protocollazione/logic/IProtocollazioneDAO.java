package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;

public interface IProtocollazioneDAO {

    List<ProtocolloAttivoBean> verificaProtocolloAttivo(List<ISoftwareComuneData> softwareComuneFromIdDettaglioList);
}