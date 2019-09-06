using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    public class BinaryFileFactory
    {
        ValidPostedFileSpecification _validPostedFileSpecification;

        public BinaryFileFactory(ValidPostedFileSpecification validPostedFileSpecification)
        {
            this._validPostedFileSpecification = validPostedFileSpecification;
        }

        public BinaryFile Create(HttpPostedFile fileUpload)
        {
            return new BinaryFile(fileUpload, this._validPostedFileSpecification);
        }
    }
}
