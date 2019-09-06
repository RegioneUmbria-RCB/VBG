using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneDomandaOnLine
{
    public class DomandaOnlineService
    {
        private readonly DataBase _database;
        private readonly string _idComune;

        public DomandaOnlineService(DataBase database, string idComune)
        {
            if (String.IsNullOrEmpty(idComune)) { throw new Exception("Impossibile inizializzare la classe DecodificheService senza valorizzare il parametro idComune"); }
            if (database == null) { throw new Exception("Impossibile inizializzare la classe DecodificheService senza valorizzare il parametro database"); }

            _database = database;
            _idComune = idComune;
        }

        /// <summary>
        /// Copia DOCUMENTIISTANZA.CODICEOGGETTO dell'istanza di partenza in FO_DOMANDE_OGGETTI.CODICEOGGETTO dell' idDomandaDestinazione
        /// </summary>
        /// <param name="codiceIstanzaOrigine">ISTANZE.CODICEISTANZA di partenza da cui copiare DOCUMENTIISTANZA.CODICEOGGETTO</param>
        /// <param name="idDomandaDestinazione">FO_DOMANDE.ID di destinazione</param>
        public void RecuperaDocumentiIstanzaCollegata( int codiceIstanzaOrigine, int idDomandaDestinazione )
        {
            var sql = $@"
                select
                    codiceoggetto
                from
                    documentiistanza
                where
                    idcomune = {_database.Specifics.QueryParameterName("idComune")} and
                    codiceistanza = {_database.Specifics.QueryParameterName("codiceIstanzaOrigine")} and
                    codiceoggetto is not null
                ";

            var oggetti = _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceIstanzaOrigine", codiceIstanzaOrigine);
                },
                x => x.GetInt("codiceoggetto").Value);


            oggetti.ToList().ForEach(
                x => {
                    
                    //controllo esistenza
                    sql = $@"
                        select 
                            count(codiceoggetto) as conteggio
                        from 
                            fo_domande_oggetti 
                        where 
                            idcomune = {_database.Specifics.QueryParameterName("idComune")} and
                            iddomanda = {_database.Specifics.QueryParameterName("idDomandaDestinazione")} and
                            codiceoggetto = {_database.Specifics.QueryParameterName("codiceoggetto")}
                        ";

                    var conteggio = _database.ExecuteScalar(sql,
                        0,
                        mp =>
                        {
                            mp.AddParameter("idComune", _idComune);
                            mp.AddParameter("idDomandaDestinazione", idDomandaDestinazione);
                            mp.AddParameter("codiceoggetto",  x );
                        });

                    if(conteggio == 0)
                    {
                        //inserimento
                        sql = $@"
                            insert into
                                fo_domande_oggetti(idcomune,iddomanda,codiceoggetto) 
                            values
                                ({_database.Specifics.QueryParameterName("idComune")},
                                    {_database.Specifics.QueryParameterName("idDomandaDestinazione")},
                                    {_database.Specifics.QueryParameterName("codiceoggetto")})
                        ";

                        _database.ExecuteNonQuery(sql,
                        mp =>
                        {
                            mp.AddParameter("idComune", _idComune);
                            mp.AddParameter("idDomandaDestinazione", idDomandaDestinazione);
                            mp.AddParameter("codiceoggetto", x);
                        });
                    }
                }
            );
        }
    }
}
