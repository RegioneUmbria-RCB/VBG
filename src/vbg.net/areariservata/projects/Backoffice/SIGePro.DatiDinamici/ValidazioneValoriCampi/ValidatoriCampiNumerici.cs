using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Globalization;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	internal abstract class CampoNumericoValidatorBase : CampoDinamicoValidator
	{
		internal CampoNumericoValidatorBase(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			: base(campo, indiceValore, messaggioErrore)
		{
		}

		internal override IEnumerable<ErroreValidazione> OnGetErroriDiValidazione()
		{
			var oldCultureInfo = Thread.CurrentThread.CurrentCulture;

			try
			{
				var newNumberFormat = new NumberFormatInfo()
				{
					CurrencyGroupSeparator = "",
					CurrencyDecimalSeparator = ",",
					NumberGroupSeparator = "",
					NumberDecimalSeparator = ","
				};

				var newCultureInfo = new CultureInfo("it-IT");
				newCultureInfo.NumberFormat = newNumberFormat;

				Thread.CurrentThread.CurrentCulture = newCultureInfo;

				var valoreDouble = 0.0d;

				var valoreCampo = this._campo.ListaValori[this._indiceValore].Valore;

				if (String.IsNullOrEmpty(valoreCampo))
					return Enumerable.Empty<ErroreValidazione>();

				var numeroValido = Double.TryParse(valoreCampo, out valoreDouble);

				return ValidaValoreNumerico(valoreCampo, valoreDouble, numeroValido);
			}
			finally
			{
				Thread.CurrentThread.CurrentCulture = oldCultureInfo;
			}
		}

		protected abstract IEnumerable<ErroreValidazione> ValidaValoreNumerico(string valoreCampo, double valoreDouble, bool numeroValido);
	}

	internal class ValidNumberValidator : CampoNumericoValidatorBase
	{
		internal ValidNumberValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			:base( campo, indiceValore, messaggioErrore)
		{
		}

		protected override IEnumerable<ErroreValidazione> ValidaValoreNumerico(string valoreCampo, double valoreDouble, bool numeroValido)
		{
			if (!numeroValido)
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta), this._campo.Id, this._indiceValore);
		}
	}

	internal class MinValueValidator : CampoNumericoValidatorBase
	{
		internal MinValueValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			:base( campo, indiceValore, messaggioErrore)
		{
		}
		
		
		protected override IEnumerable<ErroreValidazione> ValidaValoreNumerico(string valoreCampo, double valoreDouble, bool numeroValido)
		{
			if (!numeroValido)
				yield break;

			var minValue = this._campo.ValidationMinValue;

			if (valoreDouble < minValue.GetValueOrDefault(double.MinValue))
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta, minValue), this._campo.Id, this._indiceValore);
		}
	}

	internal class MaxValueValidator : CampoNumericoValidatorBase
	{
		internal MaxValueValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			: base(campo, indiceValore, messaggioErrore)
		{
		}

		protected override IEnumerable<ErroreValidazione> ValidaValoreNumerico(string valoreCampo, double valoreDouble, bool numeroValido)
		{
			if (!numeroValido)
				yield break;

			var maxValue = this._campo.ValidationMaxValue;

			if (valoreDouble > maxValue.GetValueOrDefault(double.MaxValue))
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta, maxValue), this._campo.Id, this._indiceValore);
		}
	}
	
}
