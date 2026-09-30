// -----------------------------------------------------------------------
// <copyright file="CampoDinamicoValidator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	internal abstract class CampoDinamicoValidator : ICampoDinamicoValidator
	{
		protected readonly CampoDinamicoBase _campo;
		protected readonly string _messaggioErrore;
		protected readonly int _indiceValore;

		public CampoDinamicoValidator(CampoDinamicoBase campo, int indiceValore, string messaggioErrore)
		{
			this._campo = campo;
			this._messaggioErrore = messaggioErrore;
			this._indiceValore = indiceValore;
		}

		public IEnumerable<ErroreValidazione> GetErroriDiValidazione()
		{
			if (!this._campo.ListaValori[this._indiceValore].Visibile)
				return Enumerable.Empty<ErroreValidazione>();

			var res = OnGetErroriDiValidazione();

			return res ?? Enumerable.Empty<ErroreValidazione>();
		}

		internal abstract IEnumerable<ErroreValidazione> OnGetErroriDiValidazione();
	}
}
