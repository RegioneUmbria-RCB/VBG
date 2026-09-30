using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Implementazioni.ADS.LeggiProtocollo
{
	[XmlRoot(ElementName = "DOC")]
	public class GetProtocolloResponse
	{
		[XmlElement(ElementName = "ID_DOCUMENTO")]
		public string IdDocumento{ get; set; }
		[XmlElement(ElementName = "IDRIF")]
		public string IdRif { get; set; }
		[XmlElement(ElementName = "ANNO")]
		public string Anno { get; set; }
		[XmlElement(ElementName = "NUMERO")]
		public string Numero { get; set; }
		[XmlElement(ElementName = "TIPO_REGISTRO")]
		public string TipoRegistro { get; set; }
		[XmlElement(ElementName = "DESCRIZIONE_TIPO_REGISTRO")]
		public string DescrizioneTipoRegistro { get; set; }
		[XmlElement(ElementName = "DATA")]
		public string Data { get; set; }
		[XmlElement(ElementName = "OGGETTO")]
		public string Oggetto { get; set; }
		[XmlElement(ElementName = "DATA_DOCUMENTO")]
		public string DataDocumento { get; set; }
		[XmlElement(ElementName = "NUMERO_DOCUMENTO")]
		public string NumeroDocumento { get; set; }
		[XmlElement(ElementName = "CLASS_COD")]
		public string ClassCod { get; set; }
		[XmlElement(ElementName = "CLASS_DAL")]
		public string ClassDal { get; set; }
		[XmlElement(ElementName = "FASCICOLO_ANNO")]
		public string FascicoloAnno { get; set; }
		[XmlElement(ElementName = "FASCICOLO_NUMERO")]
		public string FascicoloNumero { get; set; }
		[XmlElement(ElementName = "RISERVATO")]
		public string Riservato { get; set; }
		[XmlElement(ElementName = "STATO_PR")]
		public string StatoPR { get; set; }
		[XmlElement(ElementName = "DOCUMENTO_TRAMITE")]
		public string DocumentoTramite { get; set; }
		[XmlElement(ElementName = "TIPO_DOCUMENTO")]
		public string TipoDocumento { get; set; }
		[XmlElement(ElementName = "DESCRIZIONE_TIPO_DOCUMENTO")]
		public string DescrizioneTipoDocumento { get; set; }
		[XmlElement(ElementName = "UNITA_ESIBENTE")]
		public string UnitaEsibente { get; set; }
		[XmlElement(ElementName = "UNITA_PROTOCOLLANTE")]
		public string UnitaProtocollante { get; set; }
		[XmlElement(ElementName = "UTENTE_PROTOCOLLANTE")]
		public string UtenteProtocollante { get; set; }
		[XmlElement(ElementName = "ANNULLATO")]
		public string Annullato { get; set; }
		[XmlElement(ElementName = "DATA_ANN")]
		public string DataAnn { get; set; }
		[XmlElement(ElementName = "UTENTE_ANN")]
		public string UtenteAnn { get; set; }
		[XmlElement(ElementName = "MODALITA")]
		public string Modalita { get; set; }
	}

	[XmlRoot(ElementName = "FILE")]
	public class File
	{
		[XmlElement(ElementName = "ID_OGGETTO_FILE")]
		public string IdOggettoFile { get; set; }
		[XmlElement(ElementName = "ID_DOCUMENTO")]
		public string IdDocumento { get; set; }
		[XmlElement(ElementName = "FILENAME")]
		public string FileName { get; set; }
	}

	[XmlRoot(ElementName = "FILE_PRINCIPALE")]
	public class FilePrincipale
	{
		[XmlElement(ElementName = "FILE")]
		public File File { get; set; }
	}

	[XmlRoot(ElementName = "FILE_ALLEGATI")]
	public class FileAllegati
	{
		[XmlElement(ElementName = "FILE")]
		public File File { get; set; }
	}

	[XmlRoot(ElementName = "ALLEGATO")]
	public class Allegato
	{
		[XmlElement(ElementName = "ID_DOCUMENTO")]
		public string IdDocumento { get; set; }
		[XmlElement(ElementName = "DESC_TIPO_ALLEGATO")]
		public string DescTipoAllegato { get; set; }
		[XmlElement(ElementName = "TIPO_ALLEGATO")]
		public string TipoAllegato { get; set; }
		[XmlElement(ElementName = "DESCRIZIONE")]
		public string Descrizione { get; set; }
		[XmlElement(ElementName = "IDRIF")]
		public string IdRif { get; set; }
		[XmlElement(ElementName = "NUMERO_PAG")]
		public string NumeroPag { get; set; }
		[XmlElement(ElementName = "QUANTITA")]
		public string Quantita { get; set; }
		[XmlElement(ElementName = "RISERVATO")]
		public string Riservato { get; set; }
		[XmlElement(ElementName = "TITOLO_DOCUMENTO")]
		public string TitoloDocumento { get; set; }
		[XmlElement(ElementName = "FILE_ALLEGATI")]
		public FileAllegati FileAllegati { get; set; }
	}

	[XmlRoot(ElementName = "ALLEGATI")]
	public class Allegati
	{
		[XmlElement(ElementName = "ALLEGATO")]
		public List<Allegato> Allegato { get; set; }
	}

	[XmlRoot(ElementName = "SMISTAMENTO")]
	public class Smistamento
	{
		[XmlElement(ElementName = "ID_DOCUMENTO")]
		public string IdDocumento { get; set; }
		[XmlElement(ElementName = "DES_UFFICIO_SMISTAMENTO")]
		public string DesUfficioSmistamento { get; set; }
		[XmlElement(ElementName = "DES_UFFICIO_TRASMISSIONE")]
		public string DesUfficioTrasmissione { get; set; }
		[XmlElement(ElementName = "IDRIF")]
		public string IdRif { get; set; }
		[XmlElement(ElementName = "SMISTAMENTO_DAL")]
		public string SmistamentoDal { get; set; }
		[XmlElement(ElementName = "STATO_SMISTAMENTO")]
		public string StatoSmistamento { get; set; }
		[XmlElement(ElementName = "TIPO_SMISTAMENTO")]
		public string TipoSmistamento { get; set; }
		[XmlElement(ElementName = "UFFICIO_SMISTAMENTO")]
		public string UfficioSmistamento { get; set; }
		[XmlElement(ElementName = "UFFICIO_TRASMISSIONE")]
		public string UfficioTrasmissione { get; set; }
		[XmlElement(ElementName = "UTENTE_TRASMISSIONE")]
		public string UtenteTrasnissione { get; set; }
	}

	[XmlRoot(ElementName = "SMISTAMENTI")]
	public class Smistamenti
	{
		[XmlElement(ElementName = "SMISTAMENTO")]
		public List<Smistamento> Smistamento { get; set; }
	}

	[XmlRoot(ElementName = "RAPPORTO")]
	public class Rapporto
	{
		[XmlElement(ElementName = "ID_DOCUMENTO")]
		public string IdDocumento { get; set; }
		[XmlElement(ElementName = "COGNOME_NOME")]
		public string CognomeNome { get; set; }
		[XmlElement(ElementName = "CODICE_FISCALE")]
		public string CodiceFiscale { get; set; }
		[XmlElement(ElementName = "EMAIL")]
		public string Email { get; set; }
		[XmlElement(ElementName = "DENOMINAZIONE")]
		public string Denominazione { get; set; }
		[XmlElement(ElementName = "INDIRIZZO")]
		public string Indirizzo { get; set; }
		[XmlElement(ElementName = "CAP")]
		public string Cap { get; set; }
		[XmlElement(ElementName = "IDRIF")]
		public string IdRif { get; set; }
		[XmlElement(ElementName = "CONOSCENZA")]
		public string Conoscenza { get; set; }
		[XmlElement(ElementName = "COD_AMM")]
		public string CodAmm { get; set; }
		[XmlElement(ElementName = "COD_AOO")]
		public string CodAoo { get; set; }
		[XmlElement(ElementName = "COD_UO")]
		public string CodUo { get; set; }
		[XmlElement(ElementName = "DESCRIZIONE_AMM")]
		public string DescrizioneAmm { get; set; }
		[XmlElement(ElementName = "DESCRIZIONE_AOO")]
		public string DescrizioneAoo { get; set; }
	}

	[XmlRoot(ElementName = "RAPPORTI")]
	public class Rapporti
	{
		[XmlElement(ElementName = "RAPPORTO")]
		public List<Rapporto> Rapporto { get; set; }
	}

	[XmlRoot(ElementName = "PROTOCOLLO")]
	public class Protocollo
	{
		[XmlElement(ElementName = "DOC")]
		public GetProtocolloResponse Doc { get; set; }
		[XmlElement(ElementName = "FILE_PRINCIPALE")]
		public FilePrincipale FilePrincipale { get; set; }
		[XmlElement(ElementName = "ALLEGATI")]
		public Allegati Allegati { get; set; }
		[XmlElement(ElementName = "SMISTAMENTI")]
		public Smistamenti Smistamenti { get; set; }
		[XmlElement(ElementName = "RAPPORTI")]
		public Rapporti Rapporti { get; set; }
	}
}
