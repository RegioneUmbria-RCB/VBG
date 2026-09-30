using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class CessaConcessioneResponse: CUnicoResponse
    {
        public Esito Esito { get; private set; }
        public IEnumerable<string> IUV { get; private set; } 
        public double Importo { get; private set; }
        public int? MatricolaRichiesta { get; private set; }
        public int? MatricolaOggetto { get; private set; }
        public string ModelloPagoPABase64 { get; private set; }
        public string NomeFile { get; private set; }
        internal static CessaConcessioneResponse FromModificaConcessioneRisposta(modificaConcessioneRichiesta richiesta, modificaConcessioneRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            if (risposta.esito.esito1 == 1)
            {
                return new CessaConcessioneResponse
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

            return new CessaConcessioneResponse
            {
                Esito = new Esito
                {
                    Ok = true,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                IUV = new String[]
                {
                    risposta.IUV_RU,
                    risposta.IUV_R1,
                    risposta.IUV_R2,
                    risposta.IUV_R3,
                    risposta.IUV_R4
                }.Where(d => d != null),
                Importo = risposta.importoCalcolato,
                MatricolaRichiesta = risposta.richiestaConcessione.DIC_MTRSpecified ? risposta.richiestaConcessione.DIC_MTR : (int?)null,
                MatricolaOggetto = risposta.richiestaConcessione.oggetti?.First().OGG_MTR,
                ModelloPagoPABase64 = risposta.modelloPagoPA,
                NomeFile = risposta.nomeFile
            };
        }
    }
}