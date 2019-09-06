using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ApSystems.Allegati;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.DatiProtocollo;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione
{
    public class ProtocollazioneService
    {
        ILog logs = LogManager.GetLogger(typeof(ProtocollazioneService));
        IProtocollazione _factory;

        public ProtocollazioneService(IProtocollazione factory)
        {
            _factory = factory;
        }

        public DataSet CreaRequest(DatiProtocolloIn protoIn, bool escludiClassifica)
        {
            var ds = new protocolli();
            var r = ds.protocollo.NewprotocolloRow();

            r.tipologia = _factory.Flusso;
            r.oggetto = protoIn.Oggetto;
            r.annotazione = protoIn.TipoDocumento;

            if (!escludiClassifica)
                r.classificazione = protoIn.Classifica;

            logs.Info("RECUPERO DEI MITTENTI");
            /*var mittenti = */
            _factory.SetMittenti(ds.mittente);
            logs.Info("RECUPERO DEI MITTENTI AVVENUTO CON SUCCESSO");

            //foreach (var mittente in mittenti)
            //{
            //    var mitt = ds.mittente.NewmittenteRow();
            //    logs.InfoFormat("AGGIUNTA DEL MITTENTE CODICE: {0}, DESCRIZIONE: {1}", mittente.codice, mittente.descrizione);
            //    ds.mittente.AddmittenteRow(mittente);
            //    logs.InfoFormat("AGGIUNTA DEL MITTENTE CODICE: {0}, DESCRIZIONE: {1} AVVENUTO CON SUCCESSO", mittente.codice, mittente.descrizione);
            //}

            logs.Info("RECUPERO DEI DESTINATARI");
            _factory.SetDestinatari(ds.destinatario);
            logs.Info("RECUPERO DEI DESTINATARI AVVENUTO CON SUCCESSO");

            //foreach (var destinatario in destinatari)
            //{
            //    ds.destinatario.NewdestinatarioRow();
            //    logs.InfoFormat("AGGIUNTA DEL DESTINATARIO CODICE: {0}, DESCRIZIONE: {1}", destinatario.codice, destinatario.descrizione);
            //    ds.destinatario.AdddestinatarioRow(destinatario);
            //    logs.InfoFormat("AGGIUNTA DEL DESTINATARIO CODICE: {0}, DESCRIZIONE: {1} AVVENUTO CON SUCCESSO", destinatario.codice, destinatario.descrizione);
            //}

            logs.Info("AGGIUNTA DELLA RIGA AL PROTOCOLLO");
            ds.protocollo.AddprotocolloRow(r);
            logs.Info("AGGIUNTA DELLA RIGA AL PROTOCOLLO AVVENUTA CON SUCCESSO");

            return ds;
        }

        public DatiProtocolloRes Protocolla(ProtocollazioneServiceWrapper protoSrv, DataSet request)
        {
            return _factory.Protocolla(request, protoSrv);
        }

        public void InserisciAllegati(IEnumerable<ProtocolloAllegati> allegati, string codiceProtocollo, string numeroProtocollo, string dataProtocollo, AllegatiServiceWrapper allegatiSrv)
        {
            foreach (var allegato in allegati)
                _factory.InserisciAllegato(codiceProtocollo, numeroProtocollo, dataProtocollo, allegato.OGGETTO, allegato.NOMEFILE, allegato.CODICEOGGETTO, allegatiSrv);
        }
    }
}
