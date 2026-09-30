using VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati
{
    public class AltriAllegatiOutputAdapter : IAllegatiAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;
        long _idProtocollo;

        public AltriAllegatiOutputAdapter(ProtocollazioneServiceWrapper wsWrapper, long idProtocollo)
        {
            this._wsWrapper = wsWrapper;
            this._idProtocollo = idProtocollo;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllegatoResponseType> Adatta()
        {
            var allegati = this._wsWrapper.LeggiAllegati(this._idProtocollo);

            if (allegati == null)
            {
                return Enumerable.Empty<AllegatoResponseType>();
            }

            return allegati.Select(x => new AllegatoResponseType
            {
                IDBase = x.id.ToString(),
                Serial = x.nomeFile,
                Commento = $"{x.descrizione} ({x.nomeFile})",
            });
        }

        #endregion
    }
}
