using log4net;
using Renci.SshNet;
using System.Net;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class AllegatiService
    {
        private readonly ILog _logs = LogManager.GetLogger(typeof(AllegatiService));
        private readonly string _host;
        private readonly string _username;
        private readonly string _password;
        private readonly int? _port;

        public AllegatiService(string host, string username, string password, int? port = null)
        {
            this._host = host;
            this._username = username;
            this._password = password;
            this._port = port.HasValue ? port.Value : 22;
        }

        /// <summary>
        /// Al momento non usato, utilizzare solo sftp
        /// </summary>
        /// <param name="fileContents"></param>
        /// <param name="fileName"></param>
        public void UploadFileFtp(byte[] fileContents, string fileName)
        {
            this._logs.InfoFormat("UPLOAD DEL FILE {0} IN FTP", fileName);

            // Get the object used to communicate with the server.  
            var request = (FtpWebRequest)WebRequest.Create($"ftp://{this._host}/{fileName}");
            request.Method = WebRequestMethods.Ftp.UploadFile;

            // This example assumes the FTP site uses anonymous logon.  
            request.Credentials = new NetworkCredential(this._username, this._password);

            // Copy the contents of the file to the request stream.  
            //StreamReader sourceStream = new StreamReader("testfile.txt");
            //byte[] fileContents = Encoding.UTF8.GetBytes(sourceStream.ReadToEnd());
            //sourceStream.Close();
            request.ContentLength = fileContents.Length;

            var requestStream = request.GetRequestStream();
            requestStream.Write(fileContents, 0, fileContents.Length);
            requestStream.Close();

            var response = (FtpWebResponse)request.GetResponse();

            this._logs.InfoFormat("UPLOAD DEL FILE {0} IN FTP AVVENUTO CORRETTAMENTE", fileName);

            response.Close();
        }

        public void UploadSftp(byte[] buffer, string fileName, uint? bufferSize = null)
        {
            this._logs.InfoFormat("UPLOAD IN SFTP DEL FILE {0}. Host: {1} username: {2}, port: {3}", fileName, this._host, this._username, this._port);
            using (var client = new SftpClient(this._host, this._port.Value, this._username, this._password))
            {
                this._logs.Debug("CONNECT");
                client.Connect();
                this._logs.Debug("FINE CONNECT");

                this._logs.InfoFormat("IS CONNECT: {0}, buffer size: {1}", client.IsConnected, bufferSize.HasValue ? bufferSize.Value.ToString() : "NOT SET");

                if (!client.IsConnected)
                {
                    throw new Exception("NON E' POSSIBILE CONNETTERSI AL SERVER SFTP PER L'INVIO DEGLI ALLEGATI");
                }

                if (bufferSize.HasValue)
                {
                    this._logs.DebugFormat("SET BUFFER SIZE {0}", bufferSize.Value);
                    client.BufferSize = bufferSize.Value;
                    this._logs.DebugFormat("FINE SET BUFFER SIZE {0}", bufferSize.Value);
                }

                using (var stream = new MemoryStream(buffer))
                {
                    this._logs.DebugFormat("UPLOAD FILE {0}", fileName);
                    client.UploadFile(stream, fileName, true);
                    this._logs.DebugFormat("UPLOAD FILE {0} AVVENUTO CORRETTAMENTE", fileName);
                }
            }

            this._logs.InfoFormat("FINE UPLOAD IN SFTP DEL FILE {0}", fileName);
        }

    }
}
