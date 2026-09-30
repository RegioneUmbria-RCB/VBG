using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip
{
    public interface IZipAccessoAttiService
    {
        Task<BinaryFile> GetZipFileDocumentiAsync(int idAccessoAtti, string uuidPratica);
    }
}
