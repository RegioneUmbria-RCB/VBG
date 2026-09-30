using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione
{
    public class ParametriGenerazioneCertificatoInvio :  IParametriConfigurazione
    {
        public readonly string DescrizioneFile;
        public readonly string NomeFile;

        public ParametriGenerazioneCertificatoInvio(string nomeFile, string descrizioneFile)
        {
            DescrizioneFile = descrizioneFile;
            NomeFile = nomeFile;
        }
    }
}
