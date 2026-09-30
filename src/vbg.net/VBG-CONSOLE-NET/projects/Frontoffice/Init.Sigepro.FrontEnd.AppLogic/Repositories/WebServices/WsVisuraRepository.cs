//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
//using log4net;
//using Init.Sigepro.FrontEnd.AppLogic.Entities.Visura;
//using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
//using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
//using Init.Utils;
//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
//using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
//using CuttingEdge.Conditions;
//using Init.Sigepro.FrontEnd.AppLogic.Common;

//namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
//{
//	public class RecordCountException : Exception
//	{
//	}


//	internal class WsVisuraRepository : WsAreaRiservataRepositoryBase, IVisuraRepository
//	{
//		ILog _log = LogManager.GetLogger(typeof(WsVisuraRepository));


//		internal WsVisuraRepository( AreaRiservataServiceCreator arServiceCreator):base(arServiceCreator)
//		{
//		}

//		/// <summary>
//		/// restituisce la lista delle pratiche corrispondenti ai criteri di ricerca passati
//		/// </summary>
//		/// <param name="richiesta">criteri di ricerca</param>
//		/// <returns>lista di pratiche</returns>
//		public  List<PraticaPresentata> GetListaPratiche(string idComune, RichiestaListaPratiche richiesta)
//		{
//			_log.Debug("Invocazione del web service di visura, lettura lista pratiche");

//			using (var ws = _serviceCreator.CreateClient(idComune))
//			{
//				_log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address.ToString());

//				if (_log.IsDebugEnabled)
//					_log.DebugFormat("Parametri di invocazione:\r\n{0}", StreamUtils.SerializeClass(richiesta));

//				var result = ws.Service.GetListaPratiche(ws.Token, richiesta);

//				if (result.LimiteRecordsSuperato.GetValueOrDefault(false))
//					throw new RecordCountException();

//				_log.DebugFormat("Trovate {0} pratiche corrispondenti ai criteri di ricerca", result.Pratiche == null ? 0 : result.Pratiche.Length);

//				return PraticaPresentata.CreaLista(result);
//			}
//		}

//		public  DatiDettaglioPratica GetDettaglioPratica(string idComune, string idPratica)
//		{
//			try
//			{
//				_log.Debug("Invocazione del web service di visura, lettura dettaglio");

//                using (var ws = _serviceCreator.CreateClient(idComune))
//				{

//					_log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address.ToString());

//                    var datiRichiesta = new RichiestaPerIdPratica { CodEnte = idComune, IdPratica = idPratica };
//					var richiesta = new RichiestaDettaglioPratica { Item = datiRichiesta };

//					_log.DebugFormat("Id della pratica richiesta: {0}", idPratica);

//					return new DatiDettaglioPratica(ws.Service.GetDettaglioPratica(ws.Token, richiesta).Pratiche);
//				}
//			}
//			catch (Exception ex)
//			{
//				_log.ErrorFormat("GetDettaglioPratica: Errore durante l'invocazione del web service: {0}, id pratica = {1}", ex, idPratica);

//				return null;
//			}
//		}

		
//	}


	
//}
