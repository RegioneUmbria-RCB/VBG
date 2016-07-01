using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public class ListaDocumentiEndo
	{
		IEnumerable<DocumentoDomanda> _documenti;

		public ListaDocumentiEndo(IEnumerable<DocumentoDomanda> documenti)
		{
			this._documenti = documenti;
		}

		public IEnumerable<DocumentoDomanda> GetByIdEndo(int idEndo)
		{
			return this._documenti.Where(x => x.CodiceEndoOIntervento.HasValue && x.CodiceEndoOIntervento == idEndo);
		}

		public DocumentoDomanda GetByIdEndoIdAllegato(int idEndo, int idAllegato)
		{
			return this.GetByIdEndo(idEndo)
						.Where(x => x.IdRiferimentoBackoffice.HasValue && x.IdRiferimentoBackoffice.Value == idAllegato)
						.FirstOrDefault();
		}

		public IEnumerable<DocumentoDomanda> WhereIdEndoNotIn(IEnumerable<int> listaIdEndo)
		{
			return this._documenti.Where(x => !listaIdEndo.Contains(x.CodiceEndoOIntervento.Value));
		}

		public ListaDocumentiEndo GetDocumentiDiSistema()
		{
			return new ListaDocumentiEndo(this._documenti.Where(x => x.IdRiferimentoBackoffice.HasValue));
		}

		public IEnumerable<DocumentoDomanda> WhereIdDocumentoNotIn(IEnumerable<int> listaIdDocumento)
		{
			return this._documenti.Where(x => !listaIdDocumento.Contains(x.IdRiferimentoBackoffice.Value));
		}

		public IEnumerable<DocumentoDomanda> GetDocumentiAllegatiDallUtente()
		{
			return this._documenti.Where(x => !x.IdRiferimentoBackoffice.HasValue);
		}

		public IEnumerable<DocumentoDomanda> GetDocumentiAllegatiDaUtenteSenzaOggetto()
		{
			return this.GetDocumentiAllegatiDallUtente().Where(x => x.AllegatoDellUtente == null);
		}

		internal DocumentoDomanda GetById(int idDocumento)
		{
			return this._documenti.Where(x => x.Id == idDocumento).FirstOrDefault();
		}

		public IEnumerable<DocumentoDomanda> Documenti
		{
			get { return this._documenti; }
		}

		public IEnumerable<string> GetNomiDocumentiRichiestiENonPresenti()
		{
			return this._documenti.Where(x => x.Richiesto && x.AllegatoDellUtente == null).Select(x => x.Descrizione);
		}
	}
}
