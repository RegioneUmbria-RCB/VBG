//using Init.Sigepro.FrontEnd.AppLogic.Adapters;
//using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
//using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
//using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
//using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline;
//using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
//using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
//using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
//using Init.Sigepro.FrontEnd.Infrastructure.Server;
//using System;
//using System.IO;
//using System.Text;
//using System.Threading.Tasks;

//namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
//{
//    public class RiepilogoDomandaAsync
//    {
//        private readonly IHtmlToPdfAsyncFileConverter _fileConverter;
//        private readonly SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
//        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
//        private readonly IIstanzaSigeproAdapterService _istanzaSigeproAdapterService;
//        private readonly IPathMapper _pathMapper;

//        public RiepilogoDomandaAsync(SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService,
//                                IHtmlToPdfAsyncFileConverter fileConverter, IDatiDinamiciRepository datiDinamiciRepository, IIstanzaSigeproAdapterService istanzaSigeproAdapterService,
//                                IPathMapper pathMapper)
//        {
//            this._fileConverter = fileConverter;
//            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
//            this._datiDinamiciRepository = datiDinamiciRepository;
//            this._istanzaSigeproAdapterService = istanzaSigeproAdapterService;
//            this._pathMapper = pathMapper;
//        }


//    }
//}
