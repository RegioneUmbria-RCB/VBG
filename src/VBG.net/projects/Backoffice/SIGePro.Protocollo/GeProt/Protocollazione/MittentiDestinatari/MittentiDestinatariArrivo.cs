using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : IMittentiDestinatari
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;

        public MittentiDestinatariArrivo(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert)
        {
            _datiProto = datiProto;
            _vert = vert;
        }

        public Mittente GetMittente()
        {
            var res = new Mittente();

            if (_datiProto.AmministrazioniEsterne.Count > 0)
                res.Items = new object[] { _datiProto.AmministrazioniEsterne.First().ToAmministrazione() };

            if(_datiProto.AnagraficheProtocollo.Count > 0)
                res.Items = new object[] { _datiProto.AnagraficheProtocollo.First().ToDenominazione(), _datiProto.AnagraficheProtocollo.First().ToPersona() };

            return res;
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
                                _datiProto.Amministrazione.ToAmministrazioneInterna(_vert), 
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
            get { return RegistroTipo.Arrivo; }
        }

        public IndirizzoTelematico GetIndirizzoTelematico()
        {
            var indirizziAmministrazioni = _datiProto.AmministrazioniEsterne.Select(x => String.IsNullOrEmpty(x.EMAIL) ? GeProtConstants.IndirizzoTelematicoDefault : x.EMAIL);
            var indirizziAnagrafiche = _datiProto.AnagraficheProtocollo.Select(x => String.IsNullOrEmpty(x.EMAIL) ? GeProtConstants.IndirizzoTelematicoDefault : x.EMAIL);

            var range = new List<string>();
            range.AddRange(indirizziAmministrazioni);
            range.AddRange(indirizziAnagrafiche);

            return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = range.ToArray() };
        }
    }
}
