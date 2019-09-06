using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.WsModulisticaFrontoffice;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneModulisticaFrontoffice
{
    public class ModulisticaFrontofficeService
    {
        ModulisticaFrontofficeServiceCreator _serviceCreator;

        public ModulisticaFrontofficeService(ModulisticaFrontofficeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }


        public CategoriaModulisticaDto[] GetModulistica(string software)
        {
            using (var ws = this._serviceCreator.CreateService())
            {
                try
                {
                    return ws.Service.GetModulistica(ws.Token, software);
                }
                catch(Exception )
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }
    }
}
