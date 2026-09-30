using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    // ex LeggiProtoId
    public class LeggiProtocolloById : ILeggiProtocollo
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";
        string _idProtocollo;
        ProtocolloService _wrapper;

        // ex LeggiProtoId
        public LeggiProtocolloById(string idProtocollo, ProtocolloService wrapper)
        {
            _idProtocollo = idProtocollo;
            _wrapper = wrapper;
        }

        public DettaglioProtocolloResponse Leggi()
        {
            var idProtocolloAdapter = new IdProtocolloAdapter(_idProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var idProtocollo = idProtocolloAdapter.Adatta();

            var request = new DettaglioProtocolloRequest
            {
                Registrazione = new RegistrazioneID
                {
                    Id = new IdRegistrazioneProtocollo
                    {
                        ProgressivoDocumento = idProtocollo.ProgressivoDocumento,
                        ProgressivoMovimento = idProtocollo.ProgressivoMovimento
                    }
                }
            };

            var response = _wrapper.LeggiProtocollo(request, true);
            return response;
        }
    }
}
