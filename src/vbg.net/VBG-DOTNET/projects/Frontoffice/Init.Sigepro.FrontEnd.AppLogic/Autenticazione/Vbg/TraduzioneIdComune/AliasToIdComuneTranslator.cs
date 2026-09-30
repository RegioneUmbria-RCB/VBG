using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune
{
	public class AliasToIdComuneTranslator : IAliasToIdComuneTranslator
	{
		AliasToIdComuneRepository _repository;

		public AliasToIdComuneTranslator(AliasToIdComuneRepository repository)
		{
			this._repository = repository;
		}

		#region IAliasToIdComuneTranslator Members

		public string Translate(string aliasComune)
		{
			return _repository.GetIdComuneDaAliasComune(aliasComune);
		}

		#endregion
	}
}
