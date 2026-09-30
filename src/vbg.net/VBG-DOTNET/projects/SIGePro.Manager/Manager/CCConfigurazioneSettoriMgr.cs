using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using log4net;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCConfigurazioneSettoriMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CCConfigurazioneSettoriMgr));

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCConfigurazioneSettori> Find(string token, string software)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCConfigurazioneSettoriMgr mgr = new CCConfigurazioneSettoriMgr(authInfo.CreateDatabase());

            CCConfigurazioneSettori filtro = new CCConfigurazioneSettori();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;

            return mgr.GetList(filtro);
        }


        public DataTable GetAttivitaPerSettore(string idComune, int idCalcoloTot, string idSettore)
        {
            //TODO: query
            string sql = @"Select distinct 
								ATTIVITA.CODICEISTAT as Id, 
								ATTIVITA.ISTAT as Descrizione
							From
								CC_COEFFCONTRIB_ATTIVITA, 
								CC_CONDIZIONI_ATTIVITA, 
								ATTIVITA, 
								VW_CCICT_DESTINAZIONI, 
								CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_CONDIZIONI_ATTIVITA.IDCOMUNE = CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE and
								CC_CONDIZIONI_ATTIVITA.ID = CC_COEFFCONTRIB_ATTIVITA.FK_CCCA_ID and
								ATTIVITA.IDCOMUNE = CC_CONDIZIONI_ATTIVITA.IDCOMUNE and
								ATTIVITA.CODICEISTAT = CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} and
								ATTIVITA.CODICESETTORE = {2} order by ATTIVITA.ISTAT asc";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDCALCOLOTOT"),
                                        this.db.Specifics.QueryParameterName("IDSETTORE"));

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
                cmd.Parameters.Add(this.db.CreateParameter("IDSETTORE", idSettore));

                IDataAdapter adp = this.db.CreateDataAdapter(cmd);

                DataSet dsRet = new DataSet();

                adp.Fill(dsRet);

                return dsRet.Tables[0];
            }
        }


        public decimal GetCoefficienteDContributoAttiv(string idComune, int idCalcoloTot, string idAttivita)
        {
            //TODO: query
            string sql = @"Select 
								CC_COEFFCONTRIB_ATTIVITA.COEFFICIENTE
							From
								CC_COEFFCONTRIB_ATTIVITA, 
								CC_CONDIZIONI_ATTIVITA, 
								ATTIVITA, 
								VW_CCICT_DESTINAZIONI, 
								CC_ICALCOLOTOT
							Where
								VW_CCICT_DESTINAZIONI.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								VW_CCICT_DESTINAZIONI.CCICT_ID = CC_ICALCOLOTOT.ID and
								CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE = CC_ICALCOLOTOT.IDCOMUNE and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCDE_ID = VW_CCICT_DESTINAZIONI.CCDE_ID and
								CC_COEFFCONTRIB_ATTIVITA.FK_CCVC_ID = CC_ICALCOLOTOT.FK_CCVC_ID and
								CC_CONDIZIONI_ATTIVITA.IDCOMUNE = CC_COEFFCONTRIB_ATTIVITA.IDCOMUNE and
								CC_CONDIZIONI_ATTIVITA.ID = CC_COEFFCONTRIB_ATTIVITA.FK_CCCA_ID and
								ATTIVITA.IDCOMUNE = CC_CONDIZIONI_ATTIVITA.IDCOMUNE and
								ATTIVITA.CODICEISTAT = CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT and
								CC_ICALCOLOTOT.IDCOMUNE = {0} and
								CC_ICALCOLOTOT.ID = {1} and
								ATTIVITA.CODICEISTAT = {2}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IDCOMUNE"),
                                        this.db.Specifics.QueryParameterName("IDCALCOLOTOT"),
                                        this.db.Specifics.QueryParameterName("IDATTIVITA"));

            bool closecnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                closecnn = true;
            }

            try
            {
                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IDCOMUNE", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("IDCALCOLOTOT", idCalcoloTot));
                    cmd.Parameters.Add(this.db.CreateParameter("IDATTIVITA", idAttivita));

                    decimal? valore = null;

                    using (var rd = cmd.ExecuteReader())
                    {
                        while (rd.Read())
                        {
                            // In teoria si dovrebbe leggere un solo valore
                            if (valore.HasValue)
                            {
                                this._log.Error($"{idComune} Calcolo oneri: CCConfigurazioneSettoriMgr::GetCoefficienteDContributoAttiv ha restituito più di una riga => idComune={idComune}, idCalcoloTot={idCalcoloTot}, idAttivita={idAttivita}");

                                return valore.GetValueOrDefault(0.0m);
                            }
                            else
                            {
                                valore = Convert.ToDecimal(rd[0]);
                            }
                        }
                    }

                    return valore.GetValueOrDefault(0.0m);
                }
            }
            finally
            {
                if (closecnn)
                    this.db.Connection.Close();
            }
        }

        private void VerificaRecordCollegati(CCConfigurazioneSettori cls)
        {
            var sql = $@"SELECT 
	COUNT(*)
FROM 
	ATTIVITA
	
	INNER JOIN CC_CONDIZIONI_ATTIVITA ON 
	
		CC_CONDIZIONI_ATTIVITA.IDCOMUNE = ATTIVITA.IDCOMUNE AND
		CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT
	
WHERE 
	ATTIVITA.IDCOMUNE = {this.db.QueryParameter("idcomune")} AND 
	ATTIVITA.CODICESETTORE = {this.db.QueryParameter("codiceSettore")}";

            var cnt = this.db.ExecuteScalar(sql, 0, mp =>
                mp.Add("idcomune", cls.Idcomune)
                    .Add("codiceSettore", cls.FkSeCodicesettore)
            );

            if (cnt > 0)
                throw new ReferentialIntegrityException("CC_CONDIZIONI_ATTIVITA");
        }
    }
}
