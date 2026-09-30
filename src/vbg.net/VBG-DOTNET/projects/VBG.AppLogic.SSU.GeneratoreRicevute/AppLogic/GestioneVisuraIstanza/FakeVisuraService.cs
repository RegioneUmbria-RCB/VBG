using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.Shared.Infrastructure.ServiceModel;
using PersonalLib2.Data.Providers;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneVisuraIstanza
{
    public class FakeVisuraService : IVisuraService
    {
        public Istanze GetById(int idPratica, VisuraIstanzaFlags flags)
        {
            throw new NotImplementedException();
        }

        public Istanze GetByUuid(string uuid, bool effettuaSubVisuraMovimenti)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<VisuraListItem> GetListaPratiche(RichiestaListaPraticheV3 richiesta)
        {
            throw new NotImplementedException();
        }

        public Task<VisuraListResponse> GetListaPratichePaginataAsync(RichiestaListaPraticheV3 richiesta, QueryPaginationRequest paginationRequest)
        {
            throw new NotImplementedException();
        }

        public string GetUUIDDaCodiceIstanza(int codiceIstanza)
        {
            throw new NotImplementedException();
        }
    }

    public class FakeSostituzioneSegnapostoQrCode : ISostituzioneSegnapostoQrCode
    {
        public string ProcessaCertificato(int codiceIstanza, string htmlCertificato)
        {
            throw new NotImplementedException();
        }
    }

    public class FakeIstanzeServiceCreator : IstanzeServiceCreator
    {
        public FakeIstanzeServiceCreator(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }
    }

    public class FakeGeneratoreHtmlSchedeDinamiche : IGeneratoreHtmlSchedeDinamiche
    {
        public bool IgnoraCssDefault { get => throw new NotImplementedException(); set => throw new NotImplementedException(); }

        public string GeneraHtml(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            throw new NotImplementedException();
        }

        public Task<string> GeneraHtmlAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idScheda, int indiceMolteplicita = -1)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlDelleSchedeDellaDomanda(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public Task<string> GeneraHtmlDelleSchedeDellaDomandaAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlSchedaEndoprocedimento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            throw new NotImplementedException();
        }

        public Task<string> GeneraHtmlSchedaEndoprocedimentoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, int idEndo)
        {
            throw new NotImplementedException();
        }

        public string GeneraHtmlSchedeIntervento(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public Task<string> GeneraHtmlSchedeInterventoAsync(ISchedeDinamicheDomandaAlRiepilogoService datiDinamiciReader, GenerazioneHtmlSchedeOptions options)
        {
            throw new NotImplementedException();
        }

        public void Inizializza()
        {
            throw new NotImplementedException();
        }
    }

    public class FakeModelloDinamicoHtmlRenderer : IModelloDinamicoHtmlRenderer
    {
        public string GetHtml(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili campiNonVisibili = null)
        {
            throw new NotImplementedException();
        }

        public Task<string> GetHtmlAsync(ModelloDinamicoBase modelloDinamico, ICampiNonVisibili campiNonVisibili = null)
        {
            throw new NotImplementedException();
        }
    }
}
