using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail
{
    public interface IInpsInailService
    {
        IEnumerable<SedeInpsDto> GetSediInps(string partial);
        SedeInpsDto GetSedeInpsByCodice(string codice);
        IEnumerable<SedeInailDto> GetSediInail(string partial);
        SedeInailDto GetSedeInailByCodice(string codice);
    }
}
