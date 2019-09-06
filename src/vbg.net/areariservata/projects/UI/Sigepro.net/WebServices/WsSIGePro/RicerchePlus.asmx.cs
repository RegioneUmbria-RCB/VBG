using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using System.Web.Script.Services;
using System.Collections.Generic;
using Init.SIGePro.Authentication;
using SIGePro.WebControls.Ajax;
using System.Web.Script.Serialization;
using Init.SIGePro.Manager.Logic.Ricerche;

namespace Sigepro.net.WebServices.WsSIGePro
{
	

	/// <summary>
	/// Summary description for RicerchePlus
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	[System.Web.Script.Services.ScriptServiceAttribute()]
	public class RicerchePlus : System.Web.Services.WebService
	{

		[System.Web.Services.WebMethodAttribute()]
		public string[] GetCompletionList(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
													string software,
													bool ricercaSoftwareTT,
												  Dictionary<string,string> initParams)
		{
			try
			{
				RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software , ricercaSoftwareTT, initParams);
				return RicerchePlusCtrl.CreateResultList(sc.Find(true));
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}
		}


	}
}
