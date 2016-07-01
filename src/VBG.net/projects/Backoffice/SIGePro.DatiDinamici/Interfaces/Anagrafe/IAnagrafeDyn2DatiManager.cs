using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Anagrafe
{
	public interface IAnagrafeDyn2DatiManager
	{
		List<IAnagrafeDyn2Dati> GetValoriCampo(string idComune, int codiceAnagrafe, int codicecampo, int idModello);
		void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoAnagrafica modello ,  IEnumerable<CampoDinamico> campiDaSalvare);
		void EliminaValoriCampi(ModelloDinamicoAnagrafica modello, IEnumerable<CampoDinamico> campiDaEliminare);
	}
}
