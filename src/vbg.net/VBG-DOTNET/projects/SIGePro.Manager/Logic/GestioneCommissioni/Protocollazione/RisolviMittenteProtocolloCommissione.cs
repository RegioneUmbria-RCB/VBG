using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione
{
    public class RisolviMittenteProtocolloCommissione : IRisolviMittenteProtocolloCommissione
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly int _idCommissione;
        private readonly int _codiceAnagrafe;

        public RisolviMittenteProtocolloCommissione(DataBase db, string idComune, int idCommissione, int codiceAnagrafe)
        {
            _db = db;
            _idComune = idComune;
            _idCommissione = idCommissione;
            _codiceAnagrafe = codiceAnagrafe;
        }

        public AnagraficheProtocolloCommissione Mittente => RisolviAnagrafiche();

        private AnagraficheProtocolloCommissione RisolviAnagrafiche()
        {
            var sql = $@"SELECT 
            amministrazioni.codiceamministrazione
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

                dr => dr.GetInt("codiceamministrazione")
            ).FirstOrDefault();


            return new AnagraficheProtocolloCommissione(this._codiceAnagrafe, res);
        }
    }
}
