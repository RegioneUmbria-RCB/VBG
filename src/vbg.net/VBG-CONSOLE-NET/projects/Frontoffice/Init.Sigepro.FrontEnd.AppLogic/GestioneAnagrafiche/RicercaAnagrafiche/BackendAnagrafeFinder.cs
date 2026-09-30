// -----------------------------------------------------------------------
// <copyright file="BackendAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.Adapters;
    using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
    using Init.Utils;
    using log4net;
    using System;

    internal class BackendAnagrafeFinder : AbstractAnagrafeFinder
    {
        private readonly IAnagraficheService _anagrafeRepository;
        private readonly string _aliasComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(BackendAnagrafeFinder));


        internal BackendAnagrafeFinder(string aliasComune, IAnagraficheService anagrafeRepository)
        {
            this._anagrafeRepository = anagrafeRepository;
            this._aliasComune = aliasComune;
        }


        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            this._log.DebugFormat("Inizio ricerca anagrafica con tipo persona {0} e codice fiscale/piva {1}", tipoPersona, codiceFiscalePartitaIva);

            Anagrafe anagraficaTrovata = this._anagrafeRepository.RicercaAnagraficaBackoffice(this._aliasComune, tipoPersona, codiceFiscalePartitaIva);

            if (anagraficaTrovata == null || String.IsNullOrEmpty(anagraficaTrovata.NOMINATIVO))
            {
                this._log.DebugFormat("Anagrafica non trovata");
                return null;
            }

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Anagrafica trovata: {0}", StreamUtils.SerializeClass(anagraficaTrovata));

            // HACK: il ws di ricerca anagrafiche di Perugia non restituisce il tipo persona 
            // se il tipo persona non è presente lo imposto uguale al tipo persona passato
            if (String.IsNullOrEmpty(anagraficaTrovata.TIPOANAGRAFE))
            {
                anagraficaTrovata.TIPOANAGRAFE = tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
            }

            PresentazioneIstanzaDbV2.ANAGRAFERow row = new AnagrafeAdapter(anagraficaTrovata).ToAnagrafeRow();

            return AnagraficaDomanda.FromAnagrafeRow(row);
        }
    }
}
