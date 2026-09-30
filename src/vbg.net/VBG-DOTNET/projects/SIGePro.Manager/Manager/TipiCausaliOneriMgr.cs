using Init.SIGePro.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class TipiCausaliOneriMgr
    {
        public List<TipiCausaliOneri> GetCausaliDaMappaturaNodoPagamenti(string idComune, string software, string codPeople)
        {
            TipiCausaliOneri filtro = new TipiCausaliOneri();
            filtro.Idcomune = idComune;
            filtro.MappaturaNodoPagamenti = codPeople;
            filtro.OthersWhereClause.Add("(software='" + software + "' or software='TT')");

            return this.GetList(filtro);
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
