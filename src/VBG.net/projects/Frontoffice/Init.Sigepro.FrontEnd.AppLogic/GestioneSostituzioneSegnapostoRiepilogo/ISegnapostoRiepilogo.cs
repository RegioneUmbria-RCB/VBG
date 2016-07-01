using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	internal interface ISegnapostoRiepilogo
	{
		string NomeTag { get; }
		string NomeArgomento { get; }

		string Elabora(DomandaOnline domanda, string argomento, string espressione);
	}
}
