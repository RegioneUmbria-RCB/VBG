using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class NonZeroPostedFileSpecification : IValidPostedFileSpecification
    {
        public string ErrorMessage => "Il file inviato è vuoto oppure non è stato possibile leggerne il contenuto";

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            return (item?.ContentLength).GetValueOrDefault(0) > 0;
        }
    }
}
