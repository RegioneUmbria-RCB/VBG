using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.Visura.ProssimiPassi
{
    public interface IProssimiPassiService
    {
        IEnumerable<ProssimiPassiDto> GetProssimiPassi(int codiceIstanza);
    }
}
