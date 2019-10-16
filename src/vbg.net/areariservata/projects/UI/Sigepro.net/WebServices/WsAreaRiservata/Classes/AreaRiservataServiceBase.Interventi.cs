﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione;
using Init.SIGePro.Manager;


namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
        public AttivazioneInterventoService.RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(string token, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento)
		{
			var ai = CheckToken(token);

			using(var db = ai.CreateDatabase())
			{
				var mgr = new AlberoProcMgr(db);
				var attivazioneInterventoService = new AttivazioneInterventoService(mgr, ai.IdComune);

				return attivazioneInterventoService.VerificaAccessoIntervento(AttivazioneInterventoService.TipoPubblicazione.AreaRiservata, livelloAutenticazione, codiceIntervento);
			}
		}

        [WebMethod]
        public int GetLivelloDiAccessoIntervento(string token, int codiceIntervento)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var mgr = new AlberoProcMgr(db);
                var attivazioneInterventoService = new AttivazioneInterventoService(mgr, ai.IdComune);

                return attivazioneInterventoService.GetLivelloDiAutenticazioneRichiesto(codiceIntervento);
            }
        }
	}
}