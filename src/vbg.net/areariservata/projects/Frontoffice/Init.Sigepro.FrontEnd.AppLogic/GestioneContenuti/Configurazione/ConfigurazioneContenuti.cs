using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti.Configurazione;
using System.Xml.Serialization;
using System.IO;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti
{
	[XmlRoot(ElementName="ConfigurazioneContenuti")]
	public class XmlConfigurazioneContenuti
	{
		public class DescrizioniEstesaStep
		{
			public string Titolo { get; set; }
			public string Descrizione { get; set; }
		}

		public class DescrizioniEsteseSteps
		{
			public DescrizioniEstesaStep Step1 { get; set; }
			public DescrizioniEstesaStep Step2 { get; set; }
			public DescrizioniEstesaStep Step3 { get; set; }

			public DescrizioniEsteseSteps()
			{
				this.Step1 = new DescrizioniEstesaStep { Titolo = "Ricerca", Descrizione="l'attività che svolge la tua impresa"};
				this.Step2 = new DescrizioniEstesaStep { Titolo = "Individua", Descrizione="l'intervento di tuo interesse"};
				this.Step3 = new DescrizioniEstesaStep { Titolo = "Consulta", Descrizione = "gli adempimenti necessari" };
			}
		}

		public class TestiFrameCentrale
		{
			public class Link
			{
				public string Intestazione { get; set; }
				public string Testo { get; set; }
				public string DescrizioneEstesa { get; set; }
				public bool Visibile { get; set; }
                public string Href { get; set; }
			}

			public string Titolo { get; set; }
			public string Descrizione { get; set; }
			public string IntestazioneLinks { get; set; }
			public Link Link1 { get; set; }
			public Link Link2 { get; set; }

			public TestiFrameCentrale()
			{
				this.Titolo = "BENVENUTO";
				this.Descrizione = @"Nell’area informativa dello sportello SUAP 
									 dove gli imprenditori possono avviare o sviluppare
									 un’impresa e ricevere tutti i chiarimenti sui requisiti, 
									 la modulistica e gli adempimenti necessari.";
				this.IntestazioneLinks = "Inizia ricercando l'attività che svolge la tua impresa";

				this.Link1 = new Link
				{
					Intestazione = "Ricerca per classificazione",
					Testo = "Attività",
					Visibile = true,
					DescrizioneEstesa = "<b>ATTIVITA' PRODUTTIVA</b><br />Scegli l'attività produttiva seguendo la classficazione adottata dallo sportello unico del comune",
				};
				this.Link2 = new Link
				{
					Intestazione = "Ricerca per classificazione",
					Testo = "ATECO",
					Visibile = false,
					DescrizioneEstesa = "<b>ATECO</b><br />Scegli l'attività seguendo la classificazione ISTAT adottata dal sistema camerale",
				};
			}
		}

		public string TestoFooter { get; set; }
		public string NomeCss { get; set; }
		public string LinkRegione { get; set; }
		public string TestoRegione { get; set; }
		public string TitoloPagina { get; set; }
		public DescrizioniEsteseSteps DescrizioniSteps { get; set; }
		public TestiFrameCentrale FrameCentrale { get; set; }

		public XmlConfigurazioneContenuti()
		{
			this.TitoloPagina = "Sportello Unico per le attività produttive SUAP";
			this.DescrizioniSteps = new DescrizioniEsteseSteps();
			this.FrameCentrale = new TestiFrameCentrale();
		}

		internal static XmlConfigurazioneContenuti LoadFrom(string nomeFileConfigurazione)
		{
			var basePath = HttpContext.Current.Server.MapPath("~/Contenuti/");

			var pathFileConfigurazione = Path.Combine(basePath, nomeFileConfigurazione + ".xml");

			if (!File.Exists(pathFileConfigurazione))
				throw new ConfigurationErrorsException("Impossibile trovare il file di configurazione dell'applicazione " + pathFileConfigurazione);

			XmlConfigurazioneContenuti cls;

			using (FileStream fs = File.OpenRead(pathFileConfigurazione))
			{
				XmlSerializer xs = new XmlSerializer(typeof(XmlConfigurazioneContenuti));
				cls = (XmlConfigurazioneContenuti)xs.Deserialize(fs);
			}

			if (!cls.NomeCss.ToUpperInvariant().EndsWith(".CSS"))
				cls.NomeCss += ".css";

			return cls;
		}
	}


	public class ConfigurazioneContenuti
	{
		public readonly BoxDatiComune DatiComune;
		public readonly XmlConfigurazioneContenuti Testi;

		public ConfigurazioneContenuti(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazione<ParametriAspetto> configurazioneAspetto)
		{
			this.Testi = XmlConfigurazioneContenuti.LoadFrom(configurazioneAspetto.Parametri.FileConfigurazioneContenuti);
			this.DatiComune = BoxDatiComune.Load(aliasSoftwareResolver.AliasComune, aliasSoftwareResolver.Software , this.Testi.LinkRegione, this.Testi.TestoRegione);
			
		}
	}
}
