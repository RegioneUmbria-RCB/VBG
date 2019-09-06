using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    public class SizeBasedValidPostedFileSpecification : IValidPostedFileSpecification
    {
        int _maxSize;

        public SizeBasedValidPostedFileSpecification(int maxSize)
        {
            this._maxSize = maxSize;
        }

        public string ErrorMessage
        {
            get { return $"Le dimensioni del file caricato superano le dimensioni massime consentite ({this._maxSize.GetHumanReadableFileSize() })"; }
        }

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            if (this._maxSize == 0)
            {
                return true;
            }

            return item.ContentLength <= this._maxSize;
        }
    }
}
