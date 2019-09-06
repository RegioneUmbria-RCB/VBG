using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici
{
	internal class ListaRigheGruppoModelloDinamico : IList<RigaModelloDinamico>
	{
		List<RigaModelloDinamico> m_list = new List<RigaModelloDinamico>();

		private int IdGruppo { get; set; }

		internal ListaRigheGruppoModelloDinamico(int idGruppo)
		{
			IdGruppo = idGruppo;
		}

		/// <summary>
		/// Restituisce la molteplicità del gruppo.
		/// La molteplicità del gruppo è l'indice di molteplicità più alto tra tutte le righe del gruppo
		/// </summary>
		/// <returns>Molteplicità del gruppo</returns>
		internal int CalcolaMolteplicita()
		{
			var molteplicita = 0;

			for (int i = 0; i < m_list.Count; i++)
				molteplicita = Math.Max( m_list[i].CalcolaMolteplicita() , molteplicita );

			return molteplicita;
		}

		/// <summary>
		/// Incrementa la molteplicità del gruppo verificando che essa sia consistente su tutti i campi.
		/// </summary>
		internal void IncrementaMolteplicita()
		{
			for (int i = 0; i < m_list.Count; i++)
				m_list[i].IncrementaMolteplicita();

			VerificaConsistenzaMolteplicita();
		}


		/// <summary>
		/// Verifica che la molteplicità sia uguale per tutti i campi del gruppo
		/// </summary>
		internal void VerificaConsistenzaMolteplicita()
		{
			int molteplicita = CalcolaMolteplicita();

			for (int i = 0; i < m_list.Count; i++)
			{
				while (m_list[i].CalcolaMolteplicita() < molteplicita)
					m_list[i].IncrementaMolteplicita();
			}
		}

		internal void EliminaValoriAllIndice(int indice)
		{
			for (int i = 0; i < m_list.Count; i++)
				m_list[i].EliminaValoriAllIndice(indice);

			VerificaConsistenzaMolteplicita();
		}

		public bool IsVisibile()
		{
			foreach (var riga in m_list)
			{
				foreach (var campo in riga.Campi)
				{
					if (campo == null)
					{
						continue;
					}


					foreach (var valore in campo.ListaValori.Cast<ValoreDatiDinamici>())
					{
						if (valore.Visibile)
						{
							return true;
						}
					}
				}
			}

			return false;
		}

		#region IList<RigaModelloDinamico> Members

		public int IndexOf(RigaModelloDinamico item)
		{
			return m_list.IndexOf(item);
		}

		public void Insert(int index, RigaModelloDinamico item)
		{
			item.IdGruppo = IdGruppo;

			m_list.Insert(index, item);
		}

		public void RemoveAt(int index)
		{
			var obj = m_list[index];

			if (obj != null)
				obj.IdGruppo = -1;

			m_list.RemoveAt(index);
		}

		public RigaModelloDinamico this[int index]
		{
			get
			{
				return m_list[index];
			}
			set
			{
				value.IdGruppo = IdGruppo;
				m_list[index] = value;
			}
		}

		#endregion

		#region ICollection<RigaModelloDinamico> Members

		public void Add(RigaModelloDinamico item)
		{
			item.IdGruppo = IdGruppo;

			m_list.Add(item);
		}

		public void Clear()
		{
			for (int i = 0; i < this.Count; i++)
				this[i].IdGruppo = -1;

			m_list.Clear();
		}

		public bool Contains(RigaModelloDinamico item)
		{
			return m_list.Contains(item);
		}

		public void CopyTo(RigaModelloDinamico[] array, int arrayIndex)
		{
			m_list.CopyTo(array, arrayIndex);
		}

		public int Count
		{
			get { return m_list.Count; }
		}

		public bool IsReadOnly
		{
			get { return false; }
		}

		public bool Remove(RigaModelloDinamico item)
		{
			if (this.Contains(item))
				item.IdGruppo = -1;

			return m_list.Remove(item);
		}

		#endregion

		#region IEnumerable<RigaModelloDinamico> Members

		public IEnumerator<RigaModelloDinamico> GetEnumerator()
		{
			return m_list.GetEnumerator();
		}

		#endregion

		#region IEnumerable Members

		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return m_list.GetEnumerator();
		}

		#endregion
	}
}
