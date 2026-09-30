package it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipoMittDestAutoEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.FakeVerticalizzazioneParametro;

public class FakeVerticalizzazioneProtocolloAttivoServiceImpl implements IVerticalizzazioneProtocolloAttivoService {

    private boolean attiva;
    private String valore;
    private List<FakeVerticalizzazioneParametro> fParametri = new ArrayList<FakeVerticalizzazioneParametro>();

    public FakeVerticalizzazioneProtocolloAttivoServiceImpl() {

	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "IS_SMISTAMENTO_MULTIPLO", "E256", "CO", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "GESTIONE_PEC", "E256", "CO", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "GESTIONE_PEC", "E256", "TT", "0"));
	fParametri.add(new FakeVerticalizzazioneParametro("PROTOCOLLO_ATTIVO", "FLUSSI_VER_FIRMA_DOC_PRINC", "E256", "TT", "A"));
    }

    public static FakeVerticalizzazioneProtocolloAttivoServiceImpl AttivaConGetStringValorizzato(String valore) {

	FakeVerticalizzazioneProtocolloAttivoServiceImpl v = new FakeVerticalizzazioneProtocolloAttivoServiceImpl();
	v.attiva = true;
	v.valore = valore;
	return v;
    }

    public static FakeVerticalizzazioneProtocolloAttivoServiceImpl Attiva() {

	FakeVerticalizzazioneProtocolloAttivoServiceImpl v = new FakeVerticalizzazioneProtocolloAttivoServiceImpl();
	v.attiva = true;
	return v;
    }

    public static FakeVerticalizzazioneProtocolloAttivoServiceImpl NonAttiva() {

	FakeVerticalizzazioneProtocolloAttivoServiceImpl v = new FakeVerticalizzazioneProtocolloAttivoServiceImpl();
	v.attiva = false;
	return v;
    }

    @Override
    public String nomeVerticalizzazione() {

	return "PROTOCOLLO_ATTIVO";
    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public String isAttivoApplicaLayer() {

	return null;
    }

    @Override
    public boolean isForzaFascicolazioneNotificaAutomatica() {

	return false;
    }

    @Override
    public Integer getGestionePEC() {

	return null;
    }

    @Override
    public String getTipoMovRicevuta() {

	return null;
    }

    @Override
    public TipoMittDestAutoEnum getTipoMittDestAuto() {

	return null;
    }

    @Override
    public TipoMittDestAutoEnum getTipoMittDestAuto(String software) {

	return null;
    }

    @Override
    public String getFlussoDefault() {

	return null;
    }

    @Override
    public String getFlussoDefault(String software) {

	return null;
    }

    @Override
    public String getMappaturaDittaIndividuale() {

	return null;
    }

    @Override
    public String getMappaturaDittaIndividuale(String software) {

	return null;
    }

    @Override
    public Integer getCodiceAmministrazioneDefault() {

	return null;
    }

    @Override
    public ProtocolloMezzi getMezzoDefault() {

	return null;
    }

    @Override
    public ProtocolloModalitainvio getModalitaTrasmissioneDefault() {

	return null;
    }

    @Override
    public String getTipoDocumentoDefault() {

	return null;
    }

    @Override
    public String getTipoDocumentoDefaultBo() {

	return null;
    }

    @Override
    public String getTipoSmistamentoDefault() {

	return null;
    }

    @Override
    public boolean trasformaOggettoProtocolloUpperCase() {

	return false;
    }

    @Override
    public Integer lunghezzaMassimaOggettoProtocollo() {

	return null;
    }

    @Override
    public String getClassificaDefaultBO() {

	return null;
    }
}
