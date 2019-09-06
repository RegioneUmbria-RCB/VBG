using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Parma
{
    public class AnagrafeResponseAdapter
    {
        DataBase _db;

        public AnagrafeResponseAdapter(DataBase db)
        {
            this._db = db;
        }

        public Anagrafe Adatta(AnagrafeResponse anagrafe)
        {
            var mgr = new ComuniMgr(this._db);

            string codiceIstatResid = $"0{anagrafe.cod_comune}";
            string codiceIstatNascita = $"0{anagrafe.cod_comune_nascita}";
            string indirizzo = $"{anagrafe.indirizzo} {anagrafe.civico}";

            var comuneResidenza = mgr.GetByCodiceIstat(codiceIstatResid);
            var comuneNascita = mgr.GetByCodiceIstat(codiceIstatNascita);

            var sesso = Convert.ToInt32(anagrafe.codicefiscale.Substring(9, 2)) > 40 ? "F" : "M";

            return new Anagrafe
            {
                NOMINATIVO = anagrafe.cognome,
                NOME = anagrafe.nome,
                TELEFONO = anagrafe.telefono,
                TELEFONOCELLULARE = anagrafe.cellulare,
                EMAIL = anagrafe.mail,
                Pec = anagrafe.mailpec,
                DATANASCITA = anagrafe.datanascita,
                INDIRIZZO = indirizzo.Trim(),
                CAP = anagrafe.cap,
                CODICEFISCALE = anagrafe.codicefiscale,
                COMUNERESIDENZA = (comuneResidenza != null) ? comuneResidenza.CODICECOMUNE : "",
                CODCOMNASCITA = (comuneNascita != null) ? comuneNascita.CODICECOMUNE : "",
                PROVINCIA = comuneResidenza.SIGLAPROVINCIA,
                SESSO = sesso
            };
        }
    }
}
