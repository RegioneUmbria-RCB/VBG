using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode
{
    public class SostituzioneSegnapostoQrCode : ISostituzioneSegnapostoQrCode
    {
        public static class Constants
        {
            public const string SegnapostoVisuraGuest = "qr-visura-guest";
        }


        IGenerazioneQrCodeServiceWrapper _serviceWrapper;

        public SostituzioneSegnapostoQrCode(IGenerazioneQrCodeServiceWrapper serviceWrapper)
        {
            this._serviceWrapper = serviceWrapper;
        }

        public string ProcessaCertificato(int codiceIstanza, string htmlCertificato)
        {
            var patterns = new[] {
                "<" + Constants.SegnapostoVisuraGuest + "\\s?/>",
                "<" + Constants.SegnapostoVisuraGuest + "\\s?></" + Constants.SegnapostoVisuraGuest + "\\s?>"
            };

            foreach(var pattern in patterns)
            {
                if (Regex.Matches(htmlCertificato,pattern).Count > 0)
                {
                    var qrCode = this._serviceWrapper.CreateQrCode(codiceIstanza, LivelloAutenticazioneVisuraEnum.GUEST);
                    var htmlCode = qrCode.ToHtmlString();

                    htmlCertificato = Regex.Replace(htmlCertificato, pattern, htmlCode);
                }
            }

            return htmlCertificato;
        }
    }
}
