using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Ninject.Modules;

namespace Init.Sigepro.FrontEnd.IoC
{
	public class FrontendNinjectModule : NinjectModule
	{
		public override void Load()
		{
			// Condizioni di ingresso
			Bind<CondizioneIngressoGestioneSottoscrittori>().ToSelf();
			Bind<CondizioneIngressoStepSempreVera>().ToSelf();

			// Condizioni di uscita
			Bind<CondizioneUscitaStepSempreVera>().ToSelf();
			Bind<CondizioniUscitaGestioneAnagrafiche>().ToSelf();
			Bind<CondizioneUscitaPrivacyAccettata>().ToSelf();
		}
	}
}