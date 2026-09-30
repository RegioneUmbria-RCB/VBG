
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
	public partial class MovimentiMailMgr
	{
		private void EffettuaCancellazioneACascata(MovimentiMail cls)
		{
			EliminaMailAllegati(cls);
		}

		private void EliminaMailAllegati(MovimentiMail cls)
		{
			MovimentiMailAllegati filtro = new MovimentiMailAllegati();
			filtro.Idcomune = cls.Idcomune;
			filtro.FkMovimentimail = cls.Id;

			MovimentiMailAllegatiMgr mgr = new MovimentiMailAllegatiMgr(db);
			List<MovimentiMailAllegati> l = mgr.GetList(filtro);

			for (int i = 0; i < l.Count; i++)
				mgr.Delete(l[i]);
		}
	}
}
