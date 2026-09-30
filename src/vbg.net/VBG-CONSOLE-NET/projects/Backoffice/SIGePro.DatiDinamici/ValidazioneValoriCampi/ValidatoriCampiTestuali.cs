using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{

	internal class CampoObbligatorioValidator : CampoDinamicoValidator
	{


		internal CampoObbligatorioValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			:base(campo, indiceValore, messaggioErrore)
		{

		}

		internal override IEnumerable<ErroreValidazione> OnGetErroriDiValidazione()
		{
			if (!this._campo.Obbligatorio)
				yield break;

			if (this._campo.IgnoraObbligatorietaSuAttivita && this._campo.ModelloCorrente.Contesto.TipoContesto == ContestoModelloEnum.Attivita)
				yield break;

			if (String.IsNullOrEmpty(this._campo.ListaValori[this._indiceValore].Valore))
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta), this._campo.Id, this._indiceValore);
		}
	}

	internal class RegexCampoValidator : CampoDinamicoValidator
	{
		internal RegexCampoValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			: base(campo, indiceValore, messaggioErrore)
		{
		}

		internal override IEnumerable<ErroreValidazione> OnGetErroriDiValidazione()
		{
			var valore = this._campo.ListaValori[this._indiceValore].Valore;
			var regEx = this._campo.EspressioneRegolare;

			if(String.IsNullOrEmpty(valore) || String.IsNullOrEmpty(regEx))
				yield break;

			if (!Regex.IsMatch(valore, regEx))
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta, regEx), this._campo.Id, this._indiceValore);
		}
	}

	internal class CheckboxValidator : CampoDinamicoValidator
	{
		internal CheckboxValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
			: base(campo, indiceValore, messaggioErrore)
		{
		}

		internal override IEnumerable<ErroreValidazione> OnGetErroriDiValidazione()
		{
			var valoreCampo = this._campo.ListaValori[this._indiceValore].Valore;

			if (this._campo.TipoCampo != TipoControlloEnum.Checkbox)
				yield break;

			if (!this._campo.Obbligatorio)
				yield break;

            if (this._campo.IgnoraObbligatorietaSuAttivita && this._campo.ModelloCorrente.Contesto.TipoContesto == ContestoModelloEnum.Attivita)
                yield break;

			var propVal = this._campo.ProprietaControlloWeb.FirstOrDefault(x => x.Key.ToUpper() == "VALOREFALSE");

			if (String.IsNullOrEmpty(propVal.Key))
				yield break;
			
			if (propVal.Value == valoreCampo)
				yield return new ErroreValidazione(String.Format(this._messaggioErrore, this._campo.Etichetta), this._campo.Id, this._indiceValore);
		}
	}
	
}
