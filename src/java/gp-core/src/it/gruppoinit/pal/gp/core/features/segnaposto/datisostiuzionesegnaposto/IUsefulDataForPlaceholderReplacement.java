package it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;

public interface IUsefulDataForPlaceholderReplacement {

    Istanze getIstanza();

    Movimenti getMovimento();

    Comuniassociatisoftware getDatiComuneassociato();

    Comuniassociatisoftware getDatiComuneassociatoTT();

    Amministrazioni getAmministrazione();

    Configurazione getConfigurazione();

    Configurazione getConfigurazioneTT();

    Istanzearee getIstanzaAreaPrimaria();

    Stradario getStradarioPrimario();

    Istanzestradario getIstanzaStradarioPrimaria();

    Istanzemappali getIstanzaMappalePrimaria();

    Alberoproc getAlberoprocByScCodice(String sccodice);

    List<Istanzeprocedimenti> getEndoProcedimenti();

    List<Movimenti> getMovimentiEseguiti();

    List<Istanzemappali> getIstanzeMappali();

    List<Istanzeprocedimenti> getEndoProcedimentiAAAA(Boolean attivi, Boolean autorizzativi, Boolean acquisiti, Boolean autocertificabili);

    List<IstanzeprocedimentiHelper> getRiepilogoEndo();

    List<Documentiistanza> getDocumentiIstanza(Boolean necessario, Boolean presente);

    List<Documentiistanza> getDocumentiIstanzaValidi(boolean isvalido);

    List<Istanzeallegati> getDocumentiEndo(Boolean presente);

    Dyn2Campi getCampoDinamico(Integer codCampo);

    List<Istanzedyn2dati> getValoriCampoDinamico(Integer codiceCampoDinamico);

    Alberoproc getAlberoproc(Integer codAlberoproc);

    List<Istanzerichiedenti> getIstanzeRichiedenti(Integer codiceTipoSoggetto);

    Movimenti getMovimentoPerTipo(String codiceTipoMov);
}
