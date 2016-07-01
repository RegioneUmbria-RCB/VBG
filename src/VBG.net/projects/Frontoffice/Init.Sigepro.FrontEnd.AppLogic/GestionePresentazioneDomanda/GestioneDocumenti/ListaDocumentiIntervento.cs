using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti
{
	public class ListaDocumentiIntervento
	{
		IEnumerable<DocumentoDomanda> _documenti;

		public ListaDocumentiIntervento(IEnumerable<DocumentoDomanda> documenti)
		{
			this._documenti = documenti;
		}

		public DocumentoDomanda GetRiepilogoDomanda()
		{
			return this._documenti.Where(x => x.RiepilogoDomanda).FirstOrDefault();
		}

		public IEnumerable<DocumentoDomanda> GetByIdCategoriaNoDatiDinamici(int idCategoria)
		{
			return this._documenti.Where(x => x.Categoria != null && x.Categoria.Codice == idCategoria && !x.FromDatiDinamici);
		}

		public IEnumerable<DocumentoDomanda> GetAllegatiPresenti()
		{
			return this._documenti.Where(x => x.AllegatoDellUtente != null);
		}

		public IEnumerable<DocumentoDomanda> WhereCodiceDocumentoNotIn(IEnumerable<int> listaIdDocumento)
		{
			return this._documenti.Where(x => x.IdRiferimentoBackoffice.HasValue && !listaIdDocumento.Contains(x.IdRiferimentoBackoffice.Value));
		}

		public IEnumerable<string> GetNomiDocumentiRichiestiENonPresenti()
		{
			return this._documenti.Where(x => x.Richiesto && x.AllegatoDellUtente == null && !x.RiepilogoDomanda).Select(x => x.Descrizione);
		}

		public IEnumerable<DocumentoDomanda.CategoriaAllegato> GetListaCategorie()
		{
			return this._documenti.Where(x => !x.RiepilogoDomanda && x.Categoria != null)
								   .Select(x => x.Categoria)
								   .Distinct(new DocumentoDomanda.CategoriaAllegato.EqualityComparer())
								   .OrderBy(x => x.Descrizione);
		}

		internal DocumentoDomanda GetById(int idDocumento)
		{
			return this._documenti.Where(x => x.Id == idDocumento).FirstOrDefault();
		}

		public IEnumerable<DocumentoDomanda> Documenti
		{
			get { return this._documenti; }
		}
	}
}
