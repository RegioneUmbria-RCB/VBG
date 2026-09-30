using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public abstract class MetadatiManagerBase<T> : BaseManager
    {
        protected readonly DataBase _db;
        protected abstract string ColumnName { get; } // es: CODICEISTANZA / CODICEMOVIMENTO / FKIDAUTORIZZAZIONE
        protected abstract string TableName { get; } // es: ISTANZE_METADATI / MOVIMENTI_METADATI / AUTORIZZAZIONI_METADATI
        
        protected MetadatiManagerBase(DataBase db) : base(db)
        {
            _db = db;
        }

        public string GetValue(string idComune, int codice, string chiave)
        {
            string sql = $"SELECT valore FROM {TableName} WHERE idcomune = @idcomune AND {ColumnName} = @codice AND chiave = @chiave";
            sql = PreparaQueryParametrica(sql, "idcomune", "codice", "chiave");

            if (_db.Connection.State != ConnectionState.Open)
                _db.Connection.Open();

            try
            {
                using (IDbCommand cmd = _db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(_db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(_db.CreateParameter("codice", codice));
                    cmd.Parameters.Add(_db.CreateParameter("chiave", chiave));

                    var result = cmd.ExecuteScalar();

                    if (result == DBNull.Value || result == null)
                        return string.Empty;

                    return result.ToString();
                }
            }
            finally
            {
                _db.Connection.Close();
            }
        }

        public void SetValue(string idComune, int codice, string chiave, string valore)
        {
            if (_db.Connection.State == ConnectionState.Closed)
            {
                _db.Connection.Open();
            }

            try
            {
                var sqlCheck = $@"
                    SELECT COUNT(*) 
                    FROM {TableName}
                    WHERE idcomune = {this._db.QueryParameter("idComune")}
                      AND {ColumnName} = {this._db.QueryParameter("codice")}
                      AND chiave = {this._db.QueryParameter("chiave")}
                ";

                var count = _db.ExecuteScalar(sqlCheck, 0,
                    mp => mp.Add("idComune", idComune)
                            .Add("codice", codice)
                            .Add("chiave", chiave)
                );

                string sql;

                if (count > 0)
                {
                    sql = $@"
                        UPDATE {TableName}
                           SET valore = {this._db.QueryParameter("valore")}
                         WHERE idcomune = {this._db.QueryParameter("idComune")}
                           AND {ColumnName} = {this._db.QueryParameter("codice")}
                           AND chiave = {this._db.QueryParameter("chiave")}
                    ";
                }
                else
                {
                    sql = $@"
                        INSERT INTO {TableName}(idcomune, {ColumnName}, chiave, valore)
                        VALUES (
                            {this._db.QueryParameter("idComune")},
                            {this._db.QueryParameter("codice")},
                            {this._db.QueryParameter("chiave")},
                            {this._db.QueryParameter("valore")}
                        )
                    ";
                }

                _db.ExecuteNonQuery(sql,
                    mp => mp.Add("idComune", idComune)
                            .Add("codice", codice)
                            .Add("chiave", chiave)
                            .Add("valore", valore)
                );
            }
            finally
            {
                _db.Connection.Close();
            }
        }

    }
}