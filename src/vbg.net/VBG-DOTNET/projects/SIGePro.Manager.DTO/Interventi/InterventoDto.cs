using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Normative;
using Init.SIGePro.Manager.DTO.Procedure;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Interventi
{
	[DataContract]
	public class InterventoDto
	{
		[DataMember]
		public int Codice{ get; set; }

		[DataMember]
		public string Descrizione { get; set; }

		[DataMember]
		public List<SchedaDinamicaDto> SchedeDinamiche { get; set; }

		[DataMember]
		public List<OnereDto> Oneri { get; set; }
		
		[DataMember]
		public List<FamigliaEndoprocedimentoDto> Endoprocedimenti { get; set; }

		[DataMember]
		public List<DocumentoInterventoDto> Documenti { get; set; }

		[DataMember]
		public List<NormativaDto> Normative { get; set; }

		[DataMember]
		public List<FaseAttuativaDto> FasiAttuative { get; set; }

		[DataMember]
		public string Note { get; set; }

		[DataMember]
		public string ScCodice { get; set; }

		[DataMember]
		public bool HaNodiFiglio { get; set; }

		[DataMember]
		public bool HaNote { get; set; }

		[DataMember]
		public bool PubblicaAreaRiservata { get; set; }
	
		public InterventoDto()
		{
			SchedeDinamiche = new List<SchedaDinamicaDto>();
			Oneri = new List<OnereDto>();
			Documenti = new List<DocumentoInterventoDto>();
			Normative = new List<NormativaDto>();
			FasiAttuative = new List<FaseAttuativaDto>();
		}
	}
}
