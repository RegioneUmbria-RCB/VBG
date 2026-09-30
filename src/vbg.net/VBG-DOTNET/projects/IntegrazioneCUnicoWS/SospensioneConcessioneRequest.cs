using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace IntegrazioneCUnicoWS
{
    public class SospensioneConcessioneRequest
    { 
        public int MatricolaRichiesta { get; set; }
        public int? Anno { get; set; }
        //public double? Quantita { get; set; }
        public int GiorniDiSospensione { get; set; }
        public List<int> IdOggettiDaSospendere { get; set; }

        internal sospensioneConcessioneRichiesta TosospensioneConcessioneRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            return new sospensioneConcessioneRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                DIC_MTR = this.MatricolaRichiesta,
                DIC_MTRSpecified = true,
                ANNO = this.Anno.HasValue ? this.Anno.Value : int.MinValue,
                ANNOSpecified = this.Anno.HasValue,
                //QTA = this.Quantita.HasValue ? this.Quantita.Value : int.MinValue,
                QTASpecified = false,
                GG = this.GiorniDiSospensione,
                GGSpecified = true,
                listaOGG_MTR = this.IdOggettiDaSospendere.Select( x => (int?) x ).ToArray()
            };
        }
    }
}
