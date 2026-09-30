using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.CreaCopie
{
    public class CreaCopiePerAmministrazioniInterneRequestBuilder : IJIrideDocInRequestBuilder<CreaCopieInfo>
    {
        private readonly ParametriRegoleInfo _vert;
        private readonly ProtocolloOutXml _protocollo;
        private readonly UODestinatariaXml[] _uoDestinatarie;
        private readonly string _operatore;
        private readonly string _ruolo;

        public CreaCopiePerAmministrazioniInterneRequestBuilder(ParametriRegoleInfo verticalizzazione, ProtocolloOutXml protocollo, ProtocolloAmministrazioni mittente, List<ProtocolloAmministrazioni> destinatari, string operatore)
        {
            this._vert = verticalizzazione;
            this._protocollo = protocollo;

            var ammList = destinatari
                            .Where(amm => !String.IsNullOrEmpty(amm.PROT_UO) && !String.IsNullOrEmpty(amm.PROT_RUOLO))
                            .ToList();

            this._operatore = operatore;

            this._ruolo = mittente.PROT_RUOLO;

            if (ammList.Count == 0) { return; }

            this._uoDestinatarie = ammList
                                    .Select(x => new UODestinatariaXml
                                    {
                                        Carico = x.PROT_UO,
                                        Data = DateTime.Now.ToString("dd/MM/yyyy"),
                                        TipoUO = "UO",
                                        NumeroCopie = "1"
                                    })
                                    .ToArray();

        }
        public CreaCopieInfo Build()
        {
            return new CreaCopieInfo
            {
                AOO = this._vert.Aoo,
                CodiceAmministrazione = this._vert.CodiceAmministrazione,
                Request = new CreaCopieInXml
                {
                    AnnoProtocollo = this._protocollo.AnnoProtocollo.ToString(),
                    NumeroProtocollo = this._protocollo.NumeroProtocollo.ToString(),
                    IdDocumento = this._protocollo.IdDocumento.ToString(),
                    UODestinatarie = this._uoDestinatarie,
                    Utente = this._operatore,
                    Ruolo = this._ruolo
                },
                Url = this._vert.Url,
            };
        }
    }
}
