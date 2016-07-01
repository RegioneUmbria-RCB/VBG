
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class Dyn2CampiMgr : IDyn2CampiManager
    {
		[DataObjectMethod(DataObjectMethodType.Select)]
		public static List<Dyn2Campi> Find(string token, string software, string codice,string nomeCampo , string etichetta, string sortExpression )
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken( token );

			Dyn2CampiMgr mgr = new Dyn2CampiMgr( authInfo.CreateDatabase() );

			
			Dyn2Campi filtroCompare = new Dyn2Campi();

			var filtro = new Dyn2Campi
			{
				Software = software,
				Idcomune = authInfo.IdComune,
				Nomecampo = nomeCampo,
				Etichetta = etichetta,
				Id = String.IsNullOrEmpty(codice) ? (int?)null : Convert.ToInt32(codice),
			};

			filtroCompare.Nomecampo = "LIKE";
			filtroCompare.Etichetta = "LIKE";

			List<Dyn2Campi> list =  authInfo.CreateDatabase()
											.GetClassList(filtro, filtroCompare, false, true)
											.ToList<Dyn2Campi>();

			ListSortManager<Dyn2Campi>.Sort(list, sortExpression);

			return list;
		}

		public static List<KeyValuePair<int,string>> FindIdDescrizione(string token, string idModello , bool firstBlank)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);
			DataBase db = authInfo.CreateDatabase();

			string sql = @"SELECT distinct
							  dyn2_campi.nomecampo,
							  dyn2_campi.Id as IdCampo 
							FROM
							  dyn2_campi,
							  dyn2_modelliD,
							  dyn2_modellit
							WHERE
							  dyn2_modelliD.idComune    = dyn2_modellit.IdComune AND
							  dyn2_modelliD.fk_d2mt_id  = dyn2_modellit.Id and
							  dyn2_campi.idComune = dyn2_modelliD.idComune AND
							  dyn2_campi.id = dyn2_modelliD.fk_d2c_id AND
							  dyn2_modellit.IdComune = {0} AND
							  dyn2_modellit.Id like {1}
							order by dyn2_campi.nomecampo asc";

			sql = String.Format( sql , db.Specifics.QueryParameterName( "idComune" ),
										db.Specifics.QueryParameterName( "idModello" ) );
			bool bCloseCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					bCloseCnn = true;
				}

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", authInfo.IdComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", String.IsNullOrEmpty( idModello ) ? "%" : idModello ) );

					using( IDataReader rd = cmd.ExecuteReader() )
					{
						List<KeyValuePair<int,string>> ret = new List<KeyValuePair<int,string>>();

						while(rd.Read())
						{
							string nomecampo = rd["nomecampo"].ToString();
							int idCampo = Convert.ToInt32(rd["idCampo"] );
							
							ret.Add( new KeyValuePair<int,string>( idCampo , nomecampo ) );
						}

						if (firstBlank)
							ret.Insert(0, new KeyValuePair<int, string>(-1, ""));

						return ret;
					}
				}

			}
			finally
			{
				if (bCloseCnn)
					db.Connection.Close();
			}
		}

		public List<Dyn2CampiProprieta> GetProprietaControllo(string idComune, int id)
		{
			Dyn2CampiProprieta filtro = new Dyn2CampiProprieta();
			filtro.Idcomune = idComune;
			filtro.FkD2cId = id;

			return new Dyn2CampiProprietaMgr(db).GetList(filtro);
		}

		public List<Dyn2Campi> GetListaCampiDaSoftwareContesto(string idComune, string software , string idContesto)
		{
			Dyn2Campi filtro = new Dyn2Campi();
			filtro.Idcomune = idComune;
			filtro.OthersWhereClause.Add("(SOFTWARE='" + software + "' OR SOFTWARE='')");
			filtro.OthersWhereClause.Add("(fk_d2bc_id='" + idContesto + "' OR fk_d2bc_id is null)");

			return GetList(filtro);
		}

		private void VerificaRecordCollegati(Dyn2Campi cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle

            // Verifico se un campo è presente in un modello
            Dyn2ModelliD filtroModelli = new Dyn2ModelliD();
            filtroModelli.Idcomune = cls.Idcomune;
            filtroModelli.FkD2cId = cls.Id;

            List<Dyn2ModelliD> modelli = new Dyn2ModelliDMgr(db).GetList(filtroModelli);

            if (modelli.Count > 0)
                throw new ReferentialIntegrityException("DYN2_MODELLID");

            // Verifico se un campo è presente in ANAGRAFEDYN2DATI
            AnagrafeDyn2Dati filtroAnagrafe = new AnagrafeDyn2Dati();
            filtroAnagrafe.Idcomune = cls.Idcomune;
            filtroAnagrafe.FkD2cId = cls.Id;

            List<AnagrafeDyn2Dati> anagrafe = new AnagrafeDyn2DatiMgr(db).GetList(filtroAnagrafe);

            if (anagrafe.Count > 0)
                throw new ReferentialIntegrityException("ANAGRAFEDYN2DATI");

            // Verifico se un campo è presente in I_ATTIVITADYN2DATI
            IAttivitaDyn2Dati filtroAttivita = new IAttivitaDyn2Dati();
            filtroAttivita.Idcomune = cls.Idcomune;
            filtroAttivita.FkD2cId = cls.Id;

            List<IAttivitaDyn2Dati> attivita = new IAttivitaDyn2DatiMgr(db).GetList(filtroAttivita);

            if (attivita.Count > 0)
                throw new ReferentialIntegrityException("I_ATTIVITADYN2DATI");


            // Verifico se un campo è presente in ISTANZEDYN2DATI
            IstanzeDyn2Dati filtroIstanze = new IstanzeDyn2Dati();
            filtroIstanze.Idcomune = cls.Idcomune;
            filtroIstanze.FkD2cId = cls.Id;

            List<IstanzeDyn2Dati> istanze = new IstanzeDyn2DatiMgr(db).GetList(filtroIstanze);

            if (istanze.Count > 0)
                throw new ReferentialIntegrityException("ISTANZEDYN2DATI");

            // Verifico se un campo è presente in TIPIGRADUATORIED
            TipiGraduatorieD filtroTipiGraduatorieD = new TipiGraduatorieD();
            filtroTipiGraduatorieD.Idcomune = cls.Idcomune;
            filtroTipiGraduatorieD.FkD2cId = cls.Id;

            List<TipiGraduatorieD> listTipiGraduatorieD = new TipiGraduatorieDMgr(db).GetList(filtroTipiGraduatorieD);

            if (listTipiGraduatorieD.Count > 0)
                throw new ReferentialIntegrityException("TIPIGRADUATORIED");

            // Verifico se un campo è presente in TIPIBANDOCAMPIGRADUAT
            TipiBandoCampiGraduat filtroTipiBandoCampiGraduat = new TipiBandoCampiGraduat();
            filtroTipiBandoCampiGraduat.Idcomune = cls.Idcomune;
            filtroTipiBandoCampiGraduat.FkD2cId = cls.Id;

            List<TipiBandoCampiGraduat> listTipiBandoCampiGraduat = new TipiBandoCampiGraduatMgr(db).GetList(filtroTipiBandoCampiGraduat);

            if (listTipiBandoCampiGraduat.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOCAMPIGRADUAT");

            // Verifico se un campo è presente in ( FK_D2C_ID_RIF )
            TipiBandoOutput filtroTipiBandoOutput = new TipiBandoOutput();
            filtroTipiBandoOutput.Idcomune = cls.Idcomune;
            filtroTipiBandoOutput.FkD2cIdRif = cls.Id;

            List<TipiBandoOutput> listTipiBandoOutput = new TipiBandoOutputMgr(db).GetList(filtroTipiBandoOutput);

            if (listTipiBandoOutput.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOOUTPUT");

            // Verifico se un campo è presente in TIPIBANDOOUTPUT ( FK_D2C_ID_OUT )
            TipiBandoOutput filtroTipiBandoOutput2 = new TipiBandoOutput();
            filtroTipiBandoOutput2.Idcomune = cls.Idcomune;
            filtroTipiBandoOutput2.FkD2cIdOut = cls.Id;

            List<TipiBandoOutput> listTipiBandoOutput2 = new TipiBandoOutputMgr(db).GetList(filtroTipiBandoOutput2);

            if (listTipiBandoOutput2.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOOUTPUT");

            // Verifico se un campo è presente in CAMPIGRADUATORIA
            CampiGraduatoria filtroCampiGraduatoria = new CampiGraduatoria();
            filtroCampiGraduatoria.Idcomune = cls.Idcomune;
            filtroCampiGraduatoria.FkD2cId = cls.Id;

            List<CampiGraduatoria> listCampiGraduatoria = new CampiGraduatoriaMgr(db).GetList(filtroCampiGraduatoria);

            if (listCampiGraduatoria.Count > 0)
                throw new ReferentialIntegrityException("CAMPIGRADUATORIA");

			bool closeConnection = db.Connection.State != ConnectionState.Open;

			try
			{
				if (closeConnection)
					db.Connection.Open();

				string cmdText = "SELECT COUNT(*) FROM MERCATI_IDENTAUT WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_D2CID = " + cls.Id.ToString();
				using (IDbCommand cmd = db.CreateCommand(cmdText))
				{
					object obj = cmd.ExecuteScalar();
					if (obj != null && Convert.ToInt32(obj) > 0)
					{
						throw new ReferentialIntegrityException("MERCATI_IDENTAUT");
					}
				}
			}
			catch (Exception ex)
			{
				if (closeConnection)
					db.Connection.Close();
			}
		}

		private void EffettuaCancellazioneACascata(Dyn2Campi cls)
		{
			// Inserire la logica di cancellazione a cascata di dati collegati

			// Eliminazione delle proprietà del campo
			Dyn2CampiProprietaMgr mgrProprieta = new Dyn2CampiProprietaMgr(db);

			Dyn2CampiProprieta filtroProp = new Dyn2CampiProprieta();
			filtroProp.Idcomune = cls.Idcomune;
			filtroProp.FkD2cId = cls.Id;

			List < Dyn2CampiProprieta> proprieta = mgrProprieta.GetList(filtroProp);

			for (int i = 0; i < proprieta.Count; i++)
				mgrProprieta.Delete(proprieta[i]);

		}

		/// <summary>
		/// Verifica se il campo è presente in qualche modello in una riga flaggata come multipla
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="idCampo"></param>
		/// <returns></returns>
		public bool VerificaPresenzaInRigheMultiple(string idComune, int idCampo)
		{
			int cnt = recordCount("dyn2_modellid", "*", @"WHERE idcomune = '" + idComune + @"' AND
																fk_d2c_id = " + idCampo + @" AND
																flg_multiplo = 1 ");

			return cnt > 0;
		}

		#region IDyn2CampiManager Members

		IDyn2Campo IDyn2CampiManager.GetById(string idComune, int idCampo)
		{
			return GetById(idComune, idCampo);
		}


		public List<Dyn2Campi> GetList(string idComune, int idModello)
		{ 
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica(@"SELECT distinct
														  dyn2_campi.* 
														FROM 
														  dyn2_modellid,
														  dyn2_campi
														WHERE
														  dyn2_campi.idcomune = dyn2_modellid.idcomune AND                            
														  dyn2_campi.id = dyn2_modellid.fk_d2c_id AND                  
														  dyn2_modellid.idcomune = {0} and
														  dyn2_modellid.fk_d2mt_id = {1}",
														"idComune", "idModello");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("idModello", idModello));

					return db.GetClassList<Dyn2Campi>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
		{
			var lista = GetList(idComune, idModello);

			var rVal = new SerializableDictionary<int, IDyn2Campo>();

			lista.ForEach(x => rVal.Add(x.Id.Value, x));

			return rVal;

		}

		#endregion
	}
}
				