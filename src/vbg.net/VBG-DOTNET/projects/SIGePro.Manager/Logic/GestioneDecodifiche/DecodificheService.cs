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
                    idcomune, tabella, chiave, valore, flg_disabilitato, raggruppamento, ordine 
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
                    Valore = x.GetString("valore"),
                    Raggruppamento = x.GetString("raggruppamento"),
                    Ordine = x.GetInt("ordine")
                });
        }

        internal object GetDecodificheAttive(object coefficientiMoltiplicativi)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<DecodificaDTO> GetDecodificheAttiveByRaggruppamento(string tabella, string raggruppamento)
        {
            if (String.IsNullOrEmpty(tabella)) { throw new Exception("Impossibile richiamare DecodificheService.GetDecodificheAttive senza valorizzare il parametro tabella"); }

            return this.GetDecodificheAttiveByRaggruppamento(tabella, raggruppamento, "valore asc");
        }

        public IEnumerable<DecodificaDTO> GetDecodificheSuccessiveAttive(string tabella, int ordine)
        {
            return this.GetDecodificheAttive(tabella)
                        .Where(x => x.Ordine.HasValue && x.Ordine.Value > ordine);
        }

        public IEnumerable<DecodificaDTO> GetDecodificheAttiveByRaggruppamento(string tabella, string raggruppamento, string ordinamento)
        {
            if (String.IsNullOrEmpty(tabella)) { throw new Exception("Impossibile richiamare DecodificheService.GetDecodificheAttive senza valorizzare il parametro tabella"); }

            var sql = $@"
                select
                    idcomune, tabella, chiave, valore, flg_disabilitato, raggruppamento, ordine 
                from
                    decodifiche 
                where
                    idcomune in ('BASE',{_database.Specifics.QueryParameterName("idComune")} ) and
                    tabella = {_database.Specifics.QueryParameterName("tabella")} and
                    raggruppamento = {_database.Specifics.QueryParameterName("raggruppamento")} and
                    flg_disabilitato = 0
                order by
                    {ordinamento}
                ";

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("tabella", tabella);
                    mp.AddParameter("raggruppamento", raggruppamento);
                },
                x => new DecodificaDTO
                {
                    Idcomune = x.GetString("idcomune"),
                    Chiave = x.GetString("chiave"),
                    FlgDisabilitato = x.GetInt("flg_disabilitato").GetValueOrDefault(0) == 1,
                    Tabella = x.GetString("tabella"),
                    Valore = x.GetString("valore"),
                    Raggruppamento = x.GetString("raggruppamento"),
                    Ordine = x.GetInt("ordine")
                });
        }
    }
}
