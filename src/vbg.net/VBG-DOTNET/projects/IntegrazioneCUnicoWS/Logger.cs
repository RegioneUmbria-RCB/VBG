using IntegrazioneCUnicoWS.CUnicoWS;
using log4net;
using System;
using System.IO;
using System.Reflection;
using System.Web;
using System.Xml.Serialization;

namespace IntegrazioneCUnicoWS
{
    public class Logger
    {
        private readonly ILog _logger;
        private readonly String _guid;

        public Logger(ILog logger)
        {
            this._logger = logger;
            this._guid = Guid.NewGuid().ToString();

            Directory.CreateDirectory(Path.Combine(this.BaseLogPath, this._guid));
        }

        private string BaseLogPath
        {
            get
            {
#if NET9_0_OR_GREATER
                return Path.Combine(Assembly.GetExecutingAssembly().Location, "temp");
#else
                return HttpContext.Current.Server.MapPath("~\\Temp");
#endif
            }
        }

        public void Debug(String messaggio, Object file = null)
        {
            if (!this._logger.IsDebugEnabled)
            {
                return;
            }

            var messaggioLocal = messaggio;

            if (file != null)
            {
                var nomeFile = this.GetNomeFile();
                messaggioLocal += $" Riferimento => {nomeFile}";
                File.WriteAllText(nomeFile, this.XmlSerializeToString(file));
            }

            this._logger.Debug(messaggioLocal);
        }

        internal void Error(string eccezione, object request = null)
        {
            var messaggioLocal = eccezione;

            if (request != null)
            {
                var nomeFile = this.GetNomeFile();
                messaggioLocal += $" Riferimento => {nomeFile}";
                File.WriteAllText(nomeFile, this.XmlSerializeToString(request));
            }

            this._logger.Error(messaggioLocal);
        }

        private String GetNomeFile()
        {
            var nome = DateTime.Now.ToString("yyyyMMdd-HHmmssfff") + ".txt";

            return Path.Combine(this.BaseLogPath, this._guid, nome);
        }



        private string XmlSerializeToString(object objectInstance)
        {
            var overrides = new OverrideXml()
                    .Override<inserisciConcessioneRisposta>()
                    .Member("modelloPagoPA").XmlIgnore()
                    .Commit();

            var serializer = new XmlSerializer(objectInstance.GetType(), overrides);

            var memoryStream = new MemoryStream();
            var streamWriter = new StreamWriter(memoryStream, System.Text.Encoding.UTF8);

            serializer.Serialize(streamWriter, objectInstance);

            memoryStream.Seek(0, SeekOrigin.Begin);
            var streamReader = new StreamReader(memoryStream, System.Text.Encoding.UTF8);
            return streamReader.ReadToEnd();
        }

    }
}
