using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class MappaliCondition: QueryConditionBase
    {
        /*
         * !String.IsNullOrEmpty(this.TipoCatasto) ||
                                                    !String.IsNullOrEmpty(this.Foglio) ||
                                                    !String.IsNullOrEmpty(this.Particella) ||
                                                    !String.IsNullOrEmpty(this.Subalterno);
         * */

        public MappaliCondition(DataBase db, FiltriDatiCatastali filtri)
            : base(db, "Mappali")
        {
            if (filtri?.AlmenoUnDatoSpecificato != true)
            {
                return;
            }

            var conds = new List<string>();

            if (!String.IsNullOrEmpty(filtri.TipoCatasto))
            {
                conds.Add($"ISTANZEMAPPALI.CODICECATASTO = {QueryParameterName("tipoCatasto")}");
                AddParameter("tipoCatasto", filtri.TipoCatasto);
            }

            if (!String.IsNullOrEmpty(filtri.Foglio))
            {
                conds.Add($"ISTANZEMAPPALI.FOGLIO = {QueryParameterName("foglio")}");
                AddParameter("foglio", filtri.Foglio);
            }

            if (!String.IsNullOrEmpty(filtri.Particella))
            {
                conds.Add($"ISTANZEMAPPALI.PARTICELLA = {QueryParameterName("particella")}");
                AddParameter("particella", filtri.Particella);
            }

            if (!String.IsNullOrEmpty(filtri.Subalterno))
            {
                conds.Add($"ISTANZEMAPPALI.SUB = {QueryParameterName("sub")}");
                AddParameter("sub", filtri.Subalterno);
            }

            Query = $"({String.Join(" and ", conds.ToArray())})";
        }
    }
}
