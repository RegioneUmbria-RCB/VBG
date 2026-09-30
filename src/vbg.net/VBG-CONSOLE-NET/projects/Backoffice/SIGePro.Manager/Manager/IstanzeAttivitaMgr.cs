using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAttivita;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using PersonalLib2.Data.V2.Legacy;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{

    public class IstanzeAttivitaMgr : BaseManager
    {

        public IstanzeAttivitaMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeAttivita GetById(String pIDCOMUNE, int pID)
        {
            IstanzeAttivita retVal = new IstanzeAttivita();

            retVal.Id = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<IstanzeAttivita> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeAttivita;

            return null;
        }

        public List<IstanzeAttivita> GetList(IstanzeAttivita p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeAttivita> GetList(IstanzeAttivita p_class, IstanzeAttivita p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
        }

        public void Delete(IstanzeAttivita p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeAttivita Insert(IstanzeAttivita p_class)
        {

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            this.ParentDataIntegrations(p_class);

            return p_class;
        }

        private void ParentDataIntegrations(IstanzeAttivita p_class)
        {
            AttivitaMgr pAttivitaMg = new AttivitaMgr(this.db);
            Attivita pAttivita = pAttivitaMg.GetById(p_class.CODICEATTIVITA, p_class.IDCOMUNE);

            SettoriMgr pSettoreMgr = new SettoriMgr(this.db);
            Settori pSettori = pSettoreMgr.GetById(pAttivita.CODICESETTORE, p_class.IDCOMUNE);

            if (!this.IsStringEmpty(pSettori.FLAG_CONTAMQATTIVITA))
            {
                if (pSettori.FLAG_CONTAMQATTIVITA == "1")
                {
                    string metriQTotali = "0";

                    string sql = "Select Sum(METRIQ) as answer from ISTANZEATTIVITA, ATTIVITA, SETTORI Where ISTANZEATTIVITA.IDCOMUNE = '" + p_class.IDCOMUNE + "' AND ISTANZEATTIVITA.codiceistanza = " + p_class.CODICEISTANZA + " AND ATTIVITA.IDCOMUNE = ISTANZEATTIVITA.IDCOMUNE AND ATTIVITA.CODICEISTAT = ISTANZEATTIVITA.CODICEATTIVITA AND SETTORI.IDCOMUNE = ATTIVITA.IDCOMUNE AND SETTORI.CODICESETTORE = ATTIVITA.CODICESETTORE AND SETTORI.FLAG_CONTAMQATTIVITA = 1";

                    using (IDataReader reader = this.db.CreateCommand(sql).ExecuteReader())
                    {
                        if (reader.Read())
                            metriQTotali = (reader["answer"] == DBNull.Value) ? "0" : reader["answer"].ToString();
                    }

                    //Commentato per via di un baco della funzione "ExecuteScalar"
                    //string MetriQTotali = db.CreateCommand(sql).ExecuteScalar().ToString();

                    if (metriQTotali != "0")
                    {
                        DataProviderFactory mydp = new DataProviderFactory(this.db.Connection);
                        sql = "UPDATE ISTANZE SET METRIQUADRATI = " + mydp.Specifics.QueryParameterName("METRIQUADRATI") + " where istanze.idcomune = " + mydp.Specifics.QueryParameterName("IDCOMUNE") + " and istanze.codiceistanza = " + mydp.Specifics.QueryParameterName("CODICEISTANZA");

                        IDbCommand cmd = this.db.CreateCommand(sql);

                        IDataParameter param1 = cmd.CreateParameter();
                        param1.ParameterName = mydp.Specifics.QueryParameterName("METRIQUADRATI");
                        param1.Value = Convert.ToDouble(metriQTotali);

                        IDataParameter param2 = cmd.CreateParameter();
                        param2.ParameterName = mydp.Specifics.QueryParameterName("IDCOMUNE");
                        param2.Value = p_class.IDCOMUNE;

                        IDataParameter param3 = cmd.CreateParameter();
                        param3.ParameterName = mydp.Specifics.QueryParameterName("CODICEISTANZA");
                        param3.Value = p_class.CODICEISTANZA;

                        cmd.Parameters.Add(param1);
                        cmd.Parameters.Add(param2);
                        cmd.Parameters.Add(param3);

                        cmd.ExecuteNonQuery();
                    }
                }

            }
        }

        private void Validate(IstanzeAttivita p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);

        }

        private void ForeignValidate(IstanzeAttivita p_class)
        {
            #region ISTANZEATTIVITA.CODICEISTANZA
            if (!this.IsStringEmpty(p_class.CODICEISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ISTANZEATTIVITA.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZEATTIVITA.CODICEATTIVITA
            if (!this.IsStringEmpty(p_class.CODICEATTIVITA))
            {
                if (this.recordCount("ATTIVITA", "CODICEISTAT", "WHERE CODICEISTAT = '" + p_class.CODICEATTIVITA + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException(p_class, "ISTANZEATTIVITA.CODICEATTIVITA non trovato nella tabella ATTIVITA"));
                }
            }
            #endregion
        }

        public void DeleteByCodiceSettore(string idcomune, string codiceistanza, string codicesettore)
        {

            if (String.IsNullOrEmpty(idcomune))
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare idcomune");

            if (String.IsNullOrEmpty(codiceistanza))
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare codiceistanza");

            if (String.IsNullOrEmpty(codicesettore))
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare codicesettore");

            IstanzeAttivita filtro = new IstanzeAttivita();
            filtro.IDCOMUNE = idcomune;
            filtro.CODICEISTANZA = codiceistanza;
            filtro.OthersTables.Add("ATTIVITA");
            filtro.OthersWhereClause.Add("ATTIVITA.IDCOMUNE = ISTANZEATTIVITA.IDCOMUNE");
            filtro.OthersWhereClause.Add("ATTIVITA.CODICEISTAT = ISTANZEATTIVITA.CODICEATTIVITA");
            filtro.OthersWhereClause.Add("ATTIVITA.CODICESETTORE = '" + codicesettore.Replace("'", "''") + "'");


            List<IstanzeAttivita> list = this.GetList(filtro);
            foreach (IstanzeAttivita ia in list)
            {
                this.Delete(ia);
            }
        }
    }
}