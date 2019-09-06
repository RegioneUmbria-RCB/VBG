using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLetto Adatta(Segnatura response, ProtocolloLogs logs, DataBase db)
        {
            var leggiProtoFactory = LeggiProtocolloFactory.Create(response);

            var documenti = response.Descrizione.Documento.Select(x => new AllOut
            {
                IDBase = x.Nome,
                Serial = x.Nome,
                Commento = x.Nome,
                ContentType = new OggettiMgr(db).GetContentType(x.Nome)
            }).ToList();

            if(response.Descrizione.Allegati != null && response.Descrizione.Allegati.Documento != null && response.Descrizione.Allegati.Documento.Length  > 0)
            {
                var allegati = response.Descrizione.Allegati.Documento.Select(x => new AllOut
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

            return new DatiProtocolloLetto
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
