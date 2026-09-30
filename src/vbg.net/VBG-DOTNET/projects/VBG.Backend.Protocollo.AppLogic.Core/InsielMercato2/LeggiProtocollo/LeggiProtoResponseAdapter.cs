using PersonalLib2.Data;
using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo
{
    public class LeggiProtoResponseAdapter
    {
        protocolDetail _response;
        DataBase _db;

        public LeggiProtoResponseAdapter(protocolDetail response, DataBase db)
        {
            _response = response;
            _db = db;
        }

        public DatiProtocolloLettoResponseType Adatta()
        {
            var classifica = ClassificaResponseAdapter.Adatta(_response);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            return new DatiProtocolloLettoResponseType 
            {  
                AnnoProtocollo = _response.recordIdentifier.year.ToString(),
                NumeroProtocollo = _response.recordIdentifier.number.ToString(),
                DataProtocollo = _response.recordIdentifier.registrationDate.ToString("dd/MM/yyyy"),
                DataInserimento = _response.recordIdentifier.registrationDate.ToString("dd/MM/yyyy"),
                IdProtocollo = String.Concat(_response.recordIdentifier.documentProg.ToString(), PROTOCOLLO_INSIELMERCATO.Constants.SEPARATORE_ID_PROTOCOLLO, _response.recordIdentifier.moveProg.ToString()),
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                Classifica = classifica.Codice,
                Classifica_Descrizione = classifica.Descrizione,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Origine = mittentiDestinatari.Flusso,
                Oggetto = _response.subjectDocument,
                Allegati = AllegatiResponseAdapter.Adatta(_response, _db),
                AnnoNumeroPratica = LeggiProtocolliCollegatiAdapter.Adatta(_response)
            };
        }
    }
}
