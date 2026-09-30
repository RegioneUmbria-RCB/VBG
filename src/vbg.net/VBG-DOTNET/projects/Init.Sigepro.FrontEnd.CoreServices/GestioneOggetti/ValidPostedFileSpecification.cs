using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Microsoft.AspNetCore.Components.Forms;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class ValidPostedFileSpecification : IValidPostedFileSpecification
    {
        private readonly IConfigurazione<ParametriAllegati> _config;

        public ValidPostedFileSpecification(IConfigurazione<ParametriAllegati> config)
        {
            this._config = config;
        }

        public string ErrorMessage
        {
            get { return this._config.Parametri.WarningDimensioneMassimaAllegatoSuperata; }
        }

        public bool IsSatisfiedBy(IBrowserFile item)
        {
            if (this._config.Parametri.DimensioneMassimaAllegato == 0)
            {
                return true;
            }

            return item.Size <= this._config.Parametri.DimensioneMassimaAllegato;
        }
    }

    public class ValidPostedFileSpecificationAsync : IValidPostedFileSpecificationAsync
    {
        private readonly IConfigurazione<ParametriAllegati> _config;

        public ValidPostedFileSpecificationAsync(IConfigurazione<ParametriAllegati> config)
        {
            this._config = config;
        }

        public string ErrorMessage
        {
            get { return this._config.Parametri.WarningDimensioneMassimaAllegatoSuperata; }
        }

        public Task<bool> IsSatisfiedByAsync(IBrowserFile item)
        {
            if (this._config.Parametri.DimensioneMassimaAllegato == 0)
            {
                return Task.FromResult(true);
            }

            return Task.FromResult(item.Size <= this._config.Parametri.DimensioneMassimaAllegato);
        }
    }
}
