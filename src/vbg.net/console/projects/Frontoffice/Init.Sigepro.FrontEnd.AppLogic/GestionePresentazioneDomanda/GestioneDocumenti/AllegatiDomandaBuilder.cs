using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using CuttingEdge.Conditions;


	public class AllegatiInterventoBuilder : AllegatiDomandaBuilder
	{
		public static AllegatiDomandaBuilder BeginBuild(PresentazioneIstanzaDbV2 database, int? codiceIntervento, int? codiceDocumento)
		{
			return new AllegatiInterventoBuilder(database, codiceIntervento, codiceDocumento);
		}

		protected AllegatiInterventoBuilder(PresentazioneIstanzaDbV2 database, int? codiceIntervento, int? codiceDocumento) :
			base(database, GestioneDocumentiConstants.ProvenienzaDocumento.Intervento, codiceIntervento, codiceDocumento)
		{

		}
	}


	public class AllegatiEndoBuilder : AllegatiDomandaBuilder
	{
		public static AllegatiDomandaBuilder BeginBuild(PresentazioneIstanzaDbV2 database, int codiceEndo, int? codiceDocumento)
		{
			return new AllegatiEndoBuilder(database, codiceEndo, codiceDocumento);
		}

		protected AllegatiEndoBuilder(PresentazioneIstanzaDbV2 database, int codiceEndo, int? codiceDocumento) :
			base(database, GestioneDocumentiConstants.ProvenienzaDocumento.Endo, codiceEndo, codiceDocumento)
		{

		}
	}

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class AllegatiDomandaBuilder
	{
		PresentazioneIstanzaDbV2 _database;
		PresentazioneIstanzaDbV2.OGGETTIDataTable _dataTable;
		PresentazioneIstanzaDbV2.OGGETTIRow _dataRow = null;
		bool _isNewRow = false;

		protected AllegatiDomandaBuilder(PresentazioneIstanzaDbV2 database, string tipoDocumento, int? codiceInterventoOEndo, int? codiceDocumento)
		{
			this._database = database;
			this._dataTable = database.OGGETTI;

			if (codiceDocumento.HasValue && codiceInterventoOEndo.HasValue)
			{
				this._dataRow = this._dataTable.Where(r => !r.IsCodiceEndoOInterventoNull() &&
													  !r.IsCODICEDOCUMENTONull() &&
													  !r.IsTIPODOCUMENTONull() &&
													  r.TIPODOCUMENTO == tipoDocumento &&
													  r.CodiceEndoOIntervento == codiceInterventoOEndo &&
													  r.CODICEDOCUMENTO == codiceDocumento)
											   .FirstOrDefault();
			}

			this._isNewRow = this._dataRow == null;

			if (this._isNewRow)
			{

				this._dataRow = this._dataTable.NewOGGETTIRow();

				object newId = this._dataTable.Compute("max(ID)", null);

				this._dataRow.ID = (newId == DBNull.Value) ? 1 : Convert.ToInt32(newId) + 1;
			}

			WithTipoDocumento(tipoDocumento);
			WithCodiceDocumento(codiceDocumento);
			WithCodiceEndoOintervento(codiceInterventoOEndo);
		}

		public AllegatiDomandaBuilder WithIdModello(int? idModello)
		{
			if (idModello.HasValue)
				this._dataRow.IDMODELLO = idModello.Value;
			else
				this._dataRow.SetIDMODELLONull();

			return this;
		}

		public AllegatiDomandaBuilder WithDescrizione(string descrizione)
		{
			this._dataRow.DESCRIZIONE = String.IsNullOrEmpty(descrizione) ? String.Empty : descrizione;
			return this;
		}

        public AllegatiDomandaBuilder WithAllegatoLibero(bool allegatoLibero)
        {
            this._dataRow.AllegatoLibero = allegatoLibero;

            return this;
        }

		public AllegatiDomandaBuilder WithCategoria(int? codice, string descrizione)
		{
			if (codice.HasValue)
			{
				this._dataRow.CodiceCategoria = codice.ToString();
				this._dataRow.Categoria = descrizione;
			}
			else
			{
				this._dataRow.CodiceCategoria = String.Empty;
				this._dataRow.Categoria = String.Empty;
			}

			return this;
		}

		public AllegatiDomandaBuilder WithLinkInformazioni(string linkInformazioni)
		{
			this._dataRow.LinkInformazioni = String.IsNullOrEmpty(linkInformazioni) ? String.Empty : linkInformazioni;
			return this;
		}

		
		public AllegatiDomandaBuilder WithOggetto(int codiceOggetto, string nomeFile , bool isFirmatoDigitalmente)
		{
			var allegatiRow = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

			this._dataRow.IdAllegato = allegatiRow.Id;

			return this;
		}
		
		private AllegatiDomandaBuilder WithCodiceDocumento(int? codiceDocumento)
		{
			if (codiceDocumento.HasValue)
				this._dataRow.CODICEDOCUMENTO = codiceDocumento.Value;
			else
				this._dataRow.SetCODICEDOCUMENTONull();

			return this;
		}

		private AllegatiDomandaBuilder WithTipoDocumento(string tipoDocumento)
		{
			Condition.Requires(tipoDocumento, "tipoDocumento").IsNotNullOrEmpty();

			this._dataRow.TIPODOCUMENTO = tipoDocumento;
			return this;
		}

		public AllegatiDomandaBuilder WithFlagRichiesto(bool richiesto)
		{
			this._dataRow.RICHIESTO = richiesto;
			return this;
		}

		public AllegatiDomandaBuilder WithFlagRiepilogoDomanda(bool riepilogoDomanda)
		{
			this._dataRow.RIEPILOGODOMANDA = riepilogoDomanda ? 1 : 0;
			return this;
		}

		public AllegatiDomandaBuilder WithFlagRichiedeFirma(bool richiedeFirma)
		{
			this._dataRow.RichiedeFirma = richiedeFirma;
			return this;
		}

		public AllegatiDomandaBuilder WithTipoDownload(string tipoDownload)
		{
			this._dataRow.TipoDownload = tipoDownload;
			return this;
		}

		private AllegatiDomandaBuilder WithCodiceEndoOintervento(int? codiceEndoOIntervento)
		{
			if (codiceEndoOIntervento.HasValue)
				this._dataRow.CodiceEndoOIntervento = codiceEndoOIntervento.Value;
			else
				this._dataRow.SetCodiceEndoOInterventoNull();

			return this;
		}

		public AllegatiDomandaBuilder WithOrdine(int ordine)
		{
			this._dataRow.ORDINE = ordine;
			return this;
		}

		public AllegatiDomandaBuilder WithNomeFileModello(string nomeFileModello)
		{
			this._dataRow.NomeFileModello = nomeFileModello;
			return this;
		}

		public AllegatiDomandaBuilder WithNote(string note)
		{
			this._dataRow.Note = note;
			return this;
		}

		public void Build()
		{
			if (this._isNewRow)
			{
				this._dataTable.AddOGGETTIRow(this._dataRow);
			}
		}

		internal AllegatiDomandaBuilder FromDatiDinamici()
		{
			this._dataRow.FromDatiDinamici = "1";

			return this;
		}
	}
}
