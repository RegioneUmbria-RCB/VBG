
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
	public partial class IAttivitaDyn2DatiStoricoMgr : IIAttivitaDyn2DatiStoricoManager
    {

		#region IIAttivitaDyn2DatiStoricoManager Members

		public List<IIAttivitaDyn2DatiStorico> GetValoriCampo(string idComune, int codiceAttivita, int codiceCampo, int indiceModello, int idVersioneStorico)
		{
			var filtro = new IAttivitaDyn2DatiStorico
			{
				Idcomune = idComune,
				FkIaId = codiceAttivita,
				FkD2cId = codiceCampo,
				Indice = indiceModello,
				Idversione = idVersioneStorico,
				OrderBy = "indice_molteplicita asc"
			};

			var list = GetList(filtro);

			var rVal = new List<IIAttivitaDyn2DatiStorico>(list.Count);

			list.ForEach(x => rVal.Add(x));

			return rVal;
		}

		#endregion
	}
}
				