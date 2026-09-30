using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti
{
    public class BinaryFileFactory
    {
        private readonly ValidPostedFileSpecification _validPostedFileSpecification;

        public BinaryFileFactory(ValidPostedFileSpecification validPostedFileSpecification)
        {
            this._validPostedFileSpecification = validPostedFileSpecification;
        }

        public BinaryFile Create(HttpPostedFile fileUpload)
        {
            return new WebFormsBinaryFile(fileUpload, this._validPostedFileSpecification);
        }
    }
}