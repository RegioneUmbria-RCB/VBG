using System.Text;
using System.Xml;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Serialize
{
    public class ProtocolloSerializer : IProtocolloSerializer
    {
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloValidation _validation;

        public ProtocolloSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation validation)
        {
            this._protocolloLogs = protocolloLog;
            this._validation = validation;
        }

        public T Deserialize<T>(string xml)
        {
            var serializer = new XmlSerializer(typeof(T));

            using (TextReader reader = new StringReader(xml))
            {
                return (T)serializer.Deserialize(reader);
            }
        }

        public T Deserialize<T>(byte[] buffer)
        {
            var serializer = new XmlSerializer(typeof(T));

            using (var memoryStream = new MemoryStream(buffer))
            {
                return (T)serializer.Deserialize(memoryStream);
            }
        }

        public object Deserialize(string xmlString, Type type)
        {
            var serializer = new XmlSerializer(type);

            using (TextReader reader = new StringReader(xmlString))
            {
                return serializer.Deserialize(reader);
            }
        }

        public MemoryStream SerializeToStream<T>(T dataObject)
        {
            var serializer = new XmlSerializer(typeof(T));
            var memoryStream = new MemoryStream();
            serializer.Serialize(memoryStream, dataObject);
            return memoryStream;
        }

        public virtual string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            if (!String.IsNullOrEmpty(messaggio))
            {
                this._protocolloLogs.Debug(messaggio);
            }

            return this.Serialize(sFileName, pProtocollo);
        }

        public string Serialize(string sFileName, object pProtocollo)
        {
            return this.Serialize(sFileName, pProtocollo, ProtocolloValidation.TipiValidazione.XSD, string.Empty, false);
        }


        public string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione tipoValidazione = ProtocolloValidation.TipiValidazione.XSD)
        {
            return this.Serialize(sFileName, pProtocollo, tipoValidazione, string.Empty, false);
        }

        public string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione eTipoValidazione, string sTipoValidazione, bool bValidazione)
        {
            return this.Serialize(sFileName, pProtocollo, eTipoValidazione, sTipoValidazione, bValidazione, String.Empty);
        }

        public virtual XmlAttributeOverrides GetOverrides()
        {
            return new XmlAttributeOverrides();
        }

        public virtual string GetFileName(string fileNameOrig)
        {
            return fileNameOrig;
        }

        public virtual void LogAndValidate(
            string fileName,
            object objectToSerialize,
            string messaggio = null,
            ProtocolloValidation.TipiValidazione tipoValidazione = ProtocolloValidation.TipiValidazione.XSD,
            string sTipoValidazione = "",
            bool validazione = false,
            string encoding = "")
        {
            if (!string.IsNullOrEmpty(messaggio))
            {
                this._protocolloLogs.Debug(messaggio);
            }

            this.LogAndValidateInternal(fileName, objectToSerialize, tipoValidazione, sTipoValidazione, validazione, encoding);
        }


        private FileInfo LogAndValidateInternal(string fileName, object objectToSerialize, ProtocolloValidation.TipiValidazione tipoValidazione, string sTipoValidazione, bool validazione, string encoding)
        {
            fileName = this.GetFileName(fileName);
            var fullPath = Path.Combine(this._protocolloLogs.Folder, fileName);

            if (objectToSerialize == null)
            {
                this._protocolloLogs.InfoFormat("L'OGGETTO DA SERIALIZZARE È NULL, nome file: {0}", fileName);
                //return null;
            }

            this._protocolloLogs.DebugFormat("Serializzazione dell'oggetto: {0}, Cartella: {1}, NomeFile: {2}", objectToSerialize.GetType().ToString(), this._protocolloLogs.Folder, fileName);

            if (!Directory.Exists(this._protocolloLogs.Folder))
                Directory.CreateDirectory(this._protocolloLogs.Folder);

            using (var pStream = new FileStream(fullPath, FileMode.Create, FileAccess.Write))
            {
                var xw = new XmlTextWriter(pStream, null);

                if (!String.IsNullOrEmpty(encoding))
                    xw = new XmlTextWriter(pStream, Encoding.GetEncoding(encoding));

                var ns = new XmlSerializerNamespaces();
                ns.Add("", "");

                switch (tipoValidazione)
                {
                    case ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF:
                    case ProtocolloValidation.TipiValidazione.NO_NAMESPACE:
                    case ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2:
                    case ProtocolloValidation.TipiValidazione.STUDIOK_SEGNATURA:
                        {
                            var xmlSerializer = new XmlSerializer(objectToSerialize.GetType(), this.GetOverrides());
                            xmlSerializer.Serialize(xw, objectToSerialize, ns);
                            break;
                        }

                    case ProtocolloValidation.TipiValidazione.DTD_GEPROT:
                        {
                            var xmlSerializer = new XmlSerializer(objectToSerialize.GetType());
                            xw.WriteStartDocument();
                            xw.WriteDocType("Segnatura", null, sTipoValidazione, null);
                            xmlSerializer.Serialize(xw, objectToSerialize, ns);
                            break;
                        }

                    case ProtocolloValidation.TipiValidazione.XSD:
                        {
                            using (var sw = new StreamWriter(pStream))
                            {
                                var xmlSerializer = new XmlSerializer(objectToSerialize.GetType(), this.GetOverrides());
                                xmlSerializer.Serialize(sw, objectToSerialize);
                                sw.Flush();
                            }
                            break;
                        }
                }
            }

            if (validazione)
            {
                using (var validationStream = new FileStream(fullPath, FileMode.Open, FileAccess.Read))
                {
                    if (tipoValidazione == ProtocolloValidation.TipiValidazione.XSD && !string.IsNullOrEmpty(sTipoValidazione))
                        _validation.Validate(validationStream, sTipoValidazione);
                    else
                        _validation.Validate(validationStream);
                }
            }

            return new FileInfo(fullPath);
        }


        /// <summary>
        /// Serializza un file xml in base all'oggetto passato sul parametro pProtocollo
        /// </summary>
        /// <param name="fileName">Nome da attribuire al file xml</param>
        /// <param name="objectToSerialize">Classe da serializzare</param>
        /// <param name="tipoValidazione"></param>
        /// <param name="sTipoValidazione"></param>
        /// <param name="validazione"></param>
        /// <param name="encoding"></param>
        /// <returns>Ritorna la strina del file xml creato</returns>

        private string Serialize(string fileName, object objectToSerialize, ProtocolloValidation.TipiValidazione tipoValidazione, string sTipoValidazione, bool validazione, string encoding)
        {
            var file = this.LogAndValidateInternal(fileName, objectToSerialize, tipoValidazione, sTipoValidazione, validazione, encoding);

            //return file.OpenText().ReadToEnd();

            using (var reader = file.OpenText())
            {
                return reader.ReadToEnd();
            }
        }

        public void SerializeAndValidateStream(object pProtocollo, string sFileName)
        {
            string filePath = String.Empty;
            try
            {
                if (!Directory.Exists(this._protocolloLogs.Folder))
                    Directory.CreateDirectory(this._protocolloLogs.Folder);

                filePath = Path.Combine(this._protocolloLogs.Folder, sFileName);

                using (var pFS = new FileStream(filePath, FileMode.Create))
                {
                    var pXmlSerializer = new XmlSerializer(pProtocollo.GetType(), this.GetOverrides());
                    pXmlSerializer.Serialize(pFS, pProtocollo);

                    pFS.Seek(0, SeekOrigin.Begin);

                    _validation.ValidateXml(pFS, pProtocollo.GetType().Name);
                }
            }
            catch (Exception ex)
            {
                throw this._protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA SERIALIZZAZIONE DELLA CLASSE, CONTROLLARE IL FILE {0}", filePath), ex);
            }
        }
    }
}
