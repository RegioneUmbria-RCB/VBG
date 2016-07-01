using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.EsecuzioneScriptDaWebService
{
	public class EsecuzioneScriptSchedeDinamicheService
	{
		DataBase _database;
		ModelloDinamicoLoader _loader;

		public EsecuzioneScriptSchedeDinamicheService (DataBase database, string idComune)
		{
			this._database = database;
			this._loader = new ModelloDinamicoLoader(new Dyn2DataAccessProvider(this._database), idComune, false);
		}

		public List<string> EseguiScriptSchedaSingolaIstanza(int codiceIstanza, int idScheda, bool eseguiScriptCaricamento, bool eseguiScriptSalvataggio)
		{
			var modello = new ModelloDinamicoIstanza(this._loader, idScheda, codiceIstanza, 0, false);

			var esecutoreScript = new EsecutoreScript(modello);

			esecutoreScript.EseguiScript(eseguiScriptCaricamento, eseguiScriptSalvataggio);

			return esecutoreScript.GetErrori();
		}

		public List<string> EseguiScriptSchedeIstanza(int codiceIstanza, bool eseguiScriptCaricamento, bool eseguiScriptSalvataggio)
		{
			var mgr = new IstanzeDyn2ModelliTMgr(this._database);

			var listaModelli = mgr.GetList(new Data.IstanzeDyn2ModelliT
			{
				Idcomune = this._loader.Idcomune,
				Codiceistanza = codiceIstanza
			}).Select( x => x.FkD2mtId.Value );

			var listaErrori = new List<string>();

			foreach (var idModello in listaModelli)
			{
				var errori = EseguiScriptSchedaSingolaIstanza(codiceIstanza, idModello, eseguiScriptCaricamento, eseguiScriptSalvataggio);

				listaErrori.AddRange(errori);
			}

			return listaErrori;
		}
	}
}
