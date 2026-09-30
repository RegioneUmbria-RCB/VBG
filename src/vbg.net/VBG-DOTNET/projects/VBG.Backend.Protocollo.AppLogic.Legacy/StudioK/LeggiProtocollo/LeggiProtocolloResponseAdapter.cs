using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLettoResponseType Adatta(Segnatura response, ProtocolloLogs logs, DataBase db)
        {
            var leggiProtoFactory = LeggiProtocolloFactory.Create(response);

            var documenti = response.Descrizione.Documento.Select(x => new AllegatoResponseType
            {
                IDBase = x.Nome,
                Serial = x.Nome,
                Commento = x.Nome,
                ContentType = new OggettiMgr(db).GetContentType(x.Nome)
            }).ToList();

            if(response.Descrizione.Allegati != null && response.Descrizione.Allegati.Documento != null && response.Descrizione.Allegati.Documento.Length  > 0)
            {
                var allegati = response.Descrizione.Allegati.Documento.Select(x => new AllegatoResponseType
                {
                    IDBase = x.Nome,
                    Serial = x.Nome,
                    Commento = x.Nome,
                    ContentType = new OggettiMgr(db).GetContentType(x.Nome)
                }).ToList();
                documenti.AddRange(allegati);
            }


            DateTime dt;
            string data = response.Intestazione.Identificatore.DataRegistrazione;
            string anno = "";

            var seData = DateTime.TryParse(response.Intestazione.Identificatore.DataRegistrazione, out dt);

            if (!seData)
                logs.WarnFormat("LA DATA DI PROTOCOLLAZIONE NON HA UN FORMATO VALIDO, {0}", response.Intestazione.Identificatore.DataRegistrazione);
            else
            {
                data = dt.ToString("dd/MM/yyyy");
                anno = dt.ToString("yyyy");
            }

            return new DatiProtocolloLettoResponseType
            {
                Oggetto = response.Intestazione.Oggetto,
                InCaricoA = leggiProtoFactory.InCaricoA,
                InCaricoA_Descrizione = leggiProtoFactory.InCaricoADescrizione,
                AnnoProtocollo = anno,
                DataProtocollo = data,
                NumeroProtocollo = response.Intestazione.Identificatore.NumeroRegistrazione,
                Origine = leggiProtoFactory.Flusso,
                MittentiDestinatari = leggiProtoFactory.GetMittenteDestinatario(),
                Allegati = documenti.ToArray()
            };
        }
    }
}
