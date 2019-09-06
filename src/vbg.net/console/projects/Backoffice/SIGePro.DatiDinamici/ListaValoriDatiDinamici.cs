using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici
{

	/// <summary>
	/// Rappresenta una lista di valori associati ad un campo dinamico
	/// </summary>
	public partial class ListaValoriDatiDinamici: IEnumerable<ValoreDatiDinamici>
	{
		// Delegate e eventi per la notifica di una modifica
		internal delegate void ValoreModificatoDelegate(ListaValoriDatiDinamici sender);
		internal event ValoreModificatoDelegate ValoreModificato;

		/// <summary>
		/// Utilizzato internamente
		/// </summary>
		protected List<ValoreDatiDinamici> ListaValori { get; set; }

		/// <summary>
		/// Restituisce un riferimento al campo dinamico a cui la lista di valori appartiene
		/// </summary>
		public CampoDinamicoBase Campo { get; protected set; }

		/// <summary>
		/// REstituisce il numero di elementi presenti nella lista di valori
		/// </summary>
		public int Count{get { return ListaValori.Count; }}


		/// <summary>
		/// Restituisce il valore del campo all'indice specificato, se il valore è null restituisce il valore specificato nel parametro valoreDefault
		/// </summary>
		/// <typeparam name="T">Tipo in cui deve essere convertito il valore</typeparam>
		/// <param name="indice">Indice del valore che si intende leggere</param>
		/// <param name="valoreDefault">Valore da utilizzare se il valore del campo è null</param>
		/// <returns>Valore del campo all'indice specificato o valoreDefault se il campo è null</returns>
		/// <exception cref="System.IndexOutOfRangeException">Se l'indice specificato è maggiore del numero di valori del campo</exception>
		public T GetValoreODefault<T>(int indice, T valoreDefault)
		{
			if (indice >= Count)
				throw new IndexOutOfRangeException("Il campo dinamico non contiene l'indice " + indice);

			if (String.IsNullOrEmpty(ListaValori[indice].Valore))
				return valoreDefault;
			else
				return (T)Convert.ChangeType(ListaValori[indice].Valore, typeof(T));
		}

		bool _notificaModificheSospesa = false;

		public void SospendiNotificaModifiche()
		{
			this._notificaModificheSospesa = true;
		}

		public void RipristinaNotificaModifiche()
		{
			this._notificaModificheSospesa = false;
		}

		/// <summary>
		/// Incrementa la molteplicità del campo
		/// </summary>
		public void IncrementaMolteplicita(){AggiungiValore("", "");}

		/// <summary>
		/// Aggiunge un valore alla lista dei valori del campo
		/// </summary>
		/// <param name="valore"></param>
		/// <param name="valoreDecodificato"></param>
		public void AggiungiValore(string valore, string valoreDecodificato)
		{
			ValoreDatiDinamici val = new ValoreDatiDinamici(valore, valoreDecodificato);

			val.OnValoreCampoDinamicoModificato += new ValoreDatiDinamici.ValoreCampoDinamicoModificato(valore_OnValoreCampoDinamicoModificato);

			ListaValori.Add(val);

			if(!this._notificaModificheSospesa)
				NotificaModifica();
		}

		/// <summary>
		/// Utilizzato internamente
		/// </summary>
		public void PplSvuotaValori()
		{
			ListaValori.Clear();
			NotificaModifica();
		}

        public void SvuotaValori()
        {
            ListaValori.Clear();
            NotificaModifica();
        }

		void valore_OnValoreCampoDinamicoModificato(object sender, string vecchioValore, string nuovoValore)
		{
			NotificaModifica();
		}

		internal void NotificaModifica()
		{
			if (ValoreModificato != null)
				ValoreModificato(this);
		}

		/// <summary>
		/// Costruttore
		/// </summary>
		/// <param name="campo">Campo a cui appartiene la lista di valori</param>
		public ListaValoriDatiDinamici(CampoDinamicoBase campo)
		{
			Campo = campo;
			ListaValori = new List<ValoreDatiDinamici>();
		}


		/// <summary>
		/// Restituisce il valore all'indice specificato
		/// </summary>
		/// <param name="idx">Indice</param>
		/// <returns>valore all'indice specificato</returns>
		/// <remarks>Se l'indice specificato è maggiore del numero di valori presenti non viene sollevata un'eccezione. La molteplicità del campo viene incrementatà finchè non raggiunge il valore di idx</remarks>
		public ValoreDatiDinamici this[int idx]
		{
			get
			{
				while (idx >= ListaValori.Count)
					AggiungiValore(String.Empty, String.Empty);

				return ListaValori[idx];
			}
		}

		/// <summary>
		/// Elimina il valore all'indice specificato
		/// </summary>
		/// <param name="index">Indice a cui rimuovere il valore</param>
		/// <exception cref="System.ArgumentOutOfRangeException"></exception>
		internal void RemoveAt(int index)
		{
			if (ListaValori.Count > index)
			{
				var oldVal = ListaValori[index];
				ListaValori.RemoveAt(index);
				oldVal.Valore = String.Empty;				
			}
		}

		#region IEnumerable<ValoreDatiDinamici> Members

		public IEnumerator<ValoreDatiDinamici> GetEnumerator()
		{
			return ListaValori.GetEnumerator();
		}

		#endregion

		#region IEnumerable Members

		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			return ListaValori.GetEnumerator();
		}

		#endregion
	}
}
