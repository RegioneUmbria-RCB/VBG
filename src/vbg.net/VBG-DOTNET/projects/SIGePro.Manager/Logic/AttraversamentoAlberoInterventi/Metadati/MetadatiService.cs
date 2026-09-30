using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.Metadati
{
    public class MetadatiService
    {
        private readonly DataBase _db;

        public MetadatiService(DataBase db)
        {
            this._db = db;
        }

        public IEnumerable<AlberoProcMetadati> RecuperaMetadati(string idComune, int idIntervento)
        {
            var mgr = new AlberoProcMgr(this._db);

            var intervento = mgr.GetById(idIntervento, idComune);
            var software = intervento.SOFTWARE;

            var whereIn = intervento.GetListaScCodice().ToString();

            bool closeCnn = false;

            try
            {
                if (this._db.Connection.State == ConnectionState.Closed)
                {
                    this._db.Connection.Open();
                    closeCnn = true;
                }

                string sql = @"SELECT DISTINCT alberoproc.sc_codice, alberoproc_metadati.*
								FROM alberoproc_metadati
                                    INNER JOIN alberoproc ON
                                        alberoproc_metadati.idcomune = alberoproc.idcomune AND
                                        alberoproc_metadati.fk_scid = alberoproc.sc_id
								WHERE 
                                    alberoproc.idcomune   = {0} AND 
                                    alberoproc.software     = {1} AND
                                    alberoproc.sc_codice IN (" + whereIn + @")
								ORDER BY 
                                    alberoproc.sc_codice ASC";

                string[] parametri =
                    {
                        this._db.Specifics.QueryParameterName("idComune"),
                        this._db.Specifics.QueryParameterName("software"),
                        this._db.Specifics.QueryParameterName("scCodice")
                    };


                sql = String.Format(sql, parametri);

                using (IDbCommand cmd = this._db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this._db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this._db.CreateParameter("software", software));

                    return this._db.GetClassList<AlberoProcMetadati>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                {
                    this._db.Connection.Close();
                }
            }
        }
    }
}
