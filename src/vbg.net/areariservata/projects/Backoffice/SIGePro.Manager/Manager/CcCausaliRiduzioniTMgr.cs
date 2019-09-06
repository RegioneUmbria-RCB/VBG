
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
    public partial class CcCausaliRiduzioniTMgr
    {
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<CcCausaliRiduzioniT> Find(string token, string software, string descrizione, string sortExpression)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			CcCausaliRiduzioniTMgr mgr = new CcCausaliRiduzioniTMgr(authInfo.CreateDatabase());

			CcCausaliRiduzioniT filtro = new CcCausaliRiduzioniT();
			CcCausaliRiduzioniT filtroCompare = new CcCausaliRiduzioniT();

			filtro.Software = software;
			filtro.Idcomune = authInfo.IdComune;

			if (!String.IsNullOrEmpty(descrizione))
				filtro.Descrizione = descrizione;

			filtroCompare.Descrizione = "like";

			List<CcCausaliRiduzioniT> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CcCausaliRiduzioniT>();

			ListSortManager<CcCausaliRiduzioniT>.Sort(list, sortExpression);

			return list;
		}

		public List<CcCausaliRiduzioniT> GetListByIdcomuneSoftware(string idComune, string software)
		{
			CcCausaliRiduzioniT filtro = new CcCausaliRiduzioniT();
			filtro.Idcomune = idComune;
			filtro.Software = software;
			filtro.OrderBy = "Descrizione asc";

			return GetList(filtro);
		}

		private void EffettuaCancellazioneACascata(CcCausaliRiduzioniT cls)
		{
			bool iniziataTransazione = false;

			if (db.Transaction == null)
			{
				iniziataTransazione = true;
				db.BeginTransaction();
			}

			try
			{
				CcCausaliRiduzioniRMgr rMgr = new CcCausaliRiduzioniRMgr(db);
				List<CcCausaliRiduzioniR> causali = rMgr.GetListByIdCausaleT(cls.Idcomune, cls.Id.Value);

				foreach (CcCausaliRiduzioniR r in causali)
					rMgr.Delete(r);

				if (iniziataTransazione)
					db.CommitTransaction();
			}
			catch (Exception ex)
			{
				if (iniziataTransazione)
					db.RollbackTransaction();

				throw;
			}
		}
	}
}
				