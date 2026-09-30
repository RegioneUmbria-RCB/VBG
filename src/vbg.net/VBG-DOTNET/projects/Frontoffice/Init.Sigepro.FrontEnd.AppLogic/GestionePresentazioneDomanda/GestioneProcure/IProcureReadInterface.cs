using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
    public interface IProcureReadInterface
    {
        IEnumerable<ProcuraDomandaOnline> Procure { get; }
        bool IsUtenteProcuratore(string codiceFiscaleUtente);
        string GetCodiceFiscaleDelProcuratoreDi(string codiceFiscaleProcurato);
    }
}
