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
                    var listaErrori = this._modello.ErroriScript.Select(x => $"Errore durante l'esecuzione dello script di caricamento per la scheda {_modello.IdComune}_{_modello.IdModello}: {x.Messaggio} (campo {x.IdCampo}, {x.Indice}) - Il salvataggio non è stato effettuato");
                    _erroriEsecuzioneScript.AddRange(listaErrori);

					return;
				}
			}

			if (eseguiScriptSalvataggio)
			{
				this._modello.Salva();

				if (this._modello.HaErroriScript)
				{
					var listaErrori = this._modello.ErroriScript.Select(x => $"Errore durante l'esecuzione dello script di salvataggio per la scheda {_modello.IdComune}_{_modello.IdModello}: {x.Messaggio} (campo {x.IdCampo}, {x.Indice}) - Il salvataggio non è stato effettuato");
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
