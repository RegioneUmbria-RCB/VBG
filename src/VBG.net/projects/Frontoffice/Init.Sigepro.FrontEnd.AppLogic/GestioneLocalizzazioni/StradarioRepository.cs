using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
	public interface IStradarioRepository
	{
		Stradario GetByCodiceStradario(string aliasComune, int codiceStradario);
		Stradario GetByIndirizzo(string aliasComune, string codiceComune, string indirizzo);
		List<StradarioDto> GetByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo);
		List<StradarioColore> GetListaColori(string aliasComune);

		StradarioDto GetByCodViario(string alias, string codViario);

		IEnumerable<DatiComuneCompatto> GetComuniStradario(string codiceComune);
	}

	internal class WsStradarioRepository : IStradarioRepository
	{
		AreaRiservataServiceCreator _serviceCreator;
		IAliasSoftwareResolver _aliasResolver;
		ILog _log = LogManager.GetLogger(typeof(WsStradarioRepository));

		public WsStradarioRepository(IAliasSoftwareResolver aliasResolver, AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._aliasResolver = aliasResolver;
		}

		public Stradario GetByCodiceStradario(string aliasComune, int codiceStradario)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				try
				{
					return ws.Service.GetByCodiceStradario(ws.Token, codiceStradario);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetByCodiceStradario: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}

		public Stradario GetByIndirizzo(string aliasComune, string codiceComune, string indirizzo)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				try
				{
					return ws.Service.GetByIndirizzo(ws.Token, codiceComune, indirizzo);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetByIndirizzo: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}

		public List<StradarioDto> GetByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				try
				{
				return new List<StradarioDto>(ws.Service.GetByMatchParziale(ws.Token, codiceComune, comuneLocalizzazione, indirizzo));
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetByMatchParziale: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}

		public List<StradarioColore> GetListaColori(string aliasComune)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				try
				{
					return new List<StradarioColore>(ws.Service.GetListaColori(ws.Token));
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetListaColori: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}


		public StradarioDto GetByCodViario(string alias, string codViario)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.GetStradarioByCodViario(ws.Token, codViario);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetByCodViario: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}


		public IEnumerable<DatiComuneCompatto> GetComuniStradario(string codiceComune)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				try
				{
					return ws.Service.GetComuniLocalizzazioni(ws.Token, codiceComune);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore in GetByCodViario: {0}", ex.ToString());
					throw;
				}
				finally
				{
					ws.Service.Abort();
				}
			}
		}
	}
}
