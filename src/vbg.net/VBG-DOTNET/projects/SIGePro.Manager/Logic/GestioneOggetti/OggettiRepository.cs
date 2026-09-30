// -----------------------------------------------------------------------
// <copyright file="OggettiRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneOggetti
{
    using PersonalLib2.Data;
    using System.Collections.Generic;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class OggettiRepository : IOggettiRepository
    {
        DataBase _db;
        private readonly string _idComune;

        public OggettiRepository(DataBase db, string idComune)
        {
            this._db = db;
            _idComune = idComune;
        }

        public Data.Oggetti GetById(int id)
        {
            return new OggettiMgr(this._db).GetById(this._idComune, id);
        }

        public IEnumerable<int> GetCodiciOggettoDocumentiIstanzaDaValoreMetadato(int codiceIstanza, string chiaveMetadato, string valoreMetadato)
        {
            var sql = $@"SELECT 
                  documentiistanza.codiceoggetto
                FROM 
                  documentiistanza 
                    INNER JOIN oggetti_metadati ON
                      oggetti_metadati.idcomune = documentiistanza.idcomune AND
                      oggetti_metadati.codiceoggetto = documentiistanza.codiceoggetto
                WHERE
                  documentiistanza.codiceistanza = {this._db.Specifics.QueryParameterName("codiceIstanza")} AND 
                  documentiistanza.idcomune = {this._db.Specifics.QueryParameterName("idComune")} AND
                  oggetti_metadati.chiave = {this._db.Specifics.QueryParameterName("chiaveMetadato")} AND
                  oggetti_metadati.valore = {this._db.Specifics.QueryParameterName("valoreMetadato")}";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("chiaveMetadato", chiaveMetadato);
                    mp.AddParameter("valoreMetadato", valoreMetadato);
                },
                dr => dr.GetInt("codiceoggetto").Value
                );
        }
    }
}
