namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;

	public interface IOggettiRepository
	{
		void AggiornaOggetto(int codiceOggetto, byte[] data);
		void EliminaOggetto(int codiceOggetto);
		string GetNomeFile(int codiceOggetto);
		BinaryFile GetOggetto(string aliasComune, int codiceOggetto);
		BinaryFile GetRisorsaFrontoffice(string aliasComune, string idRisorsa);
		int InserisciOggetto(string nomeFile, string mimeType, byte[] data);
	}
}
