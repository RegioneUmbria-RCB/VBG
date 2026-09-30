using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
    internal class WsAlboPretorioRepository : IAlboPretorioRepository
    {
        private readonly AlboPretorioServiceCreator _serviceCreator;

        public WsAlboPretorioRepository(AlboPretorioServiceCreator serviceCreator)
        {
            if (serviceCreator == null)
                throw new System.ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
        }


        public List<ListaCategorie> GetCategorie(string idComune, string software)
        {

            return this._serviceCreator.Call(ws =>
            {
                List<ListaCategorie> rVal = new List<ListaCategorie>();

                var l = new ListaCategorieRequest
                {
                    token = ws.Token,
                    software = software
                };

                rVal.AddRange(ws.Service.ListaCategorie(l));

                return rVal;
            });
        }

        public List<ListaPubblicazioniValideAl> GetPubblicazioni(PubblicazioniValideAlRequest l, string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                List<ListaPubblicazioniValideAl> rVal = new List<ListaPubblicazioniValideAl>();

                l.token = ws.Token;
                l.software = software;

                rVal.AddRange(ws.Service.PubblicazioniValideAl(l));

                return rVal;
            });
        }

        public DettaglioPubblicazioneResponse GetDettaglioPubblicazioni(DettaglioPubblicazioneRequest dp, string idComune, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                DettaglioPubblicazioneResponse rVal = new DettaglioPubblicazioneResponse();

                dp.token = ws.Token;
                dp.software = software;

                rVal = ws.Service.DettaglioPubblicazione(dp);

                return rVal;
            });
        }
    }
}
