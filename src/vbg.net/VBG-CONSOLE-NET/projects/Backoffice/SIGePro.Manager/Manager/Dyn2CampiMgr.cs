
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2CampiMgr : IDyn2CampiManager
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<Dyn2Campi> Find(string token, string software, string codice, string nomeCampo, string etichetta, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            Dyn2CampiMgr mgr = new Dyn2CampiMgr(authInfo.CreateDatabase());


            Dyn2Campi filtroCompare = new Dyn2Campi();

            Dyn2Campi filtro = new Dyn2Campi
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
                                            .GetClassList(filtro, filtroCompare, false);

            ListSortManager<Dyn2Campi>.Sort(list, sortExpression);

            return list;
        }

        public static List<KeyValuePair<int, string>> FindIdDescrizione(string token, string idModello, bool firstBlank)
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

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", authInfo.IdComune));
                    cmd.Parameters.Add(db.CreateParameter("idModello", String.IsNullOrEmpty(idModello) ? "%" : idModello));

                    using (IDataReader rd = cmd.ExecuteReader())
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
            Dyn2CampiProprieta filtro = new Dyn2CampiProprieta();
            filtro.Idcomune = idComune;
            filtro.FkD2cId = id;

            return new Dyn2CampiProprietaMgr(this.db).GetList(filtro);
        }

        public List<Dyn2Campi> GetListaCampiDaSoftwareContesto(string idComune, string software, string idContesto)
        {
            Dyn2Campi filtro = new Dyn2Campi();
            filtro.Idcomune = idComune;
            filtro.OthersWhereClause.Add("(SOFTWARE='" + software + "' OR SOFTWARE='')");
            filtro.OthersWhereClause.Add("(fk_d2bc_id='" + idContesto + "' OR fk_d2bc_id is null)");

            return this.GetList(filtro);
        }

        private void VerificaRecordCollegati(Dyn2Campi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle

            // Verifico se un campo è presente in un modello
            Dyn2ModelliD filtroModelli = new Dyn2ModelliD();
            filtroModelli.Idcomune = cls.Idcomune;
            filtroModelli.FkD2cId = cls.Id;

            List<Dyn2ModelliD> modelli = new Dyn2ModelliDMgr(this.db).GetList(filtroModelli);

            if (modelli.Count > 0)
                throw new ReferentialIntegrityException("DYN2_MODELLID");
        }

        private void EffettuaCancellazioneACascata(Dyn2Campi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati

            // Eliminazione delle proprietà del campo
            Dyn2CampiProprietaMgr mgrProprieta = new Dyn2CampiProprietaMgr(this.db);

            Dyn2CampiProprieta filtroProp = new Dyn2CampiProprieta();
            filtroProp.Idcomune = cls.Idcomune;
            filtroProp.FkD2cId = cls.Id;

            List<Dyn2CampiProprieta> proprieta = mgrProprieta.GetList(filtroProp);

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
            int cnt = this.recordCount("dyn2_modellid", "*", @"WHERE idcomune = '" + idComune + @"' AND
																fk_d2c_id = " + idCampo + @" AND
																flg_multiplo = 1 ");

            return cnt > 0;
        }

        #region IDyn2CampiManager Members

        IDyn2Campo IDyn2CampiManager.GetById(string idComune, int idCampo)
        {
            return this.GetById(idComune, idCampo);
        }


        public List<Dyn2Campi> GetList(string idComune, int idModello)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = this.PreparaQueryParametrica(@"SELECT distinct
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

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idModello", idModello));

                    return this.db.GetClassList<Dyn2Campi>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
        {
            var lista = this.GetList(idComune, idModello);

            SerializableDictionary<int, IDyn2Campo> rVal = new SerializableDictionary<int, IDyn2Campo>();

            lista.ForEach(x => rVal.Add(x.Id.Value, x));

            return rVal;

        }

        #endregion
    }
}
