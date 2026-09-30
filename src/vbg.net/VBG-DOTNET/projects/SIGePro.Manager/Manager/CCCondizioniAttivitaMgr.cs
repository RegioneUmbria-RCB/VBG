using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCCondizioniAttivitaMgr
    {
        /// <summary>
        /// Ottiene una condizione attività in base al codice istat dell'attività
        /// </summary>
        /// <param name="idcomune"></param>
        /// <param name="CodiceIstat"></param>
        /// <returns></returns>
        public CCCondizioniAttivita GetByCodiceIstat(string idcomune, string CodiceIstat)
        {
            CCCondizioniAttivita c = new CCCondizioniAttivita();

            c.Idcomune = idcomune;
            c.FkAtCodiceistat = CodiceIstat;

            return this.db.GetClass(c);
        }

        /// <summary>
        /// Ottiene la lista delle condizioni attività utilizzabili per la determinazione del contributo
        /// in base al codicesettore specificato nella configurazione
        /// </summary>
        /// <param name="idComune"></param>
        /// <param name="software"></param>
        /// <returns></returns>
        public List<CCCondizioniAttivita> GetListByCodiceSettoreConfigurazione(string idComune, string software)
        {
            List<CCCondizioniAttivita> rVal = new List<CCCondizioniAttivita>();

            CCConfigurazione cfg = new CCConfigurazioneMgr(this.db).GetById(idComune, software);

            if (String.IsNullOrEmpty(cfg.FkSeCodicesettore)) return rVal;

            // query

            string sql = @"SELECT 
							cc_condizioni_attivita.* 
						FROM 
							cc_condizioni_attivita,
							attivita,
							cc_configurazione
						WHERE
							cc_condizioni_attivita.Idcomune = cc_configurazione.idcomune AND
							cc_condizioni_attivita.Software = cc_configurazione.software AND
							attivita.idcomune               = cc_condizioni_attivita.Idcomune AND
							attivita.codiceistat            = cc_condizioni_attivita.fk_at_codiceistat AND
							attivita.codicesettore          = cc_configurazione.fk_se_codicesettore AND
							cc_configurazione.idcomune      = {0} AND
							cc_configurazione.software      = {1}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("software"));

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("software", software));

                    // CCCondizioniAttivita ca = new CCCondizioniAttivita();
                    // ca.UseForeign = useForeignEnum.Yes;

                    // return db.GetClassList(cmd, ca , false, true).ToList<CCCondizioniAttivita>();
                    return this.db.GetClassList<CCCondizioniAttivita>(cmd, new GetClassListFlags
                    {
                        UseForeign = PersonalLib2.Sql.useForeignEnum.Yes,
                        SingleRowException = false
                    });
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        /// <summary>
        /// verifica se una condizione attività può essere applicata al calcolo corrispondente all'id passato
        /// </summary>
        /// <param name="idComune"></param>
        /// <param name="idICalcolo"></param>
        /// <param name="codiceIstat"></param>
        /// <returns></returns>
        public bool VerificaCondizioneAttivita(string idComune, int idICalcolo, string codiceIstat)
        {
            CCCondizioniAttivita ca = this.GetByCodiceIstat(idComune, codiceIstat);

            if (ca == null || String.IsNullOrEmpty(ca.Condizionewhere)) return false;

            CCICalcoli ccIc = new CCICalcoli();
            ccIc.Idcomune = idComune;
            ccIc.Id = idICalcolo;
            ccIc.OthersWhereClause.Add("(" + ca.Condizionewhere + ")");

            return this.db.GetClass(ccIc) != null;
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCCondizioniAttivita> Find(string token, string codiceSettore)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCCondizioniAttivita filtro = new CCCondizioniAttivita();
            filtro.Idcomune = authInfo.IdComune;
            filtro.OthersTables.Add("ATTIVITA");
            filtro.OthersWhereClause.Add("CC_CONDIZIONI_ATTIVITA.IDCOMUNE = ATTIVITA.IDCOMUNE");
            filtro.OthersWhereClause.Add("CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT");

            if (!string.IsNullOrEmpty(codiceSettore))
                filtro.OthersWhereClause.Add("ATTIVITA.CODICESETTORE = '" + codiceSettore.Replace("'", "''") + "'");

            return authInfo.CreateDatabase().GetClassList(filtro).ToList<CCCondizioniAttivita>();
        }

        private void VerificaRecordCollegati(CCCondizioniAttivita cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCCA_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCCA_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");
        }

    }
}
