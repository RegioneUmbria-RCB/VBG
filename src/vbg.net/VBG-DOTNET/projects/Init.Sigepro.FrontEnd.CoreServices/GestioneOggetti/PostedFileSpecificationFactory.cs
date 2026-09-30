using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneOggetti
{
    public class PostedFileSpecificationFactory : IPostedFileSpecificationFactory
    {
        private readonly IConfigurazione<ParametriAllegati> _configurazioneAllegati;
        private readonly IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

        public PostedFileSpecificationFactory(IConfigurazione<ParametriAllegati> configurazioneAllegati, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
        {
            this._configurazioneAllegati = configurazioneAllegati;
            this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
        }

        public IValidPostedFileSpecification Get(FileValidationFlags flags)
        {
            var validator = new CompositePostedFileSpecification();

            if (flags.Obbligatorio)
            {
                validator.Add(new NonZeroPostedFileSpecification());
            }

            if (flags.DimensioneMassimaBytes.HasValue)
            {
                validator.Add(new SizeBasedValidPostedFileSpecification(flags.DimensioneMassimaBytes.Value));
            }
            else
            {
                validator.Add(new ValidPostedFileSpecification(this._configurazioneAllegati));
            }

            if (flags.FirmatoDigitalmente)
            {
                validator.Add(new FirmatoDigitalmentePostedFileSpecification(this._verificaFirmaDigitaleService));
            }

            if (flags.EstensioniAmmesse.Any())
            {
                validator.Add(new EstensioneValidaPostedFileSpecification(flags.EstensioniAmmesse));
            }

            return validator;
        }
    }

    public class PostedFileSpecificationFactoryAsync : IPostedFileSpecificationFactoryAsync
    {
        private readonly IConfigurazione<ParametriAllegati> _configurazioneAllegati;
        private readonly IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

        public PostedFileSpecificationFactoryAsync(IConfigurazione<ParametriAllegati> configurazioneAllegati, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
        {
            this._configurazioneAllegati = configurazioneAllegati;
            this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
        }

        IValidPostedFileSpecificationAsync IPostedFileSpecificationFactoryAsync.Get(FileValidationFlags flags)
        {
            var validator = new CompositePostedFileSpecification();

            if (flags.Obbligatorio)
            {
                validator.AddAsyncSpecification(new NonZeroPostedFileSpecification());
            }

            if (flags.DimensioneMassimaBytes.HasValue)
            {
                validator.AddAsyncSpecification(new SizeBasedValidPostedFileSpecification(flags.DimensioneMassimaBytes.Value));
            }
            else
            {
                validator.AddAsyncSpecification(new ValidPostedFileSpecificationAsync(this._configurazioneAllegati));
            }

            if (flags.FirmatoDigitalmente)
            {
                validator.AddAsyncSpecification(new FirmatoDigitalmentePostedFileSpecification(this._verificaFirmaDigitaleService));
            }

            if (flags.EstensioniAmmesse.Any())
            {
                validator.AddAsyncSpecification(new EstensioneValidaPostedFileSpecification(flags.EstensioniAmmesse));
            }

            return validator;
        }
    }
}
