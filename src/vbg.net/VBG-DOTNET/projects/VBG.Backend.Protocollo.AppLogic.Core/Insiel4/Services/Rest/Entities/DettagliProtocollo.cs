
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DettagliProtocollo
    {
        [JsonPropertyName("clasCodClas")]
        public string ClasCodClas { get; set; }

        [JsonPropertyName("clasLiv1")]
        public string ClasLiv1 { get; set; }

        [JsonPropertyName("clasLiv2")]
        public string ClasLiv2 { get; set; }

        [JsonPropertyName("clasLiv3")]
        public string ClasLiv3 { get; set; }

        [JsonPropertyName("clasLiv4")]
        public string ClasLiv4 { get; set; }

        [JsonPropertyName("clasLiv5")]
        public string ClasLiv5 { get; set; }

        [JsonPropertyName("clasLiv6")]
        public string ClasLiv6 { get; set; }

        [JsonPropertyName("clasLiv7")]
        public string ClasLiv7 { get; set; }

        [JsonPropertyName("clasLiv8")]
        public string ClasLiv8 { get; set; }

        [JsonPropertyName("corrCodTipoDoc")]
        public string CorrCodTipoDoc { get; set; }

        [JsonPropertyName("corrDataRiferim")]
        public DateTime? CorrDataRiferim { get; set; }

        [JsonPropertyName("corrDescAna")]
        public string CorrDescAna { get; set; }

        //    [JsonPropertyName("corrNumRiferim")]
        //    public string CorrNumRiferim { get; set; }

        [JsonPropertyName("dest2ProgInteressa")]
        public int Dest2ProgInteressa { get; set; }

        [JsonPropertyName("docAnnoDoc")]
        public short? DocAnnoDoc { get; set; }

        [JsonPropertyName("docCntMovi")]
        public int? DocCntMovi { get; set; }

        [JsonPropertyName("docCodCatDoc")]
        public string DocCodCatDoc { get; set; }

        [JsonPropertyName("docCodTipoDoc")]
        public string DocCodTipoDoc { get; set; }

        [JsonPropertyName("docDataDoc")]
        public DateTime? DocDataDoc { get; set; }

        [JsonPropertyName("docDataFirma")]
        public DateTime? DocDataFirma { get; set; }

        [JsonPropertyName("docDescOgge")]
        public string DocDescOgge { get; set; }

        [JsonPropertyName("docFlIop")]
        public short? DocFlIop { get; set; }

        [JsonPropertyName("docImporto")]
        public decimal? DocImporto { get; set; }

        [JsonPropertyName("docNumDoc")]
        public string DocNumDoc { get; set; }

        [JsonPropertyName("docNumDocN")]
        public int? DocNumDocN { get; set; }

        [JsonPropertyName("docSubnDoc")]
        public short? DocSubnDoc { get; set; }

        [JsonPropertyName("docTotAlle")]
        public int? DocTotAlle { get; set; }

        [JsonPropertyName("docValuta")]
        public string DocValuta { get; set; }

        [JsonPropertyName("docVariAlle")]
        public short? DocVariAlle { get; set; }

        [JsonPropertyName("eccezioni")]
        public decimal? Eccezioni { get; set; }

        //    [JsonPropertyName("estSigla")]
        //    public string EstSigla { get; set; }

        //    [JsonPropertyName("fileEstDocProgDoc")]
        //    public long? FileEstDocProgDoc { get; set; }

        [JsonPropertyName("fileEstOleDocProgDoc")]
        public long? FileEstOleDocProgDoc { get; set; }

        [JsonPropertyName("fileEstOleProgDoc")]
        public long? FileEstOleProgDoc { get; set; }

        //    [JsonPropertyName("fileEstProgDoc")]
        //    public long? FileEstProgDoc { get; set; }

        [JsonPropertyName("flCopiaBlob")]
        public short? FlCopiaBlob { get; set; }

        [JsonPropertyName("flPerConoscenza")]
        public short? FlPerConoscenza { get; set; }

        //    [JsonPropertyName("flScarto")]
        //    public short? FlScarto { get; set; }

        //    [JsonPropertyName("legato2ProgLegato")]
        //    public int? Legato2ProgLegato { get; set; }

        //    [JsonPropertyName("legato2ProgLegatoSpecified")]
        //    public bool Legato2ProgLegatoSpeified { get; set; }

        [JsonPropertyName("logMoviVersione")]
        public int? LogMoviVersione { get; set; }

        [JsonPropertyName("minimoStato")]
        public decimal? MinimoStato { get; set; }

        [JsonPropertyName("mitt2ProgInteressa")]
        public int? Mitt2ProgInteressa { get; set; }

        //    [JsonPropertyName("opeSigla")]
        //    public string OpeSigla { get; set; }

        [JsonPropertyName("pratAnnoProt")]
        public short? PratAnnoProt { get; set; }

        [JsonPropertyName("pratCodAna")]
        public string PratCodAna { get; set; }

        [JsonPropertyName("pratCodReg")]
        public string PratCodReg { get; set; }

        [JsonPropertyName("pratDescOgge")]
        public string PratDescOgge { get; set; }

        //    [JsonPropertyName("pratNote")]
        //    public string PratNote { get; set; }

        [JsonPropertyName("pratNumProt")]
        public int? PratNumProt { get; set; }

        [JsonPropertyName("pratPraticaAc")]
        public short? PratPraticaAc { get; set; }

        //    [JsonPropertyName("pratPraticaDataAc")]
        //    public DateTime? PratPraticaDataAc { get; set; }

        [JsonPropertyName("pratProgDoc")]
        public long? PratProgDoc { get; set; }

        [JsonPropertyName("pratProgInPrat")]
        public int? PratProgInPrat { get; set; }

        [JsonPropertyName("pratProgLegato")]
        public int? PratProgLegato { get; set; }

        [JsonPropertyName("pratSubnProt")]
        public string PratSubnProt { get; set; }

        [JsonPropertyName("precedentePrimoProgDocP")]
        public long? PrecedentePrimoProgDocP { get; set; }

        [JsonPropertyName("precedenteProgDocP")]
        public long? PrecedenteProgDocP { get; set; }

        [JsonPropertyName("protoAnnoProt")]
        public short? ProtoAnnoProt { get; set; }

        [JsonPropertyName("protoApProt")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso ProtoApProt { get; set; }

        [JsonPropertyName("protoCodLoginAgg")]
        public string ProtoCodLoginAgg { get; set; }

        [JsonPropertyName("protoCodReg")]
        public string ProtoCodReg { get; set; }

        //    [JsonPropertyName("protoCollEmergenza")]
        //    public string ProtoCollEmergenza { get; set; }

        [JsonPropertyName("protoDataAp")]
        public DateTime? ProtoDataAp { get; set; }

        //    [JsonPropertyName("protoDataAtti")]
        //    public DateTime? ProtoDataAtti { get; set; }

        [JsonPropertyName("protoDataMovi")]
        public DateTime? ProtoDataMovi { get; set; }

        [JsonPropertyName("protoDataOraAgg")]
        public DateTime? ProtoDataOraAgg { get; set; }

        [JsonPropertyName("protoDescOgge")]
        public string ProtoDescOgge { get; set; }

        //    [JsonPropertyName("protoFlAtti")]
        //    public short? ProtoFlAtti { get; set; }

        //    [JsonPropertyName("protoFlImmDocumatic")]
        //    public short? ProtoFlImmDocumatic { get; set; }

        [JsonPropertyName("protoLivSegretezza")]
        public short? ProtoLivSegretezza { get; set; }

        //    [JsonPropertyName("protoNoteAtti")]
        //    public string ProtoNoteAtti { get; set; }

        //    [JsonPropertyName("protoNoteRiservate")]
        //    public string ProtoNoteRiservate { get; set; }

        [JsonPropertyName("protoNumProt")]
        public int? ProtoNumProt { get; set; }

        [JsonPropertyName("protoProgAnaUff")]
        public long? ProtoProgAnaUff { get; set; }

        [JsonPropertyName("protoProgDoc")]
        public long? ProtoProgDoc { get; set; }

        [JsonPropertyName("protoProgMovi")]
        public int? ProtoProgMovi { get; set; }

        [JsonPropertyName("protoStato")]
        public short? ProtoStato { get; set; }

        [JsonPropertyName("protoSubnProt")]
        public string ProtoSubnProt { get; set; }

        //    [JsonPropertyName("qualRegProgQualReg")]
        //    public int? QualRegProgQualReg { get; set; }

        [JsonPropertyName("regCodAna")]
        public string RegCodAna { get; set; }

        //    [JsonPropertyName("regCodAnaInterr")]
        //    public string RegCodAnaInterr { get; set; }

        [JsonPropertyName("regCodReg")]
        public string RegCodReg { get; set; }

        //    [JsonPropertyName("regCodRegInterr")]
        //    public string RegCodRegInterr { get; set; }

        [JsonPropertyName("regDescAna")]
        public string RegDescAna { get; set; }

        [JsonPropertyName("regDescReg")]
        public string RegDescReg { get; set; }

        //    [JsonPropertyName("regNoApProtInterr")]
        //    public short? RegNoApProtInterr { get; set; }

        [JsonPropertyName("regProgAna")]
        public long? RegProgAna { get; set; }

        //    [JsonPropertyName("ricevuteArrivate")]
        //    public decimal? RicevuteArrivate { get; set; }

        //    [JsonPropertyName("ricevuteRichieste")]
        //    public decimal? RicevuteRichieste { get; set; }

        [JsonPropertyName("tipoDocDescTipoDoc")]
        public string TipoDocDescTipoDoc { get; set; }

        [JsonPropertyName("uffFlNonVedo")]
        public short? UffFlNonVedo { get; set; }

        [JsonPropertyName("uffOpeCodAna")]
        public string UffOpeCodAna { get; set; }

        [JsonPropertyName("uffOpeDescAna")]
        public string UffOpeDescAna { get; set; }

        [JsonPropertyName("uffi2ProgInteressa")]
        public decimal? Uffi2ProgInteressa { get; set; }

        [JsonPropertyName("uffiDataRiferim")]
        public DateTime? UffiDataRiferim { get; set; }

        [JsonPropertyName("uffiDescAna")]
        public string UffiDescAna { get; set; }

        [JsonPropertyName("valuteSimbolo")]
        public string ValuteSimbolo { get; set; }

        [JsonPropertyName("immagini")]
        public bool Immagini { get; set; }

        [JsonPropertyName("documenti")]
        public bool Documenti { get; set; }

        [JsonPropertyName("dataOraProtocollazione")]
        public DateTime? DataOraProtocollazione { get; set; }

        [JsonPropertyName("codLoginProtocollazione")]
        public string CodLoginProtocollazione { get; set; }

        [JsonPropertyName("flInoltro")]
        public decimal? FlInoltro { get; set; }

        //    [JsonPropertyName("protoDataScadenza")]
        //    public DateTime? ProtoDataScadenza { get; set; }

        [JsonPropertyName("statoRicevute")]
        public decimal? StatoRicevute { get; set; }

        //    [JsonPropertyName("casellaMittInvioTelematico")]
        //    public string CasellaMittInvioTelematico { get; set; }

        [JsonPropertyName("pratNotePrat")]
        public string PratNotePrat { get; set; }

        [JsonPropertyName("isImmagini")]
        public bool IsImmagini { get; set; }

        [JsonPropertyName("isDocumenti")]
        public bool IsDocumenti { get; set; }

        [JsonPropertyName("corrNumRiferim")]
        public string CorrNumRiferim { get; set; }

        //[JsonPropertyName("infoGenerali")]
        //public DettagliProtocollo1 InfoGenerali;

        //[JsonPropertyName("mittenti")]
        //public Corrispondente[] Mittenti;

        //[JsonPropertyName("destinatari")]
        //public Destinatario1[] Destinatari;

        //[JsonPropertyName("uffici")]
        //public Corrispondente[] Uffici;

        //[JsonPropertyName("precedenti")]
        //public Precedente[] Precedenti;

        //[JsonPropertyName("classifiche")]
        //public ClassificaView[] Classifiche;

        //[JsonPropertyName("documenti")]
        //public DocumentoAllegato[] Documenti;

        //[JsonPropertyName("documentiInCarico")]
        //public DocumentoInCarico DocumentoInCarico;

        //[JsonPropertyName("pratiche")]
        //public Pratica[] Pratiche;

        //[JsonPropertyName("mnemonici")]
        //public MnemonicoView[] Mnemonici;

        //[JsonPropertyName("sigle")]
        //public Sigle Sigle;

        //[JsonPropertyName("allegati")]
        //public Allegato[] Allegati;

        //[JsonPropertyName("protocollo")]
        //public Entities.Protocollo[] Riprotocollazioni;

        //[JsonPropertyName("testoMsaggio")]
        //public string TestoMessaggio;


    }
}
