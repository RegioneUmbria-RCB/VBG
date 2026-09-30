using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class GetSoggettoResponse: CUnicoResponse
    {
        public Esito Esito { get; private set; }
        public string Nominativo { get; private set; } 
        public string Nome { get; private set; }
        public string CodiceFiscale { get; private set; }
        public string PartitaIVA { get; private set; }

        internal static GetSoggettoResponse FromrichiestaSoggettiResponse(richiestaSoggetti richiesta, richiestaSoggettiResponse risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            if (risposta.esito.esito1 == 1)
            {
                return new GetSoggettoResponse
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

            return new GetSoggettoResponse
            {
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                Nominativo = risposta.soggetto.SOG_DEN,
                Nome = risposta.soggetto.SOG_NOM,
                CodiceFiscale = risposta.soggetto.SOG_COD_FIS,
                PartitaIVA = risposta.soggetto.SOG_PAR_IVA
            };
        }
    }
}