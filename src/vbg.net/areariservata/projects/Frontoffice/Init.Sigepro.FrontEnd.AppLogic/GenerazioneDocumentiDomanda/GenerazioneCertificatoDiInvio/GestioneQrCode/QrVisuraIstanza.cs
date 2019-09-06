using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode
{
    public class QrVisuraIstanza
    {
        private static class Constants
        {
            public const string Template = "<div class='qrcode'><img src='data:image/png;base64,{0}'/><div><a href='{1}'>Visura on-line</a></div></div>";
        }



        public readonly byte[] Data;
        public string Url;

        public QrVisuraIstanza(byte[] data, string url)
        {
            this.Data = data;
            this.Url = url;
        }

        public string ToHtmlString()
        {
            return String.Format(Constants.Template, Convert.ToBase64String(this.Data), this.Url);
        }
    }
}
