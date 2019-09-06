using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Exceptions;
using Init.SIGePro.DatiDinamici.WebControls;
using System.Text.RegularExpressions;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	internal class CampiDinamiciValidationService
	{
		internal IEnumerable<ICampoDinamicoValidator> GetValidatoriPer(CampoDinamico campo)
		{
			yield return new CampiMultipliObbligatoriValidator(campo);

			if (campo.TipoNumerico)
			{
				yield return new ValoriNumericiValidiValidator(campo);
				yield return new ValoriNumericiMinValueValidator(campo);
				yield return new ValoriNumericiMaxValueValidator(campo);
			}
			else
			{
				yield return new RegexCampiValidator(campo);
			}

			if (campo.TipoCampo == TipoControlloEnum.Checkbox)
				yield return new CheckboxesObbligatorieValidator(campo);
		}

	}
}
