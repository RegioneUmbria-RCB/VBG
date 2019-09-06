using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class Ra012Result: Ravenna2Result
	{
		public Ra012Result(IDataReader dr)
			:base(true)
		{
			this.CodVia = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoCodiceVia);
			this.Frazione = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA147.CampoDescrizioneFrazione);
			this.CAP = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoCAP);
			this.Civico = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoCivico);
			this.CodCivico = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CodCivico);
			this.Esponente = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoEsponente);
			this.Sezione = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoSezione);
			this.Foglio = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoFoglio);
			this.Particella = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoParticella);
			this.Fabbricato = GetVal(dr, Ravenna2DbClient.Constants.TabellaRA012.CampoEdificio);
		}
	}
}
