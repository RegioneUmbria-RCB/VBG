using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
    /// <summary>
    /// Summary description for AlberoAtecoService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [ScriptService]
    public class AlberoAtecoService : Ninject.Web.WebServiceBase//System.Web.Services.WebService
    {
        //[Inject]
        //public IAtecoRepository _atecoRepository { get; set; }

        //[Inject]
        //public IAliasSoftwareSetter _aliasSoftwareSetter { get; set; }


        //[WebMethod(EnableSession = true)]
        //[ScriptMethod()]
        //public Ateco[] GetNodiFiglio(string aliasComune, int idPadre)
        //{
        //    this._aliasSoftwareSetter.Set(aliasComune, "");

        //    int? id = idPadre == -1 ? (int?)null : idPadre;

        //    return this._atecoRepository.GetNodiFiglio(aliasComune, id);
        //}

        //[WebMethod(EnableSession = true)]
        //[ScriptMethod()]
        //public Ateco GetDettagli(string aliasComune, int id)
        //{
        //    this._aliasSoftwareSetter.Set(aliasComune, "");

        //    return this._atecoRepository.GetDettagli(aliasComune, id);
        //}

        //[WebMethod(EnableSession = true)]
        //[ScriptMethod()]
        //public Ateco[] RicercaAteco(string aliasComune, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
        //{
        //    this._aliasSoftwareSetter.Set(aliasComune, "");

        //    if (matchParziale.Length < 2)
        //        return new Ateco[0];

        //    return this._atecoRepository.RicercaAteco(aliasComune, matchParziale, matchCount, modoRicerca, tipoRicerca);
        //}

        //[WebMethod(EnableSession = true)]
        //[ScriptMethod()]
        //public int[] CaricaGerarchia(string aliasComune, int id)
        //{
        //    this._aliasSoftwareSetter.Set(aliasComune, "");

        //    return this._atecoRepository.CaricaGerarchia(aliasComune, id);
        //}
    }
}
