using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	internal class ValoreCampo
	{
		CampoDinamicoBase _campoScheda;
		IMascheraSolaLettura _campiInSolaLettura;
		bool _solaLettura;

		public ValoreCampo(CampoDinamicoBase campoScheda, bool solaLettura)
		{
			this._campoScheda = campoScheda;
			this._solaLettura = solaLettura;
		}


		private string EstraiValore(int indicemolteplicita)
		{
			if (_campoScheda.TipoCampo == TipoControlloEnum.Label || _campoScheda.TipoCampo == TipoControlloEnum.Titolo)
				return _campoScheda.ListaValori[0].Valore;

			var valore = _campoScheda.ListaValori[indicemolteplicita].Valore;
			var valoreDecodificato = _campoScheda.ListaValori[indicemolteplicita].ValoreDecodificato;

			if (this._solaLettura && !String.IsNullOrEmpty(valoreDecodificato) && _campoScheda.TipoCampo != TipoControlloEnum.Checkbox)
				return valoreDecodificato;

			return valore;
		}

		internal string AllIndice(int indiceMolteplicita)
		{
			return EstraiValore(indiceMolteplicita);
		}
	}
}
