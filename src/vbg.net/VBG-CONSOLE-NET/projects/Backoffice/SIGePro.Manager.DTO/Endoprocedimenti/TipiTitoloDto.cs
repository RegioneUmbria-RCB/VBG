using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
	public class TipiTitoloDtoFlags
	{
		[XmlElement(Order=0)]
		public bool MostraData { get; set; }
		[XmlElement(Order = 1)]
		public bool MostraNumero { get; set; }
		[XmlElement(Order = 2)]
		public bool MostraRilasciatoDa { get; set; }
		[XmlElement(Order = 3)]
		public bool RichiedeAllegato { get; set; }
		[XmlElement(Order = 4)]
		public bool VerificaFirmaAllegato { get; set; }
		[XmlElement(Order = 5)]
		public bool AllegatoObbligatorio { get; set; }
	}

	public class TipiTitoloDto: BaseDto<int, string>
	{
		public TipiTitoloDtoFlags Flags { get; set; }

		public TipiTitoloDto()
		{
			this.Flags = new TipiTitoloDtoFlags();
		}
	}

	public static class InventarioProcTipiTitoloExtensions
	{
		public static TipiTitoloDto ToTipiTitoloDto(this InventarioProcTipiTitolo el)
		{
			if(el == null)
				return null;

			return new TipiTitoloDto{
				Codice = el.Id.Value,
				Descrizione = el.Tipotitolo,
				Flags = new TipiTitoloDtoFlags
				{
					MostraData = el.FlgMostraData.GetValueOrDefault(0) == 1,
					MostraNumero = el.FlgMostraNumero.GetValueOrDefault(0) == 1,
					MostraRilasciatoDa = el.FlgMostraRilasciatoDa.GetValueOrDefault(0) == 1,
					RichiedeAllegato = el.FlgRichiedeAllegato.GetValueOrDefault(0) == 1,
					VerificaFirmaAllegato = el.FlgVerificaFirmaAllegato.GetValueOrDefault(0) == 1,
					AllegatoObbligatorio = el.FlgAllObbligatorio.GetValueOrDefault(0) == 1
				}
			};
		}
	}
}
