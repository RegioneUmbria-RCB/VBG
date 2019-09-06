using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "DocumentoOut", IsNullable = false)]
    public class DocumentoOutXml
    {
        [XmlElement("IdDocumento")]
        public string _idDocumento { get; set; }

        [XmlIgnore()]
        public int IdDocumento
        {
            get
            {
                if (!String.IsNullOrEmpty(this._idDocumento))
                {
                    return Convert.ToInt32(this._idDocumento);
                }

                return 0;
            }
        }

        [XmlElement("AnnoProtocollo")]
        public string _annoProtocollo { get; set; }

        [XmlIgnore()]
        public short AnnoProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoProtocollo))
                {
                    return Convert.ToInt16(this._annoProtocollo);
                }

                return 0;
            }
        }

        [XmlElement("NumeroProtocollo")]
        public string _numeroProtocollo { get; set; }

        [XmlIgnore()]
        public int NumeroProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._numeroProtocollo))
                {
                    return Convert.ToInt32(this._numeroProtocollo);
                }

                return 0;
            }
        }

        [XmlElement("DataProtocollo")]
        public string _dataProtocollo { get; set; }

        [XmlIgnore()]
        public DateTime? DataProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._dataProtocollo))
                {
                    return DateTime.Parse(this._dataProtocollo);
                }

                return null;
            }
        }


        public string Oggetto { get; set; }

        public string OggettoBilingue { get; set; }

        public string Origine { get; set; }

        public string Classifica { get; set; }

        public string Classifica_Descrizione { get; set; }

        public string TipoDocumento { get; set; }

        public string TipoDocumento_Descrizione { get; set; }

        public string MittenteInterno { get; set; }

        public string MittenteInterno_Descrizione { get; set; }

        [XmlArrayItemAttribute("MittenteDestinatario", IsNullable = false)]
        public MittenteDestinatarioOut[] MittentiDestinatari { get; set; }

        [XmlArrayItemAttribute("AltroFascicolo", IsNullable = false)]
        public AltroFascicoloOut[] AltriFascicoli { get; set; }

        public string DataDocumento { get; set; }

        public string NumeroDocumento { get; set; }

        public string InCaricoA { get; set; }

        public string InCaricoA_Descrizione { get; set; }

        public string AnnoNumeroData { get; set; }

        [XmlElement("AnnoPratica")]
        public string _annoPratica { get; set; }

        public short? AnnoPratica
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoPratica))
                {
                    return short.Parse(this._annoPratica);
                }

                return null;
            }
        }

        public string NumeroPratica { get; set; }

        public string AnnoNumeroPratica { get; set; }

        public string LivelloDiSicurezza { get; set; }

        public string DataEvidenza { get; set; }

        public string DocAllegati { get; set; }

        public bool DocumentoRiservato { get; set; }

        public string IterAttivo { get; set; }

        public string DataDiCarico { get; set; }

        public string UtenteDiInserimento { get; set; }

        [XmlElement("DataInserimento")]
        public string _dataInserimento { get; set; }

        [XmlIgnore]
        public DateTime? DataInserimento
        {
            get
            {
                if (!String.IsNullOrEmpty(this._dataInserimento))
                {
                    return DateTime.Parse(this._dataInserimento);
                }
                return null;
            }
        }

        public string Messaggio { get; set; }

        [XmlArrayItemAttribute("Allegato", IsNullable = false)]
        public AllegatoOut[] Allegati { get; set; }

        [XmlArrayItemAttribute("Impegno", IsNullable = false)]
        public ImpegnoOut[] Impegni { get; set; }

        [XmlArrayItemAttribute("Accertamento", IsNullable = false)]
        public AccertamentoOut[] Accertamenti { get; set; }

        [XmlArrayItemAttribute("CdC", IsNullable = false)]
        public CentriDiCostoOut[] CentriDiCosto { get; set; }

        [XmlArrayItemAttribute("Registro", IsNullable = false)]
        public RegistroAssegnatoOut[] Registri { get; set; }

        public InteropOut Interop { get; set; }

        public RispostaAlProtocolloOut RispostaAlProtocollo { get; set; }

        [XmlArrayItemAttribute("ProtocolloGenerato", IsNullable = false)]
        public ProtocolloGeneratoOut[] ProtocolliGenerati { get; set; }

        [XmlArrayItemAttribute("Corrispondente", IsNullable = false)]
        public CorrispondenteOut[] Corrispondenti { get; set; }

        public string Errore { get; set; }

        [XmlElement("IdPratica")]
        public string _idPratica { get; set; }

        [XmlIgnore]
        public int IdPratica
        {
            get
            {
                if (!String.IsNullOrEmpty(this._idPratica))
                {
                    return int.Parse(this._idPratica);
                }
                return 0;
            }
        }

        public string DataInizioPubblicazione { get; set; }

        public string DataFinePubblicazione { get; set; }

        [XmlArrayItemAttribute(IsNullable = false)]
        public TabellaUtente[] DatiUtente { get; set; }
    }

    public partial class MittenteDestinatarioOut
    {
        public int IdSoggetto { get; set; }

        public string CognomeNome { get; set; }

        public string PartitaIVA { get; set; }

        public string ChiaveAlternativa { get; set; }
    }

    public partial class AltroFascicoloOut
    {
        public short AnnoAltroFascicolo { get; set; }
        public string NumeroAltroFascicolo { get; set; }
        public string AnnoNumeroAltroFascicolo { get; set; }
    }

    public partial class AllegatoOut
    {
        [XmlElement("Serial")]
        public string _serial { get; set; }

        [XmlIgnore]
        public int Serial
        {
            get
            {
                if (!String.IsNullOrEmpty(this._serial))
                {
                    return int.Parse(this._serial);
                }
                return 0;
            }
        }

        public string TipoFile { get; set; }

        public string ContentType { get; set; }

        [XmlElementAttribute(DataType = "base64Binary")]
        public byte[] Image { get; set; }

        public string Commento { get; set; }

        [XmlElement("IDBase")]
        public string _idBase { get; set; }

        [XmlIgnore]
        public int IDBase
        {
            get
            {
                if (!String.IsNullOrEmpty(this._idBase))
                {
                    return int.Parse(this._idBase);
                }
                return 0;
            }
        }

        [XmlElement("Versione")]
        public string _versione { get; set; }

        [XmlIgnore]
        public short Versione
        {
            get
            {
                if (!String.IsNullOrEmpty(this._versione))
                {
                    return short.Parse(this._versione);
                }
                return 0;
            }
        }

        public string TipoAllegato { get; set; }

        public string Schema { get; set; }

        public string SottoEstensione { get; set; }

        public string Firmato { get; set; }

        public string NomeAllegato { get; set; }

        public string Principale { get; set; }

        public string Pubblicato { get; set; }

        public string CommentoBilingue { get; set; }

        public string URI { get; set; }

        public string UriImportato { get; set; }

        public string Size { get; set; }
    }

    public partial class ImpegnoOut
    {
        [XmlElement("AnnoImpegno")]
        public string _annoImpegno { get; set; }

        [XmlIgnore]
        public int AnnoImpegno
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoImpegno))
                {
                    return short.Parse(this._annoImpegno);
                }
                return 0;
            }
        }

        public string NumeroImpegno { get; set; }

        public string CapitoloImpegno { get; set; }

        public string ArticoloImpegno { get; set; }

        public string CodSiopeImpegno { get; set; }

        public double ImportoImpegno { get; set; }

        public double SoggettoImpegno { get; set; }
    }

    public partial class AccertamentoOut
    {
        [XmlElement("AnnoAccertamento")]
        public string _annoAccertamento { get; set; }

        [XmlIgnore]
        public int AnnoAccertamento
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoAccertamento))
                {
                    return int.Parse(this._annoAccertamento);
                }
                return 0;
            }
        }

        public string NumeroAccertamento { get; set; }

        public string CapitoloAccertamento { get; set; }

        public string ArticoloAccertamento { get; set; }

        public string CodSiopeAccertamento { get; set; }

        [XmlElement("ImportoAccertamento")]
        public string _importoAccertamento { get; set; }

        [XmlIgnore]
        public double ImportoAccertamento
        {
            get
            {
                if (!String.IsNullOrEmpty(this._importoAccertamento))
                {
                    return double.Parse(this._importoAccertamento);
                }
                return 0;
            }
        }

        [XmlElement("SoggettoAccertamento")]
        public string _soggettoAccertamento { get; set; }

        [XmlIgnore]
        public double SoggettoAccertamento
        {
            get
            {
                if (!String.IsNullOrEmpty(this._soggettoAccertamento))
                {
                    return double.Parse(this._soggettoAccertamento);
                }
                return 0;
            }
        }
    }

    public partial class CentriDiCostoOut
    {
        public string Tipo { get; set; }

        public string Voce { get; set; }

        public string CdC_provento { get; set; }

        public string Propon_ammor { get; set; }

        [XmlElement("Importo")]
        public string _importo { get; set; }

        [XmlIgnore]
        public double Importo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._importo))
                {
                    return double.Parse(this._importo);
                }
                return 0;
            }
        }
    }

    public partial class RegistroAssegnatoOut
    {
        public string TipoRegistro { get; set; }

        [XmlElement("AnnoRegistro")]
        public string _annoRegistro { get; set; }

        [XmlIgnore]
        public int AnnoRegistro
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoRegistro))
                {
                    return int.Parse(this._annoRegistro);
                }
                return 0;
            }
        }

        [XmlElement("NumeroRegistro")]
        public string _numeroRegistro { get; set; }

        [XmlIgnore]
        public int NumeroRegistro
        {
            get
            {
                if (!String.IsNullOrEmpty(this._numeroRegistro))
                {
                    return int.Parse(this._numeroRegistro);
                }
                return 0;
            }
        }

        [XmlElement("DataRegistro")]
        public string _dataRegistro { get; set; }

        [XmlIgnore]
        public DateTime? DataRegistro
        {
            get
            {
                if (!String.IsNullOrEmpty(this._dataRegistro))
                {
                    return DateTime.Parse(this._dataRegistro);
                }
                return null;
            }
        }
    }

    public partial class InteropOut
    {
        public string CodiceAmministrazione { get; set; }

        public string Denominazione { get; set; }

        public string CodiceAOO { get; set; }

        public string AOO { get; set; }

        public string Indirizzo { get; set; }

        public string IndirizzoTelematico { get; set; }

        public string Localizzazione { get; set; }

        public string Riservato { get; set; }
    }

    public partial class RispostaAlProtocolloOut
    {
        public string NumeroRegistrazioneRP { get; set; }

        public string DataRegistrazioneRP { get; set; }

        public string TipoRP { get; set; }
    }

    public partial class ProtocolloGeneratoOut
    {
        public string NumeroRegistrazionePG { get; set; }

        public string DataRegistrazionePG { get; set; }

        public string TipoPG { get; set; }
    }

    public partial class CorrispondenteOut
    {
        [XmlElement("IdSoggetto")]
        public string _idSoggetto { get; set; }

        [XmlIgnore]
        public int IdSoggetto
        {
            get
            {
                if (!String.IsNullOrEmpty(this._idSoggetto))
                {
                    return int.Parse(this._idSoggetto);
                }
                return 0;
            }
        }

        public string Denominazione { get; set; }

        public bool FlagAmministrazione { get; set; }

        public string CodiceAmministrazione { get; set; }

        public string AOO { get; set; }

        public string CodiceAOO { get; set; }

        public string UnitaOrganizzativa { get; set; }

        public string NumeroRegistrazione { get; set; }

        [XmlElement("DataRegistrazione")]
        public string _dataRegistrazione { get; set; }

        [XmlIgnore]
        public DateTime? DataRegistrazione
        {
            get
            {
                if (!String.IsNullOrEmpty(this._dataRegistrazione))
                {
                    return DateTime.Parse(this._dataRegistrazione);
                }
                return null;
            }
        }
    }

    public partial class TabellaUtente
    {
        public string NomeTabella { get; set; }

        [XmlArrayItemAttribute("Riga", IsNullable = false)]
        public RecordUtente[] Righe { get; set; }
    }

    public partial class RecordUtente
    {
        [XmlElement("Progressivo")]
        public string _progressivo { get; set; }

        [XmlIgnore]
        public int Progressivo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._progressivo))
                {
                    return int.Parse(this._progressivo);
                }
                return 0;
            }
        }

        [XmlArrayItemAttribute("Campo", IsNullable = false)]
        public CampoUtente[] Campi { get; set; }
    }

    public partial class CampoUtente
    {
        public string NomeCampo { get; set; }

        public string TipoCampo { get; set; }

        public string ValoreCampo { get; set; }
    }
}
