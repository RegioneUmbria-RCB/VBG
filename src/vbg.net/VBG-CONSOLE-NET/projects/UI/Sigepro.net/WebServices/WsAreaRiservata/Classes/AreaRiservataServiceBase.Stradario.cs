using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Init.SIGePro.Manager;
using log4net;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		ILog _log = LogManager.GetLogger(typeof(AreaRiservataServiceBase));

		[WebMethod]
		public Stradario GetByCodiceStradario(string token, int codiceStradario)
		{
			return new StradarioService().GetByCodiceStradario(token, codiceStradario);
		}

		[WebMethod]
		public Stradario GetByIndirizzo(string token, string codiceComune, string indirizzo)
		{
			return new StradarioService().GetByIndirizzo(token, codiceComune, indirizzo);
		}

		[WebMethod]
		public List<StradarioDto> GetByMatchParziale(string token, string codiceComune, string comuneLocalizzazione, string indirizzo)
		{
			return new StradarioService().GetByMatchParziale(token, codiceComune, comuneLocalizzazione, indirizzo);
		}

		[WebMethod]
		public List<StradarioColore> GetListaColori(string token)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				return new StradarioColoreMgr(db).GetList(new StradarioColore { IDCOMUNE = authInfo.IdComune })
												 .OrderBy(x => x.COLORE)
												 .ToList();
			}
		}

		[WebMethod]
		public StradarioDto GetStradarioByCodViario(string token, string codViario)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var stradario = new StradarioMgr(db).GetByCodViario(authInfo.IdComune, codViario);

				if (stradario == null)
				{
					return null;
				}

				return new StradarioDto(stradario);
			}
		}

		[WebMethod]
		public ComuniMgr.DatiComuneCompatto[] GetComuniLocalizzazioni(string token, string codiceComune)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					return new StradarioMgr(db).GetComuniLocalizzazioni(authInfo.IdComune, codiceComune);
				}
				catch(Exception ex)
				{
					this._log.ErrorFormat("Errore durante la lettura della lista di comuni delle localizzazioni per il codce comune {0}: {1}", codiceComune, ex.ToString());
				}

				return new ComuniMgr.DatiComuneCompatto[0];
			}
		}
	}
}
