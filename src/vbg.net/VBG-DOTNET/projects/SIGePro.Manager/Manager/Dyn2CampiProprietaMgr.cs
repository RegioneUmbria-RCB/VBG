using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2CampiProprietaMgr
    {
        public void DeleteByIdCampo(string IdComune, int idCampo)
        {
            var filtro = new Dyn2CampiProprieta();
            filtro.Idcomune = IdComune;
            filtro.FkD2cId = idCampo;

            var list = this.GetList(filtro);

            list.ForEach(delegate (Dyn2CampiProprieta campiProp) { this.Delete(campiProp); });
        }
        /*
        public List<Dyn2CampiProprieta> GetListDaIdModello(string idComune, int idModello)
        {
            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                string sql = this.PreparaQueryParametrica(@"SELECT 
														  dyn2_campiproprieta.* 
														FROM 
														  dyn2_campiproprieta,
														  dyn2_modellid
														WHERE 
														  dyn2_campiproprieta.idComune = dyn2_modellid.idComune AND
														  dyn2_campiproprieta.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
														  dyn2_modellid.idComune = {0} AND
														  dyn2_modellid.fk_d2mt_id = {1}", "idComune", "idModello");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idModello", idModello));

                    return this.db.GetClassList<Dyn2CampiProprieta>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }
        */

        public IEnumerable<CampoDinamicoProprietaDto> GetProprietaCampiDaIdModello(string idComune, int idModello)
        {
            var sql = $@"
SELECT 
	dyn2_campiproprieta.* 
FROM 
	dyn2_campiproprieta,
	dyn2_modellid
WHERE 
	dyn2_campiproprieta.idComune = dyn2_modellid.idComune AND
	dyn2_campiproprieta.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
	dyn2_modellid.idComune = {this.db.QueryParameter("idComune")} AND
	dyn2_modellid.fk_d2mt_id = {this.db.QueryParameter("idModello")}";


            return this.db.ExecuteReader(sql,
                             mp => mp.Add("idcomune", idComune)
                                     .Add("idModello", idModello),

                             dr => new CampoDinamicoProprietaDto
                             {
                                 FkD2cId = dr.GetInt("FK_D2C_ID"),
                                 Proprieta = dr.GetString("PROPRIETA"),
                                 Valore = dr.GetString("VALORE")
                             });
        }

        public List<Dyn2CampiProprieta> GetListByIdModello(string idComune, int idModello)
        {
            FormattableString sql = $@"SELECT 
	                                    dyn2_campiproprieta.* 
                                    FROM 
	                                    dyn2_campiproprieta
	
	                                    INNER JOIN dyn2_modellid ON
		                                    dyn2_modellid.idcomune = dyn2_campiproprieta.idcomune AND
		                                    dyn2_modellid.FK_D2C_ID = dyn2_campiproprieta.FK_D2C_ID
                                    WHERE 
	                                    dyn2_modellid.idcomune = {idComune} AND
	                                    dyn2_modellid.fk_d2mt_id = {idModello}";

            return this.db.GetClassList<Dyn2CampiProprieta>(sql);
        }

        public List<Dyn2CampiProprieta> GetListByIdCampo(string idComune, int idCampo)
        {
            var filtro = new Dyn2CampiProprieta
            {
                Idcomune = idComune,
                FkD2cId = idCampo
            };

            return this.GetList(filtro);
        }

        public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
        {
            var lista = this.GetListByIdCampo(idComune, idCampo);

            return new List<IDyn2ProprietaCampo>(lista.ToArray());

            //lista.ForEach(x => rVal.Add(x));

            //return rVal;
        }
    }
}
