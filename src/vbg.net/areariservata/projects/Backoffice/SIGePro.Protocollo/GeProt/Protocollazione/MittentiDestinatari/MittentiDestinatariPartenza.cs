using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : IMittentiDestinatari
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;

        public MittentiDestinatariPartenza(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
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
            var destinatari = new List<Destinazione>();

            var destinatariAmministrazioni = _datiProto.AmministrazioniEsterne.Select(x => new Destinazione
            {
                IndirizzoTelematico = new IndirizzoTelematico { Text = new string[]{ String.IsNullOrEmpty(x.PEC) ? GeProtConstants.IndirizzoTelematicoDefault : x.PEC }, tipo = IndirizzoTelematicoTipo.smtp },
                Destinatario = new Destinatario[]{ new Destinatario{ Items = new object[]{ x.ToAmministrazione() } } }
            });

            var destinatariPersone = _datiProto.AnagraficheProtocollo.Select(x => new Destinazione 
            {
                IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { String.IsNullOrEmpty(x.Pec) ? GeProtConstants.IndirizzoTelematicoDefault : x.Pec }, tipo = IndirizzoTelematicoTipo.smtp },
                Destinatario = new Destinatario[] { new Destinatario { Items = new object[] { x.ToPersona() } } }
            });

            destinatari.AddRange(destinatariAmministrazioni);
            destinatari.AddRange(destinatariPersone);

            return destinatari.ToArray();
        }

        public RegistroTipo Flusso
        {
            get { return RegistroTipo.Partenza; }
        }


        public IndirizzoTelematico GetIndirizzoTelematico()
        {
            if (String.IsNullOrEmpty(_vert.IndirizzoTelematico))
                throw new Exception("INDIRIZZO TELEMATICO DELL'ENTE NON VALORIZZATO");

            return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = new string[] { _vert.IndirizzoTelematico } };
        }
    }
}
