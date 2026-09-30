using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneComuniAssociati
{
    public interface IComuniAssociatiConsoleService
    {
        IEnumerable<ComuniMgr.DatiComuneCompatto> GetComuniAssociati(string software);
        string GetPecComuniAssociato(string codiceComune, string software);
    }
}