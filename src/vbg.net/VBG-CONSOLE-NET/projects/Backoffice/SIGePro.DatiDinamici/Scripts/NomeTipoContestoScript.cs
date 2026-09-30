using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	public static class NomeTipoContestoScript
	{
		static Dictionary<TipoScriptEnum, string> m_nomi = null;

		public static string Get(TipoScriptEnum contesto)
		{
			if (m_nomi == null)
			{
				m_nomi = new Dictionary<TipoScriptEnum, string>();

				m_nomi[TipoScriptEnum.Modifica] = "Aggiornamento/Modifica";
				m_nomi[TipoScriptEnum.Caricamento] = "Caricamento";
				m_nomi[TipoScriptEnum.Salvataggio] = "Salvataggio";
				m_nomi[TipoScriptEnum.Funzioni] = "Funzioni condivise tra tutti gli eventi";
			}

			return m_nomi[contesto];
		}
	}
}
