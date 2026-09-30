using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public class DocumentiReadInterface : IDocumentiReadInterface
	{
		PresentazioneIstanzaDbV2 _database;
		List<DocumentoDomanda> _documenti;
		ListaDocumentiEndo _documentiEndo;
		ListaDocumentiIntervento _documentiIntervento;
		IEnumerable<DocumentoDomanda> _documentiLiberi;

		public DocumentiReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			PreparaDocumentiDomanda();
		}

		private void PreparaDocumentiDomanda()
		{
			this._documenti = new List<DocumentoDomanda>();

			foreach (var oggettiRow in this._database.OGGETTI.Cast<PresentazioneIstanzaDbV2.OGGETTIRow>())
			{
				PresentazioneIstanzaDbV2.AllegatiRow allegatiRow = null;

				if (!oggettiRow.IsIdAllegatoNull())
					allegatiRow = this._database.Allegati.FindById(oggettiRow.IdAllegato);

				this._documenti.Add(DocumentoDomanda.FromOggettiRow(oggettiRow, allegatiRow));
			}

			this._documentiEndo = new ListaDocumentiEndo( this._documenti.Where(x => x.Provenienza == DocumentoDomanda.ProvenienzaEnum.Endoprocedimento) );
			this._documentiIntervento = new ListaDocumentiIntervento(this._documenti.Where(x => x.Provenienza == DocumentoDomanda.ProvenienzaEnum.Intervento));
			this._documentiLiberi = this._documenti.Where(x => x.Provenienza == DocumentoDomanda.ProvenienzaEnum.Libero);
		}

		public ListaDocumentiEndo Endo
		{
			get
			{
				return this._documentiEndo;
			}
		}

		public ListaDocumentiIntervento Intervento
		{
			get
			{
				return this._documentiIntervento;
			}
		}

		public int Count()
		{
			return this._documenti.Count;
		}
	}
}
