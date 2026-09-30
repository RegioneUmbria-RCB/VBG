using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune
{
	public interface IAliasToIdComuneTranslator
	{
		string Translate(string aliasComune);
	}
}
