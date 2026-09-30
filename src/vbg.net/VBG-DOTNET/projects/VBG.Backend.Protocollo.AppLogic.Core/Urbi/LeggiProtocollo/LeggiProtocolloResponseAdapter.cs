using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo.MittentiDestinatari;
using PersonalLib2.Data;
using System;
using System.Globalization;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public static DatiProtocolloLettoResponseType Adatta(LeggiProtocolloResponse response, DataBase db)
        {
            var allegati = response.GetAllegati();
            var corrispondenti = response.GetCorrispondenti();
            var ufficiMittenti = response.GetUfficiMittenti();
            var ufficiDestinatari = response.GetUfficiDestinatari();
            var fascicoli = response.GetFascicoli();

            var proto = response.ResponseWs.getInterrogazioneProtocollo_Result.SEQ_Protocollo.Protocollo;
            var mittDest = MittentiDestinatariFactory.Create(proto.Sezione, corrispondenti, ufficiMittenti, ufficiDestinatari);



            var retVal = new DatiProtocolloLettoResponseType
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
                Classifica_Descrizione = response.GetClassifica(),
                NumeroPratica = fascicoli.Any() ? fascicoli.First().Numero : "",
                AnnoNumeroPratica = fascicoli.Any() ? $"{fascicoli.First().Anno}/{fascicoli.First().Numero}" : "" 

            };

            if (allegati != null)
            {
                retVal.Allegati = allegati.Select(x => new AllegatoResponseType
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
