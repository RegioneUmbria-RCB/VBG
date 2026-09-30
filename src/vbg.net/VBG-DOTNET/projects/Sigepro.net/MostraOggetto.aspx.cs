using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using System;

namespace SIGePro.Net
{
    /// <summary>
    /// Descrizione di riepilogo per MostraOggetto.
    /// </summary>
    public partial class MostraOggetto : BasePage
    {
        private AuthenticationInfo m_authInfo;

        public string IdOggetto
        {
            get { return this.Request.QueryString["IdOggetto"]; }
        }

        protected void Page_Load(object sender, System.EventArgs e)
        {
            if (string.IsNullOrEmpty(this.Token))
                throw new EmptyTokenException();

            if (string.IsNullOrEmpty(this.IdOggetto))
                throw new ArgumentException("IdOggetto non impostato");

            this.m_authInfo = this.CheckToken(this.Token);

            if (this.m_authInfo == null)
                throw new InvalidTokenException(this.Token);

            this.Mostra(this.IdComune, this.IdOggetto);
        }

        private void Mostra(string idComune, string idOggetto)
        {
            OggettiMgr oggMgr = new OggettiMgr(this.AuthenticationInfo.CreateDatabase());
            Oggetti ogg = oggMgr.GetById(idComune, Convert.ToInt32(idOggetto));

            if (ogg == null)
                throw new ArgumentException("Codice oggetto non valido: " + ogg.CODICEOGGETTO);

            string ctType = oggMgr.GetContentType(ogg);

            if (ctType == String.Empty)
                throw new InvalidOperationException("Content type non trovato per il file " + ogg.NOMEFILE + " (IdOggetto=" + ogg.CODICEOGGETTO + ", IdComune=" + ogg.IDCOMUNE + ")");

            this.Response.Clear();
            this.Response.AddHeader("content-disposition", "attachment;filename=" + ogg.NOMEFILE);
            this.Response.ContentType = ctType;
            this.Response.BinaryWrite(ogg.OGGETTO);
            this.Response.Flush();
            this.Response.End();
            return;
        }


    }
}
