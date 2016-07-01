using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
	public class ConfigurazioneAreaRiservataDto
	{
		public class UrlWebserviceRicercaAnagrafiche
		{
			[XmlElement(Order=0)]
			public string PersoneFisiche { get; set; }
			[XmlElement(Order = 1)]
			public string PersoneGiuridiche { get; set; }
		}

		[XmlElement(Order = 0)]
		public string StatoInizialeIstanza { get; set; }
		[XmlElement(Order = 1)]
		public string IntestazioneDettaglioVisura { get; set; }
		[XmlElement(Order = 2)]
		public string MessaggioInvioFallito { get; set; }
		[XmlElement(Order = 3)]
		public string MessaggioInvioPec { get; set; }
		[XmlElement(Order = 4)]
		public string MessaggioRegistrazioneCompletata { get; set; }
		[XmlElement(Order = 5)]
		public string NomeParametroUrlLogin { get; set; }
		[XmlElement(Order = 6)]
		public int? CodiceOggettoInvioConFirma { get; set; }
		[XmlElement(Order = 7)]
		public int? CodiceOggettoInvioConSottoscrizione { get; set; }
		[XmlElement(Order = 8)]
		public int? CodiceOggettoWorkflow { get; set; }
		[XmlElement(Order = 9)]
		public int? CodiceOggettoMenuXml { get; set; }
		[XmlElement(Order = 10)]
		public bool ImpostaAutomaticamenteTecnico { get; set; }
		[XmlElement(Order = 11)]
		public bool ImpostaAutomaticamenteRichiedente { get; set; }
		[XmlElement(Order = 12)]
		public int? CodiceNaturaScia { get; set; }
		[XmlElement(Order = 13)]
		public bool VerificaHashFilesFirmati { get; set; }
		[XmlElement(Order = 14)]
		public int? IdCampoDinamicoAttivitaAtecoPrevalente { get; set; }
		[XmlElement(Order = 15)]
		public string NomeConfigurazioneContenuti { get; set; }
		[XmlElement(Order = 16)]
		public string UrlApplicazioneFacct { get; set; }
		[XmlElement(Order = 17)]
		public UrlWebserviceRicercaAnagrafiche UrlWsRicercheAnagrafiche { get; set; }
		[XmlElement(Order = 18)]		
		public SchedaDinamicaCittadinoExtracomunitario SchedaDinamicaCittadiniExtracomunitari { get; set; }
		[XmlElement(Order = 19)]
		public ParametriRicercaVisuraDto ParametriRicercaScadenzario { get; set; }
		[XmlElement(Order = 20)]
		public ParametriRicercaVisuraDto ParametriRicercaVisuraTecnico { get; set; }
		[XmlElement(Order = 21)]
		public ParametriRicercaVisuraDto ParametriRicercaVisuraNonTecnico { get; set; }
		[XmlElement(Order = 22)]
		public ParametriRicercaVisuraDto ParametriRicercaVisuraFiltroRichiedente { get; set; }
		[XmlElement(Order = 23)]
		public string UrlPaginaIniziale{ get; set; }
		[XmlElement(Order = 24)]
		public ParametriVisuraMobileDto ParametriVisuraMobile { get; set; }
        [XmlElement(Order = 25)]
        public int? IdSchedaEstremiDocumento { get; set; }
		[XmlElement(Order = 26)]
		public string IntestazioneCertificatoInvio { get; set; }
		[XmlElement(Order = 27)]
		public int DimensioneMassimaAllegati { get; set; }
		[XmlElement(Order = 28)]
		public string WarningDimensioneMassimaAllegati { get; set; }
        [XmlElement(Order = 29)]
        public string DescrizioneDelegaATrasmettere { get; set; }

		public ConfigurazioneAreaRiservataDto()
		{
			this.ParametriRicercaScadenzario = new ParametriRicercaVisuraDto();
			this.ParametriRicercaVisuraFiltroRichiedente = new ParametriRicercaVisuraDto();
			this.ParametriRicercaVisuraTecnico = new ParametriRicercaVisuraDto();
			this.ParametriRicercaVisuraNonTecnico = new ParametriRicercaVisuraDto();
			this.ParametriVisuraMobile = new ParametriVisuraMobileDto();
		}


        
    }
}
