using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public interface IDomandaOnlineReadInterface
	{
		IOneriReadInterface Oneri { get; }
		IAnagraficheReadInterface Anagrafiche { get; }
		IDelegaATrasmettereReadInterface DelegaATrasmettere { get; }
		IAltriDatiReadInterface AltriDati { get; }
		IDocumentiReadInterface Documenti { get;}
		IEndoprocedimentiReadInterface Endoprocedimenti { get; }
		IRiepiloghiSchedeDinamicheReadInterface RiepiloghiSchedeDinamiche { get; }
		IProcureReadInterface Procure { get; }
		ILocalizzazioniReadInterface Localizzazioni { get; }
		IDatiDinamiciReadInterface DatiDinamici { get; }
		IAutorizzazioniMercatiReadInterface AutorizzazioniMercati { get; }
		IBandiUmbriaReadInterface BandiUmbria { get; }
		IDatiExtraReadInterface DatiExtra { get; }


		void Invalidate();

		bool UtentePuoAccedere(string codiceUtente);
		bool IsPresentata();
	}
}
