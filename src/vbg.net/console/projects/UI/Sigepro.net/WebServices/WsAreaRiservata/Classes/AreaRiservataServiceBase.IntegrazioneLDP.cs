using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
	    [WebMethod]
        public ConfigurazioneAlberoprocLDP GetConfigurazioneAlberoprocLDP(string token, int idIntervento)
        {
            var auth = CheckToken(token);

			using (var db = auth.CreateDatabase())
			{
                var svc = new ConfigurazioneAlberoprocLDPService(new AlberoProcMgr(db), auth.IdComune);

                return svc.GetConfigurazione(idIntervento);
			}

			
        }
		
	}
}