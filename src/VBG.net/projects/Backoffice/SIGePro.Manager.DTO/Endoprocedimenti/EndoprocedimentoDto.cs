using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Normative;
using Init.SIGePro.Manager.DTO.Oneri;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class EndoprocedimentoDto: BaseDto<int,string>
	{
		[XmlElement(Order=0)]
		public bool Richiesto { get; set; }

		[XmlElement(Order = 1)]
		public bool Principale { get; set; }

		[XmlElement(Order = 2)]
		public List<SchedaDinamicaDto> SchedeDinamiche { get; set; }

		[XmlElement(Order = 3)]
		public string Note { get; set; }

		[XmlElement(Order = 4)]
		public DateTime? UltimoAggiornamento { get;set; }

		[XmlElement(Order = 5)]
		public string Tipologia { get; set; }

		[XmlElement(Order = 6)]
		public string Tempificazione { get; set; }

		[XmlElement(Order = 7)]
		public string Amministrazione { get; set; }

		[XmlElement(Order = 8)]
		public string Movimento { get; set; }

		[XmlElement(Order = 9)]
		public int? CodiceNatura { get; set; }

		[XmlElement(Order = 10)]
		public string Natura { get; set; }

		[XmlElement(Order = 11)]
		public int BinarioDipendenze { get; set; }

		[XmlElement(Order = 12)]
		public string Applicazione { get; set; }

		[XmlElement(Order = 13)]
		public string DatiGenerali { get; set; }

		[XmlElement(Order = 14)]
		public string NormativaUE { get; set; }

		[XmlElement(Order = 15)]
		public string NormativaNazionale { get; set; }

		[XmlElement(Order = 16)]
		public string NormativaRegionale { get; set; }

		[XmlElement(Order = 17)]
		public string Regolamenti { get; set; }

		[XmlElement(Order = 18)]
		public string Adempimenti { get; set; }

		[XmlElement(Order = 19)]
		public List<TestiEstesiDto> TestiEstesi { get; set; }

		[XmlElement(Order = 20)]
		public List<AllegatoDto> Allegati { get; set; }

		[XmlElement(Order = 21)]
		public List<NormativaDto> Normative { get; set; }

		[XmlElement(Order = 22)]
		public List<OnereDto> Oneri { get; set; }

		[XmlElement(Order = 23)]
		public int Ordine { get; set; }
		

		public EndoprocedimentoDto()
		{
			Principale = false;
			Richiesto = false;
			SchedeDinamiche = new List<SchedaDinamicaDto>();
			TestiEstesi = new List<TestiEstesiDto>();
			Allegati = new List<AllegatoDto>();
			Normative = new List<NormativaDto>();
			Oneri = new List<OnereDto>();
		}

		public static List<EndoprocedimentoDto> GetEndoConSchedeDinamiche(List<InventarioProcedimenti> listaEndo, Dictionary<int, List<SchedaDinamicaDto>> listaSchedeEndo)
		{
			var rVal = new List<EndoprocedimentoDto>();

			for (int i = 0; i < listaEndo.Count; i++)
			{
				var endo = new EndoprocedimentoDto
				{
					Codice = listaEndo[i].Codiceinventario.Value,
					Descrizione = listaEndo[i].Procedimento,
					Note = String.Empty,
				};

				if (listaSchedeEndo.ContainsKey(endo.Codice))
					endo.SchedeDinamiche.AddRange( listaSchedeEndo[endo.Codice] );

				rVal.Add(endo);
			}

			return rVal;
		}
	}
}
