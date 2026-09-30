using ApiCatalogoSsuDEMO.Models;

namespace ApiCatalogoSsuDEMO.Controllers
{
    public static class Database
    {
        public static Dictionary<int, Procedimento> Procedimenti = new()
        {
            { 1644, new Procedimento
                {
                    Id = 1644,
                    Descrizione = "SCIA per apertura esercizio di commercio di vicinato non alimentare"
                }
            },
            { 1436, new Procedimento
                {
                    Id = 1436,
                    Descrizione = "Richiedere Autorizzazione Unica Ambientale AUA (DPR 13/3/2013, n. 59)"
                }
            },
            {1520, new Procedimento { Id = 1520, Descrizione = "Domanda di autorizzazione per l'esercizio del commercio di oggetti preziosi (art. 127 T.U.L.P.S.)" } },
            {1300, new Procedimento { Id = 1300, Descrizione = "Richiesta di autorizzazione per attività in materia di commercio di armi comuni (art. 31, c. 1 T.U.L.P.S.)" } },
            {1255, new Procedimento { Id = 1255, Descrizione = "SCIA per attività soggette ai controlli di prevenzione incendi" } },
            {9001, new Procedimento { Id = 9001, Descrizione = "AUA-SCHEDA A" } },
            {9002, new Procedimento { Id = 9002, Descrizione = "AUA-SCHEDA B" } },
            {9003, new Procedimento { Id = 9003, Descrizione = "AUA-SCHEDA C" } },
        };

        public static Dictionary<int, Fattispecie> Fattispecie = new()
        {
            { 1307, new Fattispecie
                {
                    Id = 1307,
                    Descrizione = "Segnalazione Certificata di Inizio Attività per l'esercizio di vicinato nel settore NON alimentare",
                    Obbligatoria = true
                }
            },
            { 1558, new Fattispecie
                {
                    Id = 1558,
                    Descrizione = "SCHEDA A – SCARICHI DI ACQUE REFLUE",
                }
            },
            { 1559, new Fattispecie
                {
                    Id = 1559,
                    Descrizione = "SCHEDA B – UTILIZZAZIONE AGRONOMICA - SEZIONE B1 EFFLUENTI DI ALLEVAMENTO TAL QUALI O TRATTATI",
                }
            },
            { 1561, new Fattispecie
                {
                    Id = 1561,
                    Descrizione = "SCHEDA C – EMISSIONI IN ATMOSFERA PER GLI STABILIMENTI",
                }
            },

            // Secondarie
            { 1401, new Fattispecie
                {
                    Id = 1401,
                    Descrizione = "Domanda di autorizzazione per l'esercizio del commercio di oggetti preziosi",
                }
            },
            { 1402, new Fattispecie
                {
                    Id = 1402,
                    Descrizione = "Domanda di autorizzazione per l'esercizio del commercio di armi comuni",
                }
            },
            { 1, new Fattispecie
                {
                    Id = 1,
                    Descrizione = "Avvio con SCIA per impianti ed edifici soggetti a Certificato Prevenzione Incendi",
                }
            },
            { 9001, new Fattispecie
                {
                    Id = 9001,
                    Descrizione = "SCHEDA A – SCARICHI DI ACQUE REFLUE",
                }
            },
            { 9002, new Fattispecie
                {
                    Id = 9002,
                    Descrizione = "SCHEDA B – UTILIZZAZIONE AGRONOMICA - SEZIONE B1 EFFLUENTI DI ALLEVAMENTO TAL QUALI O TRATTATI",
                }
            },
            { 9003, new Fattispecie
                {
                    Id = 9003,
                    Descrizione = "SCHEDA C – EMISSIONI IN ATMOSFERA PER GLI STABILIMENTI",
                }
            },
        };

        public static Dictionary<int, IEnumerable<int>> FattispeciePrimarieByProcedimento = new()
        {
            { 1644, [1307] },
            { 1436, [1558, 1559, 1561] }
        };

        public static Dictionary<int, IEnumerable<int>> FattispecieSecondarieByIdFattispeciePrimaria = new()
        {
            {1307, [1401, 1402, 1] },
            {1558, [9001] },// [new(){ Id = 9001, Descrizione = "SCHEDA A – SCARICHI DI ACQUE REFLUE"}] },
            {1559, [9002] },//[new(){ Id = 9002, Descrizione = "SCHEDA B – UTILIZZAZIONE AGRONOMICA - SEZIONE B1 EFFLUENTI DI ALLEVAMENTO TAL QUALI O TRATTATI"}] },
            {1561, [9003] }
        };

        public static Dictionary<int, int> ProcedimentiByFattispecie = new()
        {
            {1401, 1520 },
            {1402, 1300 },
            {1, 1255 },
            {9001,9001 },
            {9002, 9002 },
            {9003, 9003 }
        };

        public static IEnumerable<Procedimento> GetProcedimentiById(IEnumerable<int> listaId)
        {
            return listaId.Select(id => Procedimenti[id]);
        }
    }
}
