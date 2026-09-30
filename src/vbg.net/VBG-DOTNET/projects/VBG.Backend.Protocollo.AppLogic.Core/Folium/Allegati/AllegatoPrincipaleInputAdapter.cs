
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati
{
    public class AllegatoPrincipaleInputAdapter : IAllegatiAdapter
    {
        long _idProtocollo;
        string _nomeFile;

        public AllegatoPrincipaleInputAdapter(long idProtocollo, string nomeFile)
        {
            this._idProtocollo = idProtocollo;
            this._nomeFile = nomeFile;
        }

        public IEnumerable<AllegatoResponseType> Adatta()
        {
            if (String.IsNullOrEmpty(this._nomeFile))
            {
                return Enumerable.Empty<AllegatoResponseType>();
            }

            return new[] {
                new AllegatoResponseType
                {
                    IDBase = "0",
                    Serial = this._nomeFile,
                    Commento = $"Allegato Principale ({this._nomeFile})"
                }
            };

            /*
            var retVal = new List<AllOut>();
            retVal.Add(new AllOut
            {
                IDBase = "0",
                Serial = this._nomeFile,
                Commento = $"Allegato Principale ({this._nomeFile})"
            });

            return retVal;
            */
        }
    }
}
