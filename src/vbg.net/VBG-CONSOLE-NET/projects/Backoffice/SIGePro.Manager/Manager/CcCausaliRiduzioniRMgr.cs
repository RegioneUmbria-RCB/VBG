
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
    public partial class CcCausaliRiduzioniRMgr
    {
		public List<CcCausaliRiduzioniR> GetListByIdCausaleT(string idComune, int idCausaleT)
		{
			CcCausaliRiduzioniR filtro = new CcCausaliRiduzioniR();
			filtro.Idcomune = idComune;
			filtro.FkCccrtId = idCausaleT;

			return GetList(filtro);
		}


		private void VerificaRecordCollegati(CcCausaliRiduzioniR cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
			CcICalcoloTContributoRiduzMgr rMgr = new CcICalcoloTContributoRiduzMgr(db);

			CcICalcoloTContributoRiduz filtro = new CcICalcoloTContributoRiduz();
			filtro.Idcomune  = cls.Idcomune;
			filtro.FkCccrrId = cls.Id;

			List<CcICalcoloTContributoRiduz> lista = rMgr.GetList(filtro);

			if (lista.Count > 0)
				throw new ReferentialIntegrityException("cc_icalcolotcontributo_riduz");
		}
	}
}
				