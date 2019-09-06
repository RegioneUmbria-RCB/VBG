using Init.SIGePro.Manager.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Civilia
{
    public class VerticalizzazioniParametriServiceWrapper
    {
        public string UrlOAuth { get; private set; }
        public string UrlWs { get; private set; }
        public string ClientId { get; private set; }
        public string Secret { get; private set; }
        public string CodiceAoo { get; private set; }
        public string UrlWrapperServiceOAuth { get; private set; }
        public string UrlWsResource { get; private set; }

        VerticalizzazioneProtocolloCivilia _vert;

        public VerticalizzazioniParametriServiceWrapper(VerticalizzazioneProtocolloCivilia vert)
        {
            if (!vert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_CIVILIA NON E' ATTIVA");

            _vert = vert;

            EstraiParametri();
        }

        private void VerificaIntegritaParametri()
        {
            if (String.IsNullOrEmpty(_vert.UrlOauth))
            {
                throw new Exception("PARAMETRO URL_OAUTH NON VALORIZZATO");
            }

            if (String.IsNullOrEmpty(_vert.ClientId))
            {
                throw new Exception("PARAMETRO CLIENT_ID NON VALORIZZATO");
            }

            if (String.IsNullOrEmpty(_vert.Secret))
            {
                throw new Exception("PARAMETRO SECRET NON VALORIZZATO");
            }

            if (String.IsNullOrEmpty(_vert.UrlWs))
            {
                throw new Exception("PARAMETRO URL_WS NON VALORIZZATO");
            }

            if (String.IsNullOrEmpty(_vert.UrlWrapperServiceOAuth))
            {
                throw new Exception("PARAMETRO URL_SERVICE_OAUTH NON VALORIZZATO");
            }

            if (String.IsNullOrEmpty(_vert.UrlWsResource))
            {
                throw new Exception("PARAMETRO URL_WSRESOURCE NON VALORIZZATO");
            }
        }

        private void EstraiParametri()
        {
            VerificaIntegritaParametri();

            this.UrlOAuth = _vert.UrlOauth;
            this.UrlWs = _vert.UrlWs;
            this.ClientId = _vert.ClientId;
            this.Secret = _vert.Secret;
            this.CodiceAoo = _vert.CodiceAoo;
            this.UrlWrapperServiceOAuth = _vert.UrlWrapperServiceOAuth;
            this.UrlWsResource = _vert.UrlWsResource;
        }
    }
}
