using System;
using System.Collections.Generic;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Normative;
using Init.SIGePro.Manager.DTO.Oneri;
using System.Xml.Serialization;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
	public class EndoprocedimentoDto
	{
		[XmlElement]
		[DataMember]
		public int Codice { get; set; }

		[XmlElement]
		[DataMember]
		public string Descrizione { get; set; }

		[XmlElement]
		[DataMember]
		public bool Richiesto { get; set; }

		[XmlElement]
		[DataMember]
		public bool Principale { get; set; }

		[XmlElement]
		[DataMember]
		public List<SchedaDinamicaDto> SchedeDinamiche { get; set; }

		[XmlElement]
		[DataMember]
		public string Note { get; set; }

		[XmlElement]
		[DataMember]
		public DateTime? UltimoAggiornamento { get;set; }

		[XmlElement]
		[DataMember]
		public string Tipologia { get; set; }

		[XmlElement]
		[DataMember]
		public string Tempificazione { get; set; }

		[XmlElement]
		[DataMember]
		public string Amministrazione { get; set; }

		[XmlElement]
		[DataMember]
		public string Movimento { get; set; }

		[XmlElement]
		[DataMember]
		public int? CodiceNatura { get; set; }

		[XmlElement]
		[DataMember]
		public string Natura { get; set; }

		[XmlElement]
		[DataMember]
		public int BinarioDipendenze { get; set; }

		[XmlElement]
		[DataMember]
		public string Applicazione { get; set; }

		[XmlElement]
		[DataMember]
		public string DatiGenerali { get; set; }

		[XmlElement]
		[DataMember]
		public string NormativaUE { get; set; }

		[XmlElement]
		[DataMember]
		public string NormativaNazionale { get; set; }

		[XmlElement]
		[DataMember]
		public string NormativaRegionale { get; set; }

		[XmlElement]
		[DataMember]
		public string Regolamenti { get; set; }

		[XmlElement]
		[DataMember]
		public string Adempimenti { get; set; }

		[XmlElement]
		[DataMember]
		public List<TestiEstesiDto> TestiEstesi { get; set; }

		[XmlElement]
		[DataMember]
		public List<AllegatoDto> Allegati { get; set; }

		[XmlElement]
		[DataMember]
		public List<NormativaDto> Normative { get; set; }

		[XmlElement]
		[DataMember]
		public List<OnereDto> Oneri { get; set; }

		[XmlElement]
		[DataMember]
		public int Ordine { get; set; }

        [XmlElement]
		[DataMember]
		public bool TipoTitoloObbligatorio { get; set; }
        
		[XmlElement]
		[DataMember]
		public int OrdineFamiglia { get; set; }
        
		[XmlElement]
		[DataMember]
		public int OrdineTipo { get; set; }

		[XmlElement()]
		[DataMember]
		public List<FamigliaEndoprocedimentoDto> SubEndo { get; set; }


		public EndoprocedimentoDto()
		{
			Principale = false;
			Richiesto = false;
			SchedeDinamiche = new List<SchedaDinamicaDto>();
			TestiEstesi = new List<TestiEstesiDto>();
			Allegati = new List<AllegatoDto>();
			Normative = new List<NormativaDto>();
			Oneri = new List<OnereDto>();
			SubEndo = new List<FamigliaEndoprocedimentoDto>();
		}

		//public static List<EndoprocedimentoDto> GetEndoConSchedeDinamiche(List<InventarioProcedimenti> listaEndo, Dictionary<int, List<SchedaDinamicaDto>> listaSchedeEndo)
		//{
		//	var rVal = new List<EndoprocedimentoDto>();

		//	for (int i = 0; i < listaEndo.Count; i++)
		//	{
		//		var endo = new EndoprocedimentoDto
		//		{
		//			Codice = listaEndo[i].Codiceinventario.Value,
		//			Descrizione = listaEndo[i].Procedimento,
		//			Note = String.Empty,
		//		};

		//		if (listaSchedeEndo.ContainsKey(endo.Codice))
		//			endo.SchedeDinamiche.AddRange( listaSchedeEndo[endo.Codice] );

		//		rVal.Add(endo);
		//	}

		//	return rVal;
		//}
	}
}
