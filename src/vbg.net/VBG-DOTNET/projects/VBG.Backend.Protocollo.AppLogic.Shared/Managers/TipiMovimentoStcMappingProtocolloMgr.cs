using Init.SIGePro.Manager;
using log4net;
using PersonalLib2.Data;
using System.Data;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public class TipiMovimentoStcMappingProtocolloMgr : BaseManager
    {
        private readonly string _idComune;

        private readonly ILog _log = LogManager.GetLogger(typeof(TipiMovimentoStcMappingProtocolloMgr));

        public TipiMovimentoStcMappingProtocolloMgr(DataBase db, string idComune) : base(db)
        {
            this._idComune = idComune;
        }

        public TipiMovimentoStcMappingProtocollo GetDatiProtocolloPerAmministrazione(string tipoMovimento, string codiceAmministrazione)
        {
            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica(@"SELECT 
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

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", this._idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("tipoMovimento", tipoMovimento));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceAmministrazione", codiceAmministrazione));

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
                    this.db.Connection.Close();
            }

        }

        public TipiMovimentoStcMappingProtocollo? GetDatiProtocollo(string tipoMovimento)
        {
            var closeCnn = false;

            this._log.InfoFormat("GetDatiProtocollo, db is null? {0}", this.db == null);

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                this._log.Info("GetDatiProtocollo, preparazione della queryparametrica");
                var sql = this.PreparaQueryParametrica(@"SELECT 
														PROTOCOLLO_FLUSSO, PROTOCOLLO_TIPODOCUMENTO, PROTOCOLLO_OGGETTO, PROTOCOLLO_MITTENTE
													FROM 
														TIPIMOV_STC_MAPPING
													WHERE
														TIPIMOV_STC_MAPPING.IDCOMUNE = {0} AND
													    TIPIMOV_STC_MAPPING.TIPOMOVIMENTO = {1} AND
                                                        TIPIMOV_STC_MAPPING.FLAG_PROTOCOLLA = 1",
                                                    "idComune",
                                                    "tipoMovimento");

                this._log.InfoFormat("GetDatiProtocollo, queryparametrica: {0}, parametri, idComune: {1}, tipoMovimento: {2}", sql, this._idComune, tipoMovimento);

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", this._idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("tipoMovimento", tipoMovimento));

                    var reader = cmd.ExecuteReader();

                    this._log.InfoFormat("GetDatiProtocollo, reader is null? {0}", reader == null);

                    if (reader?.Read() ?? false)
                    {
                        this._log.InfoFormat("PROTOCOLLO_FLUSSO is null?: {0}", reader["PROTOCOLLO_FLUSSO"] == null);
                        var flusso = reader.GetString("PROTOCOLLO_FLUSSO");

                        this._log.InfoFormat("PROTOCOLLO_TIPODOCUMENTO is null?: {0}", reader["PROTOCOLLO_TIPODOCUMENTO"] == null);
                        var tipoDocumento = reader.GetString("PROTOCOLLO_TIPODOCUMENTO");

                        var oggetto = reader.GetInt("PROTOCOLLO_OGGETTO");
                        var mittente = reader.GetInt("PROTOCOLLO_MITTENTE");

                        this._log.InfoFormat("GetDatiProtocollo, dati restituiti dalla query: flusso: {0}, tipoDocumento: {1}, oggetto: {2}, mittente: {3}", flusso, tipoDocumento, oggetto, mittente);

                        return new TipiMovimentoStcMappingProtocollo(flusso, tipoDocumento, oggetto, mittente);
                    }

                    this._log.Info("ritorna null 2");
                    return null;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

    }
}
