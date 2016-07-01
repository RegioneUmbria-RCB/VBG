using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IMessaggiFrontofficeRepository
	{
		System.Collections.Generic.List<Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.FoMessaggi> GetMessaggi(string idComune, string software, string codiceFiscale, int? filtroStato);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.FoMessaggi GetMessaggio(string idComune, int idMessaggio);
		void InviaMessaggio(string idComune, string software, int? idDomanda, string nomeMittente, string codiceFiscaleDestinatario, string oggetto, string corpo);
		//void InviaMessaggioAnnullamento(string nominativoSoggettoTrasferente, DomandaOnline domanda);
		void InviaMessaggioDomandaRicevuta(string idComune, int idDomanda, int codiceIstanza);
		//void InviaMessaggioProntoInvio(string nominativoSoggettoTrasferente, DomandaOnline domanda);
		//void InviaMessaggioSottoscrizione(string nominativoSoggettoTrasferente, DomandaOnline domanda);
		//void InviaMessaggioTrasferimento(string nominativoSoggettoTrasferente, DomandaOnline domanda);
	}
}
