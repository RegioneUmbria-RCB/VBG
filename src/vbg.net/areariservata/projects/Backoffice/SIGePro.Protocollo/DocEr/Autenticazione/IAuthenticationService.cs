using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Autenticazione
{
    public interface IAuthenticationService
    {
        string Token { get; }
        string Username { get; }
        void Login();
        void Logout();
        Dictionary<string, string> GetRuoli(GestioneDocumentaleService gestDocWrapper);
    }
}
