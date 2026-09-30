using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public class ProtocollazioneFlussoInterno : IProtocollazioneFlusso
    {
        IDatiProtocollo _datiProto;

        public ProtocollazioneFlussoInterno(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;
        }

        public string Flusso
        {
            get { throw new NotImplementedException(); }
        }

        public Mittente GetMittente()
        {
            var res = new Mittente
            {
                Amministrazione = new Amministrazione
                {
                    Items = new object[] 
                    { 
                        new UnitaOrganizzativa 
                        { 
                            Denominazione = new Denominazione { Text = new string[] { _datiProto.DescrizioneAmministrazione } }, 
                            Identificativo = new Identificativo { Text = new string[] { _datiProto.Uo } } 
                        } 
                    }
                }
            };

            return res;
        }

        public Destinazione[] GetDestinazioni()
        {
            var res = new Destinatario
            {
                Items = new object[]
                { 
                    new Amministrazione
                    { 
                        Items = new object[] 
                        { 
                            new UnitaOrganizzativa 
                            { 
                                Denominazione = new Denominazione { Text = new string[] { _datiProto.DescrizioneAmministrazione } }, 
                                Identificativo = new Identificativo { Text = new string[] { _datiProto.Uo } } 
                            } 
                        } 
                    } 
                }
            };

            return new Destinazione[] { new Destinazione { Destinatario = new Destinatario[]{ res } } };
        }


        public IndirizzoTelematico IndirizzoTelematicoOrigine
        {
            get { return null; }
        }
    }
}
