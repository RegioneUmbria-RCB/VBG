using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoPartenza : IProtocollazioneFlusso
    {
        private readonly IDatiProtocollo _datiProto;
        public ProtocollazioneFlussoPartenza(IDatiProtocollo datiProto)
        {
            this._datiProto = datiProto;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA_DOCAREA; }
        }

        public Amministrazione GetAmministrazioneSegnatura()
        {
            return new Amministrazione
            {
                Denominazione = new Denominazione { Text = new string[] { this._datiProto.Amministrazione.AMMINISTRAZIONE } },
                Items = new object[] { new UnitaOrganizzativa { Identificativo = new Identificativo { Text = new string[] { this._datiProto.Uo } } } }
            };
        }


        public Destinazione[] GetDestinazioni()
        {
            var resAmm = this._datiProto.AmministrazioniEsterne.Select(x => x.ToDestinatarioAmministrazione());
            var resAnag = this._datiProto.AnagraficheProtocollo.Select(x => x.ToDestinatarioAnagrafica());

            return new Destinazione[]
            {
                new Destinazione
                {
                    confermaRicezione = DestinazioneConfermaRicezione.si,
                    Destinatario = resAmm.Union(resAnag).ToArray()
                }
            };
        }

        public Mittente GetMittente()
        {
            return new Mittente
            {
                Amministrazione = new Amministrazione
                {
                    Items = new object[]
                    {
                        new UnitaOrganizzativa
                        {
                            Identificativo = new Identificativo { Text = new string[] { this._datiProto.Amministrazione.PROT_UO } },
                            Denominazione = new Denominazione { Text = new string[] { this._datiProto.Amministrazione.AMMINISTRAZIONE } }
                        }
                    }
                }
            };
        }

        public IndirizzoTelematico IndirizzoTelematicoOrigine
        {
            get { return null; }
        }
    }
}
