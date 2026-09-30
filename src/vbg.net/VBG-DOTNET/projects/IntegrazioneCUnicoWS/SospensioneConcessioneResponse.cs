using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class SospensioneConcessioneResponse
    {
        public Esito Esito { get; private set; }
        public double Importo { get; private set; }
        public string ModelloPagoPABase64 { get; private set; } 
        public string NomeFile { get; private set; }

        internal static SospensioneConcessioneResponse FromsospensioneConcessioneRisposta(sospensioneConcessioneRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            return new SospensioneConcessioneResponse
            {
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                Importo = risposta.importoCalcolato,
                ModelloPagoPABase64 = risposta.modelloPagoPA,
                NomeFile = risposta.nomeFile

            };
        }
    }
}