using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
	public abstract class CondizioneUscitaStepBase
	{
		DomandeOnlineService _domandeService;
		IIdDomandaResolver _idDomandaResolver;

		public CondizioneUscitaStepBase(IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService)
		{
			_domandeService = domandeService;
			_idDomandaResolver = idDomandaResolver;
		}

		protected DomandaOnline Domanda
		{
			get
			{
				return _domandeService.GetById(_idDomandaResolver.IdDomanda);
			}
		}
	}
}