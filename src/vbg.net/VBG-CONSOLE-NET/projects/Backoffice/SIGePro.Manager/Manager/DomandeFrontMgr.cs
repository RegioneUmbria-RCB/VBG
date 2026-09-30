
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
    public partial class DomandeFrontMgr
    {
		public List<DomandeFront> GetByIdArea(string idComune, int dfaId)
		{
			DomandeFront filtro = new DomandeFront();
			filtro.Idcomune = idComune;
			filtro.FkDfaId = dfaId;

			return GetList(filtro);
		}

		private void EffettuaCancellazioneACascata(DomandeFront cls)
		{
			DomandeFrontEndoMgr domEndoMgr = new DomandeFrontEndoMgr( db );

			List<DomandeFrontEndo> domandeEndo = domEndoMgr.GetByIdDomanda(cls.Idcomune, cls.Codicedomanda.Value);

			bool openTransaction = db.Transaction == null;

			if (openTransaction)
				db.BeginTransaction();

			try
			{
				foreach (DomandeFrontEndo domEndo in domandeEndo)
					domEndoMgr.Delete(domEndo);
				
				if(openTransaction)
					db.CommitTransaction();
			}
			catch (Exception ex)
			{
				if (openTransaction)
					db.RollbackTransaction();

				throw;
			}
		}
	}
}
				