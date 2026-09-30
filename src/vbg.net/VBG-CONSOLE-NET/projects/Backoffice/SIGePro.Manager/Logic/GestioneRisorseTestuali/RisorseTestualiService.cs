using Init.SIGePro.Data;
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

        DataBase _db;
        string _idComune;

        public RisorseTestualiService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public IEnumerable<Risorsa> GetList(string software, string prefix)
        {
            var sql = "SELECT codicetesto, testo FROM layouttestibase where software = {0} and codicetesto like '" + prefix.ToUpper() + ".%'";

            var dictionaryBase = this._db.ExecuteReader(sql,
                mp => {
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

        public void AggiornaRisorsa(string software, string chiave, string valore)
        {
            var cls = new LayoutTesti
            {
                Idcomune = this._idComune,
                Software = software,
                Codicetesto = chiave
            };

            var existingVal = this._db.GetClassList(cls).ToList<LayoutTesti>().FirstOrDefault();

            if (existingVal != null)
            {
                existingVal.Nuovotesto = valore;

                this._db.Update(existingVal);

                return;
            }

            cls.Nuovotesto = valore;

            this._db.Insert(cls);
        }
    }
}
