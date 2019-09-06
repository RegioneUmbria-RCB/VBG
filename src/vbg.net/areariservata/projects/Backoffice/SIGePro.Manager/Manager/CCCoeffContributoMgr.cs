using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql.Collections;
using PersonalLib2.Sql;

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
			
			sql= String.Format( sql , db.Specifics.QueryParameterName( "idComune" ),
										db.Specifics.QueryParameterName( "software" ),
										db.Specifics.QueryParameterName( "ccvc" ),
										db.Specifics.QueryParameterName( "ccde" ) );

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


			using (IDbCommand cmd = db.CreateCommand(sql))
			{
				cmd.Parameters.Add( db.CreateParameter( "idComune" , idComune ) );
				cmd.Parameters.Add(db.CreateParameter("software", software));
				cmd.Parameters.Add(db.CreateParameter("ccvc", ccvcId));
				cmd.Parameters.Add(db.CreateParameter("ccde", ccdeId));

				DataClassCollection c = db.GetClassList(cmd, new CCCoeffContributo(), true, true);

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
            cls = DataIntegrations(cls);

            if (Update(cls) == 0)
            {
                Validate(cls, AmbitoValidazione.Insert);
                db.Insert(cls);
                cls = (CCCoeffContributo)ChildDataIntegrations(cls);

                ChildInsert(cls);
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
                                "COEFFICIENTE = " + cls.Coefficiente.ToString().Replace(",",".") + " " +
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
				if (db.Connection.State == ConnectionState.Closed)
				{
					internalOpen = true;
					db.Connection.Open();
				}

				using (IDbCommand cmd = db.CreateCommand(cmdText))
				{
					retVal = cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if ((db.Connection.State == ConnectionState.Open) && internalOpen)
				{
					db.Connection.Close();
				}
			}

            return retVal;
        }

        public void Delete(CCCoeffContributo cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

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

            if (db.Connection.State == ConnectionState.Closed)
            {
                internalOpen = true;
                db.Connection.Open();
            }

            using (IDbCommand cmd = db.CreateCommand(cmdText))
            {
                cmd.ExecuteNonQuery();
            }

            if ((db.Connection.State == ConnectionState.Open) && internalOpen)
            {
                db.Connection.Close();
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
				if (db.Connection.State == ConnectionState.Closed)
				{
					internalOpen = true;
					db.Connection.Open();
				}

				using (IDbCommand cmd = db.CreateCommand(cmdText))
				{
					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (internalOpen)
					db.Connection.Close();
			}
        }

		public List<CCCondizioniAttivita> GetAttivitaSelezionabili(string idComune, string software, int idIntervento, int areeCodiceArea)
		{
			CCConfigurazione cfg = new CCConfigurazioneMgr(db).GetById(idComune, software);

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


			sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
										db.Specifics.QueryParameterName("software"));

			if (idIntervento >= 0)
				sql += " and cc_coeffcontributo.fk_ccti_id = " + db.Specifics.QueryParameterName("idIntervento");

			if (areeCodiceArea >= 0)
				sql += " and cc_coeffcontributo.fk_aree_codicearea = " + db.Specifics.QueryParameterName("areeCodiceArea");
			else
				sql += " and cc_coeffcontributo.fk_aree_codicearea is null";

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("software", software));

					if (idIntervento >= 0)
						cmd.Parameters.Add(db.CreateParameter("idIntervento", idIntervento));

					if (areeCodiceArea >= 0)
						cmd.Parameters.Add(db.CreateParameter("areeCodiceArea", areeCodiceArea));

					CCCondizioniAttivita ca = new CCCondizioniAttivita();
					ca.UseForeign = useForeignEnum.Yes;

					return db.GetClassList(cmd, ca, false, true).ToList<CCCondizioniAttivita>();
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
