using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici
{
	public class RigaModelloDinamico
	{
		// lista dei campi contenuti nella riga
		List<CampoDinamicoBase> m_campi = new List<CampoDinamicoBase>();

		/// <summary>
		/// Restituisce o imposta il campo all'indice specificato
		/// </summary>
		/// <param name="idx">indice a cui impostare il campo</param>
		/// <returns>Campo che si trova all'indice specificato o null se il campo non viene trovato</returns>
		public CampoDinamicoBase this[int idx]
		{
			get
			{
				if (m_campi.Count < (idx + 1))
					return null;

				return m_campi[idx];
			}

			set
			{
				while (m_campi.Count < (idx + 1))
					m_campi.Add(null);

				m_campi[idx] = value;
			}
		}

		/// <summary>
		/// restituisce il numero di colonne presenti nella riga
		/// </summary>
		public int NumeroColonne
		{
			get { return m_campi.Count; }
		}

		/// <summary>
		/// Restituisce l'indice della riga all'interno del modello
		/// </summary>
		public int NumeroRiga{get; private set;}

		/// <summary>
		/// Restituisce o imposta se la riga è una riga multipla
		/// </summary>
		public bool RigaMultipla{get;set;}

		/// <summary>
		/// Restituisce l'id del gruppo a cui la riga appartiene 
		/// o -1 se la riga non appartiene a nessun gruppo
		/// </summary>
		public int IdGruppo { get; internal set; }

		internal IEnumerable<CampoDinamicoBase> Campi
		{
			get { return this.m_campi; }
		}


		public RigaModelloDinamico(int numeroRiga)
		{
			NumeroRiga = numeroRiga;

			IdGruppo = -1;
		}


		/// <summary>
		/// Restituisce la molteplicità della riga.
		/// La molteplicità dlla riga è l'indice di molteplicità più alto tra tutti i campi della riga
		/// </summary>
		/// <returns>Molteplicità della riga</returns>
		public int CalcolaMolteplicita()
		{
			int molteplicita = 0;

			for (int i = 0; i < m_campi.Count; i++)
			{
				if (m_campi[i] == null)
					continue;

				molteplicita = Math.Max( m_campi[i].ListaValori.Count,molteplicita );
			}

			return molteplicita;
		}

		/// <summary>
		/// Incrementa la molteplicità della riga verificando che essa sia consistente su tutti i campi.
		/// </summary>
		public void IncrementaMolteplicita()
		{
			for (int i = 0; i < m_campi.Count; i++)
			{
				var campo = m_campi[i];

				if (campo == null)
					continue;

				try
				{
					campo.ListaValori.SospendiNotificaModifiche();

					campo.ListaValori.IncrementaMolteplicita();
				}
				finally
				{
					campo.ListaValori.RipristinaNotificaModifiche();
				}
			}

			VerificaConsistenzaMolteplicita();

			for (int i = 0; i < m_campi.Count; i++)
			{
				var campo = m_campi[i];

				if (campo == null)
					continue;

				campo.ListaValori.NotificaModifica();
			}
		}


		/// <summary>
		/// Verifica che la molteplicità sia uguale per tutti i campi della riga
		/// </summary>
		internal void VerificaConsistenzaMolteplicita()
		{
			int molteplicita = CalcolaMolteplicita();

			for (int i = 0; i < m_campi.Count; i++)
			{
				var campo = m_campi[i];

				if (campo == null)
					continue;

				while (campo.ListaValori.Count < molteplicita)
					campo.ListaValori.IncrementaMolteplicita();
			}
		}

		internal void EliminaValoriAllIndice(int indice)
		{
			for (int i = 0; i < m_campi.Count; i++)
			{
				var campo = m_campi[i];

				if (campo != null)
				{
					campo.ListaValori.RemoveAt(indice);
				}
			}
		}
	}
}
