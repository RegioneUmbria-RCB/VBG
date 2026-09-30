using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels
{
	public class FirmaDigitaleAllegatoMovimentoViewModel
	{
		IAliasResolver _aliasResolver;
		EventsBus _eventBus;

		public FirmaDigitaleAllegatoMovimentoViewModel(IAliasResolver aliasResolver,EventsBus eventBus)
		{
			this._aliasResolver = aliasResolver;
			this._eventBus = eventBus;
		}

		public void FirmaRiepilogoSchedaCompletata(int idMovimento, int codiceOggetto, string fileName)
		{
			var cmd = new FirmaDigitalmenteRiepilogo(this._aliasResolver.AliasComune, idMovimento, codiceOggetto, fileName);

			this._eventBus.Send(cmd);
		}

		public void FirmaAllegatoCompletata(int idMovimento, int codiceOggetto, string fileName)
		{
			var cmd = new FirmaDigitalmenteAllegatoMovimento(this._aliasResolver.AliasComune, idMovimento, codiceOggetto, fileName);

			this._eventBus.Send(cmd);
		}
	}
}
