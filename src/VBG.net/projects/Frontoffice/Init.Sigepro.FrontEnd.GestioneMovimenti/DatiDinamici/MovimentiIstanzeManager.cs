using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class MovimentiIstanzeManager: IIstanzeManager
	{
		IDettaglioPraticaRepository _visuraRepository;

		public MovimentiIstanzeManager(IDettaglioPraticaRepository visuraRepository)
		{
			this._visuraRepository = visuraRepository;
		}

		public SIGePro.DatiDinamici.Interfaces.IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
		{
			return this._visuraRepository.GetById(idComune, codiceIstanza, false);
		}
	}
}
