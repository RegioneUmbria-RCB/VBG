using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{

		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> GetListaComuni(string token, string siglaProvincia)
		{
			return new ComuniService().GetListaComuni(token, siglaProvincia);
		}

		[WebMethod]
		public ComuniMgr.DatiComuneCompatto GetDatiComune(string token, string codiceComune)
		{
			return new ComuniService().GetDatiComune(token, codiceComune);
		}

		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> FindComuniDaMatchParziale(string token, string matchParziale)
		{
			return new ComuniService().FindComuniDaMatchParziale(token, matchParziale);
		}
		

		[WebMethod]
		public ComuniMgr.DatiProvinciaCompatto GetDatiProvincia(string token, string siglaProvincia)
		{
			return new ComuniService().GetDatiProvincia(token, siglaProvincia);
		}

		[WebMethod]
		public ComuniMgr.DatiProvinciaCompatto GetDatiProvinciaDaCodiceComune(string token, string codiceComune)
		{
			return new ComuniService().GetDatiProvinciaDaCodiceComune(token, codiceComune);
		}

		[WebMethod]
		public List<ComuniMgr.DatiProvinciaCompatto> GetListaProvincie(string token)
		{
			return new ComuniService().GetListaProvincie(token);
		}

		[WebMethod]
		public List<ComuniMgr.DatiComuneCompatto> GetComuniAssociati(string token)
		{
			return new ComuniService().GetComuniAssociati(token);
		}

		[WebMethod]
		public string GetPecComuneAssociato(string token, string codiceComuneAssociato, string software)
		{
			return new ComuniService().GetPecComuneAssociato(token, codiceComuneAssociato, software);
		}


		[WebMethod]
		public List<Cittadinanza> GetCittadinanze(string token)
		{
			return new ComuniService().GetCittadinanze(token);
		}

        [WebMethod]
        public ComuniMgr.DatiComuneCompatto GetComuneDaCodiceIstat(string token, string codiceIstat)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var comune = new ComuniMgr(db).GetByCodiceIstat(codiceIstat);

                if (comune == null)
                    return null;

                return new ComuniMgr.DatiComuneCompatto
                {
                    Cf = comune.CF,
                    CodiceComune = comune.CODICECOMUNE,
                    Comune = comune.COMUNE,
                    Provincia = comune.PROVINCIA,
                    SiglaProvincia = comune.SIGLAPROVINCIA
                };
            }
        }
	}
}
