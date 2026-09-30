using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Utils;
using log4net;
using System;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    internal class BackendAnagrafeFinder : AbstractAnagrafeFinder
    {
        private readonly IAnagraficheBackendService _anagrafeRepository;
        private readonly IComuniService _comuniService;
        private readonly ILog _log = LogManager.GetLogger(typeof(BackendAnagrafeFinder));


        internal BackendAnagrafeFinder(IAnagraficheBackendService anagrafeRepository, IComuniService comuniService)
        {
            this._anagrafeRepository = anagrafeRepository;
            this._comuniService = comuniService;
        }


        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            this._log.DebugFormat("Inizio ricerca anagrafica con tipo persona {0} e codice fiscale/piva {1}", tipoPersona, codiceFiscalePartitaIva);

            var anagraficaTrovata = this._anagrafeRepository.RicercaAnagraficaBackoffice(tipoPersona, codiceFiscalePartitaIva);

            if (anagraficaTrovata == null || String.IsNullOrEmpty(anagraficaTrovata.NOMINATIVO))
            {
                this._log.DebugFormat("Anagrafica non trovata");
                return null;
            }

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Anagrafica trovata: {0}", StreamUtils.SerializeClass(anagraficaTrovata));




            if (!String.IsNullOrEmpty(anagraficaTrovata.CODCOMNASCITA))
            {
                // HACK: Se il servizio di ricerca ANPR è attivo allora al posto del codice belfiore del comune di nascita viene restituito il codice istat
                // Quindi se il codice comune è composto da 5 cifre allora devo recuperare il codice belfiore
                bool isMatch = Regex.IsMatch(anagraficaTrovata.CODCOMNASCITA, @"^\d{6}$");

                if (isMatch)
                {
                    var comuneNascita = this._comuniService.GetByCodiceIstat(anagraficaTrovata.CODCOMNASCITA);

                    anagraficaTrovata.CODCOMNASCITA = comuneNascita?.CodiceComune ?? "";
                }
            }


            // HACK: il ws di ricerca anagrafiche di Perugia non restituisce il tipo persona 
            // se il tipo persona non è presente lo imposto uguale al tipo persona passato
            if (String.IsNullOrEmpty(anagraficaTrovata.TIPOANAGRAFE))
            {
                anagraficaTrovata.TIPOANAGRAFE = tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
            }

            var row = new AnagrafeAdapter(anagraficaTrovata, this._comuniService).ToAnagrafeRow();

            return AnagraficaDomanda.FromAnagrafeRow(row);
        }

        internal override Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            return Task.FromResult(this.Find(tipoPersona, codiceFiscalePartitaIva));
        }
    }
}
