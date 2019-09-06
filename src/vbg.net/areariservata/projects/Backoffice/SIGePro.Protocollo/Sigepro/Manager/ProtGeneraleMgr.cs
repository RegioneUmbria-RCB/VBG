using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;
using System.Data;
using Init.SIGePro.Utils;

namespace Init.SIGePro.Manager
{
    public partial class ProtGeneraleMgr
    {
        public int GetNextNumeroProtocollo(int anno, string idComune, int iPg_FkIdAOO)
        {
            Sequence seq = new Sequence();
            seq.Db = db;
            seq.IdComune = idComune;
            seq.SequenceName = "NUMERO_PROTOCOLLO$" + anno + "$" + iPg_FkIdAOO;
            return seq.NextVal();

            //object obj;

            //string sql = @"SELECT " + db.Specifics.MaxFunction("pg_numero") +
            //       " FROM prot_generale " +
            //       " WHERE pg_anno = {0} AND " +
            //       " idcomune = {1} AND " +
            //       " pg_fkidaoo = {2} ";

            //sql = String.Format(sql, db.Specifics.QueryParameterName("pg_anno"), db.Specifics.QueryParameterName("idcomune"), db.Specifics.QueryParameterName("pg_fkidaoo"));


            //using (IDbCommand cmd = db.CreateCommand(sql))
            //{
            //    cmd.Parameters.Add(db.CreateParameter("pg_anno", anno));
            //    cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
            //    cmd.Parameters.Add(db.CreateParameter("pg_fkidaoo", iPg_FkIdAOO));
            //    obj = cmd.ExecuteScalar();
            //}

            //return ((obj == DBNull.Value) ? 1 : Convert.ToInt32(obj) + 1);
        }

        private void ForeignValidate(ProtGenerale cls)
        {
            #region MOTIVO ANNULLAMENTO
            if (cls.Pg_Fkidmotivoannullamento.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                if (this.recordCount("PROT_MOTIVIANNULLAMENTO", "MA_ID", "WHERE MA_ID = " + cls.Pg_Fkidmotivoannullamento) == 0)
                    throw (new RecordNotfoundException("PROT_MOTIVIANNULLAMENTO.MA_ID (" + cls.Pg_Fkidmotivoannullamento + ") non trovato nella tabella PROT_MOTIVIANNULLAMENTO"));
            }
            #endregion


            #region Modalità
            if (cls.Pg_Fkidmodalita.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                if (this.recordCount("PROT_MODALITAPROTOCOLLO", "MP_ID", "WHERE MP_ID = " + cls.Pg_Fkidmodalita) == 0)
                    throw (new RecordNotfoundException("PROT_MODALITAPROTOCOLLO.MP_ID (" + cls.Pg_Fkidmodalita + ") non trovato nella tabella PROT_MODALITAPROTOCOLLO"));
            }
            else
                throw new RequiredFieldException("PROT_GENERALE.PG_FKIDMODALITA obbligatorio");
            #endregion

            #region Mittente e Destinatario

            int contaAnagrafe = 0;
            int contaAmministrazioni = 0;

            switch (cls.Pg_Fkidmodalita)
            {
                case 1:
                    if (cls.Pg_Fkidmittente.GetValueOrDefault(int.MinValue) != int.MinValue)
                        contaAnagrafe = this.recordCount("ANAGRAFE", "CODICEANAGRAFE", "WHERE CODICEANAGRAFE = " + cls.Pg_Fkidmittente + " AND IDCOMUNE = '" + cls.Idcomune + "'");

                    if (!String.IsNullOrEmpty(cls.Pg_Mittente))
                    {
                        //contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE AMMINISTRAZIONE = '" + cls.Pg_Mittente + "' AND IDCOMUNE = '" + cls.Idcomune + "'");
                        var list = new List<KeyValuePair<string, string>>();
                        list.Add(new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune));
                        list.Add(new KeyValuePair<string, string>("AMMINISTRAZIONE", cls.Pg_Mittente));

                        contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", list);
                    }

                    if (contaAnagrafe + contaAmministrazioni == 0)
                        throw (new RecordNotfoundException("I DATI RIGUARDANTI IL MITTENTE NON SONO STATI VALORIZZATI CORRETTAMENTE"));
                    
                    /*if (cls.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue) != int.MinValue)
                    {
                        if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + cls.Pg_Fkiddestinatario + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                            throw (new RecordNotfoundException("AMMINISTRAZIONI.CODICEAMMINISTRAZIONE (" + cls.Pg_Fkiddestinatario + ") non trovato nella tabella AMMINISTRAZIONI"));
                    }*/
                    break;
                case 2:
                   /*if (cls.Pg_Fkidmittente.GetValueOrDefault(int.MinValue) != int.MinValue)
                    {
                        if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + cls.Pg_Fkidmittente + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                            throw (new RecordNotfoundException("AMMINISTRAZIONI.CODICEAMMINISTRAZIONE (" + cls.Pg_Fkidmittente + ") non trovato nella tabella AMMINISTRAZIONI"));
                    }
                    else
                        throw new RequiredFieldException("PROT_GENERALE.PG_FKIDMITTENTE obbligatorio");*/

                    if (cls.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue) != int.MinValue)
                        contaAnagrafe = this.recordCount("ANAGRAFE", "CODICEANAGRAFE", "WHERE CODICEANAGRAFE = " + cls.Pg_Fkiddestinatario + " AND IDCOMUNE = '" + cls.Idcomune + "'");

