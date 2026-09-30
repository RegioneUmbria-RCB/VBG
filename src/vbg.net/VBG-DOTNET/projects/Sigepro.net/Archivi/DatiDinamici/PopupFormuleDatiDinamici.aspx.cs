using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.IO;
using System.Xml.Serialization;
using System.Text;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class PopupFormuleDatiDinamici : System.Web.UI.Page
	{
		protected void Page_Load(object sender, EventArgs e)
		{
			Response.Cache.SetCacheability(HttpCacheability.NoCache);
			Response.Cache.SetNoServerCaching();
			Response.Cache.SetNoStore();
			Response.Cache.SetExpires(DateTime.Now.AddDays(-1));

			LoadSnippets();
		}

		private void LoadSnippets()
		{
			string fileName = Server.MapPath("~/Archivi/Datidinamici/datiDinamiciSnippets.xml");

			SnippetCollectionNode snippets = new SnippetCollectionNode();

			using (FileStream s = File.Open(fileName, FileMode.Open,FileAccess.Read))
			{
				XmlSerializer xs = new XmlSerializer(typeof(SnippetCollectionNode));
				snippets = (SnippetCollectionNode)xs.Deserialize(s);
			}

			StringBuilder sb = new StringBuilder();
			sb.Append("var snippet;\r\nvar par;").Append(Environment.NewLine);

			string snippetGroupFmtString = "g_wizard.AddSnippetGroup(\"{0}\");";
			string snippetFmtString = "snippet = new Snippet( \"{0}\" );\r\nsnippet.SetCodeSnippet( \"{1}\" );";
			string paramFmtString = "par = new SnippetParameter( \"{0}\",\"{1}\", {2} );snippet.AddParameter( par );";


			for (int groupIdx = 0; groupIdx < snippets.Gruppo.Length; groupIdx++)
			{
				SnippetGroupNode gruppo = snippets.Gruppo[groupIdx];

				sb.AppendFormat(snippetGroupFmtString, gruppo.Nome);

				for (int i = 0; i < gruppo.Snippet.Length; i++)
				{
					string descrizione = ClientEscape(gruppo.Snippet[i].Descrizione);
					string codice = ClientEscape(gruppo.Snippet[i].Codice);

					sb.Append(String.Format(snippetFmtString, descrizione, codice));

					foreach (ParametroDatiDinamiciType par in gruppo.Snippet[i].Parametri)
					{
						string nomeParametro = ClientEscape(par.NomeParametro);
						string descrizioneParametro = ClientEscape(par.DescrizioneParametro);
						string richiedeNomeCampo = par.RichiedeNomeCampo ? "true" : "false";

						sb.Append(String.Format(paramFmtString, nomeParametro, descrizioneParametro, richiedeNomeCampo));
					}

					sb.Append("g_wizard.Groups[\"" + gruppo.Nome +"\"].AddSnippet( snippet );");
				}
			}
			sb.Append("g_wizard.SetContainer( document.getElementById(\"container\") );\r\ng_wizard.Render( );");

			this.ClientScript.RegisterStartupScript(this.GetType(), "jsWizard", sb.ToString(), true);
		}

		private string ClientEscape(string txt)
		{
			return Microsoft.JScript.GlobalObject.escape(txt).Replace("+","%2B");
			//return Server.UrlEncode(txt).Replace("+", " ");
		}
	}
}
