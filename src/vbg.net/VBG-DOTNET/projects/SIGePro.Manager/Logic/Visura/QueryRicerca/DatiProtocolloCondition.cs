using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class DatiProtocolloCondition : QueryConditionBase
    {
        public DatiProtocolloCondition(DataBase db, FiltroDatiProtocollo datiProtocollo)
            : base(db, "PROT")
        {
            if (datiProtocollo?.AlmenoUnDatoSpecificato != true)
            {
                return;
            }

            var conds = new List<string>();

            if (datiProtocollo.Data.HasValue)
            {
                var str = db.ConnectionDetails.ProviderType == ProviderType.OracleClient ?
                        $"TO_CHAR(istanze.dataprotocollo, 'YYYYMMDD') = {QueryParameterName("dataProtocollo")}" :
                        $"DATE_FORMAT(istanze.dataprotocollo, '%Y%m%d') = {QueryParameterName("dataProtocollo")}";
                        
                conds.Add(str);
                AddParameter("dataProtocollo", datiProtocollo.Data.Value.ToString("yyyyMMdd"));
            }

            if (!String.IsNullOrEmpty(datiProtocollo.Numero))
            {
                conds.Add($"istanze.numeroprotocollo = {QueryParameterName("numeroProtocollo")}");
                AddParameter("numeroProtocollo", datiProtocollo.Numero);
            }

            Query = $"({String.Join(" and ", conds.ToArray())})";
        }
    }
}
