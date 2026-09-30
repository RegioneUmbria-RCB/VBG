using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class CompositePostedFileSpecification : IValidPostedFileSpecification
    {
        private List<IValidPostedFileSpecification> _validators = new List<IValidPostedFileSpecification>();

        public string ErrorMessage { get; private set; } = "";

        public bool IsSatisfiedBy(HttpPostedFile item)
        {
            this.ErrorMessage = "";

            foreach (var validator in this._validators)
            {
                if (!validator.IsSatisfiedBy(item))
                {
                    this.ErrorMessage = validator.ErrorMessage;
                    return false;
                }
            }

            return true;
        }

        public void Add(IValidPostedFileSpecification specification)
        {
            this._validators.Add(specification);
        }
    }
}
