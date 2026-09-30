using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Utils
{
    public class Sequence
    {
        private const int LIMITE_SUPERIORE_RECORDS = 90000000;

        public string IdComune { get; set; } = String.Empty;
        /// <summary>
        /// Oggetto PersonalLib2.Data.Database con la connessione attiva ma senza transazione.
        /// </summary>
        public DataBase Db { get; set; } = null;

        /// <summary>
        /// Nome della sequenza da leggere/aggiornare
        /// </summary>
        public string SequenceName { get; set; } = String.Empty;

        /// <summary>
        /// La funzione ritorna il prossimo valore di una sequenza
        /// </summary>
        /// <returns></returns>
        public int NextVal()
        {
            return NextValInternal(this.Db, this.IdComune, this.SequenceName);
        }

        private static int NextValInternal(DataBase oldDb, string IdComune, string SequenceName)
        {
            var newDb = new DataBase(oldDb.ConnectionDetails.ConnectionString, oldDb.ConnectionDetails.ProviderType);

            newDb.Connection.Open();
            newDb.BeginTransaction();

            try
            {
                var cmdText = $"Update SEQUENCETABLE Set CURRVAL=CURRVAL Where IDCOMUNE={newDb.QueryParameter("IDCOMUNE")} and SEQUENCENAME={newDb.QueryParameter("SEQUENCENAME")}";

                newDb.ExecuteNonQuery(cmdText,
                    mp => mp.Add("IDCOMUNE", IdComune)
                            .Add("SEQUENCENAME", SequenceName));

                cmdText = "Select CURRVAL From SEQUENCETABLE Where IDCOMUNE=" + newDb.Specifics.QueryParameterName("IDCOMUNE") + " and SEQUENCENAME=" + newDb.Specifics.QueryParameterName("SEQUENCENAME");

                var nextVal = newDb.ExecuteScalar(cmdText, -1,
                                                    mp => mp.Add("IDCOMUNE", IdComune)
                                                            .Add("SEQUENCENAME", SequenceName));

                if (nextVal == -1)
                {
                    //La sequenza non è stata trovata, quindi se è del tipo tabella.colonna viene creata
                    if (SequenceName.IndexOf(".") > -1)
                    {
                        string[] tmpVet = SequenceName.Split('.');
                        string myTableName = tmpVet[0];
                        string myColumnName = tmpVet[1];

                        //viene fatta la max sulla tabella di riferimento
                        cmdText = $"select max({myColumnName}) as massimo from {myTableName} where idcomune ={newDb.QueryParameter("IDCOMUNE")} and {myColumnName} < {LIMITE_SUPERIORE_RECORDS}";

                        nextVal = newDb.ExecuteScalar(
                            cmdText,
                            -1,
                            mp => mp.Add("IDCOMUNE", IdComune)
                        );

                        if (nextVal == -1)
                            nextVal = 0;

                        //si inserisce il valore trovato nella sequencetable
                        cmdText = $"insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values ({newDb.QueryParameter("IDCOMUNE")}, {newDb.QueryParameter("SEQUENCENAME")}, {newDb.QueryParameter("CURRVAL")})";

                        newDb.ExecuteNonQuery(
                            cmdText,
                            mp => mp.Add("IDCOMUNE", IdComune)
                                    .Add("SEQUENCENAME", SequenceName)
                                    .Add("CURRVAL", nextVal));
                    }
                    else
                    {
                        //throw new Exception("Sequenza non trovata: IDCOMUNE='" + IdComune + "' and SEQUENCENAME='" + SequenceName + "'");

                        //la sequenza non è del tipo "[TABELLA].[ID]"
                        //e non è presente nella SEQUENCETABLE
                        //quindi si inserisce il valore 0 nella sequencetable
                        nextVal = 0;

                        cmdText = $"insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values ({newDb.QueryParameter("IDCOMUNE")}, {newDb.QueryParameter("SEQUENCENAME")}, {newDb.QueryParameter("CURRVAL")})";

                        newDb.ExecuteNonQuery(
                            cmdText,
                            mp => mp.Add("IDCOMUNE", IdComune)
                                    .Add("SEQUENCENAME", SequenceName)
                                    .Add("CURRVAL", 0));
                    }
                }

                nextVal = nextVal + 1;

                cmdText = $"Update SEQUENCETABLE Set CURRVAL={newDb.QueryParameter("CURRVAL")} Where IDCOMUNE={newDb.QueryParameter("IDCOMUNE")} and SEQUENCENAME={newDb.QueryParameter("SEQUENCENAME")}";

                newDb.ExecuteNonQuery(
                    cmdText,
                    mp => mp.Add("CURRVAL", nextVal)
                            .Add("IDCOMUNE", IdComune)
                            .Add("SEQUENCENAME", SequenceName)
                );

                newDb.CommitTransaction();

                return nextVal;
            }
            catch (Exception ex)
            {
                newDb.RollbackTransaction();

                throw;
            }
            finally
            {
                newDb.Connection.Close();
            }
        }
    }
}
