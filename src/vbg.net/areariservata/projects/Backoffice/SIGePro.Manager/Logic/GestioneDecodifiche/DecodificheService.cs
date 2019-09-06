using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneDecodifiche
{
    public class DecodificheService
    {
        private readonly DataBase _database;
        private readonly string _idComune;

        public DecodificheService(DataBase database, string idComune)
        {
            if (String.IsNullOrEmpty(idComune)) { throw new Exception("Impossibile inizializzare la classe DecodificheService senza valorizzare il parametro idComune"); }
            if ( database == null ) { throw new Exception("Impossibile inizializzare la classe DecodificheService senza valorizzare il parametro database"); }

            _database = database;
            _idComune = idComune;
        }

        /// <summary>
        /// Ritorna la lista delle SOLE decodifiche attive ( FLG_DISABILITATO = 0 )
        /// </summary>
        /// <param name="tabella">Parametro che indica il raggruppamento delle decodifiche da estrarre</param>
        /// <returns></returns>
        public IEnumerable<DecodificaDTO> GetDecodificheAttive(string tabella)
        {

            if ( String.IsNullOrEmpty(tabella) ) { throw new Exception("Impossibile richiamare DecodificheService.GetDecodificheAttive senza valorizzare il parametro tabella"); }

            var sql = $@"
                select
                    idcomune, tabella, chiave, valore, flg_disabilitato 
                from
                    decodifiche 
                where
                    idcomune in ('BASE',{_database.Specifics.QueryParameterName("idComune")} ) and
                    tabella = {_database.Specifics.QueryParameterName("tabella")} and
                    flg_disabilitato = 0
                order by
                    valore asc
                ";

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("tabella", tabella);
                },
                x => new DecodificaDTO
                {
                    Idcomune = x.GetString("idcomune"),
                    Chiave = x.GetString("chiave"),
                    FlgDisabilitato = x.GetInt("flg_disabilitato").GetValueOrDefault(0) == 1,
                    Tabella = x.GetString("tabella"),
                    Valore = x.GetString("valore")
                });
        }
    }
}
