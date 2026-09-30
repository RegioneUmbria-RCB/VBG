using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class DatiIscrizioneAlboProfessionale
	{
		public int? IdAlbo { get; private set; }
		public string Descrizione { get; private set; }
		public string Numero { get; private set; }
		public string SiglaProvincia { get; private set; }

		public DatiIscrizioneAlboProfessionale(int? idAlbo, string descrizione, string numero, string siglaProvincia)
		{
			this.IdAlbo = idAlbo;
			this.Descrizione = descrizione;
			this.Numero = numero;
			this.SiglaProvincia = siglaProvincia;
		}
	}



	public class DatiIscrizioneCciaa
	{
		public string Numero { get; private set; }
		public DateTime? Data { get; private set; }
		public string CodiceComune { get; private set; }

		public DatiIscrizioneCciaa(string numero, DateTime? data, string codiceComune)
		{
			this.Numero = numero;
			this.Data = data;
			this.CodiceComune = codiceComune;
		}

		public bool TuttiIDatiSonoPopolati()
		{
			var comuneCciaaPopolato = !String.IsNullOrEmpty(this.CodiceComune);
			var numeroCciaaPopolato = !String.IsNullOrEmpty(this.Numero);
			var dataCciaaPopolata = this.Data.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue;

			return comuneCciaaPopolato && numeroCciaaPopolato && dataCciaaPopolata;

		}
	}


	public class DatiIscrizioneReaAnagrafica
	{
		public string SiglaProvincia { get; private set; }
		public string Numero { get; private set; }
		public DateTime? Data { get; private set; }

		public DatiIscrizioneReaAnagrafica(string siglaProvincia, string numero, DateTime? data)
		{
			this.SiglaProvincia = siglaProvincia;
			this.Numero = numero;
			this.Data = data;
		}

		public bool TuttiICampiSonoPopolati
		{
			get
			{
				return !String.IsNullOrEmpty(this.SiglaProvincia) &&
						!String.IsNullOrEmpty(this.Numero) &&
						this.Data.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue;
			}
		}
	}


	public class DatiIscrizioneRegTrib
	{
		public string Numero { get; private set; }
		public DateTime? Data { get; private set; }
		public string CodiceComune { get; private set; }

		public DatiIscrizioneRegTrib(string numero, DateTime? data, string codiceComune)
		{
			this.Numero = numero;
			this.Data = data;
			this.CodiceComune = codiceComune;
		}
	}
}
