using Init.SIGePro.Manager.DTO.Oneri;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneOneri
{
    public interface IOneriConsoleService
    {
        IEnumerable<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(int codiceIntervento, IEnumerable<int> listaIdEndo, string codiceComuneAssociato);
    }
}
