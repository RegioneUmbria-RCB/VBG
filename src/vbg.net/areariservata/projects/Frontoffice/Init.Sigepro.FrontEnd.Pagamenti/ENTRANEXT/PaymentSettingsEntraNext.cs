using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class PaymentSettingsEntraNext
    {
        public readonly string UrlWs;
        public readonly string Identificativo;
        public readonly string Username;
        public readonly string Password;
        public readonly string IdentificativoConnettore;
        public readonly string CodiceFiscaleEnte;
        public readonly string Versione;
        public readonly string UrlBack;
        public readonly string UrlRitorno;
        public readonly string UrlNotifica;
        public readonly string TipoPagamento;


        public PaymentSettingsEntraNext(string urlWs, string username, string password, string identificativo, string identificativoConnettore, string codiceFiscaleEnte, 
                                        string versione, string urlBack, string urlRitorno, string urlNotifica, string tipoPagamento)
        {
            this.UrlWs = urlWs;
            this.Username = username;
            this.Password = password;
            this.Identificativo = identificativo;
            this.IdentificativoConnettore = identificativoConnettore;
            this.CodiceFiscaleEnte = codiceFiscaleEnte;
            this.Versione = versione;

            this.UrlBack = urlBack;
            this.UrlRitorno = urlRitorno;
            this.UrlNotifica = urlNotifica;

            this.TipoPagamento = tipoPagamento;
        }
    }
}
