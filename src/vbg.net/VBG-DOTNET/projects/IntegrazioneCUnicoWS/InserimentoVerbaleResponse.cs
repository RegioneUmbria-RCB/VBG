using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;

namespace IntegrazioneCUnicoWS
{
    public class InserimentoVerbaleResponse : CUnicoResponse
    {
        public Esito Esito { get; private set; }
        public double ImportoIntero { get; private set; }
        public double ImportoRidotto { get; private set; }
        public IEnumerable<IUVSanzioni> IUV { get; private set; }
        public int? MatricolaRichiesta { get; private set; }
        public int[] MatricolaOggetto { get; private set; }
        public string ModelloPagoPABase64 { get; private set; }
        public string NomeFile { get; private set; }
        public List<DatiOccupazione> Occupazioni { get; set; }


        internal static InserimentoVerbaleResponse FrominserisciVerbaleRisposta(inserisciVerbaleRichiesta richiesta, inserisciVerbaleRisposta risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            if (risposta.esito.esito1 == 1)
            {
                return new InserimentoVerbaleResponse
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

            return new InserimentoVerbaleResponse
            {
                Occupazioni = risposta
                                .verbale
                                .oggetti
                                .Select(x => new DatiOccupazione
                                {
                                    Matricola = x.VRBO_MTR,
                                    TipoOccupazione = x.VRBO_COD_TIP,
                                    CodiceVia = x.VRBO_COD_VIA,
                                    Civico = x.VRBO_CIV,

                                })
                                .ToList(),
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                IUV = new List<IUVSanzioni>()
                {
                    new IUVSanzioni
                    {
                        IUVIntero = risposta.iuvImportoIntero,
                        IUVRidotto = risposta.iuvImportoRidotto
                    }
                },
                ImportoIntero = risposta.importoCalcolato,
                ImportoRidotto = risposta.importoRidotto,
                MatricolaOggetto = risposta
                                    .verbale
                                    .oggetti
                                    .Select(x => x.VRBO_MTR)
                                    .ToArray(),
                MatricolaRichiesta = risposta.verbale.VRB_MTRSpecified ? risposta.verbale.VRB_MTR : (int?)null,
                ModelloPagoPABase64 = risposta.modelloPagoPA,
                NomeFile = risposta.nomeFile
            };
        }
    }
}