                    if (!String.IsNullOrEmpty(cls.Pg_Destinatario))
                    {
                        var list = new List<KeyValuePair<string, string>>();
                        list.Add(new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune));
                        list.Add(new KeyValuePair<string, string>("AMMINISTRAZIONE", cls.Pg_Mittente));

                        //contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE AMMINISTRAZIONE = '" + cls.Pg_Destinatario + "' AND IDCOMUNE = '" + cls.Idcomune + "'");
                        contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", list);
                    }

                    if (contaAnagrafe + contaAmministrazioni == 0)
                        throw (new RecordNotfoundException("I DATI RIGUARDANTI IL DESTINATARIO NON SONO STATI VALORIZZATI CORRETTAMENTE"));

                    break;
                case 3:
                    //Non ha senso questo controllo in quanto viene già fatto lato java
                    /*if (!String.IsNullOrEmpty(cls.Pg_Mittente))
                    {
                        if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + cls.Pg_Fkidmittente + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                            throw (new RecordNotfoundException("AMMINISTRAZIONI.CODICEAMMINISTRAZIONE (" + cls.Pg_Fkidmittente + ") non trovato nella tabella AMMINISTRAZIONI"));
                    }
                    else
                        throw new RequiredFieldException("PROT_GENERALE.PG_FKIDMITTENTE obbligatorio");

                    if (cls.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue) != int.MinValue)
                    {
                        if (this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", "WHERE CODICEAMMINISTRAZIONE = " + cls.Pg_Fkiddestinatario + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                            throw (new RecordNotfoundException("AMMINISTRAZIONI.CODICEAMMINISTRAZIONE (" + cls.Pg_Fkiddestinatario + ") non trovato nella tabella AMMINISTRAZIONI"));
                    }
                    else
                        throw new RequiredFieldException("PROT_GENERALE.PG_FKIDDESTINATARIO obbligatorio");*/
                    break;
            }
            #endregion

            #region Tipologia
            if (this.recordCount("PROT_TIPOLOGIAPROTOCOLLO", "TP_ID", "WHERE TP_ID = " + cls.Pg_Fkidtipologia + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                throw (new RecordNotfoundException("PROT_TIPOLOGIAPROTOCOLLO.TP_ID (" + cls.Pg_Fkidtipologia + ") non trovato nella tabella PROT_TIPOLOGIAPROTOCOLLO"));
            #endregion

            #region AOO
            if (this.recordCount("PROT_AOO", "AO_ID", "WHERE AO_ID = " + cls.Pg_Fkidaoo + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                throw (new RecordNotfoundException("PROT_AOO.AO_ID (" + cls.Pg_Fkidaoo + ") non trovato nella tabella PROT_AOO"));
            #endregion

            #region Classifica
            if (cls.Pg_Fkidclassificazione.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                if (this.recordCount("PROT_CLASSIFICAZIONE", "CL_ID", "WHERE CL_ID = " + cls.Pg_Fkidclassificazione + " AND IDCOMUNE = '" + cls.Idcomune + "'") == 0)
                    throw (new RecordNotfoundException("PROT_CLASSIFICAZIONE.CL_ID (" + cls.Pg_Fkidclassificazione + ") non trovato nella tabella PROT_AOO"));
            }
            //else
            //    throw new RequiredFieldException("PROT_GENERALE.PG_FKIDCLASSIFICAZIONE obbligatorio"); per annullamento protocollo
            #endregion
        }
    }
}
