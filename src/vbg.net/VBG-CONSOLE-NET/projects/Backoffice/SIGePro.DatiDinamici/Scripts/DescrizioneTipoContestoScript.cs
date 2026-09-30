using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	public static class DescrizioneTipoContestoScript
	{
		static Dictionary<TipoScriptEnum, string> m_descrizione = null;

		public static string Get(TipoScriptEnum contesto)
		{
			if (m_descrizione == null)
			{
				m_descrizione = new Dictionary<TipoScriptEnum, string>();

				m_descrizione[TipoScriptEnum.Modifica] =
					"Script eseguito in seguito alla modifica di un campo";

				m_descrizione[TipoScriptEnum.Caricamento] =
					"Script di inizializzazione di un campo o di un modello. Viene seguito ogni volta che il modello viene caricato.";

				m_descrizione[TipoScriptEnum.Salvataggio] =
					"Script che viene eseguito prima del salvataggio del modello. Lo script è eseguito in seguito alla validazione formale del modello quindi i dati immessi sono sempre validi.";
			}

			return m_descrizione[contesto];
		}
	}
}
