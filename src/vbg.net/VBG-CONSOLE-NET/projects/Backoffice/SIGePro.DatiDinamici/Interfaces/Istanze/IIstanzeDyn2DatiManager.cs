using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.SIGePro.DatiDinamici.Interfaces.Istanze
{
	public interface IIstanzeDyn2DatiManager
	{
		SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo);
		void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaSalvare);
		void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaEliminare);
	}
}
