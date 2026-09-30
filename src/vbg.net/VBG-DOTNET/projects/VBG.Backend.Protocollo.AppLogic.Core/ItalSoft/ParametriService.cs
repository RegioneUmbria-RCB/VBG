using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.Token;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft
{
    public class ParametriService
    {
        public Parametri Parametri { get; internal set; }

        public ParametriService(ILog logger, ProtocolloSerializer serializer, IBindingFactory bindingFactory, VerticalizzazioneProtocolloItalSoft vertProtocolloItalSoft, VerticalizzazioneProtocolloAttivo vertProtocolloAttivo)
        {

            var token = new TokenService(logger, serializer, bindingFactory, vertProtocolloItalSoft.UrlProtocollazione).GetToken(new GetTokenRequest
            {
                DomaniCode = vertProtocolloItalSoft.DomainCode,
                UserName = vertProtocolloItalSoft.UserName,
                UserPassword = vertProtocolloItalSoft.UserPassword
            });


            this.Parametri = new Parametri
            {
                CodiceUfficio = vertProtocolloItalSoft.CodiceUfficio,
                CorpoMail = null,
                DomainCode = vertProtocolloItalSoft.DomainCode,
                IndirizziEmailAbilitati = vertProtocolloAttivo.AbilitaIndirizziEmail,
                OggettoMail = null,
                Token = token,
                UrlProtocollazione = vertProtocolloItalSoft.UrlProtocollazione,
                UrlFascicolazione = vertProtocolloItalSoft.UrlFascicolazione
            };
        }
    }
}
