using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Entities.Visura;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Utils;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	public class RecordCountException : Exception
	{
	}


	internal class WsVisuraRepository : WsAreaRiservataRepositoryBase, IVisuraRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsVisuraRepository));


		internal WsVisuraRepository( AreaRiservataServiceCreator arServiceCreator):base(arServiceCreator)
		{
		}

		/// <summary>
		/// restituisce la lista delle pratiche corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="richiesta">criteri di ricerca</param>
		/// <returns>lista di pratiche</returns>
		public  List<PraticaPresentata> GetListaPratiche(string aliasComune, RichiestaListaPratiche richiesta)
		{
			_log.Debug("Invocazione del web service di visura, lettura lista pratiche");

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				_log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address.ToString());

				if (_log.IsDebugEnabled)
					_log.DebugFormat("Parametri di invocazione:\r\n{0}", StreamUtils.SerializeClass(richiesta));

				var result = ws.Service.GetListaPratiche(ws.Token, richiesta);

				if (result.LimiteRecordsSuperato.GetValueOrDefault(false))
					throw new RecordCountException();

				_log.DebugFormat("Trovate {0} pratiche corrispondenti ai criteri di ricerca", result.Pratiche == null ? 0 : result.Pratiche.Length);

				return PraticaPresentata.CreaLista(result);
			}
		}

		public  DatiDettaglioPratica GetDettaglioPratica(string aliasComune, string idPratica)
		{
			try
			{
				_log.Debug("Invocazione del web service di visura, lettura dettaglio");

				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{

					_log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address.ToString());

					var datiRichiesta = new RichiestaPerIdPratica { CodEnte = aliasComune, IdPratica = idPratica };
					var richiesta = new RichiestaDettaglioPratica { Item = datiRichiesta };

					_log.DebugFormat("Id della pratica richiesta: {0}", idPratica);

					return new DatiDettaglioPratica(ws.Service.GetDettaglioPratica(ws.Token, richiesta).Pratiche);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("GetDettaglioPratica: Errore durante l'invocazione del web service: {0}, id pratica = {1}", ex, idPratica);

				return null;
			}
		}

		
	}


	internal class WsDettaglioPraticaRepository : IDettaglioPraticaRepository
	{
		ILog _log = LogManager.GetLogger(typeof(WsVisuraRepository));
		IConfigurazioneVbgRepository _configurazioneVbgRepository;
		IstanzeServiceCreator _istanzeServiceCreator;

		internal WsDettaglioPraticaRepository(IConfigurazioneVbgRepository configurazioneVbgRepository,IstanzeServiceCreator istanzeServiceCreator)
		{
			this._configurazioneVbgRepository	= configurazioneVbgRepository;
			this._istanzeServiceCreator			= istanzeServiceCreator;
		}

		public Istanze GetById(string aliasComune, int idPratica, bool leggiDatiConfigurazione)
		{
			try
			{
				_log.Debug("Invocazione del web service di visura, lettura dettaglio");

                using (var ws = _istanzeServiceCreator.CreateClient(aliasComune))
                {
                    try
                    {
                        _log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address);
                        _log.DebugFormat("Id pratica = {0}, leggiDatiConfigurazione = {1}", idPratica, leggiDatiConfigurazione);

                        var istanza = ws.Service.GetDettaglioPratica(ws.Token, idPratica);

                        if (istanza == null)
                            return null;

                        if (istanza.Movimenti != null && istanza.Movimenti.Length > 0)
                        {
                            // Rimuovo dalla lista i movimenti che non hanno una data

                            var movimentiDaTenere = new List<Movimenti>();

                            foreach (var movimento in istanza.Movimenti)
                            {
                                if (movimento.DATA.HasValue)
                                    movimentiDaTenere.Add(movimento);
                            }

                            movimentiDaTenere.Sort((a, b) =>
                            {
                                if (!a.DATA.HasValue && !b.DATA.HasValue)
                                    return 0;

                                if (a.DATA.HasValue && !b.DATA.HasValue)
                                    return -1;

                                if (!a.DATA.HasValue && b.DATA.HasValue)
                                    return 1;

                                var id1 = a.DATA.Value.ToString("yyyyMMdd") + "-" + a.CODICEMOVIMENTO.PadLeft(10, '0');
                                var id2 = b.DATA.Value.ToString("yyyyMMdd") + "-" + b.CODICEMOVIMENTO.PadLeft(10, '0');

                                return id1.CompareTo(id2);
                            });

                            istanza.Movimenti = movimentiDaTenere.ToArray();
                        }

                        if (leggiDatiConfigurazione)
                            istanza.ConfigurazioneComune = _configurazioneVbgRepository.LeggiConfigurazioneComune(aliasComune, istanza.SOFTWARE);

                        return istanza;
                    }
                    catch (Exception)
                    {
                        ws.Service.Abort();

                        throw;
                    }
                }
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("GetDettaglioPraticaEx: Errore durante l'invocazione del web service: {0}, id pratica = {1} , leggiDatiConfigurazione = {2}", ex, idPratica, leggiDatiConfigurazione);
				return null;
			}
		}
	}
}
