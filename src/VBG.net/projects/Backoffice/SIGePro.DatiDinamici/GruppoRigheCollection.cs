using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici
{
	internal class GruppoRigheCollection
	{
		private Dictionary<int, ListaRigheGruppoModelloDinamico> m_gruppi;

		internal GruppoRigheCollection()
		{
			m_gruppi = new Dictionary<int, ListaRigheGruppoModelloDinamico>();
		}

		internal void AggiungiRigaAGruppo(int idGruppo, RigaModelloDinamico riga)
		{
			var raggruppamento = GetRaggruppamentoById(idGruppo);

			if (raggruppamento == null)
			{
				raggruppamento = new ListaRigheGruppoModelloDinamico(idGruppo);
				m_gruppi.Add(idGruppo, raggruppamento);
			}

			raggruppamento.Add(riga);
		}

		internal IEnumerable<ListaRigheGruppoModelloDinamico> GetRaggruppamenti()
		{
			return m_gruppi.Values;
		}

		internal ListaRigheGruppoModelloDinamico GetRaggruppamentoById(int id)
		{
			ListaRigheGruppoModelloDinamico val = null;

			if (m_gruppi.TryGetValue(id, out val))
				return val;

			return null;
		}
	}
}
