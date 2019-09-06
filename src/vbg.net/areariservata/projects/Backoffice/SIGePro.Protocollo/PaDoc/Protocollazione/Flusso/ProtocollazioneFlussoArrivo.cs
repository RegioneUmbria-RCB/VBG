using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.PaDoc.Protocollazione.PersonaSegnatura;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoArrivo : IProtocollazioneFlusso
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;

        public ProtocollazioneFlussoArrivo(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            _datiProto = datiProto;
            _vert = vert;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO_DOCAREA; }
        }

        public Destinazione[] GetDestinazioni()
        {
            return new Destinazione[]
            { 
                new Destinazione
                {
                    confermaRicezione = DestinazioneConfermaRicezione.si,
                    IndirizzoTelematico = new IndirizzoTelematico
                    {
                        tipo = IndirizzoTelematicoTipo.smtp,
                        Text = new string[] { _datiProto.Amministrazione.PEC }
                    },
                    Destinatario = new Destinatario[]
                    { 
                        new Destinatario
                        {
                            Items = new object[]
                            { 
                                new Amministrazione
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
                            }
                        }
                    }
                }
            };
            
        }

        public Mittente GetMittente()
        {
            var persona = PersonaFactory.Create(_datiProto);
            var amministrazione = new Amministrazione
            {
                Denominazione = new Denominazione { Text = new string[] { persona.Denominazione } }
                //Items = new object[] { persona.GetIndirizzoPostale(), persona.GetPersona() }
            };

            return  new Mittente
            {
                Amministrazione = amministrazione,
                AOO = new AOO { CodiceAOO = new CodiceAOO { Text = new string[] { _vert.CodiceAoo } } }
            };
        }


        public IndirizzoTelematico IndirizzoTelematicoOrigine
        {
            get 
            {
                var persona = PersonaFactory.Create(_datiProto);
                return persona.IndirizzoTelematico;
            }
        }
    }
}
