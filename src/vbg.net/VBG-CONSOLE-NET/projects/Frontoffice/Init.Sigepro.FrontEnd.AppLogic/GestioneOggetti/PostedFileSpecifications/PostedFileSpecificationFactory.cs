using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.PostedFileSpecifications
{
    public class PostedFileSpecificationFactory : IPostedFileSpecificationFactory
    {
        private readonly IConfigurazione<ParametriAllegati> _configurazioneAllegati;
        private readonly IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

        public PostedFileSpecificationFactory(IConfigurazione<ParametriAllegati> configurazioneAllegati, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
        {
            _configurazioneAllegati = configurazioneAllegati;
            _verificaFirmaDigitaleService = verificaFirmaDigitaleService;
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
                validator.Add(new ValidPostedFileSpecification(_configurazioneAllegati));
            }

            if (flags.FirmatoDigitalmente)
            {
                validator.Add(new FirmatoDigitalmentePostedFileSpecification(_verificaFirmaDigitaleService));
            }

            if (flags.EstensioniAmmesse.Any())
            {
                validator.Add(new EstensioneValidaPostedFileSpecification(flags.EstensioniAmmesse));
            }

            return validator;
        }
    }
}
