
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Utils;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2CampiScriptMgr
    {
        public IDyn2ScriptCampo GetById(string idComune, int idCampo, TipoScriptEnum tipoContesto)
        {
            return this.GetById(idComune, idCampo, tipoContesto.ToString());
        }

        public List<Dyn2CampiScript> GetList(string idComune, int idCampo)
        {
            var filtro = new Dyn2CampiScript
            {
                Idcomune = idComune,
                FkD2cId = idCampo
            };

            return this.GetList(filtro);
        }

        public IEnumerable<CampoDinamicoScriptDto> GetListaScriptDaIdModello(string idComune, int idModello)
        {
            var sql = $@"SELECT 
									dyn2_campi_script.EVENTO,
                                    dyn2_campi_script.FK_D2C_ID,
                                    dyn2_campi_script.SCRIPT
								FROM 
									dyn2_campi_script,
									dyn2_modellid
								WHERE 
									dyn2_campi_script.idComune = dyn2_modellid.idComune AND
									dyn2_campi_script.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
									dyn2_modellid.idComune = {this.db.QueryParameter("idComune")} AND
									dyn2_modellid.fk_d2mt_id = {this.db.QueryParameter("idModello")}";

            return this.db.ExecuteReader(sql,
                                     mp => mp.Add("idcomune", idComune)
                                             .Add("idModello", idModello),
                                     dr => new CampoDinamicoScriptDto
                                     {
                                         Evento = dr.GetString("EVENTO"),
                                         FkD2cId = dr.GetInt("FK_D2C_ID"),
                                         Script = dr.GetBytes("SCRIPT")
                                     }

            );
        }

        #region IDyn2ScriptCampiManager Members

        public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
        {
            var l = this.GetList(idComune, idCampo);

            var rVal = new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>(l.Count);

            l.ForEach(x =>
            {
                x.Checksum = Md5Utils.GetMd5(x.GetTestoScript());
                rVal.Add((TipoScriptEnum)Enum.Parse(typeof(TipoScriptEnum), x.Evento), x);
            }
            );

            return rVal;
        }

        #endregion
    }
}
