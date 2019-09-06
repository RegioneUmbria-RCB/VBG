
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

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class AllegatiMgr
    {
		public Allegati GetById(string idcomune, int id)
		{
			Allegati c = new Allegati();


			c.Idcomune = idcomune;
			c.Id = id;

			return (Allegati)db.GetClass(c);
		}

		public List<Allegati> GetByCodiceInventario(string idComune, int codiceInventario,AmbitoRicerca ambitoRicercaDocumenti)
		{
			var filtro = new Allegati
			{
				Idcomune = idComune,
				Codiceinventario = codiceInventario,
				OrderBy = "ORDINE ASC, ALLEGATO ASC"
				
			};

			filtro.OthersWhereClause.Add("pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + ")");

			return GetList(filtro);
		}
	}
}
				