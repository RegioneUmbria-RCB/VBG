using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.SiprWebTest.TipiDocumento;
using Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.TipiDocumento;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo
{
    public class LeggiProtocolloAdapter
    {
        readonly LeggiProtocolloService _wrapperLeggiProto;
        TipiDocumentoService _wrapperTipiDoc;
        readonly TipiDocumentoAdapter _tipiDocumentoAdapter;
        readonly string _numeroProtocollo;
        readonly string _annoProtocollo;
        readonly bool _usaWsTipiDoc;
        readonly ResolveDatiProtocollazioneService _resolveDatiProto;
        readonly DataBase _db;
        readonly ProtocolloLogs _log;

        public LeggiProtocolloAdapter(LeggiProtocolloService wrapper, string numeroProtocollo, string annoProtocollo, TipiDocumentoService wrapperTipiDoc, bool usaWsTipiDoc, ProtocolloLogs log, ResolveDatiProtocollazioneService resolveDatiProtocollazione, DataBase db)
        {
            _numeroProtocollo = numeroProtocollo;
            _annoProtocollo = annoProtocollo;
            _wrapperLeggiProto = wrapper;
            _wrapperTipiDoc = wrapperTipiDoc;
            _usaWsTipiDoc = usaWsTipiDoc;
            _resolveDatiProto = resolveDatiProtocollazione;
            _db = db;
            _log = log;
        }

        public DatiProtocolloLetto Adatta()
        { 
            string numero = String.Concat(_annoProtocollo, _numeroProtocollo.PadLeft(7, '0'));
            var response = _wrapperLeggiProto.LeggiProtocollo(new leggiDocumentoRequest { NumeroProtocollo = numero });
            var classifica = CodificaClassifica(response.Livello1Classificazione, response.Livello2Classificazione, response.Livello3Classificazione, response.Livello4Classificazione);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(response);
            var tipoDocumento = TipiDocumentoFactory.Create(_usaWsTipiDoc, _wrapperTipiDoc, response.CodiceTipoDocumento, _log, _resolveDatiProto, _db);

            var datiProtoLetto = new DatiProtocolloLetto
            {
                AnnoProtocollo = _annoProtocollo,
                NumeroProtocollo = _numeroProtocollo,
                DataProtocollo = response.DataProtocollo,
                Oggetto = response.Oggetto,
                Origine = response.Registro.ToString(),
                Classifica = classifica,
                Classifica_Descrizione = classifica,
                TipoDocumento = response.CodiceTipoDocumento,
                TipoDocumento_Descrizione = tipoDocumento.GetDescrizioneTipoDocumento(),
                InCaricoA = response.CodiceUfficioProtocollatore,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                MittentiDestinatari = mittentiDestinatari.MittentiDestintari
            };

            if (!String.IsNullOrEmpty(response.Annullato))
                datiProtoLetto.Annullato = response.Annullato.ToUpper();
            if (!String.IsNullOrEmpty(response.Motivazione))
                datiProtoLetto.MotivoAnnullamento = response.Motivazione;
            if (!String.IsNullOrEmpty(response.DataAnnullamento))
                datiProtoLetto.DataAnnullamento = response.DataAnnullamento;

            return datiProtoLetto;
        }

        private string CodificaClassifica(string cl1, string cl2, string cl3, string cl4)
        {
            var classifica = "";

            if (!String.IsNullOrEmpty(cl1))
                classifica += cl1;

            if (!String.IsNullOrEmpty(cl2))
                classifica += String.Concat(".", cl2);

            if (!String.IsNullOrEmpty(cl3))
                classifica += String.Concat(".", cl3);

            if (!String.IsNullOrEmpty(cl4))
                classifica += String.Concat(".", cl4);

            return classifica;
        }
    }
}
