//using Init.SIGePro.Data;
//using PersonalLib2.Data;
//using System;
//using System.Collections.Concurrent;
//using System.ComponentModel;
//using System.Linq;

//namespace Init.SIGePro.Manager
//{


//    [DataObject(true)]
//    public partial class SoftwareAttiviMgr
//    {
//        private static readonly ConcurrentDictionary<string, SoftwareAttiviList> _cacheSoftwareAttivi = new ConcurrentDictionary<string, SoftwareAttiviList>();

//        public SoftwareAttiviList GetSoftwareAttivi(string idComune)
//        {
//            return _cacheSoftwareAttivi.GetOrAdd(idComune, (_) =>
//            {
//                FormattableString sql = $"SELECT * FROM softwareattivi WHERE idcomune = {idComune}";

//                var dati = this.db.ExecuteReader(sql, dr => new SoftwareAttivi
//                {
//                    Idcomune = dr.GetString("idcomune"),
//                    AttivoFo = dr.GetInt("attivo_fo"),
//                    FkSoftware = dr.GetString("FK_SOFTWARE")
//                });

//                return new SoftwareAttiviList(dati.ToList());
//                /*
//                var filtro = new SoftwareAttivi
//                {
//                    Idcomune = idComune
//                };

//                return new SoftwareAttiviList(this.db.GetClassList(filtro).ToList<SoftwareAttivi>());
//                */
//            });

//        }

//        public static void ClearCache()
//        {
//            _cacheSoftwareAttivi.Clear();
//        }
//    }
//}
