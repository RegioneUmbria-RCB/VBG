using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
	public partial class OConfigurazioneTipiOnereMgr
	{
		public int? GetCausaleDaTipoOnereBase(string idComune, string idTipoOnereBase, string software)
		{
			OConfigurazioneTipiOnere filtro = new OConfigurazioneTipiOnere();
			filtro.Idcomune= idComune;
			filtro.FkBtoId = idTipoOnereBase;
			filtro.Software = software;

			OConfigurazioneTipiOnere cls = (OConfigurazioneTipiOnere)db.GetClass(filtro);

			if (cls == null) return null;

			return cls.FkCoId;
		}
	}
}
