using System.Xml;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.ExtractMulti
{

    public class ResponseInfo : ProxyResponseInfo
    {

        public Output_FilesUD ServiceResponse;

        public AllegatoResponseType[] ToAllOut()
        {
            if (!String.IsNullOrEmpty(this.WsError))
            {
                throw new Exception(this.WsError);
            }

            if (this.ServiceResponse != null && this.ServiceResponse.DatiFileEstratto != null)
            {
                return this.ServiceResponse.DatiFileEstratto.ToList().ConvertAll(new Converter<Output_FilesUDDatiFileEstratto, AllegatoResponseType>(AllegatiToAllOut)).ToArray();
            }
            return null;
        }

        private static AllegatoResponseType AllegatiToAllOut(Output_FilesUDDatiFileEstratto allegato)
        {
            return new AllegatoResponseType
            {
                IDBase = allegato.NroAllegato,
                Image = allegato.Content,
                Serial = (allegato.NomeFile != null) ? ((XmlNode[])allegato.NomeFile)[0].InnerText : "",
                Commento = allegato.DesOggetto,
                TipoFile = (allegato.TipoDoc != null) ? allegato.TipoDoc.Decodifica_Nome : "",
                ContentType = ""
            };
        }
    }
}
