//using Init.Sigepro.FrontEnd.AppLogic.WsModulisticaFrontoffice;
//using System;

//namespace Init.Sigepro.FrontEnd.AppLogic.GestioneModulisticaFrontoffice
//{
//    public class ModulisticaFrontofficeService
//    {
//        private readonly ModulisticaFrontofficeServiceCreator _serviceCreator;

//        public ModulisticaFrontofficeService(ModulisticaFrontofficeServiceCreator serviceCreator)
//        {
//            this._serviceCreator = serviceCreator;
//        }


//        public CategoriaModulisticaDto[] GetModulistica(string software)
//        {
//            return this._serviceCreator.Call(ws =>
//            {
//                try
//                {
//                    return ws.Service.GetModulistica(ws.Token, software);
//                }
//                catch (Exception)
//                {
//                    ws.Service.Abort();

//                    throw;
//                }
//            });
//        }
//    }
//}
