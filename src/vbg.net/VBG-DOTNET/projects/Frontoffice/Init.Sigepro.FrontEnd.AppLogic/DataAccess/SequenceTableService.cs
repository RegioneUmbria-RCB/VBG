using log4net;
using PersonalLib2.Data;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.DataAccess
{
    public class SequenceTableService
    {
        private static class Constants
        {
            public const int LimiteSuperioreRecords = 90000000;
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(SequenceTableService));
        private readonly IDatabase _database;
        private readonly string _idComune;

        public SequenceTableService(IDatabase database, string idComune)
        {
            this._database = database;
            this._idComune = idComune;
        }

        public int NextId(string tabella, string colonna)
        {
            var db = this._database;
            var idComune = this._idComune;


            var sequenceName = $"{tabella}.{colonna}".ToUpper();
            var transazioneInterna = !db.IsInTransaction;

            try
            {
                if (transazioneInterna)
                {
                    db.BeginTransaction();
                }

                FormattableString sql = $"Update SEQUENCETABLE Set CURRVAL=CURRVAL Where IDCOMUNE={idComune} and SEQUENCENAME={sequenceName}";

                db.ExecuteNonQuery(sql);

                sql = $"Select CURRVAL From SEQUENCETABLE Where IDCOMUNE={idComune} and SEQUENCENAME={sequenceName}";

                var nextId = db.ExecuteScalar(sql, -1);

                // Valore trovato in sequenza, aggiorno il nuovo id
                if (nextId > -1)
                {
                    nextId++;

                    sql = $"Update SEQUENCETABLE Set CURRVAL={nextId} Where IDCOMUNE={idComune} and SEQUENCENAME={sequenceName}";

                    db.ExecuteNonQuery(sql);

                    if (transazioneInterna)
                    {
                        db.CommitTransaction();
                    }

                    return nextId;
                }

                nextId = 1;

                //viene fatta la max sulla tabella di riferimento
                var sqlStr = $"select max({colonna}) as massimo from {tabella} where idcomune={db.QueryParameter("idComune")} and {colonna} < {Constants.LimiteSuperioreRecords}";

                nextId = db.ExecuteScalar(sqlStr, 0, mp =>
                {
                    mp.AddParameter("idComune", idComune);
                }) + 1;

                //si inserisce il valore trovato nella sequencetable
                sql = $"insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values ({idComune},{sequenceName},{nextId})";

                db.ExecuteNonQuery(sql);

                if (transazioneInterna)
                {
                    db.CommitTransaction();
                }

                return nextId;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nel calcolo della sequenza per SEQUENCENAME {sequenceName} e idcomune {idComune}, verrà eseguito il rollback della transazione: {ex}");

                if (transazioneInterna)
                {
                    db.RollbackTransaction();
                }
                throw;
            }
        }
    }
}
