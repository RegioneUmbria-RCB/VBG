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

		public IstanzaStcAdapter(IAliasResolver aliasSoftwareResolver, ITipiSoggettoService tipiSoggettoRepository, ICodiceAccreditamentoHelper codiceAccreditamentoHelper, IStrutturaModelloReader strutturaModelloReader)
		{
			this._tipiSoggettoService = tipiSoggettoRepository;
			this._codiceAccreditamentoHelper = codiceAccreditamentoHelper;
			this._strutturaModelloReader = strutturaModelloReader;
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
				new AziendaAdapter(this._tipiSoggettoService),
				new TecnicoAdapter(),
				new AltriSoggettiAdapter(),
				new ProcureAdapter(),
				new LocalizzazioneAdapter(),
				new DocumentiAdapter(),
				new AltriDatiAdapter(this._codiceAccreditamentoHelper),
				new ProcedimentiAdapter(),
				new OneriAdapter(),
				new DatiDinamiciAdapter(this._strutturaModelloReader)
			};

			foreach(var adapter in adapters)
				adapter.Adapt(readInterface, dettaglioPratica);			

			return dettaglioPratica;
		}


	}
}
