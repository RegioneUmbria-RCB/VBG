using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD
{
    public class ResponseInfo : ProxyResponseInfo
    {

        public ServiceResponseInfo ServiceResponse;

        public DatiProtocolloResponseType ToDatiProtocolloRes()
        {
            if (!String.IsNullOrEmpty(this.WsError))
            {
                return new DatiProtocolloResponseType
                {
                    Errore = new ErroreProtocolloType
                    {
                        Descrizione = this.WsError
                    }
                };
            }

            if (this.ServiceResponse == null || this.ServiceResponse.RegistrazioneDataUD == null)
            {
                throw new InvalidOperationException("Non è possibile richiamare il metodo ToDatiProtocolloRes senza aver prima valorizzato ServiceResponse");
            }

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = this.ServiceResponse.RegistrazioneDataUD[0].AnnoReg,
                DataProtocollo = null,
                IdProtocollo = this.ServiceResponse.IdUD,
                NumeroProtocollo = this.ServiceResponse.RegistrazioneDataUD[0].NumReg,
                Warning = this.WarningMessage,
                Errore = null
            };
        }
    }
}
