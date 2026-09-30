using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public static class IBrowserFileExtensions
    {
        public static async Task<BinaryFile?> ToBinaryFileAsync(this IBrowserFile postedFile, IValidPostedFileSpecificationAsync specification, long? maxAllowedSize = null)
        {
            if (postedFile is null)
            {
                return null;
            }

            if (postedFile.Size == 0)
            {
                return null;
            }

            if (!await specification.IsSatisfiedByAsync(postedFile))
            {
                throw new InvalidOperationException(specification.ErrorMessage);
            }

            using var ms = new MemoryStream();

            var maxSize = maxAllowedSize.GetValueOrDefault(20971520); // 20MB
            var rs = postedFile.OpenReadStream(maxAllowedSize: maxSize);

            await rs.CopyToAsync(ms);

            return BinaryFile.FromFileData(postedFile.Name, postedFile.ContentType, ms.ToArray());
        }
    }
}
