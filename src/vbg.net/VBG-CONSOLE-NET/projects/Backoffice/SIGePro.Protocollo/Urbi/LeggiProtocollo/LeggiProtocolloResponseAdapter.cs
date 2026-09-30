using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Urbi.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.WsDataClass;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLetto Adatta(LeggiProtocolloResponse response, DataBase db)
        {
            var allegati = response.GetAllegati();
            var corrispondenti = response.GetCorrispondenti();
            var ufficiMittenti = response.GetUfficiMittenti();
            var ufficiDestinatari = response.GetUfficiDestinatari();

            var proto = response.ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo;
            var mittDest = MittentiDestinatariFactory.Create(proto.Sezione, corrispondenti, ufficiMittenti, ufficiDestinatari);

            var retVal = new DatiProtocolloLetto
            {
                AnnoProtocollo = proto.Anno,
                DataProtocollo = DateTime.ParseExact(proto.DataProtocollo, "dd-MM-yyyy", CultureInfo.InvariantCulture).ToString("dd/MM/yyyy"),
                InCaricoA = mittDest.InCaricoA,
                InCaricoA_Descrizione = mittDest.InCaricoADescrizione,
                MittentiDestinatari = mittDest.GetMittenteDestinatario(),
                NumeroProtocollo = proto.Numero,
                Oggetto = proto.Oggetto,
                Origine = mittDest.Flusso,
                TipoDocumento = proto.TipoDoc,
                TipoDocumento_Descrizione = proto.TipoDoc,
                Classifica_Descrizione = response.GetClassifica()
            };

            if (allegati != null)
            {
                retVal.Allegati = allegati.Select(x => new AllOut
                {
                    IDBase = String.Format("{0}.{1}.{2}", x.IdTestata, x.PrgAllegato, x.IdVersione),
                    Serial = x.NomeFile,
                    TipoFile = x.Estensione,
                    Versione = x.IdVersione,
                    ContentType = new OggettiMgr(db).GetContentType(x.NomeFile)
                }).ToArray();
            }

            return retVal;
        }
    }
}
