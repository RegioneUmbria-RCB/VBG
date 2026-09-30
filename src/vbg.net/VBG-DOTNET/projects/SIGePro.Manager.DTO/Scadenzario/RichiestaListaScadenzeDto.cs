using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
	public enum FoTipiSoggettiEsterniEnum
	{
		FrontofficeRichiedenti = 1,
		FrontofficeAmministrazioni = 2
	}

	[DataContract]
    public class RichiestaListaScadenzeDto
    {

		
		[DataMember]
		public string CodEnte { get; set; }

		[DataMember]
		public string CodSportello { get; set; }

		[DataMember]
		public string IdPratica { get; set; }

		[DataMember]
		public string NumeroPratica { get; set; }

		[DataMember]
		public string NumeroProtocollo { get; set; }

		[DataMember]
		public DatiAmministrazioneDto DatiAmministrazione { get; set; }

		[DataMember]
		public string Stato { get; set; }

		[DataMember]
		public string PartitaIvaSoggetto { get; set; }
		
		[DataMember]
		public string CodiceFiscaleSoggetto { get; set; }

		[DataMember]
		public FoTipiSoggettiEsterniEnum FiltroFoSoggettiEsterni { get; set; } = FoTipiSoggettiEsterniEnum.FrontofficeRichiedenti;

	}
}
