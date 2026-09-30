using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class SubentraConcessioneRequest
    {
        public string CodiceFiscaleSubentrante { get; set; }
        public DateTime DataSubentro { get; set; }
        public double ImportoCauzione { get; set; } = 0.0d;
        public int MatricolaDichiarazioneSubentrata { get; set; }

        internal subentroConcessioneRichiesta TosubentroConcessioneRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            return new subentroConcessioneRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                codiceFiscaleSubentrante = this.CodiceFiscaleSubentrante,
                dataSubentro = DateUtils.DateToCunicoWSDate(this.DataSubentro).Value,
                matricolaDichiarazioneVecchia = this.MatricolaDichiarazioneSubentrata,
                importoCauzioneSubentro = this.ImportoCauzione
            };
        }
    }
}