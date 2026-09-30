
using System;
using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class GenerazioneRiepilogoDomandaService
    {
        public enum FormatoConversioneModello
        {
            HTML,
            PDF
        }

        RiepilogoDomanda _riepilogoDomanda;
        FacSimileDomanda _facSimileDomanda;
        IAliasSoftwareResolver _aliasResolver;
        ILog _log = LogManager.GetLogger(typeof(GenerazioneRiepilogoDomandaService));
        ISalvataggioDomandaStrategy _caricamentoDomandaStrategy;
        IInterventiRepository _interventiRepository;
        IInterventiAllegatiRepository _interventiAllegatiRepository;

        public GenerazioneRiepilogoDomandaService(RiepilogoDomanda riepilogoDomanda, FacSimileDomanda facSimileDomanda, IAliasSoftwareResolver aliasResolver, ISalvataggioDomandaStrategy caricamentoDomandaStrategy, IInterventiRepository interventiRepository, IInterventiAllegatiRepository interventiAllegatiRepository)
        {
            this._riepilogoDomanda = riepilogoDomanda;
            this._facSimileDomanda = facSimileDomanda;
            this._aliasResolver = aliasResolver;
            this._caricamentoDomandaStrategy = caricamentoDomandaStrategy;
            this._interventiRepository = interventiRepository;
            this._interventiAllegatiRepository = interventiAllegatiRepository;
        }

        public BinaryFile GeneraRiepilogoDomanda(int idDomanda, bool aggiungiPdfSchedeAListaAllegati, bool dumpXml)
        {
            var domanda = this._caricamentoDomandaStrategy.GetById(idDomanda);
            var oggettoRiepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (oggettoRiepilogo == null || !oggettoRiepilogo.CodiceOggettoModello.HasValue)
            {
                throw new Exception("Non è stato definito un riepilogo di domanda per la domanda con id " + idDomanda);
            }

            return this._riepilogoDomanda.GeneraRiepilogoDomandaOnline(domanda, oggettoRiepilogo.CodiceOggettoModello.Value, aggiungiPdfSchedeAListaAllegati, dumpXml);
        }

        public BinaryFile GeneraFacSimileDomanda(int idIntervento, IEnumerable<int> endoFacoltativiAttivati)
        {
            var allegati = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

            var domanda = this._facSimileDomanda.Genera(this._aliasResolver.AliasComune, this._aliasResolver.Software, idIntervento, endoFacoltativiAttivati);
            var codiceModelloRiepilogo = this._interventiRepository.GetidDocumentoRiepilogoDaIdIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);

            var allegatoRiepilogo = allegati.Where(x => x.Codice == codiceModelloRiepilogo).FirstOrDefault();

            return this._riepilogoDomanda.GeneraRiepilogoDomandaOnline(domanda, allegatoRiepilogo.CodiceOggettoModello.Value, false);
        }
    }
}
