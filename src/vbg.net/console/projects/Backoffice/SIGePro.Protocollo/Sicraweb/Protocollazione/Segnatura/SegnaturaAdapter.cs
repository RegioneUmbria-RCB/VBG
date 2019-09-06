using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Sicraweb.Verticalizzazioni;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.Sicraweb.Services;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Allegati;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura
{
    public class SegnaturaAdapter
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        DatiProtocolloIn _protoIn;
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;
        ProtocolloService _wrapper;

        public string SegnaturaXml { get; private set; }

        public SegnaturaAdapter(ProtocolloLogs logs, ProtocolloSerializer serializer, DatiProtocolloIn protoIn, IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ProtocolloService wrapper)
        {
            _protoIn = protoIn;
            _datiProto = datiProto;
            _vert = vert;
            _logs = logs;
            _serializer = serializer;
            _wrapper = wrapper;
        }

        public Segnatura Adatta()
        {
            var segnatura = new Segnatura();
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_protoIn.Flusso, _datiProto, _vert.CodiceAmministrazione, _vert.CodiceAoo);

            var intestazione = new Intestazione
            {
                Classifica = new Classifica { CodiceTitolario = _protoIn.Classifica },
                Oggetto = _protoIn.Oggetto,
                Identificatore = new Identificatore
                {
                    CodiceAmministrazione = _vert.CodiceAmministrazione,
                    CodiceAOO = _vert.CodiceAoo,
                    Flusso = mittentiDestinatari.FlussoProtocollo
                }
            };

            if (_vert.UsaMonoMittDest)
            {
                intestazione.Mittente = mittentiDestinatari.MittentePrincipale;
                intestazione.Destinatario = mittentiDestinatari.DestinatarioPrincipale;
            }
            else
            {
                intestazione.Mittente = mittentiDestinatari.MittentePrincipale;
                intestazione.Destinatario = mittentiDestinatari.DestinatarioPrincipale;
                intestazione.MittentiMulti = mittentiDestinatari.GetMittenti();
                intestazione.DestinatariMulti = mittentiDestinatari.GetDestinatari();
            }

            segnatura.Intestazione = intestazione;

            if (_protoIn.Allegati.Count == 0 && _vert.InviaSegnatura)
            {
                var allegatoFittizioBuilder = new AllegatoFittizioBuilder(segnatura, _logs, _serializer);
                _protoIn.Allegati.Add(allegatoFittizioBuilder.Build());
            }

            var allegatiAdapter = new AllegatiAdapter(_wrapper, _protoIn.Allegati);
            segnatura.Descrizione = allegatiAdapter.Adatta();

            return segnatura;
        }
    }
}
