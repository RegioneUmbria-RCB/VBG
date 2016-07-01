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
			IdComune = String.Empty;
			CodVia = String.Empty;
			Civico = String.Empty;
			Km = String.Empty;
			Esponente = String.Empty;
			Colore = String.Empty;
			Scala = String.Empty;
			Interno = String.Empty;
			EsponenteInterno = String.Empty;
			CodCivico = String.Empty;
			TipoCatasto = String.Empty;
			Sezione = String.Empty;
			Foglio = String.Empty;
			Particella = String.Empty;
			Sub = String.Empty;
			UI = String.Empty;
			Fabbricato = String.Empty;
			OggettoTerritoriale = String.Empty;
			DescrizioneVia = String.Empty;
			CAP = String.Empty;
			Circoscrizione = String.Empty;
			Frazione = String.Empty;
			Zona = String.Empty;
			Piano = String.Empty;
			Quartiere = String.Empty;
			CodiceComune = String.Empty;
			Longitudine = String.Empty;
			Latitudine = String.Empty;
		}

		public Sit (Sit src)
		{
			IdComune = src.IdComune;
			CodVia = src.CodVia;
			Civico = src.Civico;
			Km = src.Km;
			Esponente = src.Esponente;
			Colore = src.Colore;
			Scala = src.Scala;
			Interno = src.Interno;
			EsponenteInterno = src.EsponenteInterno;
			CodCivico = src.CodCivico;
			TipoCatasto = src.TipoCatasto;
			Sezione = src.Sezione;
			Foglio = src.Foglio;
			Particella = src.Particella;
			Sub = src.Sub;
			UI = src.UI;
			Fabbricato = src.Fabbricato;
			OggettoTerritoriale = src.OggettoTerritoriale;
			DescrizioneVia = src.DescrizioneVia;
			CAP = src.CAP;
			Circoscrizione = src.Circoscrizione;
			Frazione = src.Frazione;
			Zona = src.Zona;
			Piano = src.Piano;
			Quartiere = src.Quartiere;
			CodiceComune = src.CodiceComune;
			Longitudine = src.Longitudine;
			Latitudine = src.Latitudine;
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

		// [XmlElement(Order = 26)]
		[XmlIgnore]
		public string Longitudine { get; set; }

		// [XmlElement(Order = 27)]
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

