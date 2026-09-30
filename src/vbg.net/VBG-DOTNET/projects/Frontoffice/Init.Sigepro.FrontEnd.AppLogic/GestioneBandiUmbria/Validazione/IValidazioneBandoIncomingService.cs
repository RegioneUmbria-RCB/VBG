using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    public interface IValidazioneBandoIncomingService
    {
        IEnumerable<string> GetErrori(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello1, DatiPdfCompilabile datiModello2, IEnumerable<DatiPdfCompilabile> datiModello3);
        IEnumerable<string> GetAvvertimenti(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello2);
    }
}
