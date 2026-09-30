using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class TecnicoAdapter : IStcPartialAdapter
	{
        private readonly IFormeGiuridicheRepository _formeGiuridicheRepository;
        AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

        public TecnicoAdapter(IFormeGiuridicheRepository formeGiuridicheRepository)
        {
            _formeGiuridicheRepository = formeGiuridicheRepository;
        }

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var tecnico = _readInterface.Anagrafiche.GetTecnico();

			if (tecnico != null)
			{
				_dettaglioPratica.intermediario = _anagraficheHelper.AdattaAnagrafica(tecnico, this._formeGiuridicheRepository);
			}
		}
	}
}
