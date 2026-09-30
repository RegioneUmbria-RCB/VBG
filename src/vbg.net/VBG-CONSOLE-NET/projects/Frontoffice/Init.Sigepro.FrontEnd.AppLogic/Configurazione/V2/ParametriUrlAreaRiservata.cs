using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriUrlAreaRiservata : IParametriConfigurazione
    {
        public readonly string PaginaDefault = "~/Reserved/default.aspx";
        public readonly string ErroreIstanzaGiaPresentata = "~/Reserved/ErrorPages/DomandaGiaPresentata.aspx";
        public readonly string LogoutCompleted = "~/LogoutCompleted.aspx";
        public readonly string RegistrazioneCompletata = "~/RegistrazioneCompletata.aspx";
        public readonly string RegistrazioneCompletataCie = "~/RegistrazioneCompletataCie.aspx";
        public readonly string FirmaDigitale = "~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaDocumento.aspx";
        public readonly string UploadAllegatiMultipli = "~/Reserved/InserimentoIstanza/GestioneAllegatiMultipli.aspx";
        public readonly string EditOggetti = "~/Reserved/InserimentoIstanza/EditOggetti/Edit.aspx";
        public readonly string VisuraAutenticata = "~/Reserved/DettaglioIstanzaEx.aspx";

        public readonly string HandlerValidazioneCodiceFiscale = "~/Public/WebServices/ValidazioneCfService.asmx/ValidaCF";

        public ParametriUrlAreaRiservata()
        {
        }
    }
}
