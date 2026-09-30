using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSequenceTable
{
    public class SequenceTableService : ISequenceTableService
    {
        private static class Constants
        {
            public const int LimiteSuperioreRecords = 90000000;
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(SequenceTableService));

        public SequenceTableService(DataBase db, string idComune)
        {
            _db = db;
            _idComune = idComune;
        }

        public int NextId(string sequenceName)
        {
            var wasInTransaction = this._db.IsInTransaction;

            try
            {
                if (!wasInTransaction)
                {
                    this._db.BeginTransaction();
                }

                var sql = $"Update SEQUENCETABLE Set CURRVAL=CURRVAL Where IDCOMUNE={this._db.QueryParameter("idComune")} and SEQUENCENAME={this._db.QueryParameter("sequenceName")}";

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("sequenceName", sequenceName);
                });

                sql = $"Select CURRVAL From SEQUENCETABLE Where IDCOMUNE={this._db.QueryParameter("idComune")} and SEQUENCENAME={this._db.QueryParameter("sequenceName")}";

                var nextId = this._db.ExecuteScalar(sql, -1, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("sequenceName", sequenceName);
                });

                // Valore trovato in sequenza, aggiorno il nuovo id
                if (nextId > -1)
                {
                    nextId++;

                    sql = $"Update SEQUENCETABLE Set CURRVAL={this._db.QueryParameter("nextId")} Where IDCOMUNE={this._db.QueryParameter("idComune")} and SEQUENCENAME={this._db.QueryParameter("sequenceName")}";

                    this._db.ExecuteNonQuery(sql, mp =>
                    {
                        mp.AddParameter("idComune", this._idComune);
                        mp.AddParameter("sequenceName", sequenceName);
                        mp.AddParameter("nextId", nextId);
                    });

                    return nextId;
                }

                nextId = 1;

                // La sequenza non è stata trovata, quindi se è del tipo tabella.colonna viene creata
                // Se non è nel formato tabella.colonna crea una nuova sequenza con 1 come valore di default
                if (sequenceName.IndexOf(".") > -1)
                {
                    var sequenceParts = sequenceName.Split('.');
                    var tabella = sequenceParts[0];
                    var colonna = sequenceParts[1];

                    //viene fatta la max sulla tabella di riferimento
                    sql = $"select max({colonna}) as massimo from {tabella} where idcomune ={this._db.QueryParameter("idComune")} and {colonna} < {Constants.LimiteSuperioreRecords}";

                    nextId = this._db.ExecuteScalar(sql, 0, mp =>
                    {
                        mp.AddParameter("idComune", this._idComune);
                    }) + 1;
                }

                //si inserisce il valore trovato nella sequencetable
                sql = $"insert into SEQUENCETABLE (IDCOMUNE,SEQUENCENAME,CURRVAL) values ({this._db.QueryParameter("idComune")},{this._db.QueryParameter("sequenceName")},{this._db.QueryParameter("nextId")})";

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("sequenceName", sequenceName);
                    mp.AddParameter("nextId", nextId);
                });

                return nextId;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nel calcolo della sequenza per SEQUENCENAME {sequenceName} e idcomune {this._idComune}, la transazione {(wasInTransaction ? "non verrà" : "verrà")} rollbackata: {ex}");

                if (wasInTransaction)
                {
                    this._db.RollbackTransaction();
                }

                throw;
            }
            finally
            {
                if (!wasInTransaction)
                {
                    this._db.CommitTransaction();
                }
            }
        }
    }
}
