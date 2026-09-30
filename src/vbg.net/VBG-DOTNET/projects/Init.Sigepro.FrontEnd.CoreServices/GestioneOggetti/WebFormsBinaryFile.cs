using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Utils;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class WebFormsBinaryFile : BinaryFile
    {
        public WebFormsBinaryFile(BinaryFile bf)
            : base()
        {
            this.Initialize(bf.FileName, bf.MimeType, bf.FileContent);
        }

        //public WebFormsBinaryFile(FileUpload fileUpload, IValidPostedFileSpecification specification)
        //    : this(fileUpload.PostedFile, specification)
        //{
        //}

        public WebFormsBinaryFile(IBrowserFile postedFile, IValidPostedFileSpecification specification)
            : base()
        {
            if (postedFile.Size == 0)
            {
                throw new InvalidOperationException("Il file è vuoto o non è valido");
            }

            if (!specification.IsSatisfiedBy(postedFile))
            {
                throw new InvalidOperationException(specification.ErrorMessage);
            }

            this.Initialize(postedFile.Name, postedFile.ContentType, StreamUtils.StreamToBytes(postedFile.OpenReadStream()));
        }
        /*
        public WebFormsBinaryFile(IBrowserFile postedFile, IValidPostedFileSpecificationAsync specification)
            : base()
        {
            if (postedFile.Size == 0)
            {
                throw new InvalidOperationException("Il file è vuoto o non è valido");
            }

            if (!await specification.IsSatisfiedBy(postedFile))
            {
                throw new InvalidOperationException(specification.ErrorMessage);
            }

            this.Initialize(postedFile.Name, postedFile.ContentType, StreamUtils.StreamToBytes(postedFile.OpenReadStream()));
        }
        */
    }
}
