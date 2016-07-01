using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Web;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.Token;

namespace SIGePro.Net
{
	/// <summary>
	/// Descrizione di riepilogo per MostraOggetto.
	/// </summary>
	public partial class MostraOggetto : System.Web.UI.Page
	{
		private AuthenticationInfo m_authInfo;

		protected string Token
		{
			get { return Request.QueryString["Token"]; }
		}

		public AuthenticationInfo AuthenticationInfo
		{
			get { return m_authInfo; }
		}

		public string IdOggetto
		{
			get{return Request.QueryString["IdOggetto"];}
		}

		protected string IdComune
		{
			get{return AuthenticationInfo.IdComune;}
		}

		protected void Page_Load(object sender, System.EventArgs e)
		{
			if ( string.IsNullOrEmpty( Token ))
                throw new EmptyTokenException();

			if ( string.IsNullOrEmpty( IdOggetto ))
				throw new ArgumentException("IdOggetto non impostato");

			m_authInfo = AuthenticationManager.CheckToken(Token);

			if (m_authInfo == null)
                throw new InvalidTokenException(Token);

			Mostra( IdComune , IdOggetto );
		}

		private void Mostra(string idComune, string idOggetto)
		{
			OggettiMgr oggMgr = new OggettiMgr( AuthenticationInfo.CreateDatabase() );
			Oggetti ogg = oggMgr.GetById(idComune , Convert.ToInt32(idOggetto));
			
			if ( ogg == null )
				throw new ArgumentException("Codice oggetto non valido: " + ogg.CODICEOGGETTO);

			string ctType = oggMgr.GetContentType( ogg );

			if ( ctType == String.Empty )
				throw new InvalidOperationException("Content type non trovato per il file " + ogg.NOMEFILE + " (IdOggetto=" + ogg.CODICEOGGETTO + ", IdComune=" + ogg.IDCOMUNE + ")");

			Response.Clear();
			Response.AddHeader("content-disposition", "attachment;filename=" + ogg.NOMEFILE);
			Response.ContentType = ctType;
			Response.BinaryWrite(ogg.OGGETTO);
			Response.Flush();
			Response.End();
			return;
		}

		
	}
}
