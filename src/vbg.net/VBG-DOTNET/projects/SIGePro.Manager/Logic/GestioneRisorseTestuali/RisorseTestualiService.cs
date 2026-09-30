using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneRisorseTestuali
{
    public class RisorseTestualiService
    {


        public static class PrefissiRisorse
        {
            public const string RisorsaAreaRiservata = "AREA_RISERVATA";
        }

        public class Risorsa
        {
            public string Chiave { get; set; }
            public string Valore { get; set; }
        }

        private readonly DataBase _db;
        private readonly string _idComune;

        public RisorseTestualiService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public IEnumerable<Risorsa> GetList(string software, string prefix)
        {
            var sql = "SELECT codicetesto, testo FROM layouttestibase where software = {0} and codicetesto like '" + prefix.ToUpper() + ".%'";

            var dictionaryBase = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("software", "TT");
                },
                dr => new
                {
                    Codice = dr.GetString("codicetesto"),
                    Testo = dr.GetString("testo")
                }).ToDictionary(x => x.Codice, x => x.Testo);

            var temp = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("software", software);
                },
                dr => new
                {
                    Codice = dr.GetString("codicetesto"),
                    Testo = dr.GetString("testo")
                });

            foreach (var item in temp)
            {
                dictionaryBase[item.Codice] = item.Testo;
            }

            sql = "SELECT codicetesto, nuovotesto FROM layouttesti where idcomune = {0} and software = {1} and codicetesto like '" + prefix.ToUpper() + ".%'";

            temp = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", this._idComune);
                    mp.AddParameter("software", "TT");
                },
                dr => new
                {
                    Codice = dr.GetString("codicetesto"),
                    Testo = dr.GetString("nuovotesto")
                });

            foreach (var item in temp)
            {
                dictionaryBase[item.Codice] = item.Testo;
            }

            temp = this._db.ExecuteReader(sql,
               mp =>
               {
                   mp.AddParameter("idcomune", this._idComune);
                   mp.AddParameter("software", software);
               },
               dr => new
               {
                   Codice = dr.GetString("codicetesto"),
                   Testo = dr.GetString("nuovotesto")
               });

            foreach (var item in temp)
            {
                dictionaryBase[item.Codice] = item.Testo;
            }

            return dictionaryBase.Select(x => new Risorsa
            {
                Chiave = x.Key,
                Valore = x.Value
            });
        }

        public void AggiornaRisorsa(string software, string codicetesto, string nuovotesto, ILayoutTestiAuditService auditService)
        {
            var wasInTransaction = this._db.IsInTransaction;

            try
            {
                if (!wasInTransaction)
                {
                    this._db.BeginTransaction();
                }

                if (String.IsNullOrEmpty(nuovotesto))
                {
                    EliminaRisorsa(software, codicetesto, auditService);
                    return;
                }

                var sql = $@"select 
                            count(*) 
                        FROM 
                            layouttesti 
                        WHERE 
                            idcomune={this._db.Specifics.QueryParameterName("idComune")} AND 
                            software={this._db.Specifics.QueryParameterName("software")} AND 
                            codicetesto={this._db.Specifics.QueryParameterName("codicetesto")}";

                var count = this._db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("software", software);
                    mp.AddParameter("codicetesto", codicetesto);
                });

                if (count == 0)
                {
                    sql = $@"INSERT into layouttesti (idcomune, software, codicetesto, nuovotesto) VALUES (
                            {this._db.Specifics.QueryParameterName("idComune")}, 
                            {this._db.Specifics.QueryParameterName("software")}, 
                            {this._db.Specifics.QueryParameterName("codicetesto")}, 
                            {this._db.Specifics.QueryParameterName("nuovotesto")}
                        )";
                    this._db.ExecuteNonQuery(sql, mp =>
                    {
                        mp.AddParameter("idComune", this._idComune);
                        mp.AddParameter("software", software);
                        mp.AddParameter("codicetesto", codicetesto);
                        mp.AddParameter("nuovotesto", nuovotesto);
                    });

                    auditService.LogTestoCreato(software, codicetesto, nuovotesto);

                    return;

                }
                sql = $@"update layouttesti set nuovotesto={this._db.Specifics.QueryParameterName("nuovotesto")} 
                    where
                        idcomune={this._db.Specifics.QueryParameterName("idComune")} and
                        software={this._db.Specifics.QueryParameterName("software")} and
                        codicetesto={this._db.Specifics.QueryParameterName("codicetesto")}";

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("nuovotesto", nuovotesto);
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("software", software);
                    mp.AddParameter("codicetesto", codicetesto);
                });

                auditService.LogTestoModificato(software, codicetesto, nuovotesto);

            }
            catch (Exception)
            {
                if (!wasInTransaction)
                {
                    this._db.RollbackTransaction();
                    wasInTransaction = true; // per prevenire la commit successiva
                }

                throw;
            }
            finally
            {
                if (!wasInTransaction)
                {
                    this._db.CommitTransaction();
                }
            }

        }

        private void EliminaRisorsa(string software, string codicetesto, ILayoutTestiAuditService auditService)
        {
            var sql = $"DELETE FROM layouttesti WHERE idcomune={this._db.Specifics.QueryParameterName("idComune")} AND software={this._db.Specifics.QueryParameterName("software")} AND codicetesto={this._db.Specifics.QueryParameterName("codicetesto")}";

            this._db.ExecuteNonQuery(sql, mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("software", software);
                mp.AddParameter("codicetesto", codicetesto);
            });

            auditService.LogTestoEliminato(software, codicetesto);
        }
    }
}
