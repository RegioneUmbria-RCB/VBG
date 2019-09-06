// -----------------------------------------------------------------------
// <copyright file="BariComuniService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariComuniService : IResolveComuneDaCodiceIstat
    {
        IComuniRepository _comuniRepository;
        IAliasResolver _aliasResolver;
		ILog _log = LogManager.GetLogger(typeof(BariComuniService));


		public BariComuniService(IComuniRepository comuniRepository, IAliasResolver aliasResolver)
        {
            this._comuniRepository = comuniRepository;
            this._aliasResolver = aliasResolver;
        }

        public string GetComuneDaCodiceIstat(string codiceIstat)
        {
            // codiceIstat = codiceIstat.PadLeft(6, '0');

            var result = this._comuniRepository.GetComuneDaCodiceIstat(this._aliasResolver.AliasComune, codiceIstat);

            if (result == null)
            {
				_log.ErrorFormat("Impossibile risolvere il comune identificato dal codice istat {0}", codiceIstat);

                return "Comune " + codiceIstat + " non trovato";
            }

            return result.Comune;
        }
    }
}
