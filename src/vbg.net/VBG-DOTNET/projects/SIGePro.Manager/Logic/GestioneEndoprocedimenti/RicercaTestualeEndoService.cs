using Init.SIGePro.Manager.DTO;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{

    public class RicercaTestualeEndoService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public RicercaTestualeEndoService(DataBase db, string idComune)
        {
            _db = db;
            _idComune = idComune;
        }

        internal IEnumerable<BaseDto<int, string>> RicercaTestualeFamiglie(string software, TerminiRicerca termini)
        {
            var sql = $"SELECT codice,tipo as descrizione FROM tipifamiglieendo WHERE idcomune={this._db.Specifics.QueryParameterName("idcomune")} AND software={this._db.Specifics.QueryParameterName("software")}";

            return RicercaTestuale(sql, software, this._db.Specifics.UCaseFunction("tipo"), termini);
        }

        internal IEnumerable<BaseDto<int, string>> RicercaTestualeCategorie(string software, TerminiRicerca termini)
        {
            var sql = $"SELECT codice,tipo as descrizione FROM tipiendo WHERE idcomune={this._db.Specifics.QueryParameterName("idcomune")} AND software={this._db.Specifics.QueryParameterName("software")}";

            return RicercaTestuale(sql, software, this._db.Specifics.UCaseFunction("tipo"), termini);
        }

        internal IEnumerable<BaseDto<int, string>> RicercaTestualeEndo(string software, TerminiRicerca termini)
        {
            var sql = $"SELECT codiceinventario as codice,procedimento as descrizione FROM inventarioprocedimenti WHERE idcomune={this._db.Specifics.QueryParameterName("idcomune")} AND software={this._db.Specifics.QueryParameterName("software")} and disabilitato=0 ";

            return RicercaTestuale(sql, software, this._db.Specifics.UCaseFunction("procedimento"), termini);
        }

        private IEnumerable<BaseDto<int, string>> RicercaTestuale(string sqlParziale, string software, string campoRicerca, TerminiRicerca terminiRicerca)
        {

            var sql = sqlParziale + terminiRicerca.ToSql(campoRicerca, x => this._db.Specifics.QueryParameterName(x));

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("software", software);

                    for (int i = 0; i < terminiRicerca.Parti.Length; i++)
                    {
                        mp.AddParameter(terminiRicerca.PrefissoParametro + i, $"%{terminiRicerca.Parti[i].ToUpperInvariant()}%");
                    }
                },
                dr => new BaseDto<int, string>(
                    dr.GetInt("codice").Value,
                    dr.GetString("descrizione")
                ));
        }
    }
}
