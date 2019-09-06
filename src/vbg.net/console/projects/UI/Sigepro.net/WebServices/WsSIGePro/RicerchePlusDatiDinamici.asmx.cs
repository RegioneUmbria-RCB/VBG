using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using System.Collections.Generic;
using SIGePro.WebControls.Ajax;
using PersonalLib2.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using System.Text;
using System.Text.RegularExpressions;
using Init.Utils;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for RicerchePlusDatiDinamici
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	[System.Web.Script.Services.ScriptServiceAttribute()]
	public class RicerchePlusDatiDinamici : System.Web.Services.WebService
	{
		/*
		[System.Web.Services.WebMethodAttribute()]
		public string[] GetCompletionList(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  Dictionary<string, string> initParams,
													string campiSelect,
													string tabelleSelect,
													string condizioneJoin,
													string condizioniWhere,
													string nomeCampoValore,
													string nomeCampoTesto,
													int tipoRicerca,
													string campoRicercaCodice,
													string campoRicercaDescrizione)
		{
			try
			{
				AuthenticationInfo authInfo = AuthenticationManager.CheckToken( token );

				if (authInfo == null)
					throw new InvalidTokenException(token);


				return RicerchePlusCtrl.CreateResultList(ExecuteQuery(authInfo, prefixText , campiSelect, tabelleSelect, condizioneJoin, condizioniWhere, nomeCampoValore, nomeCampoTesto , true, tipoRicerca , campoRicercaCodice , campoRicercaDescrizione , count) );
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}

		}
		
		[System.Web.Services.WebMethodAttribute()]
		public string[] InitializeControl(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  Dictionary<string, string> initParams,
													string campiSelect,
													string tabelleSelect,
													string condizioneJoin,
													string condizioniWhere,
													string nomeCampoValore,
													string nomeCampoTesto,
													int tipoRicerca,
													string campoRicercaCodice,
													string campoRicercaDescrizione)
		{
			using (var cp = new CodeProfiler("InitializeControl"))
			{
				try
				{
					AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

					if (authInfo == null)
						throw new InvalidTokenException(token);


					return RicerchePlusCtrl.CreateResultList(ExecuteQuery(authInfo, prefixText, campiSelect, tabelleSelect, condizioneJoin, condizioniWhere, nomeCampoValore, nomeCampoTesto, false, tipoRicerca, campoRicercaCodice, campoRicercaDescrizione, count));
				}
				catch (Exception ex)
				{
					return RicerchePlusCtrl.CreateErrorResult(ex);
				}
			}
		}
	*/

	}
}
