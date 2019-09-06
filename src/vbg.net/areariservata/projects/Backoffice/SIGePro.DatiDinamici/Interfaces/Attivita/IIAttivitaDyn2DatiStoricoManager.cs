using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Attivita
{
	public interface IIAttivitaDyn2DatiStoricoManager
	{
		List<IIAttivitaDyn2DatiStorico> GetValoriCampo(string idComune, int codiceAttivita, int codiceCampo, int indiceModello, int idVersioneStorico);
	}
}
