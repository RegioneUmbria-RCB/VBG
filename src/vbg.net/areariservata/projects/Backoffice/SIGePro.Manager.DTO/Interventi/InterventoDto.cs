using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Normative;
using Init.SIGePro.Manager.DTO.Procedure;

namespace Init.SIGePro.Manager.DTO.Interventi
{
	public class InterventoDto : BaseDto<int,string>
	{
		public List<SchedaDinamicaDto> SchedeDinamiche { get; set; }
		public List<OnereDto> Oneri { get; set; }
		public List<FamigliaEndoprocedimentoDto> Endoprocedimenti { get; set; }
		public List<DocumentoInterventoDto> Documenti { get; set; }
		public List<NormativaDto> Normative { get; set; }
		public List<FaseAttuativaDto> FasiAttuative { get; set; }
		
		public string Note { get; set; }
		public string ScCodice { get; set; }
		
		public bool HaNodiFiglio { get; set; }
		public bool HaNote { get; set; }
		public bool PubblicaAreaRiservata { get; set; }
		// public bool NodoCategoriaCart { get; set; }

		

		public InterventoDto()
		{
			SchedeDinamiche = new List<SchedaDinamicaDto>();
			Oneri = new List<OnereDto>();
			Endoprocedimenti = new List<FamigliaEndoprocedimentoDto>();
			Documenti = new List<DocumentoInterventoDto>();
			Normative = new List<NormativaDto>();
			FasiAttuative = new List<FaseAttuativaDto>();
		}
	}
}
