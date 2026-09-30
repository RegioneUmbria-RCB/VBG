namespace Sigepro.net.WebServices.WsAreaRiservata
{
    ///// <summary>
    ///// Summary description for CampiVisuraServiceV2
    ///// </summary>
    //[WebService(Namespace = "http://tempuri.org/")]
    //[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    //[System.ComponentModel.ToolboxItem(false)]
    //// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    //// [System.Web.Script.Services.ScriptService]
    //public class CampiVisuraServiceV2 : SigeproWebService
    //{
    //    private const string CONTESTO_ARCHIVIO_LISTA = "ARCHIVIO_LISTA";
    //    private const string CONTESTO_ARCHIVIO_FILTRI = "ARCHIVIO_FILTRI";
    //    private const string CONTESTO_VISURA_LISTA = "VISURA_LISTA";
    //    private const string CONTESTO_VISURA_FILTRI = "VISURA_FILTRI";

    //    [WebMethod]
    //    public List<FoVisuraCampiDto> GetFiltriVisura(string token, string software)
    //    {
    //        var ai = this.CheckToken(token);

    //        using (var db = ai.CreateDatabase())
    //        {
    //            var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_FILTRI);

    //            return l?.Select(x => new FoVisuraCampiDto
    //            {
    //                Fkidcampo = x.Fkidcampo
    //            })?.ToList() ?? new List<FoVisuraCampiDto>();
    //        }
    //    }

    //    [WebMethod]
    //    public List<FoVisuraCampiDto> GetCampiListaVisura(string token, string software)
    //    {
    //        var ai = this.CheckToken(token);

    //        using (var db = ai.CreateDatabase())
    //        {
    //            var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_LISTA);

    //            return l?.Select(x => new FoVisuraCampiDto
    //            {
    //                Fkidcampo = x.Fkidcampo
    //            })?.ToList() ?? new List<FoVisuraCampiDto>();
    //        }
    //    }

    //    [WebMethod]
    //    public List<FoVisuraCampiDto> GetFiltriArchivio(string token, string software)
    //    {
    //        var ai = this.CheckToken(token);

    //        using (var db = ai.CreateDatabase())
    //        {
    //            var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_FILTRI);

    //            return l?.Select(x => new FoVisuraCampiDto
    //            {
    //                Fkidcampo = x.Fkidcampo
    //            })?.ToList() ?? new List<FoVisuraCampiDto>();
    //        }
    //    }

    //    [WebMethod]
    //    public List<FoVisuraCampiDto> GetCampiListaArchivio(string token, string software)
    //    {
    //        var ai = this.CheckToken(token);

    //        using (var db = ai.CreateDatabase())
    //        {
    //            var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_LISTA);

    //            return l?.Select(x => new FoVisuraCampiDto
    //            {
    //                Fkidcampo = x.Fkidcampo
    //            })?.ToList() ?? new List<FoVisuraCampiDto>();
    //        }
    //    }
    //}
}
