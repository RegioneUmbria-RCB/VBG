using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class RisolviOggettoProtocolloCommissione : IRisolviOggettoProtocolloCommissione
    {
        private class NumeroDataCommissione
        {
            public string Numero { get; set; }
            public string Data { get; set; }
        }

        private class NomeAutoreAmministrazione
        {
            public NomeAutoreAmministrazione(string nominativo, string amministrazione)
            {
                this.Nominativo = nominativo;
                this.Amministrazione = String.IsNullOrEmpty(amministrazione) ? "Non disponibile" : amministrazione;
            }

            public string Nominativo { get; }
            public string Amministrazione { get; }
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly int _idCommissione;
        private readonly int _codiceAnagrafe;
        private readonly int _codiceIstanza;

        public RisolviOggettoProtocolloCommissione(DataBase db, string idComune, int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            _db = db;
            _idComune = idComune;
            _idCommissione = idCommissione;
            _codiceAnagrafe = codiceAnagrafe;
            _codiceIstanza = codiceIstanza;
        }

        public string OggettoProtocollo => RisolviOggetto();

        private string RisolviOggetto()
        {
            var commissione = this.GetNumeroDataCommissione();
            var numeroPratica = this.GetNumeroPratica();
            var utente = GetUtenteAmministrazione();

            return $"Parere commissione {commissione.Numero} del {commissione.Data} per la pratica {numeroPratica}, amministrazione {utente.Amministrazione} ({utente.Nominativo}) ";
        }

        private string GetNumeroPratica()
        {
            var sql = $"SELECT numeroistanza, data FROM istanze WHERE idcomune={this._db.QueryParameter("idComune")} and codiceistanza={this._db.QueryParameter("codiceistanza")}";

            return this._db.ExecuteReader(sql,

                mp => mp.Add("idcomune", this._idComune)
                        .Add("codiceistanza", this._codiceIstanza),

                dr => $"{dr.GetString("numeroistanza")} del {dr.GetDateTime("data").Value.ToString("dd/MM/yyyy")}"
            ).FirstOrDefault();
        }

        private NumeroDataCommissione GetNumeroDataCommissione()
        {
            var sql = $"select numprotocollo, data from commissioniedilizie_t where idcomune={this._db.QueryParameter("idComune")} and codicecommissione={this._db.QueryParameter("codicecommissione")}";

            return this._db.ExecuteReader(sql,

                mp => mp.Add("idcomune", this._idComune)
                        .Add("codicecommissione", this._idCommissione),

                dr => new NumeroDataCommissione
                {
                    Numero = dr.GetString("numprotocollo"),
                    Data = dr.GetDateTime("data").Value.ToString("dd/MM/yyyy")
                }).FirstOrDefault();
        }

        private NomeAutoreAmministrazione GetUtenteAmministrazione()
        {
            var sql = $@"SELECT 
            anagrafe.nominativo,
            anagrafe.nome,
            amministrazioni.amministrazione
        FROM
            commissioniedilizie_t 
                left outer JOIN commedilizie_appello ON 
                    commedilizie_appello.idcomune = commissioniedilizie_t.idcomune AND
                    commedilizie_appello.codicecommissione = commissioniedilizie_t.codicecommissione
        
                left OUTER JOIN amministrazioni ON
                    amministrazioni.idcomune = commedilizie_appello.idcomune AND
                    amministrazioni.codiceamministrazione = commedilizie_appello.codiceamministrazione

                inner join anagrafe on                                                                                                              
                    anagrafe.idcomune = commedilizie_appello.idcomune and
                    anagrafe.codiceanagrafe = commedilizie_appello.codiceanagrafe
        WHERE 
            commissioniedilizie_t.idcomune = {this._db.QueryParameter("idComune")} AND
            commissioniedilizie_t.codicecommissione = {this._db.QueryParameter("idCommissione")} and
            commedilizie_appello.codiceanagrafe = {this._db.QueryParameter("codiceanagrafe")}";

            var res = this._db.ExecuteReader(sql,

                mp => mp.Add("idComune", this._idComune)
                        .Add("idCommissione", this._idCommissione)
                        .Add("codiceanagrafe", this._codiceAnagrafe),

                dr => new NomeAutoreAmministrazione(
                    $"{dr.GetString("nominativo")} {dr.GetString("nome")}",
                    dr.GetString("amministrazione")
            ));

            return res.FirstOrDefault();
        }
    }
}
