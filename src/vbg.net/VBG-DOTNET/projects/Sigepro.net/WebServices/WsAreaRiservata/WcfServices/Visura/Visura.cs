using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura
{
    [DataContract]
    public class Visura
    {
        [DataMember]
        public string IdComune { get; set; } = string.Empty;
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Software { get; set; } = string.Empty;
        [DataMember]
        public DateTime? Data { get; set; }
        [DataMember]
        public DateTime? DataProtocollo { get; set; }
        [DataMember]
        public string NumeroIstanza { get; set; } = string.Empty;
        [DataMember]
        public string NumeroProtocollo { get; set; } = string.Empty;
        [DataMember]
        public string Lavori { get; set; } = string.Empty;
        [DataMember]
        public string PosizioneArchivio { get; set; } = string.Empty;
        [DataMember]
        public string Uuid { get; set; } = string.Empty;
        [DataMember]
        public Comuni ComuneIstanza { get; set; }
        [DataMember]
        public AlberoProcedimenti Intervento { get; set; }
        [DataMember]
        public Responsabili Istruttore { get; set; }
        [DataMember]
        public Responsabili Operatore { get; set; }
        [DataMember]
        public Responsabili ResponsabileProc { get; set; }
        [DataMember]
        public StatiIstanza Stato { get; set; }
        [DataMember]
        public List<Movimenti> Movimenti { get; set; }
        [DataMember]
        public Anagrafe Richiedente { get; set; }
        [DataMember]
        public Anagrafe AziendaRichiedente { get; set; }
        [DataMember]
        public TipiSoggetto TipoSoggetto { get; set; }
        [DataMember]
        public Anagrafe Professionista { get; set; }
        [DataMember]
        public List<IstanzeRichiedenti> IstanzeRichiedenti { get; set; }
        [DataMember]
        public List<IstanzeStradario> IstanzeStradario { get; set; }
        [DataMember]
        public List<IstanzeMappali> IstanzeMappali { get; set; }
        [DataMember]
        public List<DocumentiIstanza> DocumentiIstanza { get; set; }
        [DataMember]
        public List<IstanzeProcedimenti> IstanzeEndoProcedimenti { get; set; }
        [DataMember]
        public List<IstanzeOneri> IstanzeOneri { get; set; }
        [DataMember]
        public List<Autorizzazioni> Autorizzazioni { get; set; }
        [DataMember]
        public List<IstanzeAllegati> IstanzeAllegati { get; set; }
    }

    [DataContract]
    public class Comuni
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Nome { get; set; } = string.Empty;
    }

    [DataContract]
    public class AlberoProcedimenti
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
        [DataMember]
        public string DescrizioneCompleta { get; set; } = string.Empty;
    }

    [DataContract]
    public class Responsabili
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Nome { get; set; } = string.Empty;
    }

    [DataContract]
    public class StatiIstanza
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Stato { get; set; } = string.Empty;
    }

    [DataContract]
    public class Movimenti
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Movimento { get; set; } = string.Empty;
        [DataMember]
        public DateTime? Data { get; set; }
        [DataMember]
        public bool Pubblicare { get; set; }
        [DataMember]
        public bool PubblicareParere { get; set; }
        [DataMember]
        public string Parere { get; set; } = string.Empty;
        [DataMember]
        public string NumeroProtocollo { get; set; } = string.Empty;
        [DataMember]
        public DateTime? DataProtocollo { get; set; }
        [DataMember]
        public string UuidPraticaCollegata { get; set; } = string.Empty;
        [DataMember]
        public List<MovimentiAllegati> MovimentiAllegati { get; set; }
    }

    [DataContract]
    public class MovimentiAllegati
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
        [DataMember]
        public bool Pubblicare { get; set; }
        [DataMember]
        public string CodiceOggetto { get; set; } = string.Empty;
        [DataMember]
        public int? ControlloOK { get; set; }
        [DataMember]
        public MovimentiAllegatiOggetti Oggetto { get; set; }
    }

    [DataContract]
    public class MovimentiAllegatiOggetti
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string IdComune { get; set; } = string.Empty;
        [DataMember]
        public string NomeFile { get; set; } = string.Empty;
        [DataMember]
        public List<OggettiMetadati> Metadati { get; set; } = new List<OggettiMetadati>();
    }

    [DataContract]
    public class Anagrafe
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Nominativo { get; set; } = string.Empty;
        [DataMember]
        public string Nome { get; set; } = string.Empty;
        [DataMember]
        public string CodiceFiscale { get; set; } = string.Empty;
        [DataMember]
        public string PartitaIva { get; set; } = string.Empty;
    }

    [DataContract]
    public class TipiSoggetto
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
        [DataMember]
        public int? FlagLivelliVisuraPratica { get; set; }
    }

    [DataContract]
    public class IstanzeRichiedenti
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public Anagrafe Richiedente { get; set; }
        [DataMember]
        public TipiSoggetto TipoSoggetto { get; set; }
        [DataMember]
        public Anagrafe AnagrafeCollegata { get; set; }
        [DataMember]
        public Anagrafe Procuratore { get; set; }
    }

    [DataContract]
    public class IstanzeStradario
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Civico { get; set; } = string.Empty;
        [DataMember]
        public string Esponente { get; set; } = string.Empty;
        [DataMember]
        public string Colore { get; set; } = string.Empty;
        [DataMember]
        public string Scala { get; set; } = string.Empty;
        [DataMember]
        public string Piano { get; set; } = string.Empty;
        [DataMember]
        public string Interno { get; set; } = string.Empty;
        [DataMember]
        public string EsponenteInterno { get; set; } = string.Empty;
        [DataMember]
        public string Fabbricato { get; set; } = string.Empty;
        [DataMember]
        public string Km { get; set; } = string.Empty;
        [DataMember]
        public Stradario Stradario { get; set; }
    }

    [DataContract]
    public class Stradario
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Prefisso { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
        [DataMember]
        public string LocalitaFrazione { get; set; } = string.Empty;
    }

    [DataContract]
    public class IstanzeMappali
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public Catasto Catasto { get; set; }
        [DataMember]
        public string Foglio { get; set; } = string.Empty;
        [DataMember]
        public string Particella { get; set; } = string.Empty;
        [DataMember]
        public string Sub { get; set; } = string.Empty;
    }

    [DataContract]
    public class Catasto
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
    }

    [DataContract]
    public class DocumentiIstanza
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string IdComune { get; set; } = string.Empty;
        [DataMember]
        public string CodiceOggetto { get; set; } = string.Empty;
        [DataMember]
        public DateTime? Data { get; set; }
        [DataMember]
        public string Documento { get; set; } = string.Empty;
        [DataMember]
        public int? ControlloOk { get; set; }
        [DataMember]
        public DocumentiIstanzaOggetti Oggetto { get; set; }
    }

    [DataContract]
    public class DocumentiIstanzaOggetti
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string NomeFile { get; set; } = string.Empty;
        [DataMember]
        public List<OggettiMetadati> Metadati { get; set; }
    }

    [DataContract]
    public class OggettiMetadati
    {
        [DataMember]
        public string Idcomune { get; set; } = string.Empty;
        [DataMember]
        public int? Codiceoggetto { get; set; }
        [DataMember]
        public string Chiave { get; set; } = string.Empty;
        [DataMember]
        public string Valore { get; set; } = string.Empty;
    }

    [DataContract]
    public class IstanzeProcedimenti
    {
        [DataMember]
        public string IdComune { get; set; } = string.Empty;
        [DataMember]
        public string CodiceIstanza { get; set; } = string.Empty;
        [DataMember]
        public string CodiceInventario { get; set; } = string.Empty;
        [DataMember]
        public InventarioProcedimenti Endoprocedimento { get; set; }
        [DataMember]
        public List<IstanzeAllegati> IstanzeAllegati { get; set; }
    }

    [DataContract]
    public class InventarioProcedimenti
    {
        [DataMember]
        public string CodiceInventario { get; set; } = string.Empty;
        [DataMember]
        public string Procedimento { get; set; } = string.Empty;
        [DataMember]
        public List<Allegati> Allegati { get; set; }
    }

    [DataContract]
    public class Allegati
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Allegato { get; set; } = string.Empty;
        [DataMember]
        public int? Codiceoggetto { get; set; }
    }

    [DataContract]
    public class IstanzeAllegati
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string CodiceOggetto { get; set; } = string.Empty;
        [DataMember]
        public string AllegatoExtra { get; set; } = string.Empty;
        [DataMember]
        public string ControlloOk { get; set; } = string.Empty;
        [DataMember]
        public IstanzeAllegatiOggetto Oggetto { get; set; }
    }

    [DataContract]
    public class IstanzeAllegatiOggetto
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string IdComune { get; set; } = string.Empty;
        [DataMember]
        public string NomeFile { get; set; } = string.Empty;
        [DataMember]
        public List<OggettiMetadati> Metadati { get; set; }
    }

    [DataContract]
    public class IstanzeOneri
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public DateTime? DataPagamento { get; set; }
        [DataMember]
        public DateTime? DataScadenza { get; set; }
        [DataMember]
        public double? ImportoPagato { get; set; }
        [DataMember]
        public TipiCausaliOneri CausaleOnere { get; set; }
    }

    [DataContract]
    public class TipiCausaliOneri
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
    }

    [DataContract]
    public class Autorizzazioni
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public DateTime? Data { get; set; }
        [DataMember]
        public string Responsabile { get; set; } = string.Empty;
        [DataMember]
        public string Numero { get; set; } = string.Empty;
        [DataMember]
        public TipologiaRegistri Registro { get; set; }
    }

    [DataContract]
    public class TipologiaRegistri
    {
        [DataMember]
        public string Codice { get; set; } = string.Empty;
        [DataMember]
        public string Descrizione { get; set; } = string.Empty;
    }
}