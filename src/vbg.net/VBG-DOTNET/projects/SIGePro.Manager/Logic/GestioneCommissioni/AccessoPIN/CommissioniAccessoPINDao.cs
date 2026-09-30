using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.AccessoPIN
{

    public class CommissioniAccessoPINDao : ICommissioniAccessoPINDao
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public CommissioniAccessoPINDao(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }
            _db = db ?? throw new ArgumentNullException(nameof(db));
            _idComune = idComune;
        }

        public RiferimentiRigaAppello AssociaUtenteACommissioneByPIN(int codiceAnagrafe, string pin)
        {
            var rifCommissione = this.GetIdCommissioneByPIN(pin);

            if (rifCommissione == null)
            {
                throw new Exception($"Non è stato possibile trovare una commissione tramite il pin {pin}. " +
                                    $"Verificare che il pin sia valido e che non esistano anagrafiche " +
                                    $"collegate all'amministrazione a cui corrisponde il PIN");
            }

            var sql = $@"update commedilizie_appello 
                        set codiceanagrafe={this._db.QueryParameter("codiceAnagrafe")}
                        where idcomune={this._db.QueryParameter("idComune")} and 
                              id={this._db.QueryParameter("id")}";

            this._db.ExecuteNonQuery(sql,
                        mp => mp.Add("codiceAnagrafe", codiceAnagrafe)
                                .Add("idComune", this._idComune)
                                .Add("id", rifCommissione.IdAppello));

            return rifCommissione;
        }

        private RiferimentiRigaAppello GetIdCommissioneByPIN(string pin)
        {
            var sql = $@"SELECT 
                            commedilizie_appello.codicecommissione, 
                            commedilizie_appello.id, 
                            commedilizie_appello.codiceamministrazione 
                        FROM 
                            commedilizie_appello 

                                INNER JOIN commissioniedilizie_t ON
                                    commissioniedilizie_t.idcomune = commedilizie_appello.idcomune AND
                                    commissioniedilizie_t.codicecommissione = commedilizie_appello.codicecommissione and 
                                    (commissioniedilizie_t.data_fine IS NULL OR commissioniedilizie_t.data_fine > {this._db.QueryParameter("data")}) AND
                                    commissioniedilizie_t.flagaperta = 1 

                        WHERE commedilizie_appello.idcomune={this._db.QueryParameter("idComune")} 
                          AND commedilizie_appello.pin={this._db.QueryParameter("pin")} 
                          and commedilizie_appello.codiceanagrafe is null";

            return this._db.ExecuteReader(sql,
                mp => mp.Add("data", DateTime.Now)
                        .Add("idComune", this._idComune)
                        .Add("pin", pin),
                
                dr => new RiferimentiRigaAppello
                {
                    IdAppello = dr.GetInt("id").Value,
                    IdCommissione = dr.GetInt("codicecommissione").Value,
                    IdAmministrazione = dr.GetInt("codiceamministrazione").Value
                }).FirstOrDefault();
        }

        public bool VerificaValiditaPIN(string pin)
        {
            var sql = $@"SELECT Count(*) 
                        FROM 
                            commedilizie_appello 
        
                                INNER JOIN commissioniedilizie_t ON
                                    commissioniedilizie_t.idcomune = commedilizie_appello.idcomune AND
                                    commissioniedilizie_t.codicecommissione = commedilizie_appello.codicecommissione and 
                                    (commissioniedilizie_t.data_fine IS NULL OR data_fine > {this._db.QueryParameter("data")}) AND
                                    commissioniedilizie_t.flagaperta = 1 

                        WHERE commedilizie_appello.idcomune={this._db.QueryParameter("idComune")} 
                          AND commedilizie_appello.pin={this._db.QueryParameter("pin")} 
                          and commedilizie_appello.codiceanagrafe is null";

            return this._db.ExecuteScalar(sql, 0,
                mp => mp.Add("data", DateTime.Now)
                        .Add("idComune", this._idComune)
                        .Add("pin", pin)) > 0;
        }
    }
}
