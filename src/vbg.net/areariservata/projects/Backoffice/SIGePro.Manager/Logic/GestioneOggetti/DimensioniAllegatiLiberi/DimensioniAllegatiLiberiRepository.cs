using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Manager.Logic.GestioneOggetti.DimensioniAllegatiLiberi
{
    public class DimensioniAllegatiLiberiRepository
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public DimensioniAllegatiLiberiRepository(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public IEnumerable<FormatoAllegatoLibero> GetList(string software)
        {
            var sql = $"select * from FO_FORMATI_DOCUMENTI where idcomune={this._db.Specifics.QueryParameterName("idcomune")} and software={this._db.Specifics.QueryParameterName("software")} order by formato ASC";

            return this._db.ExecuteReader(sql, x => {
                x.AddParameter("idcomune", this._idComune);
                x.AddParameter("software", software);
            }, 
            dr => new FormatoAllegatoLibero
            {
                Id = dr.GetInt("id").GetValueOrDefault(0),
                Formato = dr.GetString("FORMATO"),
                DimensioneMaxPagina = dr.GetInt("DIMENSIONE_MAX_PAGINA").GetValueOrDefault(0)
            });
        }
    }
}
