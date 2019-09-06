using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneStradario
{
    public interface IStradarioConsoleService
    {
        IEnumerable<StradarioDto> FindStradario(string codiceComune, string comuneLocalizzazione, string partial);
        Stradario GetById(int codiceStradario);
    }
}
