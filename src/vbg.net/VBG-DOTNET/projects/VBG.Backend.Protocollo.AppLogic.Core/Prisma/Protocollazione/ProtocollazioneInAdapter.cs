using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione
{
    public class ProtocollazioneInAdapter
    {
        private class Constants
        {
            public const string Uo = "uo";
            public const string TipoSmistamento = "tipoSmistamento";
            public const string Utente = "utente";
            public const string Smistamento = "smistamento";
            public const string DataDocumento = "dataDocumento";
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

        private IEnumerable<Parametro> GetParametro(IProtocollazionePrisma protocollazione)
        {
            yield return new Parametro { nome = Constants.Uo, valore = protocollazione.Uo ?? "" };

            if (!String.IsNullOrEmpty(protocollazione.Smistamento))
            {
                yield return new Parametro { nome = Constants.Smistamento, valore = $"{protocollazione.Smistamento}@@{this._info.TipoSmistamento}" };
            }

            if (!String.IsNullOrEmpty(this._info.TipoSmistamento))
            {
                yield return new Parametro { nome = Constants.TipoSmistamento, valore = this._info.TipoSmistamento };
            }

            if (this._info.ParametriRegola.ValorizzaDataDocumento)
            {
                yield return new Parametro { nome = Constants.DataDocumento, valore = DateTime.Now.ToString("dd/MM/yyyy") };
            }

            yield return new Parametro { nome = Constants.Utente, valore = this._info.ParametriRegola.Username };
        }
    }
}
