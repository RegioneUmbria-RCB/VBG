using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class InserisciConcessioneResponse : CUnicoResponse
    {
        public Esito Esito { get; private set; }
        public double Importo { get; private set; }
        public IEnumerable<string> IUV { get; private set; }
        public int? MatricolaRichiesta { get; private set; }
        public int[] MatricolaOggetto { get; private set; }
        public string ModelloPagoPABase64 { get; private set; }
        public string NomeFile { get; private set; }
        public List<DatiOccupazione> Occupazioni { get; set; }

        internal static InserisciConcessioneResponse FromInserisciConcessioneRisposta(inserisciConcessioneRichiesta richiesta, inserisciConcessioneRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }


            if (risposta.esito.esito1 == 1)
            {
                return new InserisciConcessioneResponse
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

            return new InserisciConcessioneResponse
            {
                Occupazioni = risposta
                                .richiestaConcessione
                                .oggetti
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
                    Ok = risposta.esito.esito1 == 0,
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

                MatricolaOggetto = risposta
                                    .richiestaConcessione
                                    .oggetti
                                    .ToList()
                                    .Select(x => x.OGG_MTR)
                                    .ToArray(),
                MatricolaRichiesta = risposta.richiestaConcessione.DIC_MTRSpecified ? risposta.richiestaConcessione.DIC_MTR : (int?)null,
                ModelloPagoPABase64 = risposta.modelloPagoPA,
                NomeFile = risposta.nomeFile
            };
        }
    }
}