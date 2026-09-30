using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.WebApp.Legacy.Interfaces
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace = "http://it.gruppoinit/Protocollazione", ConfigurationName = "SIGePro.Net.WebServices.WsSIGePro.ProtocollazioneService")]
    public interface IProtocollazioneService
    {
        [OperationContract(Action = "http://it.gruppoinit/CreaCopie")]
        DatiProtocolloResponseType CreaCopie(string token, string codiceIstanza, string codiceAmministrazione);

        [OperationContract(Action = "http://it.gruppoinit/MettiAllaFirmaXml")]
        DatiProtocolloResponseType MettiAllaFirmaXml(string token, string codiceMovimento, DatiRequestType file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneXml")]
        DatiProtocolloResponseType ProtocollazioneXml(string token, string software, DatiRequestType file, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneIstanza")]
        DatiProtocolloResponseType ProtocollazioneIstanza(string token, string codiceIstanza, int source, DatiMittentiType mittenti);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneIstanzaXml")]
        DatiProtocolloResponseType ProtocollazioneIstanzaXml(string token, string codiceIstanza, DatiRequestType file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazionePecXml")]
        DatiProtocolloResponseType ProtocollazionePecXml(string token, string codicePec, DatiRequestType file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneMovimento")]
        DatiProtocolloResponseType ProtocollazioneMovimento(string token, string codiceMovimento, DatiMittentiType mittenti = null);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneComunicazioneGraduatoria")]
        DatiProtocolloResponseType ProtocollazioneComunicazioneGraduatoria(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneMovimentoXml")]
        DatiProtocolloResponseType ProtocollazioneMovimentoXml(ProtocollazioneMovimentoXmlRequestType request);

        [OperationContract(Action = "http://it.gruppoinit/GetTipiDocumento")]
        ListaTipiDocumentoResponseType GetTipiDocumento(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetClassifiche")]
        ListaTipiClassificaType GetClassifiche(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiProtocollo")]
        //DatiProtocolloLetto LeggiProtocollo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune);
        List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest);

        [OperationContract(Action = "http://it.gruppoinit/LeggiProtocolloUORuolo")]
        List<DatiProtocolloLettoResponseType> LeggiProtocolloUORuolo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string uo, string ruolo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiProtocolloConData")]
        List<DatiProtocolloLettoResponseType> LeggiProtocolloConData(string token, string idProtocollo, DateTime dataProtocollo, string numProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiAllegato")]
        AllegatoResponseType LeggiAllegato(string token, string idBase, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiAllegatoUORuolo")]
        AllegatoResponseType LeggiAllegatoUORuolo(string token, string idBase, string uo, string ruolo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiAllegatoStorico")]
        AllegatoResponseType LeggiAllegatoStorico(string token, string idBase, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/StampaEtichette")]
        EtichetteResponseType StampaEtichette(string token, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, int numeroCopie, string stampante, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetMotiviAnnullamento")]
        ListaMotiviAnnullamentoResponseType GetMotiviAnnullamento(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/AnnullaProtocollo")]
        void AnnullaProtocollo(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/IsAnnullato")]
        DatiProtocolloAnnullatoResponseType IsAnnullato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetFascicoli")]
        ListaFascicoliResponseType GetFascicoli(string token, string codiceIstanza);

        [OperationContract(Action = "http://it.gruppoinit/SearchFascicoli")]
        ListaFascicoliResponseType SearchFascicoli(string token, string software, string codiceComune, DatiFascType datiFascicolo);

        [OperationContract(Action = "http://it.gruppoinit/IsFascicolato")]
        DatiProtocolloFascicolatoResponseType IsFascicolato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneXml")]
        DatiFascicoloResponseType FascicolazioneXml(string token, string software, DatiFascType datiFasc, string codiceComune, string idProtocollo, string numeroProtocollo, string annoProtocollo);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneIstanza")]
        DatiFascicoloResponseType FascicolazioneIstanza(string token, string codiceIstanza, int source);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneIstanzaXml")]
        DatiFascicoloResponseType FascicolazioneIstanzaXml(string token, string codiceIstanza, DatiFascType datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneMovimento")]
        DatiFascicoloResponseType FascicolazioneMovimento(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneMovimentoXml")]
        DatiFascicoloResponseType FascicolazioneMovimentoXml(string token, string codiceMovimento, DatiFascType datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/CambiaFascicoloIstanzaXml")]
        DatiFascicoloResponseType CambiaFascicoloIstanzaXml(string token, string codiceIstanza, DatiFascType datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/CreaUnitaDocumentaleIstanza")]
        CreaUnitaDocumentaleResponseType CreaUnitaDocumentaleIstanza(string token, string codiceIstanza, CreaUnitaDocumentaleRequestType request);

        [OperationContract(Action = "http://it.gruppoinit/CreaUnitaDocumentaleMovimento")]
        CreaUnitaDocumentaleResponseType CreaUnitaDocumentaleMovimento(string token, string codiceMovimento, CreaUnitaDocumentaleRequestType request);

        [OperationContract(Action = "http://it.gruppoinit/RegistrazioneIstanzaXml")]
        DatiProtocolloResponseType RegistrazioneIstanzaXml(string token, string codiceIstanza, string registro, DatiRequestType dati);

        [OperationContract(Action = "http://it.gruppoinit/RegistrazioneMovimentoXml")]
        DatiProtocolloResponseType RegistrazioneMovimentoXml(string token, string codiceMovimento, string registro, DatiRequestType dati);

        [OperationContract(Action = "http://it.gruppoinit/InvioPec")]
        void InvioPec(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/AggiungiAllegati")]
        void AggiungiAllegati(string token, string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetFirmatari")]
        ListaFirmatari GetFirmatari(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/RecuperaMetadati")]
        List<MetadatoType> RecuperaMetadati(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/EseguiAccettazione")]
        EseguiAccettazioneResponseType EseguiAccettazione(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/IsEsitato")]
        DatiProtocolloEsitatoResponseType IsEsitato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/Fascicolazione")]
        DatiFascicoloResponseType Fascicolazione(string token, string software, string codiceComune, int source, AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, DatiFascType file = null, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null);

        [OperationContract(Action = "http://it.gruppoinit/Protocollazione")]
        DatiProtocolloResponseType Protocollazione(string token, string software, string codiceComune, int source, AmbitoProtocollazioneEnum ambito, Istanze istanza = null, Init.SIGePro.Data.Movimenti movimento = null, DatiRequestType dati = null, PecInbox datiPec = null);
    }
}