using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Istanze
{
	public interface IIstanzeDyn2DatiStoricoManager
	{
		List<IIstanzeDyn2DatiStorico> GetValoriCampo(string idComune, int codiceIstanza, int codiceCampo, int indiceModello, int idVersioneStorico);
	}
}
