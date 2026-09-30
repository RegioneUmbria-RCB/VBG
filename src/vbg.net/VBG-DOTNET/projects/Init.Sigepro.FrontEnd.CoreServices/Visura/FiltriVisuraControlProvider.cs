using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1;
using Init.SIGePro.Manager.DTO.Visura.V1;

namespace Init.Sigepro.FrontEnd.CoreServices.Visura
{
    public class FiltriVisuraControlProvider : IFiltriVisuraControlProvider
    {
        private ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; }

        public FiltriVisuraControlProvider(ICampiRicercaVisuraRepository campiRicercaVisuraRepository)
        {
            this._campiRicercaVisuraRepository = campiRicercaVisuraRepository;
        }

        #region IFiltriVisuraControlProvider Members

        public int IdCodiceIstanza { get { return 41; } }
        public int IdAnnoIstanza { get { return 43; } }
        public int IdMeseIstanza { get { return 44; } }
        public int IdOggetto { get { return 48; } }
        public int IdCivico { get { return 62; } }
        public int IdNumeroAutorizzazione { get { return 66; } }
        public int IdNumProtocollo { get { return 59; } }
        public int IdStradario { get { return 46; } }
        public int IdStatoIstanza { get { return 45; } }
        public int IdDataProtocollo { get { return 60; } }
        public int IdDatiCatasto { get { return 70; } }
        public int IdRichiedente { get { return 47; } }
        public int IdIntervento { get { return 42; } }
        public int IdFabbricato { get { return 79; } }
        public int IdPoszioneArchivio { get { return 82; } }


        public int ListaIdOperatore { get { return 18; } }
        public int ListaIdRichiedente { get { return 19; } }
        public int ListaIdTipoprocedura { get { return 21; } }
        public int ListaIdOggetto { get { return 22; } }
        public int ListaIdParticella { get { return 23; } }
        public int ListaIdSubalterno { get { return 32; } }
        public int ListaIdProgressivo { get { return 28; } }
        public int ListaIdLocalizzazione { get { return 25; } }
        public int ListaIdCodicearea { get { return 26; } }
        public int ListaIdFoglio { get { return 24; } }
        public int ListaIdNumeroistanza { get { return 27; } }
        public int ListaIdDatapresentazione { get { return 17; } }
        public int ListaIdTipointervento { get { return 20; } }
        public int ListaIdStato { get { return 31; } }
        public int ListaIdNumeroprotocollo { get { return 55; } }
        public int ListaIdDataprotocollo { get { return 56; } }
        public int ListaIdCivico { get { return 64; } }
        public int ListaIdTipocatasto { get { return 72; } }
        public int ListaIdRagioneSociale { get { return 78; } }
        public int ListaIdPosizioneArchivio { get { return 81; } }

        #endregion

        public CampoVisuraFrontofficeDto[] GetCampiFiltro(string idComune, string software)
        {
            return this._campiRicercaVisuraRepository.GetFiltriVisuraFrontoffice(idComune, software);
        }


        public CampoVisuraFrontofficeDto[] GetCampiTabella(string idComune, string software)
        {
            return this._campiRicercaVisuraRepository.GetCampiTabellaVisura(idComune, software);
        }
    }
}
