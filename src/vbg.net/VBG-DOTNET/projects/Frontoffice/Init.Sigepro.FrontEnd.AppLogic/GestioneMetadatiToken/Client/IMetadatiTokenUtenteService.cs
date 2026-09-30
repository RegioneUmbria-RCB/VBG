using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client
{
    public interface IMetadatiTokenUtenteService
    {
        Task<IEnumerable<MetadatoToken>> GetMetadatiTokenUtenteAsync();
        IEnumerable<MetadatoToken> GetMetadatiTokenUtente();
    }
}