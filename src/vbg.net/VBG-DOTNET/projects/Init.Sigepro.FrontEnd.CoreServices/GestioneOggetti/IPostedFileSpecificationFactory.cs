using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public interface IPostedFileSpecificationFactory
    {
        IValidPostedFileSpecification Get(FileValidationFlags flags);
    }

    public interface IPostedFileSpecificationFactoryAsync
    {
        IValidPostedFileSpecificationAsync Get(FileValidationFlags flags);
    }
}
