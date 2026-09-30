using PersonalLib2.Data;
using System;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class NomeOCfRichiedenteCondition : QueryConditionBase
    {
        public NomeOCfRichiedenteCondition(DataBase db, string nomeOCfRichiedente)
            : base(db, "NomeCfRichiedente")
        {
            nomeOCfRichiedente = nomeOCfRichiedente?.ToUpper().Trim().Replace("%", "") ?? "";

            if (String.IsNullOrEmpty(nomeOCfRichiedente))
            {
                return;
            }

            var sb = new StringBuilder();
            var cf = "%" + nomeOCfRichiedente + "%";

            sb.Append("(");
            sb.Append($" ANAGRAFE.codicefiscale = {this.QueryParameterName("cf1")} OR ANAGRAFE.partitaiva = {this.QueryParameterName("cf2")} ");
            sb.Append($" OR {db.Specifics.UCaseFunction(db.Specifics.ConcatFunction("ANAGRAFE.NOMINATIVO", "' '", "ANAGRAFE.NOME"))} like {this.QueryParameterName("cf3")} ");
            sb.Append($" OR AZIENDA.codicefiscale = {this.QueryParameterName("cf4")} OR AZIENDA.partitaiva = {this.QueryParameterName("cf5")} ");
            sb.Append($" OR {db.Specifics.UCaseFunction(db.Specifics.ConcatFunction("AZIENDA.NOMINATIVO", "AZIENDA.NOME"))} like {this.QueryParameterName("cf6")} ");
            sb.Append(")");


            this.Query = sb.ToString();

            this.AddParameter("cf1", cf);
            this.AddParameter("cf2", cf);
            this.AddParameter("cf3", cf);
            this.AddParameter("cf4", cf);
            this.AddParameter("cf5", cf);
            this.AddParameter("cf6", cf);
        }
    }
}
