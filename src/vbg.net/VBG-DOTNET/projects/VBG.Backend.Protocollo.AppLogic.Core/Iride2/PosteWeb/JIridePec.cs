using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.PosteWeb
{
    internal class JIridePec : IPec
    {
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logs;
        private readonly IBindingFactory _bindingFactory;
        private readonly string _codiceAoo;
        private readonly string[] _destinatari;
        private readonly IEnumerable<string> _seriali;
        private readonly bool _invioInteroperabile;

        private const string SEGNATURA_FILENAME_REQUEST = "SegnaturaPECRequest.xml";

        internal JIridePec(string[] destinatari, IEnumerable<string> seriali, string codiceAoo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, bool invioInteroperabile)
        {
            logs.InfoFormat("ENTRATO SU J_IRIDE");
            this._logs = logs;
            this._serializer = serializer;
            this._bindingFactory = bindingFactory;
            this._codiceAoo = codiceAoo;
            this._destinatari = destinatari;
            this._seriali = seriali;
            this._invioInteroperabile = invioInteroperabile;
        }

        public string Invia(string url, string proxyAddress, string idDocumento, string oggetto, string corpo, string mittente, string utente, string ruolo, string codiceAmministrazione)
        {
            var allegatiAdapter = new AllegatiAdapter();
            var allegati = allegatiAdapter.Adatta(this._seriali);

            var messaggio = new MessaggioIn
            {
                DocId = idDocumento,
                OggettoMail = oggetto,
                Ruolo = ruolo,
                TestoMail = corpo,
                Utente = utente,
                MittenteMail = mittente,
                DestinatariMail = this._destinatari,
                DMSerialPrincipale = allegati.AllegatoPrincipale,
                DMSerialAllegati = allegati.AllegatiSecondari
            };

            if (this._invioInteroperabile)
            {
                messaggio.InvioInteroperabile = "S";
            }

            var segnatura = this._serializer.Serialize(SEGNATURA_FILENAME_REQUEST, messaggio);

            var service = new PECIrideService(url, proxyAddress, this._logs, this._serializer, this._bindingFactory);
            return service.InviaPEC(segnatura, codiceAmministrazione, this._codiceAoo);
        }
    }
}
