using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
	public enum UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche
	{
		Si,
		No
	}
	
	public interface IDatiDinamiciRepository
	{
		ModelloDinamicoCache GetCacheModelloDinamico(int idModello);
		WsListaModelliDinamiciDomanda GetSchedeDaInterventoEEndo(string aliasComune, int intervento, IEnumerable<int> endo, IEnumerable<string> tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche usaTipiLocalizzazioni);
		RisultatoRicercaDatiDinamici InitializeControl(string token, int idCampo, string value);
		RisultatoRicercaDatiDinamici[] GetCompletionList(string token, int idCampo, string partial, ValoreFiltroRicerca[] filtri);
	}

	internal class WsDatiDinamiciRepository : WsAreaRiservataRepositoryBase, IDatiDinamiciRepository
	{
		IAliasResolver _aliasResolver;

		public WsDatiDinamiciRepository(IAliasResolver aliasResolver, AreaRiservataServiceCreator sc)
			: base(sc)
		{
			this._aliasResolver = aliasResolver;
		}

		public WsListaModelliDinamiciDomanda GetSchedeDaInterventoEEndo(string aliasComune, int intervento, IEnumerable<int> endo, IEnumerable<string> tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche usaTipiLocalizzazioni)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var req = new GetModelliDinamiciDaInterventoEEndoRequest
				{
					CodiceIntervento = intervento,
					ListaEndo = endo.ToArray(),
					ListaTipiLocalizzazioni = tipiLocalizzazioni.ToArray(),
					IgnoraTipiLocalizzazione = usaTipiLocalizzazioni == UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No
				};

				return ws.Service.GetModelliDinamiciDaInterventoEEndo(ws.Token, req);
			}
		}

		public RisultatoRicercaDatiDinamici[] GetCompletionList(string token, int idCampo, string partial, ValoreFiltroRicerca[] filtri)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				return ws.Service.GetCompletionListRicerchePlus(token, idCampo, partial, filtri);
			}
		}

		public RisultatoRicercaDatiDinamici InitializeControl(string token, int idCampo, string value)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				return ws.Service.InitializeControlRicerchePlus(token, idCampo, value);
			}
		}



		public ModelloDinamicoCache GetCacheModelloDinamico(int idModello)
		{
			using (var ws = _serviceCreator.CreateClient(_aliasResolver.AliasComune))
			{
				var struttura = ws.Service.GetStrutturaModelloDinamico(ws.Token, idModello);


				var rVal = new ModelloDinamicoCache
				{
					Modello = struttura.Modello,
					ScriptsModello = PopolaScriptsModello(struttura),
					Struttura = new List<IDyn2DettagliModello>(struttura.Struttura),
					ListaCampiDinamici = PopolaCampiDinamici(struttura),
					ListaTesti = PopolaListaTesti(struttura.Testi),
					ScriptsCampiDinamici = PopolaScriptscampiDinamici(struttura.ScriptsCampi),
					ProprietaCampiDinamici = PopolaProprietaCampi(struttura.ProprietaCampiDinamici)
				};

				return rVal;
			}
		}

		private Dictionary<int, List<IDyn2ProprietaCampo>> PopolaProprietaCampi(Dyn2CampiProprieta[] proprieta)
		{
			var rVal = new Dictionary<int, List<IDyn2ProprietaCampo>>();

			for (int i = 0; i < proprieta.Length; i++)
			{
				var el = proprieta[i];

				var idCampo = el.FkD2cId.Value;

				if (!rVal.ContainsKey(idCampo))
					rVal.Add(idCampo, new List<IDyn2ProprietaCampo>());

				rVal[idCampo].Add(el);
			}

			return rVal;
		}

		private Dictionary<int, Dictionary<TipoScriptEnum, IDyn2ScriptCampo>> PopolaScriptscampiDinamici(Dyn2CampiScript[] scriptsCampi)
		{
			var rVal = new Dictionary<int, Dictionary<TipoScriptEnum, IDyn2ScriptCampo>>();

			for (int i = 0; i < scriptsCampi.Length; i++)
			{
				var el = scriptsCampi[i];

				var idCampo = el.FkD2cId.Value;
				var contesto = (TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), el.Evento);

				if (!rVal.ContainsKey(idCampo))
					rVal.Add(idCampo, new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>());

				rVal[idCampo].Add(contesto, el);

			}

			return rVal;
		}

		private SerializableDictionary<int, IDyn2TestoModello> PopolaListaTesti(Dyn2TestoModello[] testi)
		{
			var rVal = new SerializableDictionary<int, IDyn2TestoModello>();

			for (int i = 0; i < testi.Length; i++)
			{
				var el = testi[i];
				rVal.Add(el.Id.Value, el);
			}

			return rVal;
		}

		private SerializableDictionary<int, IDyn2Campo> PopolaCampiDinamici(StrutturaModelloDinamico struttura)
		{
			var rVal = new SerializableDictionary<int, IDyn2Campo>();

			for (int i = 0; i < struttura.CampiDinamici.Length; i++)
			{
				var el = struttura.CampiDinamici[i];
				rVal.Add(el.Id.Value, el);
			}

			return rVal;
		}

		private Dictionary<TipoScriptEnum, IDyn2ScriptModello> PopolaScriptsModello(StrutturaModelloDinamico struttura)
		{
			var rVal = new Dictionary<TipoScriptEnum, IDyn2ScriptModello>();

			for (int i = 0; i < struttura.ScriptsModello.Length; i++)
			{
				var el = struttura.ScriptsModello[i];
				var contesto = (TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), el.Evento);

				rVal.Add(contesto, el);
			}

			return rVal;
		}
	}
}
