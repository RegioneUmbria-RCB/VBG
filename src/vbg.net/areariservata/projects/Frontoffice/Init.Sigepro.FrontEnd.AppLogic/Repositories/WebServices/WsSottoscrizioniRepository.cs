using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsSottoscrizioniRepository : WsAreaRiservataRepositoryBase, ISottoscrizioniRepository
	{
		ILog logger = LogManager.GetLogger(typeof(WsDatiDomandaFoRepository));
		IDatiDomandaFoRepository _datiDomandaFoRepository;
		IMessaggiFrontofficeRepository _messaggiFrontofficeRepository;

		public WsSottoscrizioniRepository(IDatiDomandaFoRepository datiDomandaFoRepository, IMessaggiFrontofficeRepository messaggiFrontofficeRepository, AreaRiservataServiceCreator sc):base(sc)
		{
			_datiDomandaFoRepository = datiDomandaFoRepository;
			_messaggiFrontofficeRepository = messaggiFrontofficeRepository;
		}


		/// <summary>
		/// Effettua la sottoscrizione di una domanda da parte di un soggetto sottoscrittore
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		/// <param name="nominativo"></param>
		/// <param name="codicefiscale"></param>
		public void SottoscriviDomanda(string aliasComune, int idDomanda, string codicefiscale)
		{
			logger.DebugFormat("Inizio sottoscrizione della domanda {0} (aliasComune {1}) da parte dell'utente {2}", idDomanda, aliasComune, codicefiscale);

			try
			{
				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					logger.DebugFormat("Invocazione del web service di sottoscrizione domanda. Url del web service: {0}", ws.Service.Endpoint.Address.ToString());

					ws.Service.SottoscriviDomanda(ws.Token, idDomanda, codicefiscale);

					logger.Debug("Invocazione del web service riuscita");
				}
			}
			catch (Exception ex)
			{
				logger.ErrorFormat("SottoscriviDomanda: errore durante la sottoscrizione della domanda {0}-> {1}", idDomanda, ex);

				throw;
			}
		}


		public List<FoSottoscrizioni> GetSottoscrizioniUtente(string aliasComune, int idPresentazione, string codiceFiscaleUtente)
		{ 
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var arr = ws.Service.GetListaSottoscrizioniUtente(ws.Token, idPresentazione, codiceFiscaleUtente);

				return new List<FoSottoscrizioni>( arr );
			}
		}

		/// <summary>
		/// Verifica se una domanda è stata sottoscritta da un utente
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idPresentazione"></param>
		/// <param name="codiceFiscale"></param>
		/// <returns>true se l'utente ha già sottoscritto la pratica, altrimenti false</returns>
		public bool VerificaSottoscrizioneDomanda(string aliasComune, int idPresentazione, string codiceFiscale)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				FoSottoscrizioni[] sottoscrizioni = ws.Service.GetListaSottoscriventi(ws.Token, idPresentazione);

				for (int i = 0; i < sottoscrizioni.Length; i++)
				{
					if (sottoscrizioni[i].Codicefiscale.ToUpper() == codiceFiscale.ToUpper() && sottoscrizioni[i].Datasottoscrizione.HasValue)
						return true;

					if (sottoscrizioni[i].Codicefiscalesottoscrivente.ToUpper() == codiceFiscale.ToUpper() && sottoscrizioni[i].Datasottoscrizione.HasValue)
						return true;
				}


			}

			return false;
		}

		/// <summary>
		/// Legge la lista dei soggetti sottoscriventi di una domanda
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idPresentazione"></param>
		/// <returns></returns>
		public List<FoSottoscrizioni> GetListaSottoscriventi(string aliasComune, int idPresentazione)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				List<FoSottoscrizioni> lst = new List<FoSottoscrizioni>();

				lst.AddRange(ws.Service.GetListaSottoscriventi(ws.Token, idPresentazione));

				return lst;
			}
		}

	}
}
