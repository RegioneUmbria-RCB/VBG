using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class PreventivoResponse
    {
        public Esito Esito { get; private set; }
        public string Formula { get; private set; }
        public double ImportoCalcolato { get; private set; } 
        public bool ImportoMinimoPrevisto { get; private set; }
        public double TariffaApplicata { get; private set; }

        internal static PreventivoResponse FrompreventivoRisposta(preventivoRisposta risposta)
        {

            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            return new PreventivoResponse
            {
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                Formula = risposta.formula,
                ImportoCalcolato = risposta.importoCalcolatoSpecified ? risposta.importoCalcolato : 0.0d,
                ImportoMinimoPrevisto = risposta.importoMinimo,
                TariffaApplicata = risposta.tariffaApplicataSpecified ? risposta.tariffaApplicata : 0.0d
            };
        }
    }
}