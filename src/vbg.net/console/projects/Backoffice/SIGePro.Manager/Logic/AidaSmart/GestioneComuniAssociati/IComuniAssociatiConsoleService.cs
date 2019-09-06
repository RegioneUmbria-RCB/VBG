using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneComuniAssociati
{
    public interface IComuniAssociatiConsoleService
    {
        IEnumerable<ComuniMgr.DatiComuneCompatto> GetComuniAssociati();
        string GetPecComuniAssociato(string codiceComune, string software);
    }
}