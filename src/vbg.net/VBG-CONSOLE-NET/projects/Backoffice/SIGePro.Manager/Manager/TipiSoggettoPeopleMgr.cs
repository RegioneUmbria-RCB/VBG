using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;

namespace Init.SIGePro.Manager
{
	[DataObject(true)]
	public partial class TipiSoggettoPeopleMgr
	{
		[DataObjectMethod( DataObjectMethodType.Select )]
		public static List<TipiSoggettoPeople> Find(string token, string software, string codiceTsPeople, int idTipoSoggetto)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			TipiSoggettoPeopleMgr mgr = new TipiSoggettoPeopleMgr( authInfo.CreateDatabase() );

			TipiSoggettoPeople filtro = new TipiSoggettoPeople();
			filtro.Idcomune			= authInfo.IdComune;
			filtro.Software = software;
			filtro.Tiporapprpeople  = String.IsNullOrEmpty( codiceTsPeople ) ? null : codiceTsPeople;
            if (idTipoSoggetto < 0)
            {
                filtro.Codicetiposoggetto = null;
            }
            else
            {
                filtro.Codicetiposoggetto = idTipoSoggetto;
            }
            

			filtro.OrderBy = "Tiporapprpeople asc";

			return mgr.GetList(filtro);
		}
	}
}
