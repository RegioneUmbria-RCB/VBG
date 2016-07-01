using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls;

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	public class CampoDinamicoStub : CampoDinamicoBase
	{
		

		public CampoDinamicoStub()
			: this(new ModelloDinamicoStub())
		{
		}
		
		public CampoDinamicoStub(ModelloDinamicoStub modello)
			: base(modello)
		{
			this.ModelloCorrente = modello;
		}
		
		public void SetId(int value)
		{
			this.Id = value;
		}

		public void SetTipoCampo(TipoControlloEnum value)
		{
			this.TipoCampo = value;
		}

		public void SetObbligatorio(bool value)
		{
			this.Obbligatorio = value;
		}

		public void SetIgnoraObbligatorietaSuAttivita(bool value)
		{
			this.IgnoraObbligatorietaSuAttivita = value;
		}

		public void SetEtichetta(string value)
		{
			this.Etichetta = value;
		}

		public void SetRegex(string value)
		{
			this.EspressioneRegolare = value;
		}

		public void SetValoreValidazioneMin(double value)
		{
			this.ValidationMinValue = value;
		}

		public void SetValoreValidazioneMax(double value)
		{
			this.ValidationMaxValue = value;
		}

		public void AddProprietaControllo(string key, string val)
		{
			this.ProprietaControlloWeb.Add( new KeyValuePair<string,string>(key, val));
		}
	}

}
