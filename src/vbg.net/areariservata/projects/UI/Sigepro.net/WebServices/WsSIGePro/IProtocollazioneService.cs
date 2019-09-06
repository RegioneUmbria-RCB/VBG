using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using System.ServiceModel.Activation;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace = "http://it.gruppoinit/Protocollazione", ConfigurationName = "SIGePro.Net.WebServices.WsSIGePro.ProtocollazioneService")]
    public interface IProtocollazioneService
    {
        [OperationContract(Action = "http://it.gruppoinit/CreaCopie")]
        DatiProtocolloRes CreaCopie(string token, string codiceIstanza, string codiceAmministrazione);

        [OperationContract(Action = "http://it.gruppoinit/MettiAllaFirmaXml")]
        DatiProtocolloRes MettiAllaFirmaXml(string token, string codiceMovimento, Dati file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneXml")]
        DatiProtocolloRes ProtocollazioneXml(string token, string software, Dati file, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneIstanza")]
        DatiProtocolloRes ProtocollazioneIstanza(string token, string codiceIstanza, int source, DatiMittenti mittenti);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneIstanzaXml")]
        DatiProtocolloRes ProtocollazioneIstanzaXml(string token, string codiceIstanza, Dati file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazionePecXml")]
        DatiProtocolloRes ProtocollazionePecXml(string token, string codicePec, Dati file);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneMovimento")]
        DatiProtocolloRes ProtocollazioneMovimento(string token, string codiceMovimento, DatiMittenti mittenti = null);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneComunicazioneGraduatoria")]
        DatiProtocolloRes ProtocollazioneComunicazioneGraduatoria(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/ProtocollazioneMovimentoXml")]
        DatiProtocolloRes ProtocollazioneMovimentoXml(string token, string codiceMovimento, Dati file);

        [OperationContract(Action = "http://it.gruppoinit/GetTipiDocumento")]
        ListaTipiDocumento GetTipiDocumento(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetClassifiche")]
        ListaTipiClassifica GetClassifiche(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiProtocollo")]
        DatiProtocolloLetto LeggiProtocollo(string token, string idProtocollo, string annoProtocollo, string numProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/LeggiAllegato")]
        AllOut LeggiAllegato(string token, string idBase, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/StampaEtichette")]
        DatiEtichette StampaEtichette(string token, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, int numeroCopie, string stampante, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetMotiviAnnullamento")]
        ListaMotiviAnnullamento GetMotiviAnnullamento(string token, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/AnnullaProtocollo")]
        void AnnullaProtocollo(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/IsAnnullato")]
        DatiProtocolloAnnullato IsAnnullato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/GetFascicoli")]
        ListaFascicoli GetFascicoli(string token, string codiceIstanza);

        [OperationContract(Action = "http://it.gruppoinit/SearchFascicoli")]
        ListaFascicoli SearchFascicoli(string token, string software, string codiceComune, DatiFasc datiFascicolo);

        [OperationContract(Action = "http://it.gruppoinit/IsFascicolato")]
        DatiProtocolloFascicolato IsFascicolato(string token, string idProtocollo, string annoProtocollo, string numeroProtocollo, string software, string codiceComune);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneXml")]
        DatiFascicolo FascicolazioneXml(string token, string software, DatiFasc datiFasc, string codiceComune, string idProtocollo, string numeroProtocollo, string annoProtocollo);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneIstanza")]
        DatiFascicolo FascicolazioneIstanza(string token, string codiceIstanza, int source);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneIstanzaXml")]
        DatiFascicolo FascicolazioneIstanzaXml(string token, string codiceIstanza, DatiFasc datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneMovimento")]
        DatiFascicolo FascicolazioneMovimento(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/FascicolazioneMovimentoXml")]
        DatiFascicolo FascicolazioneMovimentoXml(string token, string codiceMovimento, DatiFasc datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/CambiaFascicoloIstanzaXml")]
        DatiFascicolo CambiaFascicoloIstanzaXml(string token, string codiceIstanza, DatiFasc datiFasc);

        [OperationContract(Action = "http://it.gruppoinit/CreaUnitaDocumentaleIstanza")]
        CreaUnitaDocumentaleResponse CreaUnitaDocumentaleIstanza(string token, string codiceIstanza, CreaUnitaDocumentaleRequest request);

        [OperationContract(Action = "http://it.gruppoinit/CreaUnitaDocumentaleMovimento")]
        CreaUnitaDocumentaleResponse CreaUnitaDocumentaleMovimento(string token, string codiceMovimento, CreaUnitaDocumentaleRequest request);

        [OperationContract(Action = "http://it.gruppoinit/RegistrazioneIstanzaXml")]
        DatiProtocolloRes RegistrazioneIstanzaXml(string token, string codiceIstanza, string registro, Dati dati);

        [OperationContract(Action = "http://it.gruppoinit/RegistrazioneMovimentoXml")]
        DatiProtocolloRes RegistrazioneMovimentoXml(string token, string codiceMovimento, string registro, Dati dati);

        [OperationContract(Action = "http://it.gruppoinit/InvioPec")]
        void InvioPec(string token, string codiceMovimento);

        [OperationContract(Action = "http://it.gruppoinit/AggiungiAllegati")]
        void AggiungiAllegati(string token, string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati, string software, string codiceComune);
    }
}