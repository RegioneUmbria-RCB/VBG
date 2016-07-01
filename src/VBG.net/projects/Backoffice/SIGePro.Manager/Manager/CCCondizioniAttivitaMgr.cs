using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions;
using System.Data;
using PersonalLib2.Sql;

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

            return (CCCondizioniAttivita)db.GetClass(c);
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

			CCConfigurazione cfg = new CCConfigurazioneMgr(db).GetById(idComune, software);

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

			sql = String.Format( sql ,	db.Specifics.QueryParameterName( "idComune" ),
										db.Specifics.QueryParameterName( "software" ) );

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

					CCCondizioniAttivita ca = new CCCondizioniAttivita();
					ca.UseForeign = useForeignEnum.Yes;

					return db.GetClassList(cmd, ca , false, true).ToList<CCCondizioniAttivita>();
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
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
			CCCondizioniAttivita ca = GetByCodiceIstat(idComune, codiceIstat);

			if (ca == null || String.IsNullOrEmpty(ca.Condizionewhere)) return false;

			CCICalcoli ccIc = new CCICalcoli();
			ccIc.Idcomune = idComune;
			ccIc.Id = idICalcolo;
			ccIc.OthersWhereClause.Add("(" + ca.Condizionewhere + ")");

			return db.GetClass(ccIc) != null;
		}

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCCondizioniAttivita> Find(string token, string codiceSettore)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCCondizioniAttivita filtro = new CCCondizioniAttivita();
            filtro.Idcomune = authInfo.IdComune;
            filtro.OthersTables.Add("ATTIVITA");
            filtro.OthersWhereClause.Add("CC_CONDIZIONI_ATTIVITA.IDCOMUNE = ATTIVITA.IDCOMUNE");
            filtro.OthersWhereClause.Add("CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT");

            if (!string.IsNullOrEmpty(codiceSettore))
                filtro.OthersWhereClause.Add("ATTIVITA.CODICESETTORE = '" + codiceSettore.Replace("'","''") + "'");

			return authInfo.CreateDatabase().GetClassList(filtro).ToList < CCCondizioniAttivita>();
        }

        private void VerificaRecordCollegati(CCCondizioniAttivita cls)
        {
            if (recordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCCA_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCCA_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");

        }

    }
}
