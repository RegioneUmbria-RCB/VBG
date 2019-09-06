using Init.SIGePro.Data;
using log4net;
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
        string _idComune;

        ILog _log = LogManager.GetLogger(typeof(TipiMovimentoStcMappingProtocolloMgr));

        public TipiMovimentoStcMappingProtocolloMgr(DataBase db, string idComune) : base(db)
        {
            this._idComune = idComune;
        }

        public TipiMovimentoStcMappingProtocollo GetDatiProtocolloPerAmministrazione(string tipoMovimento, string codiceAmministrazione)
        {
            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
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

        public TipiMovimentoStcMappingProtocollo GetDatiProtocollo(string tipoMovimento)
        {
            bool closeCnn = false;

            _log.InfoFormat("GetDatiProtocollo, db is null? {0}", db == null);

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                _log.Info("GetDatiProtocollo, preparazione della queryparametrica");
                var sql = PreparaQueryParametrica(@"SELECT 
														PROTOCOLLO_FLUSSO, PROTOCOLLO_TIPODOCUMENTO, PROTOCOLLO_OGGETTO, PROTOCOLLO_MITTENTE
													FROM 
														TIPIMOV_STC_MAPPING
													WHERE
														TIPIMOV_STC_MAPPING.IDCOMUNE = {0} AND
													    TIPIMOV_STC_MAPPING.TIPOMOVIMENTO = {1} AND
                                                        TIPIMOV_STC_MAPPING.FLAG_NOTIFICA_AUTOMATICA = 1",
                                                    "idComune",
                                                    "tipoMovimento");

                _log.InfoFormat("GetDatiProtocollo, queryparametrica: {0}, parametri, idComune: {1}, tipoMovimento: {2}", sql, _idComune, tipoMovimento);

                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", _idComune));
                    cmd.Parameters.Add(db.CreateParameter("tipoMovimento", tipoMovimento));

                    var reader = cmd.ExecuteReader();

                    _log.InfoFormat("GetDatiProtocollo, reader is null? {0}", reader == null);

                    while (reader.Read())
                    {
                        _log.InfoFormat("PROTOCOLLO_FLUSSO is null?: {0}", reader["PROTOCOLLO_FLUSSO"] == null);
                        var flusso = reader["PROTOCOLLO_FLUSSO"].ToString();

                        _log.InfoFormat("PROTOCOLLO_TIPODOCUMENTO is null?: {0}", reader["PROTOCOLLO_TIPODOCUMENTO"] == null);
                        var tipoDocumento = reader["PROTOCOLLO_TIPODOCUMENTO"].ToString();

                        var oggetto = reader["PROTOCOLLO_OGGETTO"] != null ? Convert.ToInt32(reader["PROTOCOLLO_OGGETTO"]) : (int?)null;
                        var mittente = reader["PROTOCOLLO_MITTENTE"] != null ? Convert.ToInt32(reader["PROTOCOLLO_MITTENTE"]) : (int?)null;

                        _log.InfoFormat("GetDatiProtocollo, dati restituiti dalla query: flusso: {0}, tipoDocumento: {1}, oggetto: {2}, mittente: {3}", flusso, tipoDocumento, oggetto, mittente);

                        return new TipiMovimentoStcMappingProtocollo(flusso, tipoDocumento, oggetto, mittente);
                    }

                    _log.Info("ritorna null 2");
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
