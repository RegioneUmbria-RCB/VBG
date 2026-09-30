using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public ProtocollazioneResponseAdapter()
        {

        }

        public DatiProtocolloResponseType Adatta(RootObject response)
        {
            var data = new DateTime();
            var isDate = DateTime.TryParse(response.dataProtocollo, out data);
            if (!isDate)
                data = DateTime.Now;

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.annoProtocollo.ToString(),
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                IdProtocollo = response.id.ToString(),
                NumeroProtocollo = response.numeroProtocollo.ToString()
            };
        }
    }
}
