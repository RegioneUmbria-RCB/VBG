using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Configuration;
using System.IO;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Logs.Services
{
    internal class LogPathFromHttpContextResolverService : ILogPathResolverService
    {
        private static class Constants
        {
            public const string HttpItemKeyName = "LogPathFromHttpContextResolverService";
            public const string TempPathKeyName = "TempPath";
        }

        ResolveDatiProtocollazioneService _datiProtocollazione;

        public LogPathFromHttpContextResolverService(ResolveDatiProtocollazioneService datiProtocollazione)
        {
            this._datiProtocollazione = datiProtocollazione;
        }

        #region ILogPathResolverService Members

        public string LogPath
        {
            get 
            {
                if (!HttpContext.Current.Items.Contains(Constants.HttpItemKeyName))
                    HttpContext.Current.Items[Constants.HttpItemKeyName] = GeneraNuovoPath();

                return HttpContext.Current.Items[Constants.HttpItemKeyName].ToString();
            }
        }

        private string GeneraNuovoPath()
        {
            var tempPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings[Constants.TempPathKeyName]);

            if (String.IsNullOrEmpty(tempPath))
                throw new Exception("PARAMETRO TEMPPATH NON PRESENTE OPPURE UGUALE AD UNA STRINGA VUOTA NEL CONFIG!!");
            else
            {
                if (!tempPath.EndsWith(@"\"))
                    tempPath += @"\";
            }

            string idComune = _datiProtocollazione.IdComune;
            string codiceIstanza = String.IsNullOrEmpty(_datiProtocollazione.CodiceIstanza) ? "0" : _datiProtocollazione.CodiceIstanza;
            string codiceMovimento = String.IsNullOrEmpty(_datiProtocollazione.CodiceMovimento) ? "0" : _datiProtocollazione.CodiceMovimento;
            string codiceOperatore = !_datiProtocollazione.CodiceResponsabile.HasValue ? "0" : _datiProtocollazione.CodiceResponsabile.Value.ToString();
            string software = String.IsNullOrEmpty(_datiProtocollazione.Software) ? "XX" : _datiProtocollazione.Software;

            string folder = String.Format("{0}.{1}.{2}.{3}.{4}.{5}", idComune, codiceIstanza, codiceMovimento, codiceOperatore, software, DateTime.Now.ToString("ddMMyyyy.HHmmss"));
            
            var res = Path.Combine(tempPath, folder);

            if (!Directory.Exists(res))
                Directory.CreateDirectory(res);

            return res;
        }

        #endregion
    }
}
