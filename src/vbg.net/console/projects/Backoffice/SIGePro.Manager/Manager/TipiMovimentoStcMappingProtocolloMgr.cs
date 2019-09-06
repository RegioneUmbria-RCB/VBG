using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public class TipiMovimentoStcMappingProtocolloMgr : BaseManager
    {
        DataBase _db;
        string _idComune;

        public TipiMovimentoStcMappingProtocolloMgr(DataBase db, string idComune) : base(db)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public TipiMovimentoStcMappingProtocollo GetDatiProtocollo(string tipoMovimento, string codiceAmministrazione)
        {
            bool closeCnn = false;

            try
            {
                if (_db.Connection.State == ConnectionState.Closed)
                {
                    _db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica(@"SELECT 
														PROTOCOLLO_FLUSSO, PROTOCOLLO_TIPODOCUMENTO, PROTOCOLLO_OGGETTO, PROTOCOLLO_MITTENTE
													FROM 
														TIPIMOV_STC_MAPPING
													WHERE
														TIPIMOV_STC_MAPPING.IDCOMUNE = {0} AND
													    TIPIMOV_STC_MAPPING.TIPOMOVIMENTO = {1} AND
                                                        TIPIMOV_STC_MAPPING.CODICEAMMINISTRAZIONE = {2}",
                                                    "idComune",
                                                    "tipoMovimento",
                                                    "codiceAmministrazione");

                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", _idComune));
                    cmd.Parameters.Add(db.CreateParameter("tipoMovimento", tipoMovimento));
                    cmd.Parameters.Add(db.CreateParameter("codiceAmministrazione", codiceAmministrazione));

                    var reader = cmd.ExecuteReader();

                    if (!reader.Read())
                    {
                        return null;
                    }

                    while (reader.Read())
                    {
                        var flusso = reader["PROTOCOLLO_FLUSSO"].ToString();
                        var tipoDocumento = reader["PROTOCOLLO_TIPODOCUMENTO"].ToString();
                        var oggetto = reader["PROTOCOLLO_OGGETTO"] != null ? Convert.ToInt32(reader["PROTOCOLLO_OGGETTO"]) : (int?)null;
                        var mittente = reader["PROTOCOLLO_MITTENTE"] != null ? Convert.ToInt32(reader["PROTOCOLLO_MITTENTE"]) : (int?)null;

                        return new TipiMovimentoStcMappingProtocollo(flusso, tipoDocumento, oggetto, mittente);
                    }

                    return null;
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }

    }
}
