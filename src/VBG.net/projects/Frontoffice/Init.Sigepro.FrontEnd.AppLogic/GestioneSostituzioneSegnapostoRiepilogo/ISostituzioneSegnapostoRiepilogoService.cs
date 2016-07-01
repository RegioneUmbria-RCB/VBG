using System;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	public interface ISostituzioneSegnapostoRiepilogoService
	{
		string ProcessaRiepilogo(DomandaOnline domandaOnline, string templateDaProcessare);
	}
}
