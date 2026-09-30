using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.TipiSoggetto;
using log4net;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TipiSoggetto
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsTipiSoggettoService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsTipiSoggettoService.svc or WsTipiSoggettoService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsTipiSoggettoService : WcfServiceBase, IWsTipiSoggettoService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsTipiSoggettoService));

        public WsTipiSoggettoService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        /// <summary>
        /// Metodo considerato legacy, restituisce i dati del soggetto ma li svincola dal contesto dell'intervento in cui viene utilizzato
        /// </summary>
        /// <param name="token"></param>
        /// <param name="id"></param>
        /// <returns></returns>
        public TipoSoggettoDto GetById(string token, int id)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new TipiSoggettoMgr(db, authInfo.IdComune);

                var tipoSoggetto = mgr.GetById(id);

                return tipoSoggetto == null ? null : new TipoSoggettoDto
                {
                    Id = id,
                    Descrizione = tipoSoggetto.TIPOSOGGETTO,
                    DescrizioneEstesa = tipoSoggetto.DescrizioneEstesa,
                    FlagTipoDato = tipoSoggetto.TIPODATO,
                    FlagLegaleRappresentante = tipoSoggetto.FlgLegaleRapp.GetValueOrDefault(0) == 1,
                    OccorrenzeMax = int.MaxValue,
                    RichiedeAnagraficaCollegata = tipoSoggetto.RICHIEDIANAGRAFECOLL == "1",
                    RichiedeDatiAlbo = tipoSoggetto.FLG_DATIALBO == "1",
                    RichiedeSpecificaDescrizione = tipoSoggetto.FLG_SPECIFICADESCRIZIONE == "1",
                    Richiesto = tipoSoggetto.FO_OBBLIGATORIO.GetValueOrDefault(0) == 1
                };
            }
        }

        public TipoSoggettoDto GetByIdECodiceIntervento(string token, int idTipoSoggetto, int idIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new TipiSoggettoMgr(db, authInfo.IdComune);

                return mgr.GetById(idTipoSoggetto, idIntervento);
            }
        }

        public IEnumerable<int> GetIdTipiSoggettoCheRicevonoNotifiche(string token, IEnumerable<int> listaIdTipiSoggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new TipiSoggettoMgr(db, authInfo.IdComune);

                return mgr.GetIdTipiSoggettoCheRicevonoNotifiche(listaIdTipiSoggetto);
            }
        }

        public IEnumerable<TipoSoggettoDto> GetObbligatori(string token, string software, int? codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new TipiSoggettoMgr(db, authInfo.IdComune);

                return mgr.GetTipiSoggettoFrontofficeDaIdInterventoFlat(software, codiceIntervento)?.Where(x => x.Richiesto) ?? Enumerable.Empty<TipoSoggettoDto>();
            }
        }

        public TipiSoggettoInterventoDto GetTipiSoggettoDaIdIntervento(string token, string software, int? codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new TipiSoggettoMgr(db, authInfo.IdComune);

                return mgr.GetTipiSoggettoFrontofficeDaIdIntervento(software, codiceIntervento);
            }
        }

        public IEnumerable<TipoSoggettoDto> GetTipiSoggettoPersonaFisica(string token, string software, int? codiceIntervento)
        {
            return this.GetTipiSoggettoDaIdIntervento(token, software, codiceIntervento)?.PersoneFisiche ?? Enumerable.Empty<TipoSoggettoDto>();
        }

        public IEnumerable<TipoSoggettoDto> GetTipiSoggettoPersonaGiurudica(string token, string software, int? codiceIntervento)
        {
            return this.GetTipiSoggettoDaIdIntervento(token, software, codiceIntervento)?.PersoneGiuridiche ?? Enumerable.Empty<TipoSoggettoDto>();
        }
    }
}
