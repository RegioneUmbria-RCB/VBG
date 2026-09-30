
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2CampiMgr
    {
        /// <summary>
        /// Metodo invocato prima della creazione di un nuovo record,
        /// verifica che non ci siano già campi con lo stesso nome all'interno dello stesso software
        /// </summary>
        /// <param name="cls"></param>
        private void OnBeforeInsert(Dyn2Campi cls)
        {
            var sql = $@"SELECT 
	                        COUNT(*) 
                        FROM 
	                        dyn2_campi
                        WHERE
	                        idcomune={this.db.QueryParameter("idcomune")} AND
	                        software={this.db.QueryParameter("software")} AND
	                        nomecampo={this.db.QueryParameter("nomecampo")}";

            var count = this.db.ExecuteScalar(sql, 0,
                mp => mp.Add("idcomune", cls.Idcomune)
                        .Add("software", cls.Software)
                        .Add("nomecampo", cls.Nomecampo.ToUpper()));

            if (count > 0)
            {
                throw new Exception($"Nel software {cls.Software} esiste già un campo con nome {cls.Nomecampo}");
            }
        }

        /// <summary>
        /// Metodo invocato prima della creazione di un nuovo record,
        /// verifica che non ci siano già campi con lo stesso nome all'interno dello stesso software
        /// </summary>
        /// <param name="cls"></param>
        private void OnBeforeUpdate(Dyn2Campi cls)
        {
            var sql = $@"SELECT 
	                        COUNT(*) 
                        FROM 
	                        dyn2_campi
                        WHERE
	                        idcomune={this.db.QueryParameter("idcomune")} AND
	                        software={this.db.QueryParameter("software")} AND
	                        nomecampo={this.db.QueryParameter("nomecampo")} AND
                            id <> {this.db.QueryParameter("id")}";

            var count = this.db.ExecuteScalar(sql, 0,
                mp => mp.Add("idcomune", cls.Idcomune)
                        .Add("software", cls.Software)
                        .Add("nomecampo", cls.Nomecampo.ToUpper())
                        .Add("id", cls.Id.Value));

            if (count > 0)
            {
                throw new Exception($"Nel software {cls.Software} esiste già un campo con nome {cls.Nomecampo}");
            }
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<Dyn2Campi> Find(string token, string software, string codice, string nomeCampo, string etichetta, string sortExpression)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

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

            List<Dyn2Campi> list = authInfo.CreateDatabase()
                                            .GetClassList(filtro, filtroCompare)
                                            .ToList<Dyn2Campi>();

            ListSortManager<Dyn2Campi>.Sort(list, sortExpression);

            return list;
        }

        public static List<KeyValuePair<int, string>> FindIdDescrizione(string token, string idModello, bool firstBlank)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            var db = authInfo.CreateDatabase();

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

            sql = String.Format(sql, db.Specifics.QueryParameterName("idComune"),
                                        db.Specifics.QueryParameterName("idModello"));
            bool bCloseCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    bCloseCnn = true;
                }

                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", authInfo.IdComune));
                    cmd.Parameters.Add(db.CreateParameter("idModello", String.IsNullOrEmpty(idModello) ? "%" : idModello));

                    using (var rd = cmd.ExecuteReader())
                    {
                        List<KeyValuePair<int, string>> ret = new List<KeyValuePair<int, string>>();

                        while (rd.Read())
                        {
                            string nomecampo = rd["nomecampo"].ToString();
                            int idCampo = Convert.ToInt32(rd["idCampo"]);

                            ret.Add(new KeyValuePair<int, string>(idCampo, nomecampo));
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
            Dyn2CampiProprieta filtro = new Dyn2CampiProprieta
            {
                Idcomune = idComune,
                FkD2cId = id
            };

            return new Dyn2CampiProprietaMgr(this.db).GetList(filtro);
        }

        private void VerificaRecordCollegati(Dyn2Campi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle

            // Verifico se un campo è presente in un modello
            Dyn2ModelliD filtroModelli = new Dyn2ModelliD
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var modelli = new Dyn2ModelliDMgr(this.db).GetList(filtroModelli);

            if (modelli.Count > 0)
                throw new ReferentialIntegrityException("DYN2_MODELLID");

            // Verifico se un campo è presente in ANAGRAFEDYN2DATI
            AnagrafeDyn2Dati filtroAnagrafe = new AnagrafeDyn2Dati
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var anagrafe = new AnagrafeDyn2DatiMgr(this.db).GetList(filtroAnagrafe);

            if (anagrafe.Count > 0)
                throw new ReferentialIntegrityException("ANAGRAFEDYN2DATI");

            // Verifico se un campo è presente in I_ATTIVITADYN2DATI
            var sql = $@"SELECT Count(*) FROM i_attivitadyn2dati 
							WHERE 
								idcomune={this.db.Specifics.QueryParameterName("idComune")} AND 
								fk_d2c_id={this.db.Specifics.QueryParameterName("idCampo")}";

            var count = this.db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune", cls.Idcomune);
                mp.AddParameter("idCampo", cls.Id);
            });

            if (count > 0)
                throw new ReferentialIntegrityException("I_ATTIVITADYN2DATI");

            // Verifico se un campo è presente in ISTANZEDYN2DATI
            IstanzeDyn2Dati filtroIstanze = new IstanzeDyn2Dati
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var istanze = new IstanzeDyn2DatiMgr(this.db).GetList(filtroIstanze);

            if (istanze.Count > 0)
                throw new ReferentialIntegrityException("ISTANZEDYN2DATI");

            // Verifico se un campo è presente in TIPIGRADUATORIED
            TipiGraduatorieD filtroTipiGraduatorieD = new TipiGraduatorieD
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var listTipiGraduatorieD = new TipiGraduatorieDMgr(this.db).GetList(filtroTipiGraduatorieD);

            if (listTipiGraduatorieD.Count > 0)
                throw new ReferentialIntegrityException("TIPIGRADUATORIED");

            // Verifico se un campo è presente in TIPIBANDOCAMPIGRADUAT
            TipiBandoCampiGraduat filtroTipiBandoCampiGraduat = new TipiBandoCampiGraduat
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var listTipiBandoCampiGraduat = new TipiBandoCampiGraduatMgr(this.db).GetList(filtroTipiBandoCampiGraduat);

            if (listTipiBandoCampiGraduat.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOCAMPIGRADUAT");

            // Verifico se un campo è presente in ( FK_D2C_ID_RIF )
            TipiBandoOutput filtroTipiBandoOutput = new TipiBandoOutput
            {
                Idcomune = cls.Idcomune,
                FkD2cIdRif = cls.Id
            };

            var listTipiBandoOutput = new TipiBandoOutputMgr(this.db).GetList(filtroTipiBandoOutput);

            if (listTipiBandoOutput.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOOUTPUT");

            // Verifico se un campo è presente in TIPIBANDOOUTPUT ( FK_D2C_ID_OUT )
            TipiBandoOutput filtroTipiBandoOutput2 = new TipiBandoOutput
            {
                Idcomune = cls.Idcomune,
                FkD2cIdOut = cls.Id
            };

            var listTipiBandoOutput2 = new TipiBandoOutputMgr(this.db).GetList(filtroTipiBandoOutput2);

            if (listTipiBandoOutput2.Count > 0)
                throw new ReferentialIntegrityException("TIPIBANDOOUTPUT");

            // Verifico se un campo è presente in CAMPIGRADUATORIA
            CampiGraduatoria filtroCampiGraduatoria = new CampiGraduatoria
            {
                Idcomune = cls.Idcomune,
                FkD2cId = cls.Id
            };

            var listCampiGraduatoria = new CampiGraduatoriaMgr(this.db).GetList(filtroCampiGraduatoria);

            if (listCampiGraduatoria.Count > 0)
                throw new ReferentialIntegrityException("CAMPIGRADUATORIA");

            bool closeConnection = this.db.Connection.State != ConnectionState.Open;

            try
            {
                if (closeConnection)
                    this.db.Connection.Open();

                string cmdText = "SELECT COUNT(*) FROM MERCATI_IDENTAUT WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_D2CID = " + cls.Id.ToString();
                using (var cmd = this.db.CreateCommand(cmdText))
                {
                    object obj = cmd.ExecuteScalar();
                    if (obj != null && Convert.ToInt32(obj) > 0)
                    {
                        throw new ReferentialIntegrityException("MERCATI_IDENTAUT");
                    }
                }
            }
            catch (Exception)
            {
                if (closeConnection)
                    this.db.Connection.Close();
            }
        }

        private void EffettuaCancellazioneACascata(Dyn2Campi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati

            // Eliminazione delle proprietà del campo
            Dyn2CampiProprietaMgr mgrProprieta = new Dyn2CampiProprietaMgr(this.db);

            Dyn2CampiProprieta filtroProp = new Dyn2CampiProprieta();
            filtroProp.Idcomune = cls.Idcomune;
            filtroProp.FkD2cId = cls.Id;

            var proprieta = mgrProprieta.GetList(filtroProp);

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
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("idcomune", idComune),
                new KeyValuePair<string, string>("fk_d2c_id", idCampo.ToString()),
                new KeyValuePair<string, string>("flg_multiplo", "1")
            };

            int cnt = this.recordCount("dyn2_modellid", "*", conditions);

            return cnt > 0;
        }

        public int? GetIdCampoDaNome(string idComune, string software, string nomeCampo)
        {
            var id = this.GetIdCampoDaNomeInternal(idComune, software, nomeCampo);
            if (!id.HasValue)
            {
                id = this.GetIdCampoDaNomeInternal(idComune, "TT", nomeCampo);
            }
            return id;
        }

        private int? GetIdCampoDaNomeInternal(string idComune, string software, string nomeCampo)
        {
            string cmdText = $@"SELECT ID FROM DYN2_CAMPI 
                                WHERE 
                                    IDCOMUNE = {this.db.QueryParameter("idComune")} AND 
                                    SOFTWARE = {this.db.QueryParameter("software")} AND 
                                    NOMECAMPO = {this.db.QueryParameter("nomeCampo")}";

            var id = this.db.ExecuteReader(cmdText,
                                        par => par.Add("idComune", idComune)
                                                  .Add("software", software)
                                                  .Add("nomeCampo", nomeCampo),
                                        dr => dr.GetInt("ID")).FirstOrDefault();
            return id;
        }

        #region IDyn2CampiManager Members

        public IDyn2Campo GetDyn2CampoById(string idComune, int idCampo) => this.GetById(idComune, idCampo);


        public List<Dyn2Campi> GetList(string idComune, int idModello)
        {
            FormattableString sql = $@"SELECT distinct
										dyn2_campi.* 
									FROM 
										dyn2_modellid,
										dyn2_campi
									WHERE
										dyn2_campi.idcomune = dyn2_modellid.idcomune AND                            
										dyn2_campi.id = dyn2_modellid.fk_d2c_id AND                  
										dyn2_modellid.idcomune = {idComune} and
										dyn2_modellid.fk_d2mt_id = {idModello}";

            return this.db.GetClassList<Dyn2Campi>(sql);
        }

        public IEnumerable<CampoDinamicoDto> GetCampiDinamiciByIdModello(string idComune, int idModello)
        {
            string sql = $@"
                SELECT
	                dyn2_campi.*
                FROM 
	                dyn2_modellid
	
		                INNER JOIN dyn2_campi ON 
			                dyn2_campi.idComune = dyn2_modellid.idComune AND
			                dyn2_campi.id = dyn2_modellid.fk_d2c_id
                WHERE 	
	                dyn2_modellid.idComune = {this.db.QueryParameter("idComune")} AND
	                dyn2_modellid.fk_d2mt_id = {this.db.QueryParameter("idModello")}";


            return this.db.ExecuteReader(sql,
                             mp => mp.Add("idcomune", idComune)
                                     .Add("idModello", idModello),
                             dr => new CampoDinamicoDto
                             {
                                 Descrizione = dr.GetString("DESCRIZIONE"),
                                 Etichetta = dr.GetString("etichetta"),
                                 FkD2bcId = dr.GetString("FK_D2BC_ID"),
                                 Id = dr.GetInt("id").Value,
                                 Nomecampo = dr.GetString("NOMECAMPO"),
                                 Obbligatorio = dr.GetInt("OBBLIGATORIO").GetValueOrDefault(0),
                                 Tipodato = dr.GetString("TIPODATO")
                             });
        }

        #endregion
    }
}
