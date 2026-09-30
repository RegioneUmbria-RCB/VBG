using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace Init.Utils
{
    /// <summary>
    /// Funzioni utili per la gestione di streams
    /// </summary>
    public class StreamUtils
    {
        public StreamUtils()
        {
        }


        /// <summary>
        /// Trasferisce il contenuto di uno stream in un altro
        /// </summary>
        /// <param name="istream">Stream da cui leggere i dati</param>
        /// <param name="ostream">Stream su cui scrivere i dati</param>
        public static void BulkTransfer(Stream istream, Stream ostream)
        {
            istream.Seek(0, SeekOrigin.Begin);
            ostream.Seek(0, SeekOrigin.Begin);

            istream.CopyTo(ostream);


            //         byte[] buffer = new byte[istream.Length];
            //istream.Read(buffer, 0, buffer.Length);
            //ostream.Write(buffer, 0, buffer.Length);
            ostream.Seek(0, SeekOrigin.Begin);
        }

        /// <summary>
        /// Converte una stringa in un MemoryStream
        /// </summary>
        /// <param name="instr">Stringa da convertire</param>
        /// <returns>Stream con il contenuto della stringa</returns>
        public static MemoryStream StringToStream(string instr)
        {
            return StringToStream(instr, TipoCodifica.UTF8);
        }

        /// <summary>
        /// Converte una stringa in un MemoryStream
        /// </summary>
        /// <param name="instr">Stringa da convertire</param>
        /// <returns>Stream con il contenuto della stringa</returns>
        public static MemoryStream StringToStream(string instr, TipoCodifica tipoCodifica)
        {
            byte[] buffer = StringEncoding.StringToBytes(instr, tipoCodifica);
            MemoryStream ms = new MemoryStream(buffer);
            ms.Seek(0, SeekOrigin.Begin);

            return ms;
        }

        /// <summary>
        /// Converte il contenuto di uno stream in una stringa
        /// </summary>
        /// <param name="istream">Stream da cui leggere ida ti</param>
        /// <returns>Stringa con il contenuto dello stream</returns>
        public static string StreamToString(Stream istream)
        {
            return StreamToString(istream, TipoCodifica.UTF8);
        }


        /// <summary>
		/// Converte il contenuto di uno stream in una stringa
		/// </summary>
		/// <param name="istream">Stream da cui leggere ida ti</param>
		/// <returns>Stringa con il contenuto dello stream</returns>
		public static string StreamToString(Stream istream, TipoCodifica tipoCodifica)
        {
            istream.Seek(0, SeekOrigin.Begin);
            byte[] byteBuffer = new byte[istream.Length];
            istream.Read(byteBuffer, 0, byteBuffer.Length);
            string retVal = StringEncoding.BytesToString(byteBuffer, tipoCodifica);

            return retVal;
        }

        /// <summary>
        /// Legge il contenuto di un file in uno stream in memoria
        /// </summary>
        /// <param name="fileName">nome del file da leggere</param>
        /// <returns>Stream contenente il contenuto del file</returns>
        public static MemoryStream FileToStream(string fileName)
        {
            MemoryStream retVal = new MemoryStream();

            using (StreamReader sr = new StreamReader(fileName))
            {
                BulkTransfer(sr.BaseStream, retVal);
            }

            return retVal;
        }

        public static MemoryStream FileToStream(byte[] fileContents)
        {
            MemoryStream retVal = new MemoryStream();
            retVal.Write(fileContents, 0, fileContents.Length);

            retVal.Seek(0, SeekOrigin.Begin);

            return retVal;
        }


        /// <summary>
        /// Scrive il contenuto di uno stream su file
        /// </summary>
        /// <param name="istream">Stream in ingresso</param>
        /// <param name="fileName">Nome del file da creare o da sovrascrivere</param>
        public static void StreamToFile(Stream istream, string fileName)
        {
            using (StreamWriter sw = new StreamWriter(fileName))
            {
                BulkTransfer(istream, sw.BaseStream);
            }
        }


        /// <summary>
        /// Legge il contenuto di uno stream in un array di bytes
        /// </summary>
        /// <param name="istream">Stream in ingresso</param>
        /// <returns>dati contenuti nello stream</returns>
        public static byte[] StreamToBytes(Stream istream)
        {
            if (istream.Position != 0)
                istream.Seek(0, SeekOrigin.Begin);

            byte[] byteBuffer = new byte[istream.Length];
            istream.Read(byteBuffer, 0, byteBuffer.Length);

            return byteBuffer;
        }

        public static string SerializeClass(object cls)
        {
            StringBuilder sb = new StringBuilder();
            using (XmlWriter ms = XmlWriter.Create(sb))
            {
                XmlSerializer xs = new XmlSerializer(cls.GetType());
                xs.Serialize(ms, cls);
            }

            return sb.ToString();
        }
    }

    #region Enum per i tipi di codifica
    public enum TipoCodifica { UTF8, ISO88591 }
    #endregion
}