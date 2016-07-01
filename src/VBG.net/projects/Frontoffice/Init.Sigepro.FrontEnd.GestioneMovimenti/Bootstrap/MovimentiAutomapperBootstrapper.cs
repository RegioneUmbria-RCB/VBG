// -----------------------------------------------------------------------
// <copyright file="MovimentiAutomapperBootstrapper.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap
{
	using AutoMapper;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Converters;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;
	using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
	using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

	/// <summary>
	/// Inizializzatore del componente automapper relativo al componente di gesione movimenti del frontoffice
	/// </summary>
	public class MovimentiAutomapperBootstrapper
	{
		/// <summary>
		/// Inizializza l'automapper
		/// </summary>
		public static void Bootstrap()
		{
			Mapper.CreateMap<DatiMovimentoDaEffettuareDto, DatiMovimentoDiOrigine>().ConvertUsing(new DatiMovimentoToDatiMovimentoDiOrigineConverter());
			Mapper.CreateMap<DatiMovimentoDaEffettuare, NotificaAttivitaRequest>().ConvertUsing(new DatiMovimentoDaEffettuareToNotificaAttivitaRequestConverter());
			Mapper.CreateMap<IstanzeStradario, LocalizzazioneIstanza>().ConvertUsing(new IstanzeStradarioToLocalizzazioneIstanzaConverter());

			Mapper.AssertConfigurationIsValid();
		}
	}
}
