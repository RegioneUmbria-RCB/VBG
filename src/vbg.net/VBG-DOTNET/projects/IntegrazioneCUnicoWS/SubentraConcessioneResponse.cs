using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class SubentraConcessioneResponse: CUnicoResponse
    {
        public Esito Esito { get; private set; } 
        public double Importo { get; private set; }
        public IEnumerable<string> IUV { get; private set; }
        public int MatricolaRichiesta { get; private set; }
        public int[] MatricolaOggetto { get; private set; }
        public string ModelloPagoPABase64 { get; private set; }
        public string NomeFile { get; private set; }
        public List<DatiOccupazione> Occupazioni { get; set; }

        internal static SubentraConcessioneResponse FromsubentroConcessioneRisposta(subentroConcessioneRichiesta richiesta, subentroConcessioneRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            if (risposta.esito.esito1 == 1)
            {
                return new SubentraConcessioneResponse
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

            return new SubentraConcessioneResponse
            {
                Occupazioni = risposta
                               .concessioneNew
                               .oggetti
                               .ToList()
                               .Select(x => new DatiOccupazione
                               {
                                   Matricola = x.OGG_MTR,
                                   TipoOccupazione = x.OGG_COD_TIP,
                                   CodiceVia = x.OGG_COD_VIA,
                                   Civico = x.OGG_CIV,

                               })
                               .ToList(),
                Esito = new Esito
                {
                    Ok = true,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                IUV = new String[]
                       {
                            risposta.newIUV_RU,
                            risposta.newIUV_R1,
                            risposta.newIUV_R2,
                            risposta.newIUV_R3,
                            risposta.newIUV_R4
                       }
                       .Where(d => d != null),
                Importo = risposta.newImportoCalcolato,
                MatricolaOggetto = risposta
                                   .concessioneNew
                                   .oggetti
                                   .Where(x => !x.cancellato)
                                   .Select(x => x.OGG_MTR)
                                   .ToArray(),
                MatricolaRichiesta = risposta.concessioneNew.DIC_MTR,
                ModelloPagoPABase64 = risposta.newModelloPagoPA,
                NomeFile = risposta.newNomeFile
            };
        }
    }
}