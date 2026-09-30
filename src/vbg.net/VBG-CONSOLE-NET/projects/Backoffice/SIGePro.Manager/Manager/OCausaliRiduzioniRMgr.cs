
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
    public partial class OCausaliRiduzioniRMgr
    {
		public override void RegisterHandlers()
		{
			this.Deleting += new DeletingDelegate(OCausaliRiduzioniRMgr_Deleting);
		}

		#region gestione della cancellazione
		void OCausaliRiduzioniRMgr_Deleting(OCausaliRiduzioniR cls)
		{
			VerificaRecordCollegati(cls);
		}

		private void VerificaRecordCollegati(OCausaliRiduzioniR cls)
		{
			if (recordCount("O_ICALCOLOCONTRIBR_RIDUZ", "fk_ocrr_id", "where IDCOMUNE = '" + cls.Idcomune + "' and fk_ocrr_id = " + cls.Id.ToString()) > 0)
				throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR_RIDUZ");

		}
		#endregion

		public List<OCausaliRiduzioniR> GetListByCausaliRiduzioniT(string idComune, int fkOcrtId)
		{
			OCausaliRiduzioniR filtro = new OCausaliRiduzioniR();
			filtro.Idcomune = idComune;
			filtro.FkOcrtId = fkOcrtId;
			filtro.OrderBy = "Descrizione";

			return GetList(filtro);
		}
	}
}
				