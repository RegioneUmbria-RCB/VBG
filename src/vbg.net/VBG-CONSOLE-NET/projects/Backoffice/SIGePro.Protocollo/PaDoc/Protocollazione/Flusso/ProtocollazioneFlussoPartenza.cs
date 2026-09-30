using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoPartenza : IProtocollazioneFlusso
    {
        IDatiProtocollo _datiProto;
        public ProtocollazioneFlussoPartenza(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA_DOCAREA; }
        }

        public Amministrazione GetAmministrazioneSegnatura()
        {
            return new Amministrazione
            {
                Denominazione = new Denominazione { Text = new string[] { _datiProto.Amministrazione.AMMINISTRAZIONE } },
                Items = new object[] { new UnitaOrganizzativa { Identificativo = new Identificativo { Text = new string[] { _datiProto.Uo } } } }
            };
        }


        public Destinazione[] GetDestinazioni()
        {
            var resAmm = _datiProto.AmministrazioniEsterne.Select(x => x.ToDestinatarioAmministrazione());
            var resAnag = _datiProto.AnagraficheProtocollo.Select(x => x.ToDestinatarioAnagrafica());

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
                            Identificativo = new Identificativo { Text = new string[] { _datiProto.Amministrazione.PROT_UO } },
                            Denominazione = new Denominazione { Text = new string[] { _datiProto.Amministrazione.AMMINISTRAZIONE } }
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
