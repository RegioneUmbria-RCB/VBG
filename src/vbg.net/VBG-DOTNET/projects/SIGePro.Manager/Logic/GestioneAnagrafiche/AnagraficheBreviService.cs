using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
    public class AnagraficheBreviService : IAnagraficheBreviService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public AnagraficheBreviService(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            _db = db ?? throw new ArgumentNullException(nameof(db));
            _idComune = idComune;
        }

        public string GetNomeUtente(int codiceAnagrafe)
        {
            var sql = $"select nominativo, nome from anagrafe where idcomune={this._db.QueryParameter("idComune")} and codiceAnagrafe={_db.QueryParameter("codiceAnagrafe")}";

            var utente = this._db.ExecuteReader(sql,
                mp => {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                },
                dr => new {
                    nominativo = dr.GetString("nominativo"),
                    nome = dr.GetString("nome")
                }).FirstOrDefault();

            if (utente == null)
            {
                return $"[SCONOSCIUTO (codiceanagrafe={codiceAnagrafe})]";
            }

            return (String.IsNullOrEmpty(utente.nome) ? "" : $"{utente.nome} ") + utente.nominativo + $" ({codiceAnagrafe})";
        }
    }
}
