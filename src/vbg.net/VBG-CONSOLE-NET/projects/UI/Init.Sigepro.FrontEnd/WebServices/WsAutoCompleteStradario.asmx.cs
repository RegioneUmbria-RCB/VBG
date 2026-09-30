using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using System.Collections.Generic;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.WebServices
{
	/// <summary>
	/// Summary description for WsAutoCompleteStradario
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	[System.Web.Script.Services.ScriptServiceAttribute()]
	public class WsAutoCompleteStradario : Ninject.Web.WebServiceBase
	{
		//[Inject]
		//public IStradarioRepository _stradarioRepository { get; set; }



		//[WebMethod(EnableSession=true)]
		//[System.Web.Script.Services.ScriptMethod()]
		//public string[] GetCompletionList(string prefixText, int count, string contextKey)
		//{
		//    string[] com = contextKey.Split('$');

		//    var listaIndirizzi = _stradarioRepository.GetByMatchParziale( com[0], com[1], prefixText);

		//    List<string> l = new List<string>();

		//    var maxCnt = Math.Min(listaIndirizzi.Count, count);

		//    for (int i = 0; i < maxCnt; i++)
		//    {
		//        var indirizzo = listaIndirizzi[i];

		//        l.Add(AutoCompleteExtender.CreateAutoCompleteItem(indirizzo.DESCRIZIONE, indirizzo.CODICESTRADARIO));
		//    }

		//    return l.ToArray();
		//}
	}
}
