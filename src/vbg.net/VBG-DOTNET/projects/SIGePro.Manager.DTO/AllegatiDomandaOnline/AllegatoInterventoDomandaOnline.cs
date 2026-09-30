// -----------------------------------------------------------------------
// <copyright file="AllegatoInterventoDomandaOnline.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.DTO.AllegatiDomandaOnline
{
    using System;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class AllegatoInterventoDomandaOnlineDto : BaseDto<int, string>
	{
		public class CategoriaAllegatoIntervento : BaseDto<int,string>
		{

		}
		public string LinkInformazioni { get; set; }
		public int? CodiceOggettoModello { get; set; }
		public bool Richiesto { get; set; }
		public bool RichiedeFirma { get; set; }
		public bool RiepilogoDomanda { get; set; }
		public string TipoDownload { get; set; }
		public int? Ordine { get; set; }
		public string NomeFileModello { get; set; }
		public CategoriaAllegatoIntervento Categoria { get; set; }
		public string Note { get; set; }
        public int? DimensioneMassima { get; set; }
        public string EstensioniAmmesse { get; set; }
	}
}
