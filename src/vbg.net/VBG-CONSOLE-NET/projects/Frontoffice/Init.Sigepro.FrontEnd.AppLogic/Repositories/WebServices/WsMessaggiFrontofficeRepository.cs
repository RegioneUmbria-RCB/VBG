using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;


namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsMessaggiFrontofficeRepository : WsAreaRiservataRepositoryBase, IMessaggiFrontofficeRepository
	{
		const string COD_INVIO = "AR_INVIO";
		const string COD_TRASFERIMENTO = "AR_TRASFERIMENTO";
		const string COD_SOTTOSCRIZIONE = "AR_SOTTOSCRIZIONE";
		const string COD_PRONTA_INVIO = "AR_PRONTA_INVIO";
		const string COD_ANNULLAMENTO = "AR_ANNULLAMENTO";


		public WsMessaggiFrontofficeRepository(AreaRiservataServiceCreator sc)
			: base(sc)
		{
		}
		/*
		/// <summary>
		/// Invia un messaggio di notifica trasferimento 
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idDomanda"></param>
		public void InviaMessaggioTrasferimento(string nominativoSoggettoTrasferente, DomandaOnline domanda)
		{
			using (var ws = _serviceCreator.CreateClient(domanda.DataKey.IdComune))
			{
				ws.Service.CreaMessaggioDomanda(ws.Token, domanda.DataKey.IdPresentazione, COD_TRASFERIMENTO, "", nominativoSoggettoTrasferente, Deserializza(domanda));
			}
		}
		*/
		/// <summary>
		/// Invia un messaggio di notifica invio, il messaggio viene inviato in maniera asincrona
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idDomanda"></param>
		public void InviaMessaggioDomandaRicevuta(string idComune, int idDomanda, int codiceIstanza)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				ws.Service.CreaMessaggioDomandaRicevuta(ws.Token, idDomanda, codiceIstanza);
			}
		}
		/*
		/// <summary>
		/// Invia un messaggio di notifica sottoscrizione
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idDomanda"></param>
		public void InviaMessaggioSottoscrizione(string nominativoSoggettoTrasferente, DomandaOnline domanda)
		{
			using (var ws = _serviceCreator.CreateClient(domanda.AliasComune))
			{
				ws.Service.CreaMessaggioDomanda(ws.Token, domanda.IdDomanda, COD_SOTTOSCRIZIONE, "", nominativoSoggettoTrasferente, Deserializza(domanda ) );
			}
		}
		*/
		/*
		/// <summary>
		/// Invia un messaggio di notifica invio pronto
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idDomanda"></param>
		public void InviaMessaggioProntoInvio(string nominativoSoggettoTrasferente, DomandaOnline domanda)
		{
			using (var ws = _serviceCreator.CreateClient(domanda.AliasComune))
			{
				ws.Service.CreaMessaggioDomanda(ws.Token, domanda.IdDomanda, COD_PRONTA_INVIO, "", nominativoSoggettoTrasferente, Deserializza(domanda));
			}
		}
		*/
		/*
		/// <summary>
		/// Invia un messaggio di notifica annullamento istanza 
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idDomanda"></param>
		public void InviaMessaggioAnnullamento(string nominativoSoggettoTrasferente, DomandaOnline domanda)
		{
			using (var ws = _serviceCreator.CreateClient(domanda.AliasComune))
			{
				ws.Service.CreaMessaggioDomanda(ws.Token, domanda.IdDomanda, COD_ANNULLAMENTO, "", nominativoSoggettoTrasferente, Deserializza(domanda));
			}
		}
		*/
		/// <summary>
		/// Crea un nuovo messaggio libero
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="software"></param>
		/// <param name="idDomanda"></param>
		/// <param name="nomeMittente"></param>
		/// <param name="codiceFiscaleDestinatario"></param>
		/// <param name="oggetto"></param>
		/// <param name="corpo"></param>
		public void InviaMessaggio(string idComune, string software, int? idDomanda, string nomeMittente, string codiceFiscaleDestinatario, string oggetto, string corpo)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				ws.Service.InserisciMessaggio(ws.Token, software, idDomanda, nomeMittente, codiceFiscaleDestinatario, oggetto, corpo);
			}
		}


		public FoMessaggi GetMessaggio(string idComune, int idMessaggio)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetMessaggio(ws.Token, idMessaggio);
			}
		}

		public List<FoMessaggi> GetMessaggi(string idComune, string software, string codiceFiscale, int? filtroStato)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return new List<FoMessaggi>(ws.Service.GetMessaggi(ws.Token, software, codiceFiscale, filtroStato));
			}
		}
		/*
		private string Deserializza(DomandaOnline domanda)
		{
			return new IstanzaSigeproAdapter(domanda).AdattaToString(string.Empty);
		}
		*/
	}
}
