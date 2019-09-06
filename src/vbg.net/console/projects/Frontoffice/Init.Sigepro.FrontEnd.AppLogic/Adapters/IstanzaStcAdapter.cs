using System;
using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
	public interface IIstanzaStcAdapter
	{
		DettaglioPraticaType Adatta(DomandaOnline domandaFo);
	}


	internal class IstanzaStcAdapter : IIstanzaStcAdapter
	{
		ITipiSoggettoService _tipiSoggettoService;
		ICodiceAccreditamentoHelper _codiceAccreditamentoHelper;
		IStrutturaModelloReader _strutturaModelloReader;
        IConfigurazione<ParametriStc> _parametriStc;
        private readonly IFormeGiuridicheRepository _formeGiuridicheRepository;
        IAliasResolver _aliasResolver;

        public IstanzaStcAdapter(IAliasResolver aliasResolver, ITipiSoggettoService tipiSoggettoRepository, ICodiceAccreditamentoHelper codiceAccreditamentoHelper, IStrutturaModelloReader strutturaModelloReader, IConfigurazione<ParametriStc> parametriStc, IFormeGiuridicheRepository formeGiuridicheRepository)
		{
            this._aliasResolver = aliasResolver;
			this._tipiSoggettoService = tipiSoggettoRepository;
			this._codiceAccreditamentoHelper = codiceAccreditamentoHelper;
			this._strutturaModelloReader = strutturaModelloReader;
            this._parametriStc = parametriStc;
            this._formeGiuridicheRepository = formeGiuridicheRepository;
        }


		public DettaglioPraticaType Adatta(DomandaOnline domandaFo)
		{
			var readInterface   = domandaFo.ReadInterface;
			var dettaglioPratica = new DettaglioPraticaType();
			

			var adapters = new IStcPartialAdapter[]
			{
				new DatiPraticaAdapter(),
				new ComuniAssociatiAdapter(),
				new RichiedenteAdapter(),
				new AziendaAdapter(this._tipiSoggettoService, this._formeGiuridicheRepository),
				new TecnicoAdapter(this._formeGiuridicheRepository),
				new AltriSoggettiAdapter(this._parametriStc,this._formeGiuridicheRepository),
				new ProcureAdapter(),
				new LocalizzazioneAdapter(),
				new DocumentiAdapter(this._aliasResolver),
				new AltriDatiAdapter(this._codiceAccreditamentoHelper),
				new ProcedimentiAdapter(this._aliasResolver),
				new OneriAdapter(),
				new DatiTaresAdapter(),
				new DatiDinamiciAdapter(this._strutturaModelloReader)
			};

			foreach(var adapter in adapters)
				adapter.Adapt(readInterface, dettaglioPratica);			

			return dettaglioPratica;
		}


	}
}
