using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
	public interface ITokenApplicazioneService
	{
		string GetToken(string aliasComune);
	}
}
