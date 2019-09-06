using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo
{
    public class DatiConfigurazioneProtocolloDefault : IDatiConfigurazioneInterventoProtocollo
    {
        IDatiProtocollo _metadati;
        VerticalizzazioniServiceWrapper _regole;
        ProtocolloLogs _logs;

        public DatiConfigurazioneProtocolloDefault(IDatiProtocollo metadati, VerticalizzazioniServiceWrapper regole, ProtocolloLogs logs)
        {
            this._metadati = metadati;
            this._regole = regole;
            this._logs = logs;
        }

        public DatiConfigurazioneProtocolloInfo GetDati()
        {
            this._logs.Info($"RECUPERO DEI DATI DI DEFAULT RELATIVI ALL'INTERVENTO DELLA PRATICA");

            return new DatiConfigurazioneProtocolloInfo
            {
                Classifica = this._metadati.ProtoIn.Classifica,
                Uo = this._metadati.Uo,
                Ruolo = this._metadati.Ruolo,
                TipoDocumento = this._metadati.ProtoIn.TipoDocumento,
                Regole = this._regole
            };
        }
    }
}
