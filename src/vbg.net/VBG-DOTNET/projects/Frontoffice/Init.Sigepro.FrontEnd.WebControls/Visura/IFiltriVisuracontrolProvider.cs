using Init.SIGePro.Manager.DTO.Visura.V1;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
    internal interface IFiltriVisuraControlProvider
    {
        int IdCodiceIstanza { get; }
        int IdAnnoIstanza { get; }
        int IdMeseIstanza { get; }
        int IdOggetto { get; }
        int IdCivico { get; }
        int IdNumeroAutorizzazione { get; }
        int IdNumProtocollo { get; }
        int IdStradario { get; }
        int IdStatoIstanza { get; }
        int IdDataProtocollo { get; }
        int IdDatiCatasto { get; }
        int IdRichiedente { get; }
        int IdIntervento { get; }
        int IdFabbricato { get; }
        int IdPoszioneArchivio { get; }

        int ListaIdOperatore { get; }
        int ListaIdRichiedente { get; }
        int ListaIdTipoprocedura { get; }
        int ListaIdOggetto { get; }
        int ListaIdParticella { get; }
        int ListaIdSubalterno { get; }
        int ListaIdProgressivo { get; }
        int ListaIdLocalizzazione { get; }
        int ListaIdCodicearea { get; }
        int ListaIdFoglio { get; }
        int ListaIdNumeroistanza { get; }
        int ListaIdDatapresentazione { get; }
        int ListaIdTipointervento { get; }
        int ListaIdStato { get; }
        int ListaIdNumeroprotocollo { get; }
        int ListaIdDataprotocollo { get; }
        int ListaIdCivico { get; }
        int ListaIdTipocatasto { get; }
        int ListaIdRagioneSociale { get; }
        int ListaIdPosizioneArchivio { get; }

        CampoVisuraFrontofficeDto[] GetCampiFiltro(string idComune, string software);
        CampoVisuraFrontofficeDto[] GetCampiTabella(string idComune, string software);
    }
}
