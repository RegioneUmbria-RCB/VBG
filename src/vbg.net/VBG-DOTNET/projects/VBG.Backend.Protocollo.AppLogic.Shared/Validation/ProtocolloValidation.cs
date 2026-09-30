using System.Xml;
using System.Xml.Schema;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Validation
{
    public class ProtocolloValidation
    {
        #region Enum per i tipi di validazione

        public enum TipiValidazione { DTD_GEPROT, DTD_EGRAMMATA2, XSD, STUDIOK_SEGNATURA, PROTOCOLLOXML_PROTOINF, NO_NAMESPACE }

        #endregion

        #region Membri privati

        private bool b_success;
        private string sMessage = "";

        #endregion

        public string SchemaPath { get; }

        public ProtocolloValidation()
        {
            // vecchio percorso relativo \WebServices\WsSIGePro\Schema
            var path = Path.Combine("WebServices", "WsSIGePro", "Schema");

            path = Path.Combine(AppContext.BaseDirectory, path);

            if (!path.EndsWith(Path.DirectorySeparatorChar.ToString()) &&
                !path.EndsWith(Path.AltDirectorySeparatorChar.ToString()))
            {
                path += Path.DirectorySeparatorChar;
            }

            SchemaPath = path;
        }


        /// <summary>
        /// Metodo usato per validare il file segnatura.xml in base al file xsd
        /// </summary>
        /// <param name="pStream">File xml da validare</param>
        public void Validate(Stream pStream, string sXsd)
        {
            try
            {
                this.b_success = true;
                pStream.Seek(0, SeekOrigin.Begin);
                var reader = new XmlTextReader(pStream);

                //Creo un validating reader.
                using (var vreader = new XmlValidatingReader(reader))
                {
                    if (!string.IsNullOrEmpty(sXsd))
                    {
                        var xsc = new XmlSchemaCollection();
                        xsc.Add(null, Path.Combine(this.SchemaPath, sXsd));
                        //Valido usando lo schema conservato nello schema collection.
                        vreader.Schemas.Add(xsc);
                    }

                    vreader.ValidationEventHandler += new ValidationEventHandler(this.ValidationCallBack);
                    //Leggo e valido il file xml.
                    while (vreader.Read())
                    {
                        if (!this.b_success)
                            throw new Exception(this.sMessage);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la validazione. " + ex.Message, ex);
            }
        }

        public void Validate(Stream stream)
        {
            this.Validate(stream, string.Empty);
        }

        public void ValidateXml(Stream stream, string name)
        {
            //Verifico la validità del file xml di lettura o di protocollazione
            switch (name)
            {
                case "DatiProtocollo":
                    this.Validate(stream, "Protocollo.xsd");
                    break;
                case "DatiProtocolloLetto":
                    this.Validate(stream, "ProtocolloLetto.xsd");
                    break;
                case "DatiProtocolloAnnullato":
                    this.Validate(stream, "ProtocolloAnnullato.xsd");
                    break;
                case "ListaTipiDocumento":
                    this.Validate(stream, "ListaTipiDocumento.xsd");
                    break;
                case "ListaTipiClassifica":
                    this.Validate(stream, "ListaClassifiche.xsd");
                    break;
                case "DatiProtocolloFascicolato":
                    this.Validate(stream, "ProtocolloFascicolato.xsd");
                    break;
                case "ListaFascicoli":
                    this.Validate(stream, "ListaFascicoli.xsd");
                    break;
                case "DatiFascicolo":
                    this.Validate(stream, "Fascicolo.xsd");
                    break;
                case "DatiEtichette":
                    this.Validate(stream, "Etichette.xsd");
                    break;
            }
        }

        private void ValidationCallBack(object sender, ValidationEventArgs args)
        {
            this.b_success = false;
            this.sMessage = "Errore di validazione: " + args.Message;
        }
    }
}
