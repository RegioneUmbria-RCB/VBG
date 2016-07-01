using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.IO;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda
{
	public class RiepilogoDomanda
	{
		IOggettiService _oggettiService;
		FileConverterService _fileConverterService;
		SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;

		public RiepilogoDomanda(IOggettiService oggettiService, FileConverterService fileConverterService, SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService)
		{
			this._oggettiService						= oggettiService;
			this._fileConverterService					= fileConverterService;
			this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
		}

		public BinaryFile GeneraFileDaDomanda(DomandaOnline domanda, int idFileModello, GenerazioneRiepilogoSettings settings)
		{
			var oggetto = _oggettiService.GetById(idFileModello);

			if (oggetto == null)
				throw new ArgumentException("L'oggetto " + idFileModello + " non è stato trovato");

			var istanzaAdapter = new IstanzaSigeproAdapter(domanda.ReadInterface);

			istanzaAdapter.AggiungiPdfSchedeAListaAllegati = settings.AggiungiPdfSchedeAListaAllegati;

			string istanzaXml = istanzaAdapter.AdattaToString(domanda.DataKey.ToString());

			if (settings.DumpXml && HttpContext.Current != null)
			{
				var path = HttpContext.Current.Server.MapPath("~/Logs/");
				path = Path.Combine(path, String.Format("Dump{0}.xml", Guid.NewGuid()));
				using (var fs = File.Open(path, FileMode.CreateNew))
				{
					fs.Write(Encoding.UTF8.GetBytes(istanzaXml), 0, Encoding.UTF8.GetByteCount(istanzaXml));
				}
			}

			// Converto l'xsl della domanda in formato UTF-8
			var oggettoXsl = Encoding.UTF8.GetString(oggetto.FileContent);

			var risultatoTrasformazione = Encoding.UTF8.GetString(_fileConverterService.ApplicaTrasformazione(istanzaXml, oggettoXsl));

			// Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
			// per leggerle in formato html
			risultatoTrasformazione = _sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(domanda, risultatoTrasformazione);

			var fileDaConvertire = Encoding.UTF8.GetBytes(risultatoTrasformazione);

			if ( RimuoviBomDaRiepilogo())
			{
				var bytes = fileDaConvertire.Take(3).ToArray();

				if ( bytes[0] == 0xEF && 
					 bytes[1] == 0xBB &&
					 bytes[2] == 0xBF )
				{
					fileDaConvertire = fileDaConvertire.Skip(3).ToArray();
				}				
			}

			var modelloCompilato = _fileConverterService.Converti("modello." + oggetto.Estensione.ToUpper(), fileDaConvertire, settings.FormatoConversione);

			modelloCompilato.FileName = Path.GetFileNameWithoutExtension(oggetto.FileName) + Path.GetExtension(modelloCompilato.FileName);

			return modelloCompilato;
		}

		private bool RimuoviBomDaRiepilogo()
		{
			var val = ConfigurationManager.AppSettings["RimuoviBOMDaRiepilogo"];

			if(String.IsNullOrEmpty(val))
			{
				return true;
			}

			return val.ToUpperInvariant() == "TRUE";
		}
	}
}
