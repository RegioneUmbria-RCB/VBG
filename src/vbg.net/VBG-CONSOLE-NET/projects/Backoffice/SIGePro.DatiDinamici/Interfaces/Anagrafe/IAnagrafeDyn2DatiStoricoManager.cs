using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Anagrafe
{
	public interface IAnagrafeDyn2DatiStoricoManager
	{
		List<IAnagrafeDyn2DatiStorico> GetValoriCampo(string idComune, int codiceAnagrafe, int codiceCampo, int indiceModello, int idVersioneStorico);
	}
}
