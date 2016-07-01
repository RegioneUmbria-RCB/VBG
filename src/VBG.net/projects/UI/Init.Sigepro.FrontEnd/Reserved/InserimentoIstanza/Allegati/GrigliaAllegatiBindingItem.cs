namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using System.Configuration;

	public class GrigliaAllegatiBindingItem
	{
		public int Id { get; set; }

		public string Descrizione { get; set; }

		public bool Richiesto { get; set; }

		public bool RichiedeFirmaDigitale { get; set; }

		public bool HaLinkPdfCompilabile
		{
			get
			{
				return !string.IsNullOrEmpty(this.LinkPdfCompilabile);
			}
		}

		public string LinkPdfCompilabile { get; set; }

		public bool HaLinkPdf
		{
			get
			{
				return !string.IsNullOrEmpty(this.LinkPdf);
			}
		}

		public string LinkPdf { get; set; }

		public bool HaLinkRtf 
		{ 
			get 
			{
				return !string.IsNullOrEmpty(this.LinkRtf); 
			} 
		}

		public string LinkRtf { get; set; }

		public bool HaLinkDoc 
		{ 
			get 
			{
				return !string.IsNullOrEmpty(this.LinkDoc); 
			} 
		}

		public string LinkDoc { get; set; }

		public bool HaLinkOO 
		{ 
			get 
			{
				return !string.IsNullOrEmpty(this.LinkOO); 
			} 
		}

		public string LinkOO { get; set; }

		public bool HaLinkDownloadSenzaPrecompilazione 
		{ 
			get 
			{
				return !string.IsNullOrEmpty(this.LinkDownloadSenzaPrecompilazione); 
			} 
		}

		public bool MostraBottoneAllega
		{
			get
			{
				if (SoloFirma)
					return false;

				return !HaFile;
			}
		}

		public bool MostraBottoneRimuovi
		{
			get
			{
				if (SoloFirma)
					return false;

				return HaFile;
			}
		}

		public string LinkDownloadSenzaPrecompilazione { get; set; }

		public bool HaFile 
		{ 
			get 
			{
				return this.CodiceOggetto.HasValue; 
			} 
		}

		public string LinkDownloadFile { get; set; }

		public string NomeFile { get; set; }

		public int? CodiceOggetto { get; set; }

		public int Ordine { get; set; }

		public bool HaNote 
		{ 
			get 
			{ 
				return !string.IsNullOrEmpty(this.Note); 
			} 
		}

		public string Note { get; set; }

		public bool MostraBottoneFirma 
		{ 
			get 
			{
				if (SoloFirma)
					return true;

				return this.RichiedeFirmaDigitale && this.CodiceOggetto.HasValue && !this.FirmatoDigitalmente; 
			} 
		}

		public bool MostraAvvertimentoFirma
		{
			get
			{
				return this.RichiedeFirmaDigitale && this.CodiceOggetto.HasValue && !this.FirmatoDigitalmente; 
			}
		}

		public bool FirmatoDigitalmente { get; set; }

		public bool MostraBottonecompila 
		{ 
			get 
			{
				if (SoloFirma)
					return false;

				//var setting = ConfigurationManager.AppSettings["attiva.compilazione.documenti.online"];

				//if (String.IsNullOrEmpty(setting) || setting.ToUpper() != "TRUE")
				//    return false;

				return !this.CodiceOggetto.HasValue && (this.HaLinkPdfCompilabile || this.HaLinkDoc || this.HaLinkRtf); 
			} 
		}

		public bool SoloFirma { get;set; }
	}
}