//using AutoMapper;
//using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
//using Init.Sigepro.FrontEnd.GestioneMovimenti.Converters;
//using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
//using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
//using Init.SIGePro.Manager.DTO.Scadenzario;

//namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap
//{
//    public static class MovimentiAutomapperConfiguration
//    {
//        public static void Bootstrap(IMapperConfigurationExpression cfg)
//        {
//            cfg.CreateMap<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>().ConvertUsing(new DatiMovimentoToDatiMovimentoDiOrigineConverter());
//            cfg.CreateMap<IstanzeStradario, LocalizzazioneIstanza>().ConvertUsing(new IstanzeStradarioToLocalizzazioneIstanzaConverter());

//        }
//    }
//}
