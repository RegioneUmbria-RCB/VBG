using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Utils;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.STC;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsIstanzePresentateRepository : WsAreaRiservataRepositoryBase, IIstanzePresentateRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsIstanzePresentateRepository));

		IStcService _stcService;

		public WsIstanzePresentateRepository(IStcService stcService, AreaRiservataServiceCreator sc)
			: base(sc)
		{
			this._stcService = stcService;
		}


		public List<FoVisuraCampi> GetFiltri(string alias, string software, TipoContestoVisuraEnum contestoVisura)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				FoVisuraCampi[] lista = null;

				switch (contestoVisura)
				{
					case TipoContestoVisuraEnum.FiltriVisura:
						lista = ws.Service.GetFiltriVisuraV2(ws.Token, software);
						break;
					case TipoContestoVisuraEnum.ListaVisura:
						lista = ws.Service.GetCampiListaVisuraV2(ws.Token, software);
						break;
					case TipoContestoVisuraEnum.FiltriArchivio:
						lista = ws.Service.GetFiltriArchivioV2(ws.Token, software);
						break;
					case TipoContestoVisuraEnum.ListaArchivio:
						lista = ws.Service.GetCampiListaArchivioV2(ws.Token, software);
						break;
				}

				return new List<FoVisuraCampi>(lista);
			}
		}

		public RichiestaPraticheListaResponse GetListaPratiche(string idComune, string software, RichiestaPraticheListaRequest richiesta)
		{
			try
			{
				return this._stcService.RichiestaPraticheLista(richiesta);

			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante l'invocazione del servizio di visura STC (GetListaPraticheV2). Dettagli dell'errore:r\n{0} ", ex.ToString());
				throw;
			}

		}

		public RichiestaPraticaResponse GetDettaglioPratica(string alias, string software, string codiceIstanza)
		{
			try
			{
				_log.DebugFormat("Invocazione di STC (GetDettaglioPratica) con alias={0} e id={1}", alias, codiceIstanza);

				return this._stcService.RichiestaPratica(codiceIstanza);
			}
			catch (Exception)
			{
				_log.ErrorFormat("Errore durante l'invocazione del servizio di visura STC (GetDettaglioPratica)");
				throw;
			}


		}

		public BinaryFile GetDocumentoPratica(string aliasComune, string software, string codiceOggetto)
		{
			try
			{
				var allegatoStc = this._stcService.AllegatoBinario(codiceOggetto);
				
				return new BinaryFile
				{
					FileName = allegatoStc.fileName,
					MimeType = allegatoStc.mimeType,
					FileContent = allegatoStc.binaryData
				};
			}
			catch (Exception)
			{
				_log.ErrorFormat("Errore in GetDocumentoPratica durante la chiamata ad stc");

				throw;
			}
		}

	}
}
