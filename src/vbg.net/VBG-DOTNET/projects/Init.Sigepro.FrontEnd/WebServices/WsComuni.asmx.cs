using System.ComponentModel;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.WebServices
{
    /// <summary>
    /// Summary description for WsComuni
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    [System.Web.Script.Services.ScriptServiceAttribute()]
    public class WsComuni : Ninject.Web.WebServiceBase
    {
        //    [Inject]
        //    public IComuniRepository _comuniRepository { get; set; }


        //    [WebMethod]
        //    [System.Web.Script.Services.ScriptMethod()]
        //    public CascadingDropDownNameValue[] GetProvincie(string knownCategoryValues, string category, string contextKey)
        //    {
        //        var provincie = _comuniRepository.GetListaProvincie(contextKey);

        //        List<CascadingDropDownNameValue> values = new List<CascadingDropDownNameValue>();
        //        for (int i = 0; i < provincie.Count; i++)	
        //        {
        //            var prov = provincie[i];
        //            values.Add(new CascadingDropDownNameValue(prov.Provincia, prov.SiglaProvincia));
        //        }

        //        return values.ToArray();
        //    }


        //    [WebMethod]
        //    [System.Web.Script.Services.ScriptMethod()]
        //    public CascadingDropDownNameValue[] GetComuniDaProvincia(string knownCategoryValues, string category, string contextKey)
        //    {
        //        StringDictionary kv = CascadingDropDown.ParseKnownCategoryValuesString( knownCategoryValues );

        //        string idProvincia = kv["Provincia"];
        //        if (!kv.ContainsKey("Provincia"))
        //        {
        //            return null;
        //        }

        //        var comuni = _comuniRepository.GetListaComuni(contextKey, idProvincia);

        //        List<CascadingDropDownNameValue> values = new List<CascadingDropDownNameValue>();

        //        for (int i = 0; i < comuni.Count; i++ )
        //        {
        //            var comune = comuni[i];
        //            values.Add(new CascadingDropDownNameValue(comune.Comune, comune.CodiceComune));
        //        }

        //        return values.ToArray();
        //    }
    }
}
