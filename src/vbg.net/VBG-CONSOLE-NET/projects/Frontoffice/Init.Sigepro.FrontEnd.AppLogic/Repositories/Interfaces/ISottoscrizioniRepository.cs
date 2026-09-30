using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface ISottoscrizioniRepository
	{
		void SottoscriviDomanda(string aliasComune, int idDomanda,  string codicefiscale);
		bool VerificaSottoscrizioneDomanda(string aliasComune, int idPresentazione, string codiceFiscale);
		List<FoSottoscrizioni> GetListaSottoscriventi(string aliasComune, int idPresentazione);
		List<FoSottoscrizioni> GetSottoscrizioniUtente(string aliasComune, int idPresentazione, string codiceFiscaleUtente);
	}
}
