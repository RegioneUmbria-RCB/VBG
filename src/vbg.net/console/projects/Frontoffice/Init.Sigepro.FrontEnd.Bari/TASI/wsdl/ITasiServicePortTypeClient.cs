using System;
namespace Init.Sigepro.FrontEnd.Bari.TASI.wsdl
{
	// ATTENZIONE, aggiungere l'interfaccia alla classe TasiServicePortTypeClient (sta sotto tasiservice.xsd)

	public interface ITasiServicePortTypeClient : IDisposable
	{
		datiImmobiliContribuenteResponse getDatiImmobili(datiAutorizzazioneType datiAutorizzazione, datiIdentificativiType datiIdentificativi);
		commonResponseType setAgevolazioneCaf(datiAutorizzazioneType datiAutorizzazione, datiTracciamentoCafType datiTracciamento, richiestaAgevolazioneRequestType datiRichiesta);
		void Abort();
	}
}
