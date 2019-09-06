using System;
using System.Runtime.InteropServices;
using System.Xml.Serialization;

namespace Init.SIGePro.Sit.Data
{
	/// <summary>
	/// Classe che implementa l'interfaccia ISit.
	/// </summary>
	[Serializable]
	public class Sit : ISit
	{
		public Sit()
		{ 
			this.IdComune = String.Empty;
			this.CodVia = String.Empty;
			this.Civico = String.Empty;
			this.Km = String.Empty;
			this.Esponente = String.Empty;
			this.Colore = String.Empty;
			this.Scala = String.Empty;
			this.Interno = String.Empty;
			this.EsponenteInterno = String.Empty;
			this.CodCivico = String.Empty;
			this.TipoCatasto = String.Empty;
			this.Sezione = String.Empty;
			this.Foglio = String.Empty;
			this.Particella = String.Empty;
			this.Sub = String.Empty;
			this.UI = String.Empty;
			this.Fabbricato = String.Empty;
			this.OggettoTerritoriale = String.Empty;
			this.DescrizioneVia = String.Empty;
			this.CAP = String.Empty;
			this.Circoscrizione = String.Empty;
			this.Frazione = String.Empty;
			this.Zona = String.Empty;
			this.Piano = String.Empty;
			this.Quartiere = String.Empty;
			this.CodiceComune = String.Empty;
			this.Longitudine = String.Empty;
			this.Latitudine = String.Empty;
            this.AccessoTipo = String.Empty;
            this.AccessoNumero = String.Empty;
            this.AccessoDescrizione = String.Empty;
		}

		public Sit (Sit src)
		{
			this.IdComune = src.IdComune;
			this.CodVia = src.CodVia;
			this.Civico = src.Civico;
			this.Km = src.Km;
			this.Esponente = src.Esponente;
			this.Colore = src.Colore;
			this.Scala = src.Scala;
			this.Interno = src.Interno;
			this.EsponenteInterno = src.EsponenteInterno;
			this.CodCivico = src.CodCivico;
			this.TipoCatasto = src.TipoCatasto;
			this.Sezione = src.Sezione;
			this.Foglio = src.Foglio;
			this.Particella = src.Particella;
			this.Sub = src.Sub;
			this.UI = src.UI;
			this.Fabbricato = src.Fabbricato;
			this.OggettoTerritoriale = src.OggettoTerritoriale;
			this.DescrizioneVia = src.DescrizioneVia;
			this.CAP = src.CAP;
			this.Circoscrizione = src.Circoscrizione;
			this.Frazione = src.Frazione;
			this.Zona = src.Zona;
			this.Piano = src.Piano;
			this.Quartiere = src.Quartiere;
			this.CodiceComune = src.CodiceComune;
			this.Longitudine = src.Longitudine;
			this.Latitudine = src.Latitudine;
            this.AccessoTipo = src.AccessoTipo;
            this.AccessoNumero = src.AccessoNumero;
            this.AccessoDescrizione = src.AccessoDescrizione;
		}

		[XmlElement(Order=0)]
		public string IdComune { get; set; }

		[XmlElement(Order = 1)]
		public string CodVia { get; set; }

		[XmlElement(Order = 2)]
		public string Civico { get; set; }

		[XmlElement(Order = 3)]
		public string Km { get; set; }

		[XmlElement(Order = 4)]
		public string Esponente { get; set; }

		[XmlElement(Order = 5)]
		public string Colore { get; set; }

		[XmlElement(Order = 6)]
		public string Scala { get; set; }

		[XmlElement(Order = 7)]
		public string Interno { get; set; }

		[XmlElement(Order = 8)]
		public string EsponenteInterno { get; set; }

		[XmlElement(Order = 9)]
		public string CodCivico { get; set; }

		[XmlElement(Order = 10)]
		public string TipoCatasto { get; set; }

		[XmlElement(Order = 11)]
		public string Sezione { get; set; }

		[XmlElement(Order = 12)]
		public string Foglio { get; set; }

		[XmlElement(Order = 13)]
		public string Particella { get; set; }

		[XmlElement(Order = 14)]
		public string Sub { get; set; }

		[XmlElement(Order = 15)]
		public string UI { get; set; }

		[XmlElement(Order = 16)]
		public string Fabbricato { get; set; }

		[XmlElement(Order = 17)]
		public string OggettoTerritoriale { get; set; }

		[XmlElement(Order = 18)]
		public string DescrizioneVia { get; set; }

		[XmlElement(Order = 19)]
		public string CAP { get; set; }

		[XmlElement(Order = 20)]
		public string Circoscrizione { get; set; }

		[XmlElement(Order = 21)]
		public string Frazione { get; set; }

		[XmlElement(Order = 22)]
		public string Zona { get; set; }

		[XmlElement(Order = 23)]
		public string Piano { get; set; }

		[XmlElement(Order = 24)]
		public string Quartiere { get; set; }

		[XmlElement(Order = 25)]
		public string CodiceComune { get; set; }

        [XmlElement(Order = 26)]
        public string AccessoTipo { get; set; }

        [XmlElement(Order = 27)]
        public string AccessoNumero { get; set; }

        [XmlElement(Order = 28)]
        public string AccessoDescrizione { get; set; }  
        
        [XmlIgnore]
		public string Longitudine { get; set; }

        [XmlIgnore]
		public string Latitudine { get; set; }


		public void ExtendWith(ISit src)
		{
			foreach (var prop in GetType().GetProperties())
			{
				var val = prop.GetValue(src, null);

				if (val == null || String.IsNullOrEmpty(val.ToString()))
				{
					continue;
				}

				prop.SetValue(this, val, null);
			}
		}
	}
}

