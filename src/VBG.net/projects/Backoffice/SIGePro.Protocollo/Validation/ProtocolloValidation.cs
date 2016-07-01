using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;
using System.Web;
using System.Configuration;

namespace Init.SIGePro.Protocollo.Validation
{
    public class ProtocolloValidation
    {
        #region Enum per i tipi di validazione

        public enum TipiValidazione { DTD_GEPROT, DTD_EGRAMMATA2, XSD }

        #endregion

        #region Membri privati

        private bool b_success;
        private string sMessage = "";
        private string _SchemaPath = "";

        #endregion

        #region Proprietà
        
        public string SchemaPath
        {
            get
            {
                if (string.IsNullOrEmpty(_SchemaPath))
                    _SchemaPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["SchemaPath"]);

                if (string.IsNullOrEmpty(_SchemaPath))
                    throw new Exception("Parametro SchemaPath non presente oppure uguale ad una stringa vuota nel config!!");
                else
                {
                    if (!_SchemaPath.EndsWith(@"\"))
                        _SchemaPath += @"\";
                }

                return _SchemaPath;
            }
            set
            {
                _SchemaPath = value;
                if (!value.EndsWith(@"\"))
                    _SchemaPath += @"\";
            }
        }

        #endregion

        #region Costruttori

        public ProtocolloValidation()
        {

        }

        #endregion

        /// <summary>
        /// Metodo usato per validare il file segnatura.xml in base al file xsd
        /// </summary>
        /// <param name="pStream">File xml da validare</param>
        public void Validate(Stream pStream, string sXsd)
        {
            XmlValidatingReader vreader = null;
            try
            {
                b_success = true;
                pStream.Seek(0, SeekOrigin.Begin);
                XmlTextReader reader = new XmlTextReader(pStream);

                //Creo un validating reader.
                vreader = new XmlValidatingReader(reader);

                if (!string.IsNullOrEmpty(sXsd))
                {
                    XmlSchemaCollection xsc = new XmlSchemaCollection();
                    xsc.Add(null, Path.Combine(SchemaPath, sXsd));
                    //Valido usando lo schema conservato nello schema collection.
                    vreader.Schemas.Add(xsc);
                }

                vreader.ValidationEventHandler += new ValidationEventHandler(ValidationCallBack);
                //Leggo e valido il file xml.
                while (vreader.Read())
                {
                    if (!b_success)
                        throw new Exception(sMessage);
                }

            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la validazione. " + ex.Message);
            }
            finally
            {
                //Il file viene chiuso tramite lo stream writer
                if (vreader != null)
                    vreader.Close();
            }
        }

        public void Validate(Stream stream)
        {
            Validate(stream, string.Empty);
        }

        public void ValidateXml(Stream stream, string name)
        {
            //Verifico la validità del file xml di lettura o di protocollazione
            switch (name)
            {
                case "DatiProtocollo":
                    Validate(stream, "Protocollo.xsd");
                    break;
                case "DatiProtocolloLetto":
                    Validate(stream, "ProtocolloLetto.xsd");
                    break;
                case "DatiProtocolloAnnullato":
                    Validate(stream, "ProtocolloAnnullato.xsd");
                    break;
                case "ListaTipiDocumento":
                    Validate(stream, "ListaTipiDocumento.xsd");
                    break;
                case "ListaTipiClassifica":
                    Validate(stream, "ListaClassifiche.xsd");
                    break;
                case "DatiProtocolloFascicolato":
                    Validate(stream, "ProtocolloFascicolato.xsd");
                    break;
                case "ListaFascicoli":
                    Validate(stream, "ListaFascicoli.xsd");
                    break;
                case "DatiFascicolo":
                    Validate(stream, "Fascicolo.xsd");
                    break;
                case "DatiEtichette":
                    Validate(stream, "Etichette.xsd");
                    break;
            }
        }
        
        private void ValidationCallBack(object sender, ValidationEventArgs args)
        {
            b_success = false;
            sMessage = "Errore di validazione: " + args.Message;
        }
    }
}
