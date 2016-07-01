namespace Init.SIGePro.DatiDinamici
{
	using System;
	using Init.SIGePro.DatiDinamici.Interfaces;

	public interface IModelloDinamicoLoader
	{
		IDyn2DataAccessProvider DataAccessProvider { get; }
		ModelloDinamicoBase.FlagsModello GetFlags();
		ModelloDinamicoBase.ScriptsModello GetScripts();
		ModelloDinamicoBase.StrutturaModello GetStrutturaModello();
		void SetModello(ModelloDinamicoBase modello);
		string Idcomune { get; }
		string Token { get; }
		string NomeModello { get; }
		bool GetModelloFrontoffice();
	}
}
