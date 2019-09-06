using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public class SegnaturaAdapter
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniWrapper _vert;
        IEnumerable<IAnagraficaAmministrazione> _mittDest;
        ProtocollazioneServiceWrapper _wrapper;
        ProtocolloSerializer _serializer;
        IProtocollazioneAdapter _protoFlussoAdapter;

        public SegnaturaAdapter(IDatiProtocollo datiProto, IEnumerable<IAnagraficaAmministrazione> mittDest, VerticalizzazioniWrapper vert, ProtocollazioneServiceWrapper wrapper, ProtocolloSerializer serializer)
        {
            _datiProto = datiProto;
            _vert = vert;
            _mittDest = mittDest;
            _wrapper = wrapper;
            _serializer = serializer;

            _protoFlussoAdapter = ProtocollazioneAdapterFactory.Create(_mittDest, _datiProto, _vert);

        }

        public Segnatura Adatta()
        {
            var allegati = new List<Documento>();

            foreach (var x in _datiProto.ProtoIn.Allegati)
            {
                var id = _wrapper.InserisciDocumento(x);
                if (id != -1)
                {
                    allegati.Add(new Documento
                    {
                        id = id.ToString(),
                        nome = x.NOMEFILE,
                        TitoloDocumento = new TitoloDocumento { Text = new string[] { x.Descrizione } }
                    });
                }
            }

            object oDescrizione = new TestoDelMessaggio { id = "DOCUMENTI NON PRESENTI" };

            if (allegati.Count() > 0)
                oDescrizione = allegati.First();

            var segnatura = new Segnatura
            {
                Intestazione = new Intestazione
                {
                    Identificatore = new Identificatore
                    {
                        CodiceAmministrazione = new CodiceAmministrazione { Text = new string[] { _vert.CodiceAmministrazione } },
                        CodiceAOO = new CodiceAOO { Text = new string[] { _vert.CodiceAoo } },
                        NumeroRegistrazione = new NumeroRegistrazione { Text = new string[] { "0" } },
                        DataRegistrazione = new DataRegistrazione { Text = new string[] { DateTime.Now.ToString("dd/MM/yyyy") } }
                    },
                    Origine = _protoFlussoAdapter.GetMittente(),
                    Destinazione = _protoFlussoAdapter.GetDestinatari(),
                    Oggetto = new Oggetto { Text = new string[] { _datiProto.ProtoIn.Oggetto } },
                },
                Descrizione = new Descrizione { Item = oDescrizione }
            };

            if (allegati.Count() > 1)
                segnatura.Descrizione.Allegati = allegati.Skip(1).ToArray();

            var piuInfo = _protoFlussoAdapter.GetCustomMetadata();
            var xmlPiuInfo = _serializer.Serialize(ProtocolloLogsConstants.PiuInfo, piuInfo, Validation.ProtocolloValidation.TipiValidazione.STUDIOK_SEGNATURA);

            segnatura.PiuInfo = new PiuInfo { MetadatiInterni = xmlPiuInfo };

            return segnatura;
        }
    }
}