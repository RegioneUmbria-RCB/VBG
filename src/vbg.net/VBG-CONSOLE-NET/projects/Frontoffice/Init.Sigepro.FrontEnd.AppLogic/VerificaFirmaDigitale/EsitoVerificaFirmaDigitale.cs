using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{

    public class EsitoVerificaFirmaDigitale
    {
        public StatoVerificaFirma Stato { get; private set; }
        public string Errore { get; private set; }

        private readonly string[] _nominativiSoggettiFirmatari;

        public EsitoVerificaFirmaDigitale(StatoVerificaFirma esito, IEnumerable<string> nominativiSoggettiFirmatari = null)
        {
            this.Stato = esito;
            this.Errore = EstraiMessaggioDaEsito(esito);
            this._nominativiSoggettiFirmatari = nominativiSoggettiFirmatari == null ? new string[0] : nominativiSoggettiFirmatari.ToArray();
        }

        private static string EstraiMessaggioDaEsito(StatoVerificaFirma esito)
        {
            switch (esito)
            {
                case StatoVerificaFirma.CertificatoRevocato:
                    return "Certificato revocato o non valido";
                case StatoVerificaFirma.Errore:
                    return "Errore durante la verifica della firma";
                case StatoVerificaFirma.FirmaNonValida:
                    return "Il file non sembra essere firmato digitalmente o contiene una firma non valida";
            }

            return String.Empty;
        }

        public IEnumerable<string> StringheSoggettiFirmatari
        {
            get { return this._nominativiSoggettiFirmatari.Select(x => x.ToUpperInvariant()); }
        }
    }


}
