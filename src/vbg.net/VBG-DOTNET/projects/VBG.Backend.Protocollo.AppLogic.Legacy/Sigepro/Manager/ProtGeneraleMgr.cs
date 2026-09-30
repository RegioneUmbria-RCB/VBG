using System;
using System.Collections.Generic;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Utils;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
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
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("MA_ID", cls.Pg_Fkidmotivoannullamento.ToString())
                };
                if (this.recordCount("PROT_MOTIVIANNULLAMENTO", "MA_ID", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_MOTIVIANNULLAMENTO.MA_ID ({cls.Pg_Fkidmotivoannullamento}) non trovato nella tabella PROT_MOTIVIANNULLAMENTO"));
            }
            #endregion

            #region Modalità
            if (cls.Pg_Fkidmodalita.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("MP_ID", cls.Pg_Fkidmodalita.ToString())
                };
                if (this.recordCount("PROT_MODALITAPROTOCOLLO", "MP_ID", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_MODALITAPROTOCOLLO.MP_ID ({cls.Pg_Fkidmodalita}) non trovato nella tabella PROT_MODALITAPROTOCOLLO"));
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
                    {
                        var conditions = new List<KeyValuePair<string, string>>
                        {
                            new KeyValuePair<string, string>("CODICEANAGRAFE", cls.Pg_Fkidmittente.ToString()),
                            new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                        };
                        contaAnagrafe = this.recordCount("ANAGRAFE", "CODICEANAGRAFE", conditions);
                    }

                    if (!String.IsNullOrEmpty(cls.Pg_Mittente))
                    {
                        var list = new List<KeyValuePair<string, string>>();
                        list.Add(new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune));
                        list.Add(new KeyValuePair<string, string>("AMMINISTRAZIONE", cls.Pg_Mittente));

                        contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", list);
                    }

                    if (contaAnagrafe + contaAmministrazioni == 0)
                        throw (new RecordNotfoundException("I DATI RIGUARDANTI IL MITTENTE NON SONO STATI VALORIZZATI CORRETTAMENTE"));
                    break;
                case 2:
                    if (cls.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue) != int.MinValue)
                    {
                        var conditions = new List<KeyValuePair<string, string>>
                        {
                            new KeyValuePair<string, string>("CODICEANAGRAFE", cls.Pg_Fkiddestinatario.ToString()),
                            new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                        };
                        contaAnagrafe = this.recordCount("ANAGRAFE", "CODICEANAGRAFE", conditions);
                    }

                    if (!String.IsNullOrEmpty(cls.Pg_Destinatario))
                    {
                        var list = new List<KeyValuePair<string, string>>();
                        list.Add(new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune));
                        list.Add(new KeyValuePair<string, string>("AMMINISTRAZIONE", cls.Pg_Mittente));

                        contaAmministrazioni = this.recordCount("AMMINISTRAZIONI", "CODICEAMMINISTRAZIONE", list);
                    }

                    if (contaAnagrafe + contaAmministrazioni == 0)
                        throw (new RecordNotfoundException("I DATI RIGUARDANTI IL DESTINATARIO NON SONO STATI VALORIZZATI CORRETTAMENTE"));

                    break;
                case 3:
                    // No check (see original comments)
                    break;
            }
            #endregion

            #region Tipologia
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("TP_ID", cls.Pg_Fkidtipologia.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                };
                if (this.recordCount("PROT_TIPOLOGIAPROTOCOLLO", "TP_ID", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_TIPOLOGIAPROTOCOLLO.TP_ID ({cls.Pg_Fkidtipologia}) non trovato nella tabella PROT_TIPOLOGIAPROTOCOLLO"));
            }
            #endregion

            #region AOO
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("AO_ID", cls.Pg_Fkidaoo.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                };
                if (this.recordCount("PROT_AOO", "AO_ID", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_AOO.AO_ID ({cls.Pg_Fkidaoo}) non trovato nella tabella PROT_AOO"));
            }
            #endregion

            #region Classifica
            if (cls.Pg_Fkidclassificazione.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CL_ID", cls.Pg_Fkidclassificazione.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune)
                };
                if (this.recordCount("PROT_CLASSIFICAZIONE", "CL_ID", conditions) == 0)
                    throw (new RecordNotfoundException($"PROT_CLASSIFICAZIONE.CL_ID ({cls.Pg_Fkidclassificazione}) non trovato nella tabella PROT_AOO"));
            }
            #endregion
        }
    }
}
