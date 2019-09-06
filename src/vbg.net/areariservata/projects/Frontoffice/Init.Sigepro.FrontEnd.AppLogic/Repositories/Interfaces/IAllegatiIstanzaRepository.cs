using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IAllegatiIstanzaRepository
	{
		//void AggiungiAllegatoIstanza(string idComune, int codiceIstanza, string descrizioneFile, Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.BinaryFile file);
		void AggiungiAllegatoIstanza(int codiceIstanza, string descrizioneFile, int codiceOggetto);
	}
}
