using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.DatiDinamici.Statistiche;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
    public partial class StatisticheDatiDinamiciQueryGenerator
    {
        private readonly string m_idComune;
        private readonly DataBase m_database;
        private readonly string m_nomeTabella;
        private readonly string m_nomeCampoId;
        private readonly string m_nomeCampoIdCorrelato;
        private int m_idx = 0;
        private StringBuilder m_commandText = new StringBuilder();
        private List<KeyValuePair<string, object>> m_parameters;

        public StatisticheDatiDinamiciQueryGenerator(string idComune, DataBase database, string nomeTabella, string nomeCampoId, string nomeCampoIdCorrelato)
        {
            this.m_idComune = idComune;
            this.m_database = database;
            this.m_nomeTabella = nomeTabella;
            this.m_nomeCampoId = nomeCampoId;
            this.m_nomeCampoIdCorrelato = nomeCampoIdCorrelato;
        }

        public QueryStatisticheDatiDinamici CreaQuery(DsFiltriStatisticheDatiDinamici dsFiltri)
        {
            this.m_commandText = new StringBuilder();
            this.m_parameters = new List<KeyValuePair<string, object>>();
            this.m_idx = 0;

            //foreach ( DsFiltriStatisticheDatiDinamici.DtFiltriRow row in dsFiltri.DtFiltri)
            for (int idx = 0; idx < dsFiltri.DtFiltri.Rows.Count; idx++)
            {
                this.CreaQueryRiga(dsFiltri.DtFiltri[idx], idx);
            }

            return new QueryStatisticheDatiDinamici(" (" + this.m_commandText.ToString() + ") ", this.m_parameters);
        }


        private void CreaQueryRiga(DsFiltriStatisticheDatiDinamici.DtFiltriRow row, int indiceRiga)
        {
            if (row.IdCampo == -1) return;

            bool confrontoNull = this.TipoConfrontoNull(row.Criterio);
            bool richiedeValore = this.ConfrontoRichiedeValore(row.Criterio);
            string testoConfronto = this.EstraiTipoConfronto(row.Criterio);

            bool richiedesubstring = this.RichiedeSubstring(row.Criterio);
            bool richiedeToNumber = this.RichiedeToNumber(row.Criterio);

            string nomeCampoValore = this.m_database.Specifics.UCaseFunction(this.m_nomeTabella + ".VALORE");

            if (richiedesubstring)
                nomeCampoValore = this.m_database.Specifics.ToCharFunction(this.m_database.Specifics.SubstrFunction(nomeCampoValore, 0, 4000));

            if (richiedeToNumber)
            {
                var str = "replace(replace({0}.valore, '.',''),',','.')";
                nomeCampoValore = this.m_database.Specifics.ToIntegerFunction(String.Format(str, this.m_nomeTabella));
            }

            string sqlFmtBase = "SELECT " + this.m_nomeTabella + "." + this.m_nomeCampoIdCorrelato + " FROM " + this.m_nomeTabella + " WHERE " + this.m_nomeTabella + ".idcomune = {{{0}}} AND " + this.m_nomeTabella + ".FK_D2C_ID = {{{1}}} AND {2} {3} ";

            string sqlCondizioneBase = String.Format(sqlFmtBase, this.m_idx++, this.m_idx++, nomeCampoValore, testoConfronto);

            if (richiedeValore)
                sqlCondizioneBase += " {" + (this.m_idx++) + "}";

            this.RegistraParametro("parIdcomune", this.m_idComune);
            this.RegistraParametro("parIdCampo", row.IdCampo);

            if (richiedeValore)
                this.RegistraParametro("parValore", row.Valore.ToUpper());


            // Legame con la join
            string sqlPre = "";
            string sqlPost = "";

            if (indiceRiga > 0)
                sqlPre += " " + row.Concatenazione + " ";

            if (row.ParentesiIn)
                sqlPre += "(";

            sqlPre += this.m_nomeCampoId;

            if (confrontoNull)
                sqlPre += " not ";

            sqlPre += " in ( ";

            sqlPost = " )";

            if (row.ParentesiOut)
                sqlPost += " )";

            this.m_commandText.Append(" ").Append(sqlPre).Append(sqlCondizioneBase).Append(sqlPost);
        }

        private bool RichiedeToNumber(string operatore)
        {
            var confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), operatore);

            switch (confronto)
            {

                case TipoConfrontoFiltroEnum.LessThan:
                case TipoConfrontoFiltroEnum.LessThanOrEqual:
                case TipoConfrontoFiltroEnum.GreaterThan:
                case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
                    return true;

                case TipoConfrontoFiltroEnum.Equal:
                case TipoConfrontoFiltroEnum.NotEqual:
                case TipoConfrontoFiltroEnum.Like:
                case TipoConfrontoFiltroEnum.Null:
                case TipoConfrontoFiltroEnum.NotNull:
                    return false;
            }

            return false;
        }



        private void RegistraParametro(string name, object value)
        {
            this.m_parameters.Add(new KeyValuePair<string, object>(name + this.m_parameters.Count, value));
        }

        private bool TipoConfrontoNull(string val)
        {
            TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

            return confronto == TipoConfrontoFiltroEnum.Null;
        }

        private bool ConfrontoRichiedeValore(string val)
        {
            TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

            return confronto != TipoConfrontoFiltroEnum.Null && confronto != TipoConfrontoFiltroEnum.NotNull;
        }

        private string EstraiTipoConfronto(string val)
        {
            TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

            switch (confronto)
            {
                case TipoConfrontoFiltroEnum.Equal:
                    return "like";
                case TipoConfrontoFiltroEnum.NotEqual:
                    return "not like";
                case TipoConfrontoFiltroEnum.LessThan:
                    return "<";
                case TipoConfrontoFiltroEnum.LessThanOrEqual:
                    return ">";
                case TipoConfrontoFiltroEnum.GreaterThan:
                    return ">";
                case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
                    return ">=";
                case TipoConfrontoFiltroEnum.Null:
                    return "is not null";
                case TipoConfrontoFiltroEnum.NotNull:
                    return "is not null";
                case TipoConfrontoFiltroEnum.Like:
                    return "like";
            }

            return "";

        }

        private bool RichiedeSubstring(string val)
        {
            TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

            switch (confronto)
            {

                case TipoConfrontoFiltroEnum.LessThan:
                case TipoConfrontoFiltroEnum.LessThanOrEqual:
                case TipoConfrontoFiltroEnum.GreaterThan:
                case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
                    return true;

                case TipoConfrontoFiltroEnum.Equal:
                case TipoConfrontoFiltroEnum.NotEqual:
                case TipoConfrontoFiltroEnum.Like:
                case TipoConfrontoFiltroEnum.Null:
                case TipoConfrontoFiltroEnum.NotNull:
                    return false;
            }

            return false;
        }


    }
}
