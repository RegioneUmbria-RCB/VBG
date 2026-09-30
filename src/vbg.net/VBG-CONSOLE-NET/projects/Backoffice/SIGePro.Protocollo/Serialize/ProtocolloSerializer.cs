using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.Logs;
using System.IO;
using System.Xml;
using System.Xml.Serialization;
using Init.Utils;

namespace Init.SIGePro.Protocollo.Serialize
{
    public class ProtocolloSerializer
    {
        private ProtocolloLogs _protocolloLogs;

        public ProtocolloSerializer(ProtocolloLogs protocolloLog)
        {
            _protocolloLogs = protocolloLog;
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

        public string Serialize(string sFileName, object pProtocollo)
        {
            return Serialize(sFileName, pProtocollo, ProtocolloValidation.TipiValidazione.XSD, string.Empty, false);
        }

        public string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione tipoValidazione = ProtocolloValidation.TipiValidazione.XSD)
        {
            return Serialize(sFileName, pProtocollo, tipoValidazione, string.Empty, false);
        }

        public string Serialize(string sFileName, object pProtocollo, ProtocolloValidation.TipiValidazione eTipoValidazione, string sTipoValidazione, bool bValidazione)
        {
            return Serialize(sFileName, pProtocollo, eTipoValidazione, sTipoValidazione, bValidazione, String.Empty);
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
        public string Serialize(string fileName, object objectToSerialize, ProtocolloValidation.TipiValidazione tipoValidazione, string sTipoValidazione, bool validazione, string encoding)
        {
            if (objectToSerialize == null)
            {
                _protocolloLogs.InfoFormat("L'OGGETTO DA SERIALIZZARE E' NULL, nome file: {0}", fileName);
                return null;
            }

            _protocolloLogs.DebugFormat("Serializzazione dell'oggetto: {0}, Cartella: {1}, NomeFile: {2}", objectToSerialize.GetType().ToString(), _protocolloLogs.Folder, fileName);

            string returnValue = string.Empty;
            var val = new ProtocolloValidation();

            //_protocolloLogs.CreateFolder();

            using (var pStream = new FileStream(Path.Combine(_protocolloLogs.Folder, fileName), FileMode.Create))
            {
                using (var pMemStreamOut = new MemoryStream())
                {
                    switch (tipoValidazione)
                    {
                        case ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2:
                        case ProtocolloValidation.TipiValidazione.STUDIOK_SEGNATURA:
                            {
                                //Utilizzato solo da Geprot, dovrà essere cambiato
                                //XmlTextWriter xw = new XmlTextWriter(pStream, Encoding.GetEncoding("iso-8859-1"));
                                XmlTextWriter xw = new XmlTextWriter(pStream, null); //La scelta iso-8859-1 è più corretta

                                if (!String.IsNullOrEmpty(encoding))
                                    xw = new XmlTextWriter(pStream, Encoding.GetEncoding(encoding));

                                var xmlSerializer = new XmlSerializer(objectToSerialize.GetType());

                                XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
                                ns.Add("", "");

                                xmlSerializer.Serialize(xw, objectToSerialize, ns);

                                Init.Utils.StreamUtils.BulkTransfer(pStream, pMemStreamOut);
                                pStream.Seek(0, SeekOrigin.Begin);
                                pMemStreamOut.Seek(0, SeekOrigin.Begin);

                                if (validazione)
                                {
                                    new ProtocolloValidation().Validate(pStream);
                                }

                                return Init.Utils.StreamUtils.StreamToString(pMemStreamOut);
                            }
                        case ProtocolloValidation.TipiValidazione.DTD_GEPROT:
                            {
                                //Utilizzato solo da Geprot, dovrà essere cambiato
                                //XmlTextWriter xw = new XmlTextWriter(pStream, Encoding.GetEncoding("iso-8859-1"));
                                XmlTextWriter xw = new XmlTextWriter(pStream, null); //La scelta iso-8859-1 è più corretta

                                if (!String.IsNullOrEmpty(encoding))
                                    xw = new XmlTextWriter(pStream, Encoding.GetEncoding(encoding));

                                xw.WriteStartDocument();
                                xw.WriteDocType("Segnatura", null, sTipoValidazione, null);

                                var xmlSerializer = new XmlSerializer(objectToSerialize.GetType());

                                XmlSerializerNamespaces ns = new XmlSerializerNamespaces();
                                ns.Add("", "");

                                xmlSerializer.Serialize(xw, objectToSerialize, ns);

                                Init.Utils.StreamUtils.BulkTransfer(pStream, pMemStreamOut);
                                pStream.Seek(0, SeekOrigin.Begin);
                                pMemStreamOut.Seek(0, SeekOrigin.Begin);

                                if (validazione)
                                {
                                    new ProtocolloValidation().Validate(pStream);
                                }

                                return Init.Utils.StreamUtils.StreamToString(pMemStreamOut);
                            }
                        case ProtocolloValidation.TipiValidazione.XSD:
                            //StreamWriter sw = new StreamWriter(pStream, Encoding.GetEncoding("iso-8859-1"));
                            StreamWriter sw = new StreamWriter(pStream); //La scelta iso-8859-1 è più corretta

                            try
                            {
                                XmlSerializer pXmlSerializer = null;

                                pXmlSerializer = new XmlSerializer(objectToSerialize.GetType());

                                pXmlSerializer.Serialize(sw, objectToSerialize);

                                Init.Utils.StreamUtils.BulkTransfer(pStream, pMemStreamOut);
                                pStream.Seek(0, SeekOrigin.Begin);
                                pMemStreamOut.Seek(0, SeekOrigin.Begin);

                                if (validazione && !string.IsNullOrEmpty(sTipoValidazione))
                                    val.Validate(pStream, sTipoValidazione);

                                returnValue = Init.Utils.StreamUtils.StreamToString(pMemStreamOut);
                            }
                            catch (Exception ex)
                            {
                                throw new Exception("Problema generato durante la creazione del file " + fileName + ": " + ex.ToString() + "\r\n");
                            }
                            break;
                    }
                }
            }

            return returnValue;
        }

        public void SerializeAndValidateStream(object pProtocollo)
        {
            try
            {
                var pXmlSerializer = new XmlSerializer(pProtocollo.GetType());
                using (var pMemStream = new MemoryStream())
                {
                    pXmlSerializer.Serialize(pMemStream, pProtocollo);
                    pMemStream.Seek(0, SeekOrigin.Begin);

                    var val = new ProtocolloValidation();
                    val.ValidateXml(pMemStream, pProtocollo.GetType().Name);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA SERIALIZZAZIONE DELLA CLASSE " + pProtocollo.GetType().Name, ex);
            }
        }

        public void SerializeAndValidateStream(object pProtocollo, string sFileName)
        {
            string filePath = String.Empty;
            try
            {
                if (!Directory.Exists(_protocolloLogs.Folder))
                    Directory.CreateDirectory(_protocolloLogs.Folder);

                filePath = Path.Combine(_protocolloLogs.Folder, sFileName);

                using (var pFS = new FileStream(filePath, FileMode.Create))
                {
                    var pXmlSerializer = new XmlSerializer(pProtocollo.GetType());
                    pXmlSerializer.Serialize(pFS, pProtocollo);

                    pFS.Seek(0, SeekOrigin.Begin);

                    var val = new ProtocolloValidation();
                    val.ValidateXml(pFS, pProtocollo.GetType().Name);
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA SERIALIZZAZIONE DELLA CLASSE, CONTROLLARE IL FILE {0}", filePath), ex);
            }
        }
    }
}
