using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTasi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneImu;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDenunceTares;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public interface IDomandaOnlineWriteInterface
	{
		IDocumentiWriteInterface Documenti { get; }
		IDatiDinamiciWriteInterface DatiDinamici { get; }
		IRiepiloghiSchedeDinamicheWriteInterface RiepiloghiSchedeDinamiche { get; }
		IEndoprocedimentiWriteInterface Endoprocedimenti { get; }
		IDelegaATrasmettereWriteInterface DelegaATrasmettere { get; }
		IOneriWriteInterface Oneri { get; }
		ILocalizzazioniWriteInterface Localizzazioni { get; }
		IAltriDatiWriteInterface AltriDati { get; }
		IProcureWriteInterface Procure { get; }
		IAnagraficheWriteInterface Anagrafiche { get; }
		IAllegatiWriteInterface Allegati { get; }
		IAutorizzazioniMercatiWriteInterface AutorizzazioniMercati { get; }
		ITaresBariWriteInterface TaresBari { get; }
		ITasiBariWriteInterface TasiBari { get; }
		IImuBariWriteInterface ImuBari { get; }
		IBandiUmbriaWriteInterface BandiUmbria { get; }
		IDatiExtraWriteinterface DatiExtra { get; }
		IDenunceTaresBariWriteInterface DenunceTaresBari { get; }
        IBookmarksWriteInterface Bookmarks { get; }
	}
}
