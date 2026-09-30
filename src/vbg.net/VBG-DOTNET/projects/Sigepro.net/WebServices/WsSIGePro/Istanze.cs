using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneOggetti;
using Init.SIGePro.Manager.Logic.SubvisuraPratica;
using Init.SIGePro.Manager.Logic.Visura;
using Init.Utils;
using log4net;
using Ninject;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using Sigepro.net.WebServices.WsSIGePro;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;
using System.ServiceModel;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for Istanze
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [ServiceContract(Namespace = "http://init.sigepro.it")]
    public class IstanzeWs : SigeproWebService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(IstanzeWs));

        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        [WebMethod]
        [OperationContract]
        public Init.SIGePro.Data.Istanze GetDettaglioPratica(string token, int codiceIstanza)
        {
            var ai = this.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);


            try
            {
                var istanza = new IstanzeMgr(ai.CreateDatabase()).GetById(ai.IdComune, codiceIstanza, useForeignEnum.Recoursive);// VisuraPraticheManager(ai).GetDettaglioPratica(request);

                return istanza;
            }
            catch (Exception ex)
            {
                this._log.Error($"GetDettaglioPratica: {ex}");
                throw;
            }
        }

        [WebMethod]
        [OperationContract]
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
                        // Leggo eventuali poratiche collegate
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

        [WebMethod]
        [OperationContract]
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


        [WebMethod]
        public int[] GetOggettiIstanzaDaValoriMetadato(string token, int codiceIstanza, string chiaveMetadato, string valoreMetadato)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var repo = new OggettiRepository(db, authInfo.IdComune);

                return repo.GetCodiciOggettoDocumentiIstanzaDaValoreMetadato(codiceIstanza, chiaveMetadato, valoreMetadato).ToArray();

            }
        }

        [WebMethod]
        [OperationContract]
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

    }
}
