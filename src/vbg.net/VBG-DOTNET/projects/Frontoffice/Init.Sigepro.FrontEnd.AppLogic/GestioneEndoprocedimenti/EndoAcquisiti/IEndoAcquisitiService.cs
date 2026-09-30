using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti
{
    public interface IEndoAcquisitiService
    {
        void SalvaEndoAcquisiti(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoAcquisiti);
        Task SalvaEndoAcquisitiAsync(int idDomanda, IEnumerable<DatiEndoprocedimentoPresente> listaEndoUtente);

        IEnumerable<string> VerificaCorrettezzaListaEndoAcquisiti(IEnumerable<DatiEndoprocedimentoPresente> listaEndoAcquisiti);

        Dictionary<int, IEnumerable<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(IEnumerable<int> codiciInventario);

        void AllegaFileAEndoAcquisito(int idDomanda, int codiceInventario, BinaryFile file, bool verificaFirma);
        void AllegaFileAEndoAcquisito(int idDomanda, int codiceInventario, int? codiceOggetto);
        Task AllegaFileAEndoAcquisitoAsync(int idDomanda, int codiceInventario, int? codiceOggetto);

        TipiTitoloDto GetTipoTitoloById(int codiceTipoTitolo);
    }
}
