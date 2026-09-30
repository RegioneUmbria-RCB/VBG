using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneRisorseTestuali
{
    public class LayoutTestiAuditService : ILayoutTestiAuditService
    {
        public static class Constants
        {
            public const string SequenceName = "LAYOUTTESTI_AUDIT.ID";
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly IAnagraficheBreviService _anagraficheBreviService;
        private readonly int _codiceAnagrafe;
        private readonly ILog _log = LogManager.GetLogger(typeof(LayoutTestiAuditService));

        public LayoutTestiAuditService(DataBase db, string idComune, int codiceAnagrafe)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            this._db = db ?? throw new ArgumentNullException(nameof(db));
            this._idComune = idComune;
            this._codiceAnagrafe = codiceAnagrafe;
            this._anagraficheBreviService = new AnagraficheBreviService(db, idComune);
        }


        public void LogTestoCreato(string software, string codiceTesto, string nuovoTesto)
        {
            var utente = this._anagraficheBreviService.GetNomeUtente(_codiceAnagrafe);
            var msg = $"{utente} ha creato il testo {codiceTesto} del software {software}: \"{nuovoTesto}\"";

            Audit(software, codiceTesto, msg);
        }

        public void LogTestoModificato(string software, string codiceTesto, string nuovoTesto)
        {
            var utente = this._anagraficheBreviService.GetNomeUtente(_codiceAnagrafe);
            var msg = $"{utente} ha modificato il testo {codiceTesto} del software {software} in \"{nuovoTesto}\"";

            Audit(software, codiceTesto, msg);
        }

        public void LogTestoEliminato(string software, string codiceTesto)
        {
            var utente = this._anagraficheBreviService.GetNomeUtente(_codiceAnagrafe);
            var msg = $"{utente} ha eliminato il testo {codiceTesto} del software {software}, verrà ripristinato il testo di default.";

            Audit(software, codiceTesto, msg);
        }

        private void Audit(string software, string codiceTesto, string messaggio)
        {
            try
            {
                if (messaggio.Length > 2000)
                {
                    messaggio = messaggio.Substring(0, 2000);
                }

                var sql = $@"INSERT INTO layouttesti_audit(idcomune, id, data, software, codicetesto, messaggio) VALUES
                        ({this._db.QueryParameter("idcomune")}, 
                         {this._db.QueryParameter("id")}, 
                         {this._db.QueryParameter("data")}, 
                        {this._db.QueryParameter("software")}, 
                        {this._db.QueryParameter("codicetesto")}, 
                        {this._db.QueryParameter("messaggio")}
                        )";

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("idcomune", _idComune);
                    mp.AddParameter("id", NextId());
                    mp.AddParameter("data", DateTime.Now);
                    mp.AddParameter("software", software);
                    mp.AddParameter("codicetesto", codiceTesto);
                    mp.AddParameter("messaggio", messaggio);
                });
            }
            catch (Exception ex)
            {
                this._log.Error($"Si è verificato un errore nell'auditing del messaggio \"{messaggio}\" per il software {software} e codiceTesto {codiceTesto}: {ex}");
            }
        }

        private int NextId()
        {
            return new SequenceTableService(this._db, this._idComune).NextId(Constants.SequenceName);
        }
    }
}
