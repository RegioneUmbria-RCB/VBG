// -----------------------------------------------------------------------
// <copyright file="ModelloDinamicoStub.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.DatiDinamici;
	using Init.SIGePro.DatiDinamici.Contesti;

	public class LoaderStub : IModelloDinamicoLoader
	{
		bool _modelloFrontoffice;
		ModelloDinamicoBase _modello;

		public void SetModelloFrontoffice(bool value)
		{
			this._modelloFrontoffice = value;
		}

		public Init.SIGePro.DatiDinamici.Interfaces.IDyn2DataAccessProvider DataAccessProvider
		{
			get { throw new NotImplementedException(); }
		}

		public ModelloDinamicoBase.FlagsModello GetFlags()
		{
			throw new NotImplementedException();
		}

		public ModelloDinamicoBase.ScriptsModello GetScripts()
		{
			throw new NotImplementedException();
		}

		public ModelloDinamicoBase.StrutturaModello GetStrutturaModello()
		{
			throw new NotImplementedException();
		}

		public void SetModello(ModelloDinamicoBase modello)
		{
			_modello = modello;
		}

		public string Idcomune
		{
			get { return "idComune"; }
		}

		public string Token
		{
			get { return "token"; }
		}

		public string NomeModello
		{
			get { return "nome modello"; }
		}


		public bool GetModelloFrontoffice()
		{
			return this._modelloFrontoffice;
		}
	}



	public class ModelloDinamicoStub : ModelloDinamicoBase
	{
		public ModelloDinamicoStub()
			:base(0, 0, false, null, new LoaderStub())
		{

		}

		public void ImpostaContesto(ContestoModelloEnum tipoContesto)
		{
			this.Contesto = new ContestoModelloDinamico("token", tipoContesto, null);
		}
		
		protected override void SetContesto()
		{
			this.Contesto = new ContestoModelloDinamico("token", ContestoModelloEnum.Istanza, null);
		}

		protected override void PopolaValoriCampi()
		{
			throw new NotImplementedException();
		}

		protected override void PopolaValoriCampiStorico()
		{
			throw new NotImplementedException();
		}

		protected override void SalvaValoriCampi(bool salvaStorico, IEnumerable<CampoDinamico> campiDaSalvare)
		{
			throw new NotImplementedException();
		}

		protected override void EliminaValoriCampi(IEnumerable<CampoDinamico> campiDaEliminare)
		{
			throw new NotImplementedException();
		}
	}
}
