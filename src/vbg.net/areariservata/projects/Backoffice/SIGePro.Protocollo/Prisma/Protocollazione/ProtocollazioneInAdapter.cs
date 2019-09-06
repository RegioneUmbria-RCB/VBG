using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneInAdapter
    {
        private class Constants
        {
            public const string Uo = "uo";
            public const string TipoSmistamento = "tipoSmistamento";
            public const string Utente = "utente";
            public const string Smistamento = "smistamento";
        }


        ProtocolloSerializer _serializer;
        ProtocollazioneInfo _info;

        public ProtocollazioneInAdapter(ProtocolloSerializer serializer, ProtocollazioneInfo info)
        {
            this._serializer = serializer;
            this._info = info;
        }

        public DocAreaSegnaturaInput Adatta()
        {
            var factory = ProtocollazioneFactory.Create(this._info);

            var allegati = new ProtocollazioneAllegatiAdapter();
            var descrizione = allegati.Adatta(this._info.ProtocollazioneSrv, this._info.Allegati, this._info.ParametriRegola.TipoDocumentoPrincipale, this._info.ParametriRegola.TipoDocumentoAllegato);

            return new DocAreaSegnaturaInput
            {
                Intestazione = new Intestazione
                {
                    Classifica = new Classifica { CodiceTitolario = this._info.Classifica },
                    Oggetto = this._info.Oggetto,
                    Identificatore = new Identificatore
                    {
                        Flusso = factory.Flusso,
                        CodiceAmministrazione = this._info.ParametriRegola.CodiceEnte,
                        CodiceAOO = this._info.ParametriRegola.CodiceAoo,
                        DataRegistrazione = "0",
                        NumeroRegistrazione = "0"
                    },
                    Mittente = factory.GetMittente(),
                    Destinatario = factory.GetDestinatario()
                },
                Descrizione = descrizione,
                ApplicativoProtocollo = new ApplicativoProtocollo
                {
                    nome = this._info.ParametriRegola.ApplicativoProtocollo,
                    Parametro = GetParametro(factory).ToArray()
                }
            };
        }

        private IEnumerable<Parametro> GetParametro(IProtocollazione protocollazione)
        {
            yield return new Parametro { nome = Constants.Uo, valore = protocollazione.Uo ?? "" };

            if (!String.IsNullOrEmpty(protocollazione.Smistamento))
            {
                yield return new Parametro { nome = Constants.Smistamento, valore = $"{protocollazione.Smistamento}@@{this._info.TipoSmistamento}" };
            }

            yield return new Parametro { nome = Constants.Utente, valore = this._info.ParametriRegola.Username };
        }
    }
}
