using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Utils;
using System;
using System.Web;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti
{
    public class WebFormsBinaryFile : BinaryFile
    {
        public WebFormsBinaryFile(BinaryFile bf)
            : base()
        {
            this.Initialize(bf.FileName, bf.MimeType, bf.FileContent);
        }

        public WebFormsBinaryFile(FileUpload fileUpload, IValidPostedFileSpecification specification)
            : this(fileUpload.PostedFile, specification)
        {
        }

        public WebFormsBinaryFile(HttpPostedFile postedFile, IValidPostedFileSpecification specification)
            : base()
        {
            if (postedFile.ContentLength == 0)
            {
                throw new InvalidOperationException("Il file è vuoto o non è valido");
            }

            if (!specification.IsSatisfiedBy(postedFile))
            {
                throw new InvalidOperationException(specification.ErrorMessage);
            }

            this.Initialize(postedFile.FileName, postedFile.ContentType, StreamUtils.StreamToBytes(postedFile.InputStream));
        }
    }
}