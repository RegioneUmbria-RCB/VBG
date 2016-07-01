// -----------------------------------------------------------------------
// <copyright file="InvioDomandaSTCStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using log4net;
namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	internal class InvioDomandaSTCStrategy : IInvioDomandaStrategy
	{
		IComuniService _comuniService;
		IStcService _stcService;
		IIstanzaStcAdapter _istanzaStcAdapter;
		ILog m_logger = LogManager.GetLogger(typeof(IInvioDomandaStrategy));


		public InvioDomandaSTCStrategy(IComuniService comuniService, IStcService stcService, IIstanzaStcAdapter istanzaStcAdapter)
		{
			this._comuniService = comuniService;
			this._stcService = stcService;
			this._istanzaStcAdapter = istanzaStcAdapter;
		}

		#region IInvioDomandaStrategy Members


		public InvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario)
		{
			m_logger.Debug("Inizio invio della domanda al backoffice tramite STC");

/*			if (_datiDomandaFoRepository.VerificaSePresentata(domanda.AliasComune, domanda.IdDomanda))
			{
				m_logger.ErrorFormat("La domanda con idComune {0} e id {1} è già stata presentata.", domanda.AliasComune, domanda.IdDomanda);

				throw new InvalidOperationException("La domanda è già stata presentata");
			}
*/
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

			try
			{
				var result = this._stcService.InserimentoPratica( request, pecDestinatario );

				// ErroreType viene restituito quando si è verificato un errore durante l'inserimento 
				// della domanda nel backoffice (l'istanza non esiste su domandeSTC)
				if (result.Items[ 0 ] is ErroreType)
					return InvioIstanzaResult.InvioFallito();

				var resConverted = result.Items[ 0 ] as RiferimentiPraticaType;
				var idPratica = resConverted.idPratica;
				var numeroPratica = resConverted.numeroPratica;

				if (idPratica == istanzaInFormatoStc.idPratica)
					return InvioIstanzaResult.InvioRiuscitoNoBackend();

				
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

		#endregion

	}
}
