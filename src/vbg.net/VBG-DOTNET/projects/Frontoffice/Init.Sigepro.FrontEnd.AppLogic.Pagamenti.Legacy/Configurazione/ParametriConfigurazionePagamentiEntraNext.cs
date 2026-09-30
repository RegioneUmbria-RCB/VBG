using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using VBG.Pagamenti.Legacy.ENTRANEXT;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione
{
    public class ParametriConfigurazionePagamentiEntraNext : PaymentSettingsEntraNext, IParametriConfigurazione
    {
        public ParametriConfigurazionePagamentiEntraNext(string urlWs, string identificativoConnettore, string codiceFiscaleEnte, string versione, string identificativo, string username, string password, string urlRitorno, string urlNotifica, string codiceTipoPagamento)
            : base(urlWs, username, password, identificativo, identificativoConnettore, codiceFiscaleEnte, versione, urlRitorno, urlRitorno, urlNotifica, codiceTipoPagamento)
        {

        }
    }
}
