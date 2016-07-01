using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class LeggiDocumentoResponseMetadataAdapter
    {


        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;
        GestioneDocumentaleService _wrapper;
        string _unitaDocumentale;

        public LeggiDocumentoResponseMetadataAdapter(GestioneDocumentaleService wrapper, string unitaDocumentale, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _wrapper = wrapper;
            _serializer = serializer;
            _logs = logs;
            _unitaDocumentale = unitaDocumentale;
        }

        public DatiProtocolloLetto Adatta()
        {
            var response = _wrapper.LeggiDocumento(_unitaDocumentale);

            if(_logs.IsDebugEnabled)
                _serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, response);

            var dic = response.ToDictionary(x => x.key, y => y.value);

            var mittenti = (Mittenti)_serializer.Deserialize(dic[LeggiDocumentoConstants.Mittenti], typeof(Mittenti));
            var destinatari = (Destinatari)_serializer.Deserialize(dic[LeggiDocumentoConstants.Destinatari], typeof(Destinatari));
            var factory = MittentiDestinatariFactory.Create(dic[LeggiDocumentoConstants.Flusso], mittenti.Items, destinatari.Items);

            var allegatiAdapter = new AllegatiAdapter(_unitaDocumentale, _wrapper, dic);
            var allegati = allegatiAdapter.Adatta();

            var codiceClassifica = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.CodiceTitolario]) ? dic[LeggiDocumentoConstants.CodiceClassifica] : dic[LeggiDocumentoConstants.CodiceTitolario];
            var numeroFascicolo = String.IsNullOrEmpty(dic[LeggiDocumentoConstants.NumeroFascicolo]) ? dic[LeggiDocumentoConstants.ProgressivoFascicolo] : dic[LeggiDocumentoConstants.NumeroFascicolo];

            var res = new DatiProtocolloLetto
            {
                NumeroProtocollo = dic[LeggiDocumentoConstants.NumeroProtocollo],
                DataProtocollo = DateTime.Parse(dic[LeggiDocumentoConstants.DataProtocollo]).ToString("dd/MM/yyyy"),
                AnnoProtocollo = dic[LeggiDocumentoConstants.AnnoProtocollo],
                Classifica = codiceClassifica,
                Classifica_Descrizione = String.Format("{0} - {1}", dic[LeggiDocumentoConstants.CodiceClassifica], dic[LeggiDocumentoConstants.DescrizioneClassifica]),
                IdProtocollo = dic[LeggiDocumentoConstants.IdProtocollo],
                Oggetto = dic[LeggiDocumentoConstants.Oggetto],
                TipoDocumento = dic[LeggiDocumentoConstants.TipoDocumento],
                TipoDocumento_Descrizione = dic[LeggiDocumentoConstants.TipoDocumentoDescrizione],
                Origine = factory.Flusso,
                MittentiDestinatari = factory.GetMittenteDestinatario(),
                InCaricoA = factory.InCaricoA,
                InCaricoA_Descrizione = factory.InCaricoADescrizione,
                Allegati = allegati,
                AnnoNumeroPratica = String.Format("{0}/{1}", numeroFascicolo, dic[LeggiDocumentoConstants.AnnoFascicolo])
            };

            return res;
        }
    }
}
