using Init.Sigepro.FrontEnd.WebServices.Nla;

namespace AreaRiservataCore.wcf.nla
{
    public static class ErroriInserimentoAttivita
    {
        public static InserimentoAttivitaNLAResponse1 AttivitaNonSupportata(string nomeAttivita)
        {
            return CreaErrore("ATT_001", $"Attività {nomeAttivita} non supportata");
        }

        public static InserimentoAttivitaNLAResponse1 DettagliCorrezioniSsuNonTrovati(string nomeElemento)
        {
            return CreaErrore("SSU_001", $"La richiesta di correzione SSU non contiene i dettagli della correzione nell'elemento {nomeElemento} della sezione altriDati");
        }

        public static InserimentoAttivitaNLAResponse1 DettagliIntegrazioniSsuNonTrovati(string nomeElemento)
        {
            return CreaErrore("SSU_001", $"La richiesta di integrazione SSU non contiene i dettagli dell'integrazione nell'elemento {nomeElemento} della sezione altriDati");
        }

        public static InserimentoAttivitaNLAResponse1 ErroreGenerico(string messaggio)
        {
            return CreaErrore("ATT_000", messaggio);
        }


        private static InserimentoAttivitaNLAResponse1 CreaErrore(string codice, string descrizione)
        {
            return new InserimentoAttivitaNLAResponse1
            {
                InserimentoAttivitaNLAResponse = new InserimentoAttivitaNLAResponse
                {
                    Items =
                        [
                            new ErroreType
                            {
                                descrizione = descrizione,
                                numeroErrore = codice
                            }
                        ]
                }
            };
        }
    }
}
