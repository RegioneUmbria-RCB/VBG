using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using AutoMapper;

namespace Init.Sigepro.FrontEnd.AppLogic.AutomapperBootstrapper
{
    public class AutomapperApplogicBootstrapper
    {
        public static void Bootstrap()
        {
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Anagrafe, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Anagrafe>()
                .ForMember(dst => dst.COMUNERESIDENZA, opt => opt.MapFrom(src => src.COMUNERESIDENZA))
                .ForMember(dst => dst.TitoloClass, opt => opt.MapFrom(src => src.TitoloClass))
                .ForMember(dst => dst.ElencoProfessionale, opt => opt.MapFrom(src => src.ElencoProfessionale))
                .ForMember(dst => dst.FormaGiuridicaClass, opt => opt.MapFrom(src => src.FormaGiuridicaClass))
                .ForMember(dst => dst.AnagrafeDocumenti, opt => opt.MapFrom(src => src.AnagrafeDocumenti))
                .ForMember(dst => dst.AnagrafeDyn2ModelliT, opt => opt.MapFrom(src => src.AnagrafeDyn2ModelliT))
                .ForMember(dst => dst.AnagrafeDyn2Dati, opt => opt.MapFrom(src => src.AnagrafeDyn2Dati))
                .ForMember(dst => dst.ComuneNascita, opt => opt.MapFrom(src => src.ComuneNascita))
                .ForMember(dst => dst.ComuneRegDitte, opt => opt.MapFrom(src => src.ComuneRegDitte))
                .ForMember(dst => dst.ComuneRegTrib, opt => opt.MapFrom(src => src.ComuneRegTrib))
                .ForMember(dst => dst.PresenzeStoriche, opt => opt.MapFrom(src => src.PresenzeStoriche))
                .ForMember(dst => dst.ComuneCorrispondenza, opt => opt.MapFrom(src => src.ComuneCorrispondenza))
                .ForMember(dst => dst.ComuneResidenza, opt => opt.MapFrom(src => src.ComuneResidenza))
                .ForMember(dst => dst.Cittadinanza, opt => opt.MapFrom(src => src.Cittadinanza));

            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Titoli, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Titoli>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElenchiProfessionaliBase, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ElenchiProfessionaliBase>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.FormeGiuridiche, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.FormeGiuridiche>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDocumenti, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.AnagrafeDocumenti>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Oggetti, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Oggetti>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDyn2ModelliT, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.AnagrafeDyn2ModelliT>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.AnagrafeDyn2Dati, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.AnagrafeDyn2Dati>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Comuni, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Comuni>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.VwProvince, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.VwProvince>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.Cittadinanza, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.Cittadinanza>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.MercatiPresenzeStorico, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.MercatiPresenzeStorico>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElencoInpsBase, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ElencoInpsBase>();
            Mapper.CreateMap<Init.Sigepro.FrontEnd.AppLogic.RicercheAnagraficheWebService.ElencoInailBase, Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.ElencoInailBase>();
            Mapper.CreateMap<AreaRiservataService.Configurazione, Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione>();
            Mapper.CreateMap<AreaRiservataService.Responsabili, Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Responsabili>();



            Mapper.AssertConfigurationIsValid();
        }
    }
}
