using Sigepro.net.WebServices.WsSIGePro;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for CampiRicercaVisura
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class CampiRicercaVisuraService : SigeproWebService
    {

        //[WebMethod]
        //public List<CampoVisuraFrontofficeDto> GetFiltriVisuraFrontoffice(string token, string software)
        //{
        //    var authResult = this.CheckToken(token);

        //    using (var db = authResult.CreateDatabase())
        //    {
        //        var l = new FoConfigurazioneMgr(db).GetFiltriVisuraFrontoffice(authResult.IdComune, software);

        //        return l?.Select(x => new CampoVisuraFrontofficeDto
        //        {
        //            Codice = x.Codice,
        //            Etichetta = x.Etichetta,
        //            IdRisorsa = x.IdRisorsa,
        //            Valore = x.Valore
        //        })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
        //    }
        //}


        //[WebMethod]
        //public List<CampoVisuraFrontofficeDto> GetCampiTabellaVisura(string token, string software)
        //{
        //    var authResult = this.CheckToken(token);

        //    using (var db = authResult.CreateDatabase())
        //    {
        //        var l = new FoConfigurazioneMgr(db).GetCampiTabellaVisura(authResult.IdComune, software);

        //        return l?.Select(x => new CampoVisuraFrontofficeDto
        //        {
        //            Codice = x.Codice,
        //            Etichetta = x.Etichetta,
        //            IdRisorsa = x.IdRisorsa,
        //            Valore = x.Valore
        //        })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
        //    }
        //}

        //[WebMethod]
        //public List<CampoVisuraFrontofficeDto> GetFiltriArchivioIstanzeFrontoffice(string token, string software)
        //{
        //    var authResult = this.CheckToken(token);

        //    using (var db = authResult.CreateDatabase())
        //    {
        //        var l = new FoConfigurazioneMgr(db).GetFiltriArchivioIstanzeFrontoffice(authResult.IdComune, software);

        //        return l?.Select(x => new CampoVisuraFrontofficeDto
        //        {
        //            Codice = x.Codice,
        //            Etichetta = x.Etichetta,
        //            IdRisorsa = x.IdRisorsa,
        //            Valore = x.Valore
        //        })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
        //    }
        //}

        //[WebMethod]
        //public List<CampoVisuraFrontofficeDto> GetCampiTabellaArchivioIstanze(string token, string software)
        //{
        //    var authResult = this.CheckToken(token);

        //    using (var db = authResult.CreateDatabase())
        //    {
        //        var l = new FoConfigurazioneMgr(db).GetCampiTabellaArchivioIstanze(authResult.IdComune, software);

        //        return l?.Select(x => new CampoVisuraFrontofficeDto
        //        {
        //            Codice = x.Codice,
        //            Etichetta = x.Etichetta,
        //            IdRisorsa = x.IdRisorsa,
        //            Valore = x.Valore
        //        })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
        //    }
        //}


        //[WebMethod]
        //public int GetRecordPerPagina(string token, string software)
        //{
        //    var authResult = this.CheckToken(token);

        //    using (var db = authResult.CreateDatabase())
        //        return new FoConfigurazioneMgr(db).GetRecordPerPagina(authResult.IdComune, software);
        //}
    }
}
