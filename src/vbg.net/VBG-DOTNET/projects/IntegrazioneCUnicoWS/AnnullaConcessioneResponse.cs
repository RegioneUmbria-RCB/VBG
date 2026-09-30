using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class AnnullaConcessioneResponse : CUnicoResponse
    {
        public Esito Esito { get; private set; }

        internal static AnnullaConcessioneResponse FromannullaConcessioneRisposta(annullaConcessioneRichiesta richiesta, annullaConcessioneRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            if (risposta.esito.esito1 == 1)
            {
                return new AnnullaConcessioneResponse
                {
                    Esito = new Esito
                    {
                        Ok = false,
                        Codice = risposta.esito.faultCode,
                        Messaggio = risposta.esito.faultString,
                        XMLRichiesta = XmlSerializeToString(richiesta),
                        XMLRisposta = XmlSerializeToString(risposta)
                    }
                };
            }

            return new AnnullaConcessioneResponse
            {
                Esito = new Esito
                {
                    Ok = true,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                }
            };
        }
    }
}