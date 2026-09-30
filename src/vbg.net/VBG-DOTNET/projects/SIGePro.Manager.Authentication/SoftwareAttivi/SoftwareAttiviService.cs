using PersonalLib2.Data;
using System;
using System.Collections.Concurrent;
using System.Text;

namespace Init.SIGePro.Manager.Authentication.SoftwareAttivi
{
    public class SoftwareAttiviService
    {
        private static readonly ConcurrentDictionary<string, SoftwareAttiviList> _cacheSoftwareAttivi = new ConcurrentDictionary<string, SoftwareAttiviList>();

        private readonly DataBase _db;
        private readonly string _idComune;

        public SoftwareAttiviService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public SoftwareAttiviList GetSoftwareAttivi()
        {
            return _cacheSoftwareAttivi.GetOrAdd(this._idComune, (_) =>
            {
                FormattableString sql = $"SELECT * FROM softwareattivi WHERE idcomune = {this._idComune}";

                var dati = this._db.ExecuteReader(sql, dr => new SoftwareAttivo
                {
                    Idcomune = dr.GetString("idcomune"),
                    AttivoFo = dr.GetInt("attivo_fo"),
                    FkSoftware = dr.GetString("FK_SOFTWARE")
                });

                return new SoftwareAttiviList(dati);
            });

        }

        public static void ClearCache()
        {
            _cacheSoftwareAttivi.Clear();
        }
    }
}
