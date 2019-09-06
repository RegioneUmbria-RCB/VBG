// -----------------------------------------------------------------------
// <copyright file="InvioDomandaSTCStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	internal class InvioDomandaSTCStrategy : IInvioDomandaStrategy
	{
		IComuniService _comuniService;
		IStcService _stcService;
		IIstanzaStcAdapter _istanzaStcAdapter;
		ILog m_logger = LogManager.GetLogger(typeof(IInvioDomandaStrategy));
        IBookmarksService _bookmarksService;

		public InvioDomandaSTCStrategy(IComuniService comuniService, IStcService stcService, IIstanzaStcAdapter istanzaStcAdapter, IBookmarksService bookmarksService)
		{
			this._comuniService = comuniService;
			this._stcService = stcService;
			this._istanzaStcAdapter = istanzaStcAdapter;
            this._bookmarksService = bookmarksService;
		}

		#region IInvioDomandaStrategy Members


		public InvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario)
		{
			m_logger.Debug("Inizio invio della domanda al backoffice tramite STC");

			var istanzaInFormatoStc = this._istanzaStcAdapter.Adatta(domanda);

			var pecSportello = pecDestinatario;

			if (String.IsNullOrEmpty(pecSportello))
			{
				var software = domanda.DataKey.Software;
				var codicecomune = domanda.ReadInterface.AltriDati.CodiceComune;

				pecSportello = _comuniService.GetPecComuneAssociato( software, codicecomune);
			}

			var request = new InserimentoPraticaRequest
			{
				dettaglioPratica = istanzaInFormatoStc,
			};

            SportelloType sportelloDestinatario = null;

            if (!String.IsNullOrEmpty(domanda.ReadInterface.Bookmarks.Bookmark))
            {
                var datiBookmark = this._bookmarksService.GetDatiBookmark(domanda.ReadInterface.Bookmarks.Bookmark);

                sportelloDestinatario = new SportelloType
                {
                    idEnte = datiBookmark.NodoDestinatario.IdEnte,
                    idNodo = datiBookmark.NodoDestinatario.IdNodo,
                    idSportello = datiBookmark.NodoDestinatario.IdSportello,
                    pecSportello = pecSportello
                };

                // Todo: impostare gli altri dati della domanda
                if (datiBookmark.NodoDestinatario.Parametri != null)
                {
                    var altriDati = datiBookmark.NodoDestinatario.Parametri.Select(x => CreaParametroType(x));

                    request.dettaglioPratica.altriDati = request.dettaglioPratica.altriDati.Union(altriDati).ToArray();
                }
            }

			try
			{
                var result = this._stcService.InserimentoPratica(request, pecSportello, sportelloDestinatario);

				// ErroreType viene restituito quando si è verificato un errore durante l'inserimento 
				// della domanda nel backoffice (l'istanza non esiste su domandeSTC)
				if (result.Items[ 0 ] is ErroreType)
					return InvioIstanzaResult.InvioFallito();

				var resConverted = result.Items[ 0 ] as RiferimentiPraticaType;
				var idPratica = resConverted.idPratica;
				var numeroPratica = resConverted.numeroPratica;

				if (idPratica == istanzaInFormatoStc.idPratica)
					return InvioIstanzaResult.InvioRiuscitoNoBackend( idPratica, numeroPratica );

				
				// A questo punto l'invio dovrebbe essere riuscito ma non sono sicuro che l'istanza sia stata creata nel 
				// BO. Per essere sicuro che l'istanza esista devo effettuare una richiestaPratica ad stc
				if (!this._stcService.PraticaEsisteNelBackend( idPratica ))
					return InvioIstanzaResult.InserimentoFallito();

				return InvioIstanzaResult.InvioRiuscito(idPratica, numeroPratica);

			}
			catch(Exception ex)
			{
				m_logger.ErrorFormat(@"Errore imprevisto durante l'invio della domanda tramite STC: {0}", ex.ToString());

				return InvioIstanzaResult.InvioFallito();	
			}
		}

        private ParametroType CreaParametroType(NodoDestinazioneParameteriDto x)
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
