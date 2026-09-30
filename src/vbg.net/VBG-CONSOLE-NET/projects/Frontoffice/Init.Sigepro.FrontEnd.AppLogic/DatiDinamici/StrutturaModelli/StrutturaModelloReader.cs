using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli
{
	internal class StrutturaModelloReader : IStrutturaModelloReader
	{
		internal class StrutturaModelloDinamico : IStrutturaModello
		{
			public int IdModello
			{
				get { return this._modello.Id.Value; }
			}

			public string CodiceScheda
			{
				get { return this._modello.CodiceScheda; }
			}

			public string Descrizione
			{
				get { return this._modello.Descrizione; }
			}

			public IEnumerable<ICampoDinamico> Campi
			{
				get;
				private set;
			}

			IDyn2Modello _modello;

			public StrutturaModelloDinamico(IDyn2Modello modello, IEnumerable<IDyn2Campo> campi)
			{
				this._modello = modello;
				this.Campi = campi.Select(x => new Campo(x));
			}
		}

		internal class Campo : ICampoDinamico
		{
			public int Id
			{
				get { return this._campo.Id.Value; }
			}

			public string Etichetta
			{
				get { return this._campo.Etichetta; }
			}

			public string NomeCampo
			{
				get { return this._campo.Nomecampo; }
			}

			IDyn2Campo _campo;

			public Campo(IDyn2Campo campo)
			{
				this._campo = campo;
			}
		}

		IDatiDinamiciRepository _repository;

		public StrutturaModelloReader(IDatiDinamiciRepository repository)
		{
			this._repository = repository;
		}

		public IStrutturaModello Read(int idModello)
		{
			var strutturaModello = this._repository.GetCacheModelloDinamico(idModello);

			return new StrutturaModelloDinamico(strutturaModello.Modello, strutturaModello.ListaCampiDinamici.Values);
		}
	}
}
