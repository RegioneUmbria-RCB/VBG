namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    using log4net;
    using System;
    using System.IO;
    using System.Web;
    using System.Xml;
    using System.Xml.Schema;

    internal class AdrierXmlValidator
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AdrierXmlValidator));
        private readonly string _baseXsdPath;

        public AdrierXmlValidator(string baseXsdPath)
        {
            this._baseXsdPath = baseXsdPath;
        }

        private enum TipoValidazioneParix
        {
            ListaImprese,
            DettaglioImpresaRidotto
        }

        public void ValidaListaImprese(Stream stream)
        {
            this.ValidateXml(stream, TipoValidazioneParix.ListaImprese);
        }

        public void ValidaDettaglioImpresa(Stream stream)
        {
            this.ValidateXml(stream, TipoValidazioneParix.DettaglioImpresaRidotto);
        }

        /// <summary>
        /// Metodo usato per validare il file segnatura.xml in base al file xsd
        /// </summary>
        /// <param name="stream">File xml da validare</param>
        private void ValidateXml(Stream stream, TipoValidazioneParix tipoValidazione)
        {
            var fileName = tipoValidazione == TipoValidazioneParix.ListaImprese ? "ListaImprese.xsd" : "DettaglioImpresaRidotto.xsd";

            try
            {
                var verificaPositiva = true;
                var erroreValidazione = "";

                stream.Seek(0, SeekOrigin.Begin);
                XmlTextReader reader = new XmlTextReader(stream);
#if NET48
                //Creo un validating reader.
                using (var vreader = new XmlValidatingReader(reader))
				{
					var xsc = new XmlSchemaCollection();

					string schemaPath = HttpContext.Current.Server.MapPath(this._baseXsdPath);

					xsc.Add(null, Path.Combine(schemaPath, fileName));

					//Valido usando lo schema conservato nello schema collection.
					vreader.Schemas.Add(xsc);

					vreader.ValidationEventHandler += (sender, args) =>
					{
						verificaPositiva = false;
						erroreValidazione = args.Message;
					};

					//Leggo e valido il file xml.
					while (vreader.Read())
					{
						if (!verificaPositiva)
							throw new Exception(erroreValidazione);
					}
				}
#endif
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("AnagrafeSearcherParixBase->ValidateXml->errore durante la validazione sul file {0}: {1}", fileName, ex.ToString());
                throw new Exception("Errore generato durante la validazione. " + ex.Message);
            }
        }
    }
}
