using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public class DocumentoDomanda
	{
		public enum TipoFormatoConversione
		{
			DOC,
			OPN,
			PDF,
			PDFC,
			RTF
		}

		public class AllegatoUtente : AllegatoDellaDomanda
		{
			DocumentoDomanda _parent;

			public AllegatoUtente(DocumentoDomanda parent, int codiceOggetto, string nomeFile, string md5, string note, bool firmatoDigitalmente):
				base( codiceOggetto , nomeFile , firmatoDigitalmente , note )
			{
				this._parent = parent;
			}

			public static AllegatoUtente FromAllegatiRow(DocumentoDomanda parent,PresentazioneIstanzaDbV2.AllegatiRow row)
			{
				return new AllegatoUtente(parent, row.CodiceOggetto, row.NomeFile, row.Md5, row.Note, row.FirmatoDigitalmente);
			}

			public DocumentiType ToDocumentiType(IAliasResolver aliasResolver)
			{
				return new DocumentiType
				{
					id = String.Empty,//this._parent.IdRiferimentoBackoffice.ToString(),
					documento = this._parent.Descrizione,
					tipoDocumento = this._parent.RiepilogoDomanda ? "RiepilogoDomanda" : "Altro",
					allegati = new AllegatiType
					{
						id = $"{aliasResolver.AliasComune}@{this.CodiceOggetto.ToString()}",
						allegato = this.NomeFile
					}
				};
			}
		}
		
		public enum ProvenienzaEnum
		{
			Endoprocedimento,
			Intervento,
			Libero
		}

		public class CategoriaAllegato
		{
			public int Codice { get; private set; }
			public string Descrizione{ get; private set; }

			public CategoriaAllegato(int codice , string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}

			public class EqualityComparer : IEqualityComparer<CategoriaAllegato>
			{
				public bool Equals(CategoriaAllegato x, CategoriaAllegato y)
				{
					return x.Codice.Equals(y.Codice);
				}

				public int GetHashCode(CategoriaAllegato obj)
				{
					return obj.Codice.GetHashCode();
				}
			}
		}

		public int Id { get; private set; }
		public string Descrizione { get; private set; }
		public AllegatoUtente AllegatoDellUtente { get; private set; }
		public CategoriaAllegato Categoria { get; private set; }
		public bool Richiesto { get; private set; }
		public bool RiepilogoDomanda { get; private set; }
		public bool RichiedeFirmaDigitale { get; private set; }
		public int Ordine { get; private set; }
		public string Note { get; private set; }
		public string LinkInformazioni { get; private set; }
		public ProvenienzaEnum Provenienza { get; private set; }
		internal int? IdRiferimentoBackoffice { get; private set; }

		private string _formatiConversioneSupportati;
		private string _nomeFileModello;
		public int?	CodiceOggettoModello { get; private set; }
		public int? CodiceEndoOIntervento { get; private set; }
		protected string[] FormatiConversione { get { return this._formatiConversioneSupportati.ToUpper().Split(','); } }

		public bool FromDatiDinamici { get; private set; }

		public static DocumentoDomanda FromOggettiRow(PresentazioneIstanzaDbV2.OGGETTIRow oggettiRow, PresentazioneIstanzaDbV2.AllegatiRow allegatiRow)
		{
			var d = new DocumentoDomanda()
			{
				Id						= oggettiRow.ID,
				Descrizione				= oggettiRow.DESCRIZIONE,
				Richiesto				= oggettiRow.IsRICHIESTONull() ? false : oggettiRow.RICHIESTO,
				RichiedeFirmaDigitale	= oggettiRow.IsRichiedeFirmaNull() ? false : oggettiRow.RichiedeFirma,
				Ordine					= oggettiRow.IsORDINENull() ? 9999 : oggettiRow.ORDINE,

				_formatiConversioneSupportati = oggettiRow.TipoDownload,
				_nomeFileModello		= oggettiRow.NomeFileModello,
				CodiceOggettoModello	= oggettiRow.IsIDMODELLONull() ? (int?)null : Convert.ToInt32(oggettiRow.IDMODELLO),

				RiepilogoDomanda		= oggettiRow.IsRIEPILOGODOMANDANull() ? false : oggettiRow.RIEPILOGODOMANDA == 1,
				IdRiferimentoBackoffice = oggettiRow.IsCODICEDOCUMENTONull() ? (int?)null : oggettiRow.CODICEDOCUMENTO,
				LinkInformazioni		= oggettiRow.LinkInformazioni,

				Provenienza				= DecodificaProvenienza(oggettiRow.TIPODOCUMENTO),
				CodiceEndoOIntervento	= oggettiRow.IsCodiceEndoOInterventoNull() ? (int?)null : (int)oggettiRow.CodiceEndoOIntervento,
				Note					= oggettiRow.Note,
				FromDatiDinamici		= oggettiRow.FromDatiDinamici == "1" 
			};

			if (allegatiRow != null)
			{
				d.AllegatoDellUtente = AllegatoUtente.FromAllegatiRow(d,allegatiRow);
			}

			if (!String.IsNullOrEmpty(oggettiRow.CodiceCategoria))
			{
				var codCategoria = Convert.ToInt32(oggettiRow.CodiceCategoria);
				var desCategoria = oggettiRow.Categoria;
				d.Categoria = new CategoriaAllegato(codCategoria , desCategoria);
			}

			return d;
		}
		/*
		internal static DocumentoDomanda Test(
		
			
		int id , string descrizione , AllegatoUtente allegatoDellUtente { get; private set; }
		public CategoriaAllegato Categoria { get; private set; }
		public bool Richiesto { get; private set; }
		public bool RiepilogoDomanda { get; private set; }
		public bool RichiedeFirmaDigitale { get; private set; }
		public int Ordine { get; private set; }
		public string Note { get; private set; }
		public string LinkInformazioni { get; private set; }
		public ProvenienzaEnum Provenienza { get; private set; }
		internal int? IdRiferimentoBackoffice { get; private set; }

		private string _formatiConversioneSupportati;
		private string _nomeFileModello;
		public int?	CodiceOggettoModello { get; private set; }
		public int? CodiceEndoOIntervento { get; private set; }
			)
		*/

		internal DocumentoDomanda()
		{
		}
		
		private static ProvenienzaEnum DecodificaProvenienza(string tipoDocumento)
		{
			if (tipoDocumento == GestioneDocumentiConstants.ProvenienzaDocumento.Endo)
				return ProvenienzaEnum.Endoprocedimento;

			if (tipoDocumento == GestioneDocumentiConstants.ProvenienzaDocumento.Intervento)
				return ProvenienzaEnum.Intervento;

			return ProvenienzaEnum.Libero;
		}

		public bool SupportaPrecompilazione()
		{
			if (!HaModelloScaricabile())
				return false;

			if (Path.GetExtension(this._nomeFileModello).ToUpper() != ".XSL" &&
					Path.GetExtension(this._nomeFileModello).ToUpper() != ".RTF")
				return false;

			return true;
		}

		private bool HaModelloScaricabile()
		{
			return (this.CodiceOggettoModello.HasValue && this.CodiceOggettoModello.Value > 0);
		}

		public bool SupportaPrecompilazioneNelFormato(TipoFormatoConversione formato)
		{
			if (!HaModelloScaricabile())
				return false;

			if(!FormatiConversione.Contains(formato.ToString()))
				return false;

			if (formato == TipoFormatoConversione.PDFC)
				return Path.GetExtension(this._nomeFileModello).ToUpperInvariant() == ".PDF";
				

			if (!this.SupportaPrecompilazione())
				return false;

			return true;
		}

	}
}
