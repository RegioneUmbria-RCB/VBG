using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        DettaglioProtocolloResponse _response;
        ProtocolloLogs _logs;

        public LeggiProtocolloOutputAdapter(DettaglioProtocolloResponse response, ProtocolloLogs logs)
        {
            this._response = response;
            this._logs = logs;
        }

        public DatiProtocolloLettoResponseType Adatta(bool usaLivelliClassifica)
        {
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            string codiceClassifica = "";

            if (_response.Classifiche != null && _response.Classifiche.Length > 0)
            {
                var classificaAdapter = new ClassificaAdapter();
                codiceClassifica = classificaAdapter.EstraiClassifica(usaLivelliClassifica, _response.Classifiche[_response.Classifiche.Length - 1]);
            }
            else
            {
                if (_response.Fascicoli != null && _response.Fascicoli.Count() > 0)
                {
                    var classificaAdapter = new ClassificaAdapter();
                    codiceClassifica = classificaAdapter.EstraiClassifica(usaLivelliClassifica, _response.Fascicoli.ToList().Last().CodiceRegistro);
                }
            }

            string descrizioneClassifica = _response.Classifiche.Length == 0 ? "" : $"[{codiceClassifica}] {_response.Classifiche[_response.Classifiche.Length - 1].Descrizione}";
            string idProtocollo = String.Format("{0};{1}", _response.InfoGenerali.ProtoProgDoc, _response.InfoGenerali.ProtoProgMovi);

            var retVal = new DatiProtocolloLettoResponseType
            {
                NumeroProtocollo = _response.InfoGenerali.ProtoNumProt.Value.ToString(),
                DataProtocollo = _response.InfoGenerali.ProtoDataOraAgg.Value.ToString("dd/MM/yyyy"),
                AnnoProtocollo = _response.InfoGenerali.ProtoAnnoProt.Value.ToString(),
                IdProtocollo = idProtocollo,
                TipoDocumento = _response.InfoGenerali.DocCodTipoDoc,
                TipoDocumento_Descrizione = _response.InfoGenerali.TipoDocDescTipoDoc,
                Oggetto = _response.InfoGenerali.DocDescOgge,
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                Classifica = codiceClassifica,
                Classifica_Descrizione = String.IsNullOrEmpty(descrizioneClassifica) ? codiceClassifica : descrizioneClassifica,
                Annullato = _response.InfoGenerali.ProtoStato.GetValueOrDefault(0) == 1 ? IsAnnullato.si.ToString() : IsAnnullato.no.ToString(),
                Origine = _response.InfoGenerali.ProtoApProt.ConvertToString(),
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = _response.Documenti.Select(x => new AllegatoResponseType
                {
                    IDBase = x.IdDoc.ToString(),
                    Serial = x.Nome,
                    TipoFile = x.TipoDoc,
                    Commento = x.Nome
                }).ToArray(),
            };

            if (this._response.Fascicoli != null && this._response.Fascicoli.Count() > 0)
            {
                retVal.AnnoNumeroPratica = $"{codiceClassifica}/{this._response.Fascicoli.First().Anno}/{this._response.Fascicoli.First().Numero}";
                retVal.NumeroPratica = this._response.Fascicoli.First().Numero.ToString();
            }

            return retVal;
        }
    }
}