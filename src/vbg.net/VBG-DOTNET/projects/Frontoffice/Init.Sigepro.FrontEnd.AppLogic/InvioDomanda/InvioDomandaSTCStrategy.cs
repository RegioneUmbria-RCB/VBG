// -----------------------------------------------------------------------
// <copyright file="InvioDomandaSTCStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.SIGePro.Manager.DTO.Bookmarks;
using log4net;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    internal class InvioDomandaSTCStrategy : IInvioDomandaStrategy
    {
        private readonly IComuniService _comuniService;
        private readonly IStcService _stcService;
        private readonly IIstanzaStcAdapter _istanzaStcAdapter;
        private readonly ILog _log = LogManager.GetLogger(typeof(IInvioDomandaStrategy));
        private readonly IBookmarksService _bookmarksService;

        public InvioDomandaSTCStrategy(IComuniService comuniService, IStcService stcService, IIstanzaStcAdapter istanzaStcAdapter, IBookmarksService bookmarksService)
        {
            this._comuniService = comuniService;
            this._stcService = stcService;
            this._istanzaStcAdapter = istanzaStcAdapter;
            this._bookmarksService = bookmarksService;
        }

        private class InserimentoPraticaRequestParams
        {
            public readonly InserimentoPraticaRequest Request;
            public readonly SportelloType? SportelloDestinatario;
            public readonly string PecSportello;

            public InserimentoPraticaRequestParams(InserimentoPraticaRequest request, SportelloType? sportelloDestinatario, string pecSportello)
            {
                this.Request = request;
                this.SportelloDestinatario = sportelloDestinatario;
                this.PecSportello = pecSportello;
            }
        }

        private InserimentoPraticaRequestParams PreparaInserimentoPraticaRequest(DomandaOnline domanda, string pecDestinatario, SportelloStcDestinatario? sportelloStcDestinatario)
        {
            var istanzaInFormatoStc = this._istanzaStcAdapter.Adatta(domanda);

            var pecSportello = pecDestinatario;

            if (String.IsNullOrEmpty(pecSportello))
            {
                var software = domanda.DataKey.Software;
                var codicecomune = domanda.ReadInterface.AltriDati.CodiceComune;

                pecSportello = this._comuniService.GetPecComuneAssociato(software, codicecomune);
            }

            var request = new InserimentoPraticaRequest
            {
                dettaglioPratica = istanzaInFormatoStc,
            };

            var sportelloDestinatario = sportelloStcDestinatario == null ? null : new SportelloType
            {
                idEnte = sportelloStcDestinatario.idEnte,
                idNodo = sportelloStcDestinatario.idNodo,
                idSportello = sportelloStcDestinatario.idSportello,
                pecSportello = sportelloStcDestinatario.pecSportello
            };

            if (sportelloDestinatario == null && !String.IsNullOrEmpty(domanda.ReadInterface.Bookmarks.Bookmark))
            {
                var datiBookmark = this._bookmarksService.GetDatiBookmark(domanda.ReadInterface.Bookmarks.Bookmark);

                sportelloDestinatario = new SportelloType
                {
                    idEnte = datiBookmark.NodoDestinatario.IdEnte,
                    idNodo = datiBookmark.NodoDestinatario.IdNodo,
                    idSportello = datiBookmark.NodoDestinatario.IdSportello
                };

                // Todo: impostare gli altri dati della domanda
                if (datiBookmark.NodoDestinatario.Parametri != null)
                {
                    var altriDati = datiBookmark.NodoDestinatario.Parametri.Select(x => this.CreaParametroType(x));

                    request.dettaglioPratica.altriDati = request.dettaglioPratica.altriDati.Union(altriDati).ToArray();
                }
            }

            if (sportelloDestinatario != null)
            {
                sportelloDestinatario.pecSportello = pecSportello;
            }


            return new InserimentoPraticaRequestParams(request, sportelloDestinatario, pecSportello);
        }

        private InvioIstanzaResult StcResponseToInserimentoPraticaResponse(SportelloType? sportelloDestinatario, string idPraticaOriginale, InserimentoPraticaResponse result, bool verificaEsistenzaPraticaSuBackoffice)
        {
            var resConverted = result.Items[0] as RiferimentiPraticaType;
            var idPratica = resConverted.idPratica;
            var numeroPratica = resConverted.numeroPratica;
            var numProtocollo = resConverted.numeroProtocolloGenerale;
            var dataProtocollo = resConverted.dataProtocolloGeneraleSpecified ? resConverted.dataProtocolloGenerale : (DateTime?)null;

            if (idPratica == idPraticaOriginale)
                return InvioIstanzaResult.InvioRiuscitoNoBackend(idPratica, numeroPratica);

            // A questo punto l'invio dovrebbe essere riuscito ma non sono sicuro che l'istanza sia stata creata nel 
            // BO. Per essere sicuro che l'istanza esista devo effettuare una richiestaPratica ad stc
            // TODO: Rimuovere questa chiamata ve siamo in modalità SSU
            if (verificaEsistenzaPraticaSuBackoffice && !this._stcService.PraticaEsisteNelBackend(idPratica, sportelloDestinatario))
                return InvioIstanzaResult.InserimentoFallito();

            return InvioIstanzaResult.InvioRiuscito(idPratica, numeroPratica, numProtocollo, dataProtocollo);
        }

        #region IInvioDomandaStrategy Members

        public async ValueTask<InvioIstanzaResult> SendAsync(DomandaOnline domanda, string pecDestinatario, SportelloStcDestinatario? sportelloDestinatario = null, bool verificaEsistenzaPraticaNelBackoffice = true)
        {
            this._log.Debug("Inizio invio della domanda al backoffice tramite STC");
            var request = this.PreparaInserimentoPraticaRequest(domanda, pecDestinatario, sportelloDestinatario);

            InserimentoPraticaResponse result;
            try
            {
                result = await this._stcService.InserimentoPraticaAsync(request.Request, request.PecSportello, request.SportelloDestinatario);

                // ErroreType viene restituito quando si è verificato un errore durante l'inserimento 
                // della domanda nel backoffice (l'istanza non esiste su domandeSTC)
                if (result.Items[0] is ErroreType)
                    return InvioIstanzaResult.InvioFallito();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat(@"Errore imprevisto durante l'invio della domanda tramite STC: {0}", ex.ToString());

                return InvioIstanzaResult.ErroreInvio();
            }

            return this.StcResponseToInserimentoPraticaResponse(request.SportelloDestinatario, request.Request.dettaglioPratica.idPratica, result, verificaEsistenzaPraticaNelBackoffice);
        }



        public InvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario)
        {
            this._log.Debug("Inizio invio della domanda al backoffice tramite STC");
            var request = this.PreparaInserimentoPraticaRequest(domanda, pecDestinatario, null);

            InserimentoPraticaResponse result;
            try
            {
                result = this._stcService.InserimentoPratica(request.Request, request.PecSportello, request.SportelloDestinatario);

                // ErroreType viene restituito quando si è verificato un errore durante l'inserimento 
                // della domanda nel backoffice (l'istanza non esiste su domandeSTC)
                if (result.Items[0] is ErroreType)
                    return InvioIstanzaResult.InvioFallito();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat(@"Errore imprevisto durante l'invio della domanda tramite STC: {0}", ex.ToString());

                return InvioIstanzaResult.ErroreInvio();
            }

            return this.StcResponseToInserimentoPraticaResponse(request.SportelloDestinatario, request.Request.dettaglioPratica.idPratica, result, true);
        }

        private ParametroType CreaParametroType(BookmarkInterventoDto.NodoDestinazioneParameteriDto x)
        {
            return new ParametroType
            {
                nome = x.Nome,
                valore = new[]{
                            new ValoreParametroType{
                                codice = x.Valore,
                                descrizione = x.Valore
                            }
                        }
            };
        }

        #endregion

    }
}
