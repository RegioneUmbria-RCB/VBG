using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Exceptions;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace Init.SIGePro.DatiDinamici
{
	/// <summary>
	/// Eccezione sollevata durante la validazione dei valori di un modello dinamico
	/// </summary>
	public partial class ValidazioneModelloDinamicoException : Exception
	{
		/// <summary>
		/// Errori che si sono verificati durante la validazione
		/// </summary>
		public IEnumerable<ErroreValidazione> ListaErrori
		{
			get;
			private set;
		}

		internal ValidazioneModelloDinamicoException(IEnumerable<ErroreValidazione> erroriValidazione)
			: base("Sono stati individuati errori durante la validazione del modello. Utilizzare la property ListaErrori per ottenere la lista degli errori")
		{
			ListaErrori = erroriValidazione;
		}
	}
}
