
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoDomandeOggettiMgr
    {
		public List<FoDomandeOggetti> GetByCodiceDomanda(string idComune, int idDomanda)
		{
			FoDomandeOggetti domOgg = new FoDomandeOggetti();
			domOgg.Idcomune = idComune;
			domOgg.Iddomanda = idDomanda;

			return GetList(domOgg);
		}


		public int SalvaAllegatoDomanda(string idComune, int idDomanda, int codiceOggetto)
		{
			try
			{
				db.BeginTransaction();

				var domOgg = Insert(new FoDomandeOggetti { 
					Idcomune = idComune,
					Iddomanda = idDomanda,
					Codiceoggetto = codiceOggetto
				});

				db.CommitTransaction();

				return domOgg.Codiceoggetto.GetValueOrDefault(-1);
			}
			catch (Exception ex)
			{
				db.RollbackTransaction();

				throw;
			}

		}


		public void EliminaAllegatoDomanda(string idComune, int idDomanda, int codiceOggetto)
		{
			FoDomandeOggetti domOgg = GetById(idComune, idDomanda, codiceOggetto);

			if (domOgg == null)
				return;

			try
			{
				//db.BeginTransaction();

				Delete(domOgg);

				//db.CommitTransaction();
			}
			catch (Exception ex)
			{
				//db.RollbackTransaction();

				throw;
			}
		}

		public void Delete(FoDomandeOggetti cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			EliminaOggettoDaDb(cls);
		}

		private void EliminaOggettoDaDb(FoDomandeOggetti cls)
		{
			// Elimino l'oggetto binario della domanda
			OggettiMgr oggMgr = new OggettiMgr(db);

			oggMgr.EliminaOggetto(cls.Idcomune, cls.Codiceoggetto.GetValueOrDefault(-1));
		}


		private void EffettuaCancellazioneACascata(FoDomandeOggetti cls)
		{
		}
	}
}
				