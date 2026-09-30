using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DettagliProtocollo1
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
        public Nullable<DateTime> CorrDataRiferim { get; set; }

        [JsonPropertyName("corrDescAna")]
        public string CorrDescAna { get; set; }

        [JsonPropertyName("corrNumRiferim")]
        public string CorrNumRiferim { get; set; }

        [JsonPropertyName("dest2ProgInteressa")]
        public Nullable<int> Dest2ProgInteressa { get; set; }

        [JsonPropertyName("docAnnoDoc")]
        public Nullable<short> DocAnnoDoc { get; set; }

        [JsonPropertyName("docCntMovi")]
        public Nullable<int> DocCntMovi { get; set; }

        [JsonPropertyName("docCodCatDoc")]
        public string DocCodCatDoc { get; set; }

        [JsonPropertyName("docCodTipoDoc")]
        public string DocCodTipoDoc { get; set; }

        [JsonPropertyName("docDataDoc")]
        public Nullable<DateTime> DocDataDoc { get; set; }

        [JsonPropertyName("docDataFirma")]
        public Nullable<DateTime> DocDataFirma { get; set; }

        [JsonPropertyName("docDescOgge")]
        public string DocDescOgge { get; set; }

        [JsonPropertyName("docFlIop")]
        public Nullable<short> DocFlIop { get; set; }

        [JsonPropertyName("docImporto")]
        public Nullable<decimal> DocImporto { get; set; }

        [JsonPropertyName("docNumDoc")]
        public string DocNumDoc { get; set; }

        [JsonPropertyName("docNumDocN")]
        public Nullable<int> DocNumDocN { get; set; }

        [JsonPropertyName("docSubnDoc")]
        public Nullable<short> DocSubnDoc { get; set; }

        [JsonPropertyName("docTotAlle")]
        public Nullable<int> DocTotAlle { get; set; }

        [JsonPropertyName("docValuta")]
        public string DocValuta { get; set; }

        [JsonPropertyName("docVariAlle")]
        public Nullable<short> DocVariAlle { get; set; }

        [JsonPropertyName("eccezioni")]
        public Nullable<decimal> Eccezioni { get; set; }

        [JsonPropertyName("estSigla")]
        public string EstSigla { get; set; }

        [JsonPropertyName("fileEstDocProgDoc")]
        public Nullable<long> FileEstDocProgDoc { get; set; }

        [JsonPropertyName("fileEstOleDocProgDoc")]
        public Nullable<long> FileEstOleDocProgDoc { get; set; }

        [JsonPropertyName("fileEstOleProgDoc")]
        public Nullable<long> FileEstOleProgDoc { get; set; }

        [JsonPropertyName("fileEstProgDoc")]
        public Nullable<long> FileEstProgDoc { get; set; }

        [JsonPropertyName("flCopiaBlob")]
        public Nullable<short> FlCopiaBlob { get; set; }

        [JsonPropertyName("flPerConoscenza")]
        public Nullable<short> FlPerConoscenza { get; set; }

        [JsonPropertyName("flScarto")]
        public Nullable<short> FlScarto { get; set; }

        [JsonPropertyName("legato2ProgLegato")]
        public Nullable<int> Legato2ProgLegato { get; set; }

        [JsonPropertyName("logMoviVersione")]
        public Nullable<int> LogMoviVersione { get; set; }

        [JsonPropertyName("minimoStato")]
        public Nullable<decimal> MinimoStato { get; set; }

        [JsonPropertyName("mitt2ProgInteressa")]
        public Nullable<int> Mitt2ProgInteressa { get; set; }

        [JsonPropertyName("opeSigla")]
        public string OpeSigla { get; set; }

        [JsonPropertyName("pratAnnoProt")]
        public Nullable<short> PratAnnoProt { get; set; }

        [JsonPropertyName("pratCodAna")]
        public string PratCodAna { get; set; }

        [JsonPropertyName("pratCodReg")]
        public string PratCodReg { get; set; }

        [JsonPropertyName("pratDescOgge")]
        public string PratDescOgge { get; set; }

        [JsonPropertyName("pratNote")]
        public string PratNote { get; set; }

        [JsonPropertyName("pratNumProt")]
        public Nullable<int> PratNumProt { get; set; }

        [JsonPropertyName("pratPraticaAc")]
        public Nullable<short> PratPraticaAc { get; set; }

        [JsonPropertyName("pratPraticaDataAc")]
        public Nullable<DateTime> PratPraticaDataAc { get; set; }

        [JsonPropertyName("pratProgDoc")]
        public Nullable<long> PratProgDoc { get; set; }

        [JsonPropertyName("pratProgInPrat")]
        public Nullable<int> PratProgInPrat { get; set; }

        [JsonPropertyName("pratProgLegato")]
        public Nullable<int> PratProgLegato { get; set; }

        [JsonPropertyName("pratSubnProt")]
        public string PratSubnProt { get; set; }

        [JsonPropertyName("precedentePrimoProgDocP")]
        public Nullable<long> PrecedentePrimoProgDocP { get; set; }

        [JsonPropertyName("precedenteProgDocP")]
        public Nullable<long> PrecedenteProgDocP { get; set; }

        [JsonPropertyName("protoAnnoProt")]
        public Nullable<short> ProtoAnnoProt { get; set; }

        [JsonPropertyName("protoApProt")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso ProtoApProt { get; set; }

        [JsonPropertyName("protoCodLoginAgg")]
        public string ProtoCodLoginAgg { get; set; }

        [JsonPropertyName("protoCodReg")]
        public string ProtoCodReg { get; set; }

        [JsonPropertyName("protoCollEmergenza")]
        public string ProtoCollEmergenza { get; set; }

        [JsonPropertyName("protoDataAp")]
        public Nullable<DateTime> ProtoDataAp { get; set; }

        [JsonPropertyName("protoDataAtti")]
        public Nullable<DateTime> ProtoDataAtti { get; set; }

        [JsonPropertyName("protoDataMovi")]
        public Nullable<DateTime> ProtoDataMovi { get; set; }

        [JsonPropertyName("protoDataOraAgg")]
        public Nullable<DateTime> ProtoDataOraAgg { get; set; }

        [JsonPropertyName("protoDescOgge")]
        public string ProtoDescOgge { get; set; }

        [JsonPropertyName("protoFlAtti")]
        public Nullable<short> ProtoFlAtti { get; set; }

        [JsonPropertyName("protoFlImmDocumatic")]
        public Nullable<short> ProtoFlImmDocumatic { get; set; }

        [JsonPropertyName("protoLivSegretezza")]
        public Nullable<short> ProtoLivSegretezza { get; set; }

        [JsonPropertyName("protoNoteAtti")]
        public string ProtoNoteAtti { get; set; }

        [JsonPropertyName("protoNoteRiservate")]
        public string ProtoNoteRiservate { get; set; }

        [JsonPropertyName("protoNumProt")]
        public Nullable<int> ProtoNumProt { get; set; }

        [JsonPropertyName("protoProgAnaUff")]
        public Nullable<long> ProtoProgAnaUff { get; set; }

        [JsonPropertyName("protoProgDoc")]
        public Nullable<long> ProtoProgDoc { get; set; }

        [JsonPropertyName("protoProgMovi")]
        public Nullable<int> ProtoProgMovi { get; set; }

        [JsonPropertyName("protoStato")]
        public Nullable<short> ProtoStato { get; set; }

        [JsonPropertyName("protoSubnProt")]
        public string ProtoSubnProt { get; set; }

        [JsonPropertyName("qualRegProgQualReg")]
        public Nullable<int> QualRegProgQualReg { get; set; }

        [JsonPropertyName("regCodAna")]
        public string RegCodAna { get; set; }

        [JsonPropertyName("regCodAnaInterr")]
        public string RegCodAnaInterr { get; set; }

        [JsonPropertyName("regCodReg")]
        public string RegCodReg { get; set; }

        [JsonPropertyName("regCodRegInterr")]
        public string RegCodRegInterr { get; set; }

        [JsonPropertyName("regDescAna")]
        public string RegDescAna { get; set; }

        [JsonPropertyName("regDescReg")]
        public string RegDescReg { get; set; }

        [JsonPropertyName("regNoApProtInterr")]
        public Nullable<short> RegNoApProtInterr { get; set; }

        [JsonPropertyName("regProgAna")]
        public Nullable<long> RegProgAna { get; set; }

        [JsonPropertyName("ricevuteArrivate")]
        public Nullable<decimal> RicevuteArrivate { get; set; }

        [JsonPropertyName("ricevuteRichieste")]
        public Nullable<decimal> RicevuteRichieste { get; set; }

        [JsonPropertyName("tipoDocDescTipoDoc")]
        public string TipoDocDescTipoDoc { get; set; }

        [JsonPropertyName("uffFlNonVedo")]
        public Nullable<short> UffFlNonVedo { get; set; }

        [JsonPropertyName("uffOpeCodAna")]
        public string UffOpeCodAna { get; set; }

        [JsonPropertyName("uffOpeDescAna")]
        public string UffOpeDescAna { get; set; }

        [JsonPropertyName("uffi2ProgInteressa")]
        public Nullable<decimal> Uffi2ProgInteressa { get; set; }

        [JsonPropertyName("uffiDataRiferim")]
        public Nullable<DateTime> UffiDataRiferim { get; set; }

        [JsonPropertyName("uffiDescAna")]
        public string UffiDescAna { get; set; }

        [JsonPropertyName("valuteSimbolo")]
        public string ValuteSimbolo { get; set; }

        [JsonPropertyName("isImmagini")]
        public Nullable<bool> IsImmagini { get; set; }

        [JsonPropertyName("isDocumenti")]
        public Nullable<bool> IsDocumenti { get; set; }

        [JsonPropertyName("dataOraProtocollazione")]
        public Nullable<DateTime> DataOraProtocollazione { get; set; }

        [JsonPropertyName("codLoginProtocollazione")]
        public string CodLoginProtocollazione { get; set; }

        [JsonPropertyName("flInoltro")]
        public Nullable<decimal> FlInoltro { get; set; }

        [JsonPropertyName("protoDataScadenza")]
        public Nullable<DateTime> ProtoDataScadenza { get; set; }

        [JsonPropertyName("statoRicevute")]
        public Nullable<decimal> StatoRicevute { get; set; }

        [JsonPropertyName("casellaMittInvioTelematico")]
        public string CasellaMittInvioTelematico { get; set; }
    }

}
