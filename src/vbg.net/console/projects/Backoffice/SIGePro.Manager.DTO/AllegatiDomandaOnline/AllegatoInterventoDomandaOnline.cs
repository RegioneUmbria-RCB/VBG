// -----------------------------------------------------------------------
// <copyright file="AllegatoInterventoDomandaOnline.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.DTO.AllegatiDomandaOnline
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Data;

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

		public static AllegatoInterventoDomandaOnlineDto FromAllegatoIntervento(AlberoProcDocumenti documento, Func<int,string> risolviNomeFile)
		{
			var codiceOggetto = String.IsNullOrEmpty(documento.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(documento.CODICEOGGETTO);

			var allegato = new AllegatoInterventoDomandaOnlineDto
			{
				Codice = Convert.ToInt32(documento.SM_ID),
				Descrizione = documento.DESCRIZIONE,
				LinkInformazioni = String.Empty,
				CodiceOggettoModello = codiceOggetto,
				Richiesto = documento.RICHIESTO == "1",
				RichiedeFirma = documento.FO_RICHIEDEFIRMA.GetValueOrDefault(0) == 1,
				TipoDownload = documento.FO_TIPODOWNLOAD,
				Ordine = documento.ORDINE.GetValueOrDefault(0),
				NomeFileModello = codiceOggetto.HasValue ? risolviNomeFile(codiceOggetto.Value) : String.Empty,
				Categoria = null,
				RiepilogoDomanda = documento.FLG_DOMANDAFO.GetValueOrDefault(0) == 1,
				Note = documento.NoteFrontend
			};

			if (documento.Categoria != null && documento.Categoria.Id.HasValue)
			{
				allegato.Categoria = new CategoriaAllegatoIntervento
				{
					Codice = documento.Categoria.Id.Value,
					Descrizione = documento.Categoria.Descrizione
				};
			}

			return allegato;
		}

		public static AllegatoInterventoDomandaOnlineDto FromAllegatoProcedura(TipiProcedureDocumenti documento, Func<int, string> risolviNomeFile)
		{
			var codiceOggetto = String.IsNullOrEmpty(documento.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(documento.CODICEOGGETTO);

			var allegato = new AllegatoInterventoDomandaOnlineDto
			{
				Codice = Convert.ToInt32(documento.TP_ID) * -1,
				Descrizione = documento.DESCRIZIONE,
				LinkInformazioni = String.Empty,
				CodiceOggettoModello = codiceOggetto,
				Richiesto = documento.Richiesto.GetValueOrDefault(0) == 1,
				RichiedeFirma = documento.FoRichiedefirma.GetValueOrDefault(0) == 1,
				TipoDownload = documento.FoTipodownload,
				Ordine = 0,
				NomeFileModello = codiceOggetto.HasValue ? risolviNomeFile(codiceOggetto.Value) : String.Empty,
				Categoria = null,
				RiepilogoDomanda = false,
				Note = documento.NoteFrontend
			};
			
			return allegato;
		}
	}
}
