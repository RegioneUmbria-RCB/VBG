namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using log4net;
	using System.IO;
	using System.Xml;
	using System.Xml.Schema;
	using System.Web;

	internal class AdrierXmlValidator
	{
		ILog _log = LogManager.GetLogger(typeof(AdrierXmlValidator));

		string _baseXsdPath;

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
			ValidateXml(stream, TipoValidazioneParix.ListaImprese);
		}

		public void ValidaDettaglioImpresa(Stream stream)
		{
			ValidateXml(stream, TipoValidazioneParix.DettaglioImpresaRidotto);
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

			}
			catch (Exception ex)
			{
				_log.ErrorFormat("AnagrafeSearcherParixBase->ValidateXml->errore durante la validazione sul file {0}: {1}", fileName, ex.ToString());
				throw new Exception("Errore generato durante la validazione. " + ex.Message);
			}
		}
	}
}
