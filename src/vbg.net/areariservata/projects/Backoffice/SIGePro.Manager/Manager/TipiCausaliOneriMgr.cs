
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
    public partial class TipiCausaliOneriMgr
    {
		public List<TipiCausaliOneri> GetCausaliDaCodicePeople(string idComune, string software, string codPeople)
		{
			TipiCausaliOneri filtro = new TipiCausaliOneri();
			filtro.Idcomune = idComune;
			filtro.Codicecausalepeople = codPeople;
			filtro.OthersWhereClause.Add("(software='" + software + "' or software='TT')");

			return GetList(filtro);
		}

        private TipiCausaliOneri DataIntegrations(TipiCausaliOneri cls)
        {
            if (cls.PagamentiRegulus == null)
            {
                cls.PagamentiRegulus = 0;  
            }
            return cls;
        }
	}
}
				