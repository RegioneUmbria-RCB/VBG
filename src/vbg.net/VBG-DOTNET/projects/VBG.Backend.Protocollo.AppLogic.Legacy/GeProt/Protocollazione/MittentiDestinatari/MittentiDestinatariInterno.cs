using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : IMittentiDestinatari
    {
        private readonly IDatiProtocollo _datiProto;
        private readonly VerticalizzazioniConfiguration _vert;

        public MittentiDestinatariInterno(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            _datiProto = datiProto;
            _vert = vert;
        }

        public Mittente GetMittente()
        {
            return new Mittente
            {
                Items = new object[]
                {
                    _datiProto.Amministrazione.ToAmministrazioneInterna(_vert),
                    new AOO
                    {
                        CodiceAOO = new CodiceAOO{ Text = new string[]{ _vert.CodiceAoo } },
                        Denominazione = new Denominazione{ Text = new string[] { _vert.DenominazioneAoo } }
                    }
                }
            };
        }

        public Destinazione[] GetDestinatari()
        {
            return new Destinazione[]
            {
                new Destinazione
                {
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { String.IsNullOrEmpty(_vert.IndirizzoTelematico) ? GeProtConstants.IndirizzoTelematicoDefault : _vert.IndirizzoTelematico }, tipo = IndirizzoTelematicoTipo.smtp },
                    Destinatario = new Destinatario[]
                    {
                        new Destinatario
                        {
                            Items = new object[]
                            {
                                _datiProto.AmministrazioniInterne[0].ToAmministrazioneInterna(_vert),
                                new AOO
                                {
                                    CodiceAOO = new CodiceAOO{ Text = new string[]{ _vert.CodiceAoo } },
                                    Denominazione = new Denominazione{ Text = new string[] { _vert.DenominazioneAoo } }
                                }
                            }
                        }
                    }
                }
            };
        }

        public RegistroTipo Flusso
        {
            get { return RegistroTipo.Interno; }
        }


        public IndirizzoTelematico GetIndirizzoTelematico()
        {
            if (String.IsNullOrEmpty(GeProtConstants.IndirizzoTelematicoDefault))
            {
                throw new ProtocolloException("INDIRIZZO TELEMATICO NON VALORIZZATO");
            }

            return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = new string[] { GeProtConstants.IndirizzoTelematicoDefault } };
        }
    }
}
