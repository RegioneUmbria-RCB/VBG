using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using Init.SIGePro.Manager.Logic.GestioneOggetti;
using Init.SIGePro.Manager.Logic.SubvisuraPratica;
using Init.SIGePro.Manager.Logic.Visura;
using Init.SIGePro.Manager.Logic.Visura.ProssimiPassi;
using Init.Utils;
using log4net;
using Ninject;
using PersonalLib2.Data.Providers;
using PersonalLib2.Sql;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Istanze
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsIstanzeService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsIstanzeService.svc or WsIstanzeService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsIstanzeService : WcfServiceBase, IWsIstanzeService
    {
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(WsIstanzeService));

        public WsIstanzeService()
        {

        }

        public Init.SIGePro.Data.Istanze GetDettaglioPratica(string token, int codiceIstanza)
        {
            var ai = this.CheckToken(token);

            try
            {
                var istanza = new IstanzeMgr(ai.CreateDatabase()).GetById(ai.IdComune, codiceIstanza, useForeignEnum.Recoursive);// VisuraPraticheManager(ai).GetDettaglioPratica(request);

                return istanza;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore su chiamata a GetDettaglioPratica: {ex}", ex.ToString());
                throw new Exception(ex.Message);
            }
        }

        public Init.SIGePro.Data.Istanze GetDettaglioPraticaByUuid(string token, string uuid, bool effettuaSubVisuraMovimenti)
        {
            var ai = this.CheckToken(token);

            try
            {
                using (var db = ai.CreateDatabase())
                {
                    var mgr = new IstanzeMgr(db);
                    var riferimentiIstanza = mgr.GetCodiceIstanzaDaUuid(ai.IdComune, uuid);

                    var istanza = mgr.GetById(riferimentiIstanza.IdComune, riferimentiIstanza.CodiceIstanza, useForeignEnum.Recoursive);
                    istanza.Movimenti = istanza.Movimenti?.OrderBy(x => x.DATA.GetValueOrDefault(new DateTime(1970, 1, 1))).ToList();

                    if (effettuaSubVisuraMovimenti)
                    {
                        // Leggo eventuali pratiche collegate
                        var verticalizzazioneStc = this._verticalizzazioniFactory.Create<VerticalizzazioneStc>(ai.Alias, istanza.SOFTWARE);
                        var subVisuraService = new SubvisuraPraticheService(ai, verticalizzazioneStc);

                        // TODO: spostare il ciclo nel service e magari parallelizzare il lavoro
                        // visto che si tratta di un'operazione abbastanza lenta
                        foreach (var movimento in istanza.Movimenti.Where(x => x.INVIATO_CON_STC.GetValueOrDefault(0) != 0))
                        {
                            var uidPraticaCollegata = subVisuraService.GetUidPraticaCollegataDaIdMovimento(movimento);

                            movimento.UuidPraticaCollegata = uidPraticaCollegata;
                        }
                    }
                    var alberoProcMgr = new AlberoProcMgr(db);

                    var datiAlbero = alberoProcMgr.GetTitoloInterventoDaMetadati(ai.IdComune, istanza.Intervento.Sc_id.Value);

                    if (datiAlbero != null && datiAlbero.Titolo != istanza.Intervento.SC_DESCRIZIONE)
                    {
                        istanza.Intervento.DescrizioneCompleta = datiAlbero.Titolo;
                    }

                    return istanza;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore nella visura della pratica {uuid}: {ex}");
                throw;
            }
        }

        public RisultatoVisuraPraticaV3 GetListaPraticheV3(string token, RichiestaListaPraticheV3 richiestaLista)
        {
            var ai = this.CheckToken(token);

            try
            {
                return new VisuraPraticheV3Service(this._verticalizzazioniFactory, ai).GetListaPratiche(richiestaLista);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetListaPratiche: {0} - {1}", ex.ToString(), StreamUtils.SerializeClass(richiestaLista));

                throw;
            }
        }

        public RisultatoVisuraPraticaV3 GetListaPraticheV3Paginato(string token, RichiestaListaPraticheV3 richiestaLista, QueryPaginationRequest paginationRequest)
        {
            var ai = this.CheckToken(token);

            try
            {
                return new VisuraPraticheV3Service(this._verticalizzazioniFactory, ai).GetListaPratiche(richiestaLista, paginationRequest);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetListaPratiche: {0} - {1} - {2}", ex.ToString(), StreamUtils.SerializeClass(richiestaLista), StreamUtils.SerializeClass(paginationRequest));

                throw;
            }
        }

        public int[] GetOggettiIstanzaDaValoriMetadato(string token, int codiceIstanza, string chiaveMetadato, string valoreMetadato)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var repo = new OggettiRepository(db, authInfo.IdComune);

                return repo.GetCodiciOggettoDocumentiIstanzaDaValoreMetadato(codiceIstanza, chiaveMetadato, valoreMetadato).ToArray();

            }
        }

        public string GetUuidDaCodiceIstanza(string token, int codiceIstanza)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var uuid = new IstanzeMgr(db).GetUUIDdaCodiceIstanza(authInfo.IdComune, codiceIstanza);

                if (String.IsNullOrEmpty(uuid))
                {
                    throw new ArgumentException($"Non è stato possibile risalire ad un UUID valido per il codice istanza {authInfo.IdComune}-{codiceIstanza}");
                }

                return uuid;
            }
        }

        public IEnumerable<ProssimiPassiDto> GetProssimiPassi(string token, int codiceIstanza)
        {
            var ai = this.CheckToken(token);

            try
            {

                using (var db = ai.CreateDatabase())
                {
                    var svc = new ProssimiPassiService(db, ai.IdComune);
                    return svc.GetProssimiPassi(codiceIstanza);
                }
            }
            catch (Exception e)
            {
                this._log.Error($"Errore nella lettura dei prossimi passi per la pratica {codiceIstanza}: {e}");

                return Enumerable.Empty<ProssimiPassiDto>();
            }
        }

    }
}
