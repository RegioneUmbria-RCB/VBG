using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.SIGePro.Manager.DTO;
using Ninject;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
    /// <summary>
    /// Summary description for AutocompleteStradario
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class AutocompleteStradario : Ninject.Web.WebServiceBase
    {
        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; }

        [Inject]
        public IAliasSoftwareSetter _aliasSoftwareSetter { get; set; }

        public class AutocompleteStradarioResultItem : BaseDto<int, string>
        {
            public string CodViario { get; set; }
        }

        public class AutocompleteStradarioResult
        {
            public AutocompleteStradarioResultItem[] Items { get; set; }
            public int ItemCount { get; set; }
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod]
        public AutocompleteStradarioResult Autocomplete(string idComune, string codiceComune, string comuneLocalizzazione, string match)
        {
            this._aliasSoftwareSetter.Set(idComune);

            var listaIndirizzi = this._stradarioRepository.GetByMatchParziale(idComune, codiceComune, comuneLocalizzazione, match);

            var q = listaIndirizzi.Select(s => new AutocompleteStradarioResultItem
            {
                Codice = s.CodiceStradario,
                Descrizione = s.NomeVia,
                CodViario = s.CodViario
            });

            var rVal = new AutocompleteStradarioResult
            {
                ItemCount = q.Count(),
                Items = q.Take(30).ToArray()
            };


            return rVal;

        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod]
        public AutocompleteStradarioResult AutocomlpeteStradario(string idComune, string codiceComune, string comuneLocalizzazione, string match)
        {
            return this.Autocomplete(idComune, codiceComune, comuneLocalizzazione, match);
        }
    }

}
