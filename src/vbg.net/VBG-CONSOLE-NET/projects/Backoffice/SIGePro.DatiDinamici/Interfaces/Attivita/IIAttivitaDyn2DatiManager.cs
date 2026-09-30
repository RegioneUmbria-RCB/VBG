using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Attivita
{
	public interface IIAttivitaDyn2DatiManager
	{
		List<IIAttivitaDyn2Dati> GetValoriCampo(string idComune, int codiceAttivita, int codicecampo, int idModello);
		void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoAttivita modello,IEnumerable<CampoDinamico> campiDaSalvare);
		void EliminaValoriCampi(ModelloDinamicoAttivita modello, IEnumerable<CampoDinamico> campiDaEliminare);
	}
}
