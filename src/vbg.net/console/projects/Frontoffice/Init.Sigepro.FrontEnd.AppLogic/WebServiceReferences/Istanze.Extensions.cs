using Init.SIGePro.DatiDinamici.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class AnagrafeExtensions
    {
        public static bool HaCodiceFiscale(this Anagrafe anagrafe, string codiceFiscale)
        {
            var cfUcase = codiceFiscale.ToUpperInvariant();

            if (anagrafe == null)
            {
                return false;
            }

            if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE) && anagrafe.CODICEFISCALE.ToUpperInvariant() == cfUcase)
            {
                return true;
            }

            if (!String.IsNullOrEmpty(anagrafe.PARTITAIVA) && anagrafe.PARTITAIVA.ToUpperInvariant() == cfUcase)
            {
                return true;
            }

            return false;
        }
    }


    // Questa classe è utilizzata solamente per definire IClasseContestoModelloDinamico come
    // classe base di Istanze, altrimenti darebbe errore quando si va ad assegnare la classe 
    // nel Dyn2IstanzeManager
    public partial class Istanze : IClasseContestoModelloDinamico
    {
        public bool PuoVisualizzareDatiCompleti(string codiceFiscale)
        {
            if (this.Richiedente != null && this.Richiedente.HaCodiceFiscale(codiceFiscale))
            {
                return true;
            }

            if (this.Professionista != null && this.Professionista.HaCodiceFiscale(codiceFiscale))
            {
                return true;
            }

            if (this.AziendaRichiedente != null && this.AziendaRichiedente.HaCodiceFiscale(codiceFiscale))
            {
                return true;
            }

            return false;
        }
    }
}
