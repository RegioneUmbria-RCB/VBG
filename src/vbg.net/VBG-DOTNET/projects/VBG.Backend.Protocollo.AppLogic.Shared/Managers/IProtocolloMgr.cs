using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public interface IProtocolloMgr : IDisposable
    {
        void AggiungiAllegati(string numeroProtocollo, DateTime? dataProtocollo, string idProtocollo, int[] codiciAllegati);
        void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento);
        DatiFascicoloResponseType CambiaFascicolo(DatiFascType datiFascicolo);
        DatiProtocolloResponseType CreaCopie(string codiceAmministrazione);
        CreaUnitaDocumentaleResponseType CreaUnitaDocumentale(CreaUnitaDocumentaleRequestType request);
        void Dispose();
        EseguiAccettazioneResponseType EseguiAccettazione(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        DatiFascicoloResponseType Fascicola(DatiFascType dati, int source = 16, string idProtocollo = null, string numeroProtocollo = null, string annoProtocollo = null, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE);
        ListaFirmatari GetFirmatari();
        // void Initialize(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null, PecInbox datiPec = null);
        void InvioPec();
        DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        DatiProtocolloEsitatoResponseType IsEsitato(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        AllegatoResponseType LeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato);
        AllegatoResponseType LeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato, string uo, string ruolo);
        AllegatoResponseType LeggiAllegatoStorico(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato);
        List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest);
        List<DatiProtocolloLettoResponseType> LeggiProtocolloConData(string idProtocollo, DateTime dataProtocollo, string numeroProtocollo);
        List<DatiProtocolloLettoResponseType> LeggiProtocolloUoRuolo(LeggiProtocolloRequest leggiProtocolloRequest, string uo, string ruolo);
        ListaTipiClassificaType ListaClassifiche();
        ListaFascicoliResponseType ListaFascicoli(DatiFascType datiFascicolo);
        ListaMotiviAnnullamentoResponseType ListaMotivoAnnullamento();
        ListaTipiDocumentoResponseType ListaTipiDocumento();
        DatiProtocolloResponseType MettiAllaFirma(DatiRequestType dati);
        DatiProtocolloResponseType? Protocollazione(TipoProvenienza provenienza, DatiRequestType dati, Source tipoInserimento);
        List<MetadatoType> RecuperaMetadati();
        DatiProtocolloResponseType Registrazione(string registro, DatiRequestType dati, TipoProvenienza provenienza = TipoProvenienza.BACKOFFICE, int iSource = 16);
        EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante);
    }
}