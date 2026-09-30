using Init.SIGePro.Manager.DTO.Visura.V1;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1
{


    internal class WsCampiRicercaVisuraRepository : ICampiRicercaVisuraRepository
    {
        private readonly CampiRicercaPraticheServiceCreator _serviceCreator;

        public WsCampiRicercaVisuraRepository(CampiRicercaPraticheServiceCreator serviceCreator)
        {
            if (serviceCreator == null)
                throw new System.ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
        }

        public CampoVisuraFrontofficeDto[] GetFiltriVisuraFrontoffice(string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetFiltriVisuraFrontoffice(ws.Token, software);
            });
        }

        public CampoVisuraFrontofficeDto[] GetFiltriArchivioIstanzeFrontoffice(string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetFiltriArchivioIstanzeFrontoffice(ws.Token, software);
            });
        }

        public CampoVisuraFrontofficeDto[] GetCampiTabellaVisura(string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetCampiTabellaVisura(ws.Token, software);
            });
        }

        public CampoVisuraFrontofficeDto[] GetCampiTabellaArchivioIstanze(string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetCampiTabellaArchivioIstanze(ws.Token, software);
            });
        }

        public int GetRecordPerPagina(string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                return ws.Service.GetRecordPerPagina(ws.Token, software);
            });
        }
    }
}
