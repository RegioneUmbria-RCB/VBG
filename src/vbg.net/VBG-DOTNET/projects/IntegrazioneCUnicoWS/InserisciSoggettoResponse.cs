using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class InserisciSoggettoResponse
    {
        public Esito Esito { get; private set; } 

        internal static InserisciSoggettoResponse FrominserisciSoggettoRisposta(inserisciSoggettoRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            return new InserisciSoggettoResponse
            {
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                }
            };
        }
    }
}