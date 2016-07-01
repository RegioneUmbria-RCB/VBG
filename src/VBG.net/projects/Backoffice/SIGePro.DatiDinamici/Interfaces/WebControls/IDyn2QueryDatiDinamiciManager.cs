using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;

namespace Init.SIGePro.DatiDinamici.Interfaces.WebControls
{
	public interface IDyn2QueryDatiDinamiciManager
	{
		DataSet EseguiQuery(string idComune,string campiSelect, string tabelleSelect, string condizioneJoin, string condizioniWhere, string nomeCampoTesto, string nomeCampoValore);
	}
}
