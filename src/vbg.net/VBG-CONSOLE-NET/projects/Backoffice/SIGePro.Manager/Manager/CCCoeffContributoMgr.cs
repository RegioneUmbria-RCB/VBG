using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class CCCoeffContributoMgr
    {
        private CCCoeffContributo DataIntegrations(CCCoeffContributo cls)
        {
            if (cls.Coefficiente.GetValueOrDefault(float.MinValue) == float.MinValue)
                cls.Coefficiente = 0;

            return cls;
        }


        public CCCoeffContributo GetRow(string idComune, string software, int ccvcId, int? areeCodicearea, int ccdeId, int? idTipoIntervento, int? cccaId)
        {
            string sql = @"select 
							  * 
							from 
							  cc_coeffcontributo 
							where 
							  idcomune = {0} and
							  software = {1} and
							  fk_ccvc_id = {2} and
							  fk_ccde_id = {3} ";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("software"),
                                        this.db.Specifics.QueryParameterName("ccvc"),
                                        this.db.Specifics.QueryParameterName("ccde"));

            if (cccaId.HasValue)
                sql += " and fk_ccca_id = " + cccaId.Value.ToString();
            else
                sql += " and fk_ccca_id is null";

            if (areeCodicearea.HasValue)
                sql += " and fk_aree_codicearea = " + areeCodicearea.Value;
            else
                sql += " and fk_aree_codicearea is null";

            if (idTipoIntervento.HasValue)
                sql += " and fk_ccti_id = " + idTipoIntervento.Value.ToString();
            else
                sql += " and fk_ccti_id is null ";


            using (IDbCommand cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("software", software));
                cmd.Parameters.Add(this.db.CreateParameter("ccvc", ccvcId));
                cmd.Parameters.Add(this.db.CreateParameter("ccde", ccdeId));

                List<CCCoeffContributo> c = this.db.GetClassList<CCCoeffContributo>(cmd, new GetClassListFlags { SingleRowException = true });

                if (c.Count == 0) return null;

                return (CCCoeffContributo)c[0];
            }

        }

        private CCCoeffContributo Insert(CCCoeffContributo cls)
        {
            throw new NotImplementedException("Il metodo CCoeffContributoMgr.Insert non è implementabile. Utilizzare il metodo CCoeffContributoMgr.Save");
        }

        public CCCoeffContributo Save(CCCoeffContributo cls)
        {
            cls = this.DataIntegrations(cls);

            if (this.Update(cls) == 0)
            {
                this.Validate(cls, AmbitoValidazione.Insert);
                this.db.Insert(cls);
                cls = (CCCoeffContributo)this.ChildDataIntegrations(cls);

                this.ChildInsert(cls);
            }

            return cls;
        }

        private int Update(CCCoeffContributo cls)
        {
            int retVal = 0;
            bool internalOpen = false;

            //TODO: query
            string cmdText = "UPDATE " +
                                "CC_COEFFCONTRIBUTO " +
                             "SET " +
                                "COEFFICIENTE = " + cls.Coefficiente.ToString().Replace(",", ".") + " " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_CCVC_ID = " + cls.FkCcvcId.ToString() + " AND " +
                                "FK_CCDE_ID = " + cls.FkCcdeId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (cls.FkCctiId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCTI_ID = " + cls.FkCctiId.ToString();

            if (cls.FkCccaId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCCA_ID = " + cls.FkCccaId.ToString();

            if (cls.FkAreeCodicearea.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_AREE_CODICEAREA = " + cls.FkAreeCodicearea.ToString();

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    internalOpen = true;
                    this.db.Connection.Open();
                }

                using (IDbCommand cmd = this.db.CreateCommand(cmdText))
                {
                    retVal = cmd.ExecuteNonQuery();
                }
            }
            finally
            {
                if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
                {
                    this.db.Connection.Close();
                }
            }

            return retVal;
        }

        public void Delete(CCCoeffContributo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            //TODO: query
            bool internalOpen = false;
            string cmdText = "DELETE FROM " +
                                "CC_COEFFCONTRIBUTO " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_CCVC_ID = " + cls.FkCcvcId.ToString();

            if (cls.Id.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND ID = " + cls.Id.ToString();

            if (cls.FkCcdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCDE_ID = " + cls.FkCcdeId.ToString();

            if (cls.FkCctiId.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_CCTI_ID = " + cls.FkCctiId.ToString();

            if (cls.FkAreeCodicearea.GetValueOrDefault(int.MinValue) != int.MinValue)
                cmdText += " AND FK_AREE_CODICEAREA = " + cls.FkAreeCodicearea.ToString();

            foreach (string owc in cls.OthersWhereClause)
            {
                cmdText += " AND " + owc;
            }

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                this.db.Connection.Open();
            }

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((this.db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                this.db.Connection.Close();
            }
        }

        public void DeleteSingleRow(CCCoeffContributo cls)
        {
            //TODO: query
            bool internalOpen = false;
            string cmdText = "DELETE FROM " +
                                "CC_COEFFCONTRIBUTO " +
                             "WHERE " +
                                "IDCOMUNE = '" + cls.Idcomune + "' AND " +
                                "SOFTWARE = '" + cls.Software + "' AND " +
                                "FK_CCVC_ID = " + cls.FkCcvcId.ToString() + " AND " +
                                "FK_CCDE_ID = " + cls.FkCcdeId.ToString();

            cmdText += (cls.FkCccaId.GetValueOrDefault(int.MinValue) == int.MinValue) ? " and fk_ccca_id is null " : " and fk_ccca_id = " + cls.FkCccaId.ToString();
            cmdText += (cls.FkCctiId.GetValueOrDefault(int.MinValue) == int.MinValue) ? " AND FK_CCTI_ID IS NULL " : " AND FK_CCTI_ID = " + cls.FkCctiId.ToString();
            cmdText += (cls.FkAreeCodicearea.GetValueOrDefault(int.MinValue) == int.MinValue) ? " AND FK_AREE_CODICEAREA IS NULL " : " AND FK_AREE_CODICEAREA = " + cls.FkAreeCodicearea.ToString();

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    internalOpen = true;
                    this.db.Connection.Open();
                }

                using (IDbCommand cmd = this.db.CreateCommand(cmdText))
                {
                    cmd.ExecuteNonQuery();
                }
            }
            finally
            {
                if (internalOpen)
                    this.db.Connection.Close();
            }
        }

        public List<CCCondizioniAttivita> GetAttivitaSelezionabili(string idComune, string software, int idIntervento, int areeCodiceArea)
        {
            CCConfigurazione cfg = new CCConfigurazioneMgr(this.db).GetById(idComune, software);

            if (String.IsNullOrEmpty(cfg.FkSeCodicesettore)) return new List<CCCondizioniAttivita>();

            // query

            string sql = @"SELECT distinct
							cc_condizioni_attivita.* 
						FROM 
							cc_condizioni_attivita,
							attivita,
							cc_configurazione,
							cc_coeffcontributo
						WHERE
							cc_coeffcontributo.idcomune     = cc_configurazione.idcomune AND
							cc_coeffcontributo.software     = cc_configurazione.software AND
							cc_condizioni_attivita.Idcomune = cc_coeffcontributo.idcomune AND
							cc_condizioni_attivita.Software = cc_coeffcontributo.software AND
							cc_condizioni_attivita.Id       = cc_coeffcontributo.FK_CCCA_ID and
							attivita.idcomune               = cc_condizioni_attivita.Idcomune AND
							attivita.codiceistat            = cc_condizioni_attivita.fk_at_codiceistat AND
							attivita.codicesettore          = cc_configurazione.fk_se_codicesettore AND
							cc_configurazione.idcomune      = {0} AND
							cc_configurazione.software      = {1}";


            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("software"));

            if (idIntervento >= 0)
                sql += " and cc_coeffcontributo.fk_ccti_id = " + this.db.Specifics.QueryParameterName("idIntervento");

            if (areeCodiceArea >= 0)
                sql += " and cc_coeffcontributo.fk_aree_codicearea = " + this.db.Specifics.QueryParameterName("areeCodiceArea");
            else
                sql += " and cc_coeffcontributo.fk_aree_codicearea is null";

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

                    if (idIntervento >= 0)
                        cmd.Parameters.Add(this.db.CreateParameter("idIntervento", idIntervento));

                    if (areeCodiceArea >= 0)
                        cmd.Parameters.Add(this.db.CreateParameter("areeCodiceArea", areeCodiceArea));

                    CCCondizioniAttivita ca = new CCCondizioniAttivita();
                    ca.UseForeign = useForeignEnum.Yes;

                    return this.db.GetClassList<CCCondizioniAttivita>(cmd, new GetClassListFlags(useForeignEnum.Yes));
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
