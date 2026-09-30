using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Init.Sigepro.FrontEnd.Pagamenti.ESED
{
    public class ClientESED
    {
        String _encryptIV;
        String _encryptKey;
        String _codicePortale;

        ILog _log = LogManager.GetLogger(typeof(ClientESED));

        public ClientESED(String encryptIV, String encryptKey, String codicePortale)
        {
            this._encryptIV = encryptIV;
            this._encryptKey = encryptKey;
            this._codicePortale = codicePortale;
        }

        public String GetBufferPaymentRequest(String bufferDati)
        {
            var md5 = MD5.Create();
            var tagOrario = DateTime.Now.ToString("yyyyMMddHHmm");

            var hash = GetMD5(this._encryptIV + bufferDati + this._encryptKey + tagOrario);

            var bufferCrypt = Convert.ToBase64String(Encoding.UTF8.GetBytes(bufferDati));

            var buff = $"<Buffer><TagOrario>{tagOrario}</TagOrario><CodicePortale>{this._codicePortale}</CodicePortale><BufferDati>{bufferCrypt}</BufferDati><Hash>{hash}</Hash></Buffer>";

            return buff;
        }

        private string GetMD5(string text)
        {
            var md5 = MD5.Create();
            var inputBytes = System.Text.Encoding.UTF8.GetBytes(text);

            var hashBytes = md5.ComputeHash(inputBytes);
            var sb = new StringBuilder();

            for (int i = 0; i < hashBytes.Length; i++)
            {
                sb.Append(hashBytes[i].ToString("X2"));
            }

            return sb.ToString();
        }

        public String GetBufferRID(String rID)
        {
            return CreaBuffer(rID);
        }

        public String GetBufferPID(String pID)
        {
            return CreaBuffer(pID);
        }

        public String GetPaymentData(String buffer, int window_minutes)
        {
            return DecodeBuffer(buffer, window_minutes);
        }

        private string DecodeBuffer(string buffer, int window_minutes)
        {
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(buffer);

            XmlNode root = doc.DocumentElement;

            var sTagOrario = root.SelectSingleNode("/Buffer/TagOrario").InnerText;
            var sBufferDatiCrypt = root.SelectSingleNode("/Buffer/BufferDati").InnerText;
            var sHashRicevuto = root.SelectSingleNode("/Buffer/Hash").InnerText;


            if (String.IsNullOrEmpty(sTagOrario))
                throw new Exception("TagOrario non valido");

            if (String.IsNullOrEmpty(sBufferDatiCrypt))
                throw new Exception("BufferDati non valido");

            if (String.IsNullOrEmpty(sHashRicevuto))
                throw new Exception("Hash non valido");

            // TODO
            // verificaFinestraTemporale(sTagOrario, window_minutes);
            var base64EncodedBytes = System.Convert.FromBase64String(sBufferDatiCrypt);
            var bufferDati = System.Text.Encoding.UTF8.GetString(base64EncodedBytes);

            var hashConfronto = GetMD5(this._encryptIV + bufferDati + this._encryptKey + sTagOrario);

            _log.InfoFormat("HASH Confronto: {0}, HASH RICEVUTO: {1}, confronto: {2}", hashConfronto.ToLower(), sHashRicevuto.ToLower(), (hashConfronto.ToLower() == sHashRicevuto.ToLower()));

            if (hashConfronto.ToLower() != sHashRicevuto.ToLower())
            {
                throw new Exception("L'hash del buffer ricevuto non corrisponde all'hash del messaggio");
            }

            return bufferDati;
        }

        private String CreaBuffer(String bufferDati)
        {
            String buffer = null;

            var tagOrario = DateTime.Now.ToString("yyyyMMddHHmm");

            var hash = GetMD5(this._encryptIV + bufferDati + this._encryptKey + tagOrario);

            var bufferDatiCrypt = Convert.ToBase64String(Encoding.UTF8.GetBytes(bufferDati));

            buffer = "<Buffer><TagOrario>" +
                     tagOrario + "</TagOrario>" +
                     "<CodicePortale>" + this._codicePortale + "</CodicePortale>" +
                     "<BufferDati>" + bufferDatiCrypt + "</BufferDati>" +
                     "<Hash>" + hash + "</Hash>" +
                     "</Buffer>";


            return buffer;
        }
    }
}
