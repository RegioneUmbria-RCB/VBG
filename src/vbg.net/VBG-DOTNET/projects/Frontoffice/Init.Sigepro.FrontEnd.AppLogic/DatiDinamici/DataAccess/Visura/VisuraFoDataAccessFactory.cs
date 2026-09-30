//using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
//using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess.Common;
//using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
//using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
//using Newtonsoft.Json;
//using System;
//using VBG.DatiDinamici.GestioneLocalizzazioni;
//using VBG.DatiDinamici.Interfaces;

//namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess.Visura
//{
//    public class VisuraFoDataAccessFactory : ARDataAccessFactoryBase
//    {
//        public class DummyIstanza : IClasseContestoModelloDinamico
//        {

//        }

//        private class DummyClassLoader : IClasseContestoLoader
//        {
//            private readonly Istanze _istanza;

//            public DummyClassLoader(Istanze istanza)
//            {
//                this._istanza = istanza;
//            }

//            public IClasseContestoModelloDinamico LoadClass()
//            {
//                return JsonConvert.DeserializeObject<Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Istanze>(JsonConvert.SerializeObject(this._istanza));
//            }
//        }

//        private readonly Istanze _istanza;

//        public VisuraFoDataAccessFactory(ModelloDinamicoCache cache, Istanze istanza, ITokenApplicazioneService tokenService)
//            : base(cache, tokenService)
//        {
//            this._istanza = istanza;
//        }


//        public override IClasseContestoLoader GetClassLoader()
//        {
//            return new DummyClassLoader(this._istanza);
//        }

//        public override IDyn2DatiRepository GetRepository()
//        {
//            return new VisuraDatiRepository(this._istanza);
//        }

//        public override IQueryLocalizzazioni GetQueryLocalizzazioni()
//        {
//            throw new NotImplementedException();
//        }
//    }
//}
