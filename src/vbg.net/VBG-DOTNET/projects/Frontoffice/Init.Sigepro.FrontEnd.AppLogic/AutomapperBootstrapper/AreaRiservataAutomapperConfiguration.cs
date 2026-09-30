//using AutoMapper;

//namespace Init.Sigepro.FrontEnd.AppLogic.AutomapperBootstrapper
//{
//    public static class AreaRiservataAutomapperConfiguration
//    {
//        public static void Bootstrap(IMapperConfigurationExpression cfg)
//        {

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Anagrafe, VBG.Frontend.AppLogic.WsAnagraficheService.Anagrafe>()
//                .ForMember(dst => dst.COMUNERESIDENZA, opt => opt.MapFrom(src => src.COMUNERESIDENZA))
//                .ForMember(dst => dst.TitoloClass, opt => opt.MapFrom(src => src.TitoloClass))
//                .ForMember(dst => dst.ElencoProfessionale, opt => opt.MapFrom(src => src.ElencoProfessionale))
//                .ForMember(dst => dst.FormaGiuridicaClass, opt => opt.MapFrom(src => src.FormaGiuridicaClass))
//                .ForMember(dst => dst.AnagrafeDocumenti, opt => opt.MapFrom(src => src.AnagrafeDocumenti))
//                .ForMember(dst => dst.AnagrafeDyn2ModelliT, opt => opt.MapFrom(src => src.AnagrafeDyn2ModelliT))
//                .ForMember(dst => dst.AnagrafeDyn2Dati, opt => opt.MapFrom(src => src.AnagrafeDyn2Dati))
//                .ForMember(dst => dst.ComuneNascita, opt => opt.MapFrom(src => src.ComuneNascita))
//                .ForMember(dst => dst.ComuneRegDitte, opt => opt.MapFrom(src => src.ComuneRegDitte))
//                .ForMember(dst => dst.ComuneRegTrib, opt => opt.MapFrom(src => src.ComuneRegTrib))
//                .ForMember(dst => dst.PresenzeStoriche, opt => opt.MapFrom(src => src.PresenzeStoriche))
//                .ForMember(dst => dst.ComuneCorrispondenza, opt => opt.MapFrom(src => src.ComuneCorrispondenza))
//                .ForMember(dst => dst.COMUNECORRISPONDENZA, opt => opt.MapFrom(src => src.COMUNECORRISPONDENZA))
//                .ForMember(dst => dst.ComuneResidenza, opt => opt.MapFrom(src => src.ComuneResidenza))
//                .ForMember(dst => dst.Cittadinanza, opt => opt.MapFrom(src => src.Cittadinanza))
//                .ForMember(dst => dst.CassaEdileCodiceSede, opt => opt.Ignore())
//                .ForMember(dst => dst.CassaEdileMatricola, opt => opt.Ignore())
//                .ForMember(dst => dst.DataIdentificazione, opt => opt.Ignore())
//                .ForMember(dst => dst.DataInizioAttivita, opt => opt.Ignore())
//                .ForMember(dst => dst.FlagIdentificato, opt => opt.Ignore())
//                .ForMember(dst => dst.OperIdentificazione, opt => opt.Ignore())
//                .ForMember(dst => dst.REFERENTE, opt => opt.Ignore())
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore())
//                ;

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Titoli, VBG.Frontend.AppLogic.WsAnagraficheService.Titoli>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElenchiProfessionaliBase, VBG.Frontend.AppLogic.WsAnagraficheService.ElenchiProfessionaliBase>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.FormeGiuridiche, VBG.Frontend.AppLogic.WsAnagraficheService.FormeGiuridiche>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDocumenti, VBG.Frontend.AppLogic.WsAnagraficheService.AnagrafeDocumenti>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Oggetti, VBG.Frontend.AppLogic.WsAnagraficheService.AnagrafeDocumentiOggetti>()
//                .ForMember(dst => dst.Metadati, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDyn2ModelliT, VBG.Frontend.AppLogic.WsAnagraficheService.AnagrafeDyn2ModelliT>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDyn2Dati, VBG.Frontend.AppLogic.WsAnagraficheService.AnagrafeDyn2Dati>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Dyn2Campi, VBG.Frontend.AppLogic.WsAnagraficheService.Dyn2Campi>().ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Comuni, VBG.Frontend.AppLogic.WsAnagraficheService.Comuni>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.VwProvince, VBG.Frontend.AppLogic.WsAnagraficheService.VwProvince>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Cittadinanza, VBG.Frontend.AppLogic.WsAnagraficheService.Cittadinanza>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.MercatiPresenzeStorico, VBG.Frontend.AppLogic.WsAnagraficheService.MercatiPresenzeStorico>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElencoInpsBase, VBG.Frontend.AppLogic.WsAnagraficheService.ElencoInpsBase>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElencoInailBase, VBG.Frontend.AppLogic.WsAnagraficheService.ElencoInailBase>()
//                .ForMember(dst => dst.UseForeign, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersTables, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersJoinClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersWhereClause, opt => opt.Ignore())
//                .ForMember(dst => dst.OthersSelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.SelectColumns, opt => opt.Ignore())
//                .ForMember(dst => dst.OrderBy, opt => opt.Ignore());

//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.ConfigurazioneAreaRiservataWs.Configurazione, Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione>();
//            cfg.CreateMap<Init.Sigepro.FrontEnd.AppLogic.ConfigurazioneAreaRiservataWs.Responsabili, Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Responsabili>();

//        }
//    }
//}
