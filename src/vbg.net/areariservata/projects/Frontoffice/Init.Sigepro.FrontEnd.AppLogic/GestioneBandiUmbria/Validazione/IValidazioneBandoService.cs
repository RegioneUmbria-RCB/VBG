// -----------------------------------------------------------------------
// <copyright file="IValidazioneBandoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IValidazioneBandoService
    {
		IEnumerable<string> GetErrori(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello1, DatiPdfCompilabile datiModello2);
        IEnumerable<string> GetAvvertimenti(IDomandaOnlineReadInterface domanda, DatiPdfCompilabile datiModello2);
    }
}
