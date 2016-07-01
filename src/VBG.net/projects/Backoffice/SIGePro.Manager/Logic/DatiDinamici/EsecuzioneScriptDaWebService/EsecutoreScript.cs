using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.EsecuzioneScriptDaWebService
{
	internal class EsecutoreScript
	{
		ModelloDinamicoBase _modello;
		List<string>		_erroriEsecuzioneScript;

		internal EsecutoreScript(ModelloDinamicoBase modello)
		{
			this._modello = modello;
		}

		internal void EseguiScript(bool eseguiScriptCaricamento, bool eseguiScriptSalvataggio)
		{
			_erroriEsecuzioneScript = new List<string>();

			if (eseguiScriptCaricamento)
			{
				this._modello.EseguiScriptCaricamento();

				if(this._modello.HaErroriScript)
				{
					var errFormatString = "Errore durante l'esecuzione dello script di caricamento per la scheda {0}_{1}: {2} - Lo script di salvataggio non verrà eseguito";
					var listaErrori = this._modello.ErroriScript.Select( x => String.Format( errFormatString , _modello.IdComune , _modello.IdModello , x ));
					_erroriEsecuzioneScript.AddRange(listaErrori);

					return;
				}
			}

			if (eseguiScriptSalvataggio)
			{
				this._modello.Salva();

				if (this._modello.HaErroriScript)
				{
					var errFormatString = "Errore durante l'esecuzione dello script di salvataggio per la scheda {0}_{1}: {2} - Il salvataggio non è stato effettuato";
					var listaErrori = this._modello.ErroriScript.Select(x => String.Format(errFormatString, _modello.IdComune, _modello.IdModello, x));
					_erroriEsecuzioneScript.AddRange(listaErrori);

					return;
				}
			}
		}

		internal List<string> GetErrori()
		{
			return this._erroriEsecuzioneScript;
		}
	}
}
