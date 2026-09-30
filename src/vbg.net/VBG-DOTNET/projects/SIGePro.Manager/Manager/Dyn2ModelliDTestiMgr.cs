using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class Dyn2ModelliDTestiMgr : BaseManager
    {
        public Dyn2ModelliDTestiMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2ModelliDTesti? GetById(string idcomune, int id)
        {
            FormattableString sql = $@"SELECT * FROM dyn2_modellidtesti WHERE idcomune = {idcomune} AND id = {id}";

            return this.db.GetClassList<Dyn2ModelliDTesti>(sql).FirstOrDefault();
        }

        public List<Dyn2ModelliDTesti> GetListByIdModello(string idComune, int idModello)
        {
            FormattableString sql = $@"SELECT 
	                                    dyn2_modellidtesti.* 
                                    FROM 
	                                    dyn2_modellidtesti
	
	                                    INNER JOIN dyn2_modellid ON 
		                                    dyn2_modellidtesti.idcomune = dyn2_modellid.idcomune AND
		                                    dyn2_modellidtesti.id = dyn2_modellid.fk_d2mdt_id
                                    WHERE 	
	                                    dyn2_modellid.idcomune={idComune} AND
	                                    dyn2_modellid.fk_d2mt_id = {idModello}";

            return this.db.GetClassList<Dyn2ModelliDTesti>(sql);

        }

        public Dyn2ModelliDTesti Insert(Dyn2ModelliDTesti cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }


        public Dyn2ModelliDTesti Update(Dyn2ModelliDTesti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Dyn2ModelliDTesti cls)
        {
            this.db.Delete(cls);
        }


        private void Validate(Dyn2ModelliDTesti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


        public IEnumerable<ModelloDinamicoTestoDto> GetTestiDtoByIdModello(string idComune, int idModello)
        {
            var sql = $@"SELECT 
														  
                            dyn2_modellidtesti.idcomune,
                            dyn2_modellidtesti.id,
                            dyn2_modellidtesti.fk_d2btt_id,
                            dyn2_modellidtesti.testo,
                            dyn2_modellid.id as idNelModello
						FROM 
							dyn2_modellid,
							dyn2_modellidtesti
						WHERE
							dyn2_modellidtesti.idcomune = dyn2_modellid.idcomune AND                            
							dyn2_modellidtesti.id = dyn2_modellid.fk_d2mdt_id AND                  
							dyn2_modellid.idcomune = {this.db.QueryParameter("idComune")} and               
							dyn2_modellid.fk_d2mt_id = {this.db.QueryParameter("idModello")}";

            return this.db.ExecuteReader(sql,
                             mp => mp.Add("idcomune", idComune)
                                     .Add("idModello", idModello),

                             dr => new ModelloDinamicoTestoDto
                             {
                                 Id = dr.GetInt("id"),
                                 IdNelModello = dr.GetInt("idNelModello"),
                                 IdTipoTesto = dr.GetString("FK_D2BTT_ID"),
                                 Testo = dr.GetString("testo")
                             }
                );
        }


        public void Delete(string idComune, int idTesto)
        {
            var sql = $"delete from dyn2_modellidtesti where idcomune={this.db.Specifics.QueryParameterName("idComune")} and id={this.db.Specifics.QueryParameterName("idTesto")}";

            this.db.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", idComune);
                mp.AddParameter("idTesto", idTesto);
            });
        }
    }
}
