using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Core.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.WebApp.Core.ProtocolliRemoti
{
    public class ProtocolloRemoto : IProtocolloMgr
    {
        private readonly ConfigurazioneProtocolloLegacy _config;
        private readonly ClientProtocollazioneLegacyServiceCreator _clientProtocollazioneLegacyServiceCreator;
        private AuthenticationInfo _authInfo;
        private ResolveDatiProtocollazioneService _datiProtocollazione;

        public ProtocolloRemoto(ILog log, IBindingFactory bindingFactory, ConfigurazioneProtocolloLegacy config)
        {
            this._config = config;
            this._clientProtocollazioneLegacyServiceCreator = new ClientProtocollazioneLegacyServiceCreator(log, bindingFactory, config.Url);
        }

        public void AggiungiAllegati(string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                ws.Service.AggiungiAllegati(this._authInfo.Token, numeroProtocollo, dataProtocollo, idProtocollo, codiciAllegati, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
            }
        }

        public void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                ws.Service.AnnullaProtocollo(this._authInfo.Token, idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
            }
        }

        public AppLogic.Shared.WsDataClass.DatiFascicoloResponseType CambiaFascicolo(AppLogic.Shared.WsDataClass.DatiFascType datiFascicolo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.CambiaFascicoloIstanzaXml(this._authInfo.Token, this._datiProtocollazione.CodiceIstanza, datiFascicolo);
                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloResponseType CreaCopie(string codiceAmministrazione)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.CreaCopie(this._authInfo.Token, this._datiProtocollazione.CodiceIstanza, codiceAmministrazione);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.CreaUnitaDocumentaleResponseType CreaUnitaDocumentale(AppLogic.Shared.WsDataClass.CreaUnitaDocumentaleRequestType request)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = new AppLogic.Shared.WsDataClass.CreaUnitaDocumentaleResponseType();

                if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                {
                    ws.Service.CreaUnitaDocumentaleMovimento(this._authInfo.Token, this._datiProtocollazione.CodiceMovimento, request);
                }

                if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                {
                    response = ws.Service.CreaUnitaDocumentaleIstanza(this._authInfo.Token, this._datiProtocollazione.CodiceIstanza, request);
                }

                return response;
            }
        }

        public void Dispose()
        {
            if (this._datiProtocollazione.Db == null)
            {
                throw new Exception("ERRORE DURANTE IL DISPOSE DEL PROTOCOLLO REMOTO, IL DATABASE NON E' STATO GENERATO");
            }

            this._datiProtocollazione.Db.Dispose();
        }

        public AppLogic.Shared.WsDataClass.EseguiAccettazioneResponseType EseguiAccettazione(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.EseguiAccettazione(this._authInfo.Token, idProtocollo, annoProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiFascicoloResponseType Fascicola(AppLogic.Shared.WsDataClass.DatiFascType dati, int source = 16, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {

                var response = ws.Service.Fascicolazione(this._datiProtocollazione.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune, source, this._datiProtocollazione.TipoAmbito, (Istanze)this._datiProtocollazione.Istanza, (Movimenti)this._datiProtocollazione.Movimento, dati, idProtocollo, numeroProtocollo, annoProtocollo);

                return response;
            }
        }

        public ListaFirmatari GetFirmatari()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.GetFirmatari(this._authInfo.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public void Initialize(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null, PecInbox datiPec = null)
        {
            if (string.IsNullOrEmpty(software))
                throw new ArgumentNullException(nameof(software));

            if (authInfo == null)
                throw new ArgumentNullException(nameof(authInfo));

            if (string.IsNullOrEmpty(authInfo.IdComune))
                throw new ArgumentNullException("authInfo.IdComune");

            this._authInfo = authInfo;

            var dataBase = authInfo.CreateDatabase();

            var idComune = authInfo.IdComune;
            var idComuneAlias = authInfo.Alias;
            var codOperatore = authInfo.CodiceResponsabile;
            var token = authInfo.Token;

            this._datiProtocollazione = new ResolveDatiProtocollazioneService(idComune, idComuneAlias, software, dataBase, istanza, movimento, codOperatore, ambito, token, codiceComune, datiPec);
        }

        public void InvioPec()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                ws.Service.InvioPec(this._authInfo.Token, this._datiProtocollazione.CodiceMovimento);
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.IsAnnullato(this._authInfo.Token, idProtocollo, annoProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloEsitatoResponseType IsEsitato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.IsEsitato(this._authInfo.Token, idProtocollo, annoProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.IsFascicolato(this._authInfo.Token, idProtocollo, annoProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.AllegatoResponseType LeggiAllegato(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var idBase = $"{idProtocollo}|{numProtocollo}|{annoProtocollo}|{idAllegato}";
                var response = ws.Service.LeggiAllegato(this._authInfo.Token, idBase, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.AllegatoResponseType LeggiAllegato(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato, string uo, string ruolo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var idBase = $"{idProtocollo}|{numProtocollo}|{annoProtocollo}|{idAllegato}";
                var response = ws.Service.LeggiAllegatoUORuolo(this._authInfo.Token, idBase, uo, ruolo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.AllegatoResponseType LeggiAllegatoStorico(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var idBase = $"{idProtocollo}|{numProtocollo}|{annoProtocollo}|{idAllegato}";
                var response = ws.Service.LeggiAllegatoStorico(this._authInfo.Token, idBase, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public List<AppLogic.Shared.WsDataClass.DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.LeggiProtocollo(leggiProtocolloRequest);

                return response.ToList();
            }
        }

        public List<DatiProtocolloLettoResponseType> LeggiProtocolloConData(string idProtocollo, DateTime dataProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.LeggiProtocolloConData(this._authInfo.Token, idProtocollo, dataProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response.ToList();
            }
        }

        public List<AppLogic.Shared.WsDataClass.DatiProtocolloLettoResponseType> LeggiProtocolloUoRuolo(LeggiProtocolloRequest leggiProtocolloRequest, string uo, string ruolo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.LeggiProtocolloUORuolo(this._authInfo.Token, leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo, uo, ruolo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response.ToList();
            }
        }

        public ListaTipiClassificaType ListaClassifiche()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.GetClassifiche(this._authInfo.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.ListaFascicoliResponseType ListaFascicoli(AppLogic.Shared.WsDataClass.DatiFascType datiFascicolo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.GetFascicoli(this._authInfo.Token, this._datiProtocollazione.CodiceIstanza);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.ListaMotiviAnnullamentoResponseType ListaMotivoAnnullamento()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.GetMotiviAnnullamento(this._authInfo.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.ListaTipiDocumentoResponseType ListaTipiDocumento()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.GetTipiDocumento(this._authInfo.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloResponseType MettiAllaFirma(AppLogic.Shared.WsDataClass.DatiRequestType dati)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.MettiAllaFirmaXml(this._authInfo.Token, this._datiProtocollazione.CodiceMovimento, dati);

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloResponseType Protocollazione(TipoProvenienza provenienza, AppLogic.Shared.WsDataClass.DatiRequestType dati, Source tipoInserimento)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.Protocollazione(this._datiProtocollazione.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune, (int)tipoInserimento, this._datiProtocollazione.TipoAmbito, (Istanze)this._datiProtocollazione.Istanza, (Movimenti)this._datiProtocollazione.Movimento, dati, this._datiProtocollazione.DatiPec);

                return response;
            }
        }

        public List<AppLogic.Shared.WsDataClass.MetadatoType> RecuperaMetadati()
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.RecuperaMetadati(this._authInfo.Token, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response.ToList();
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloResponseType Registrazione(string registro, AppLogic.Shared.WsDataClass.DatiRequestType dati, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE, int iSource = 16)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = new AppLogic.Shared.WsDataClass.DatiProtocolloResponseType();

                if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                {
                    response = ws.Service.RegistrazioneIstanzaXml(this._datiProtocollazione.Token, this._datiProtocollazione.CodiceIstanza, registro, dati);
                }

                if (this._datiProtocollazione.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                {
                    response = ws.Service.RegistrazioneMovimentoXml(this._datiProtocollazione.Token, this._datiProtocollazione.CodiceMovimento, registro, dati);
                }

                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var response = ws.Service.StampaEtichette(this._authInfo.Token, idProtocollo, numeroProtocollo, dataProtocollo, numeroCopie, stampante, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);

                return response;
            }
        }

    }
}
