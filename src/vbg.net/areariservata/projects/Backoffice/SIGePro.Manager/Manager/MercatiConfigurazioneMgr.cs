
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
	public partial class MercatiConfigurazioneMgr
	{

		public MercatiConfigurazione Insert(MercatiConfigurazione cls)
		{
			throw new Exception("Metodo MercatiConfigurazioneMgr.Insert non implementato in quanto la gestione viene effettuata tramite Java");
		}

		public MercatiConfigurazione Update(MercatiConfigurazione cls)
		{
			throw new Exception("Metodo MercatiConfigurazioneMgr.Update non implementato in quanto la gestione viene effettuata tramite Java");
		}


		public void Delete(MercatiConfigurazione cls)
		{
			throw new Exception("Metodo MercatiConfigurazioneMgr.Delete non implementato in quanto la gestione viene effettuata tramite Java");
		}
	}
}
