using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.GeProt.Protocollazione.Documenti;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione
{
    public class SegnaturaAdapter
    {
        DatiProtocolloIn _protoIn;
        ProtocolloLogs _logs;
        VerticalizzazioniConfiguration _vert;

        public SegnaturaAdapter(VerticalizzazioniConfiguration vert, DatiProtocolloIn protoIn, ProtocolloLogs logs)
        {
            _protoIn = protoIn;
            _logs = logs;
            _vert = vert;
        }

        public Segnatura Adatta(string operatore, string descrizioneTipoDocumento, bool inviaPec)
        {
            var datiProto = DatiProtocolloInsertFactory.Create(_protoIn);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(datiProto, _vert);
            var parametriAdapter = new ParametriAdapter();
            var classificaAdapter = new ClassificaAdapter();

            var segnatura = new Segnatura
            {
                Intestazione = new Intestazione
                {
                    Identificatore = new Identificatore
                    {
                        CodiceAmministrazione = new CodiceAmministrazione { Text = new string[] { _vert.CodiceAmministrazione } },
                        CodiceAOO = new CodiceAOO { Text = new string[] { _vert.CodiceAoo } },
                        DescrizioneAmministrazione = new DescrizioneAmministrazione { Text = new string[] { _vert.DenominazioneAmministrazione } },
                        DescrizioneAOO = new DescrizioneAOO { Text = new string[] { _vert.DenominazioneAoo } }
                    }
                    ,
                    Registro = new Registro { tipo = mittentiDestinatari.Flusso },
                    Oggetto = new Oggetto { Text = new string[] { _protoIn.Oggetto.Replace(Environment.NewLine, " ") } },
                    Classifica = classificaAdapter.Adatta(_protoIn.Classifica),
                    Parametri = parametriAdapter.Adatta(operatore, descrizioneTipoDocumento),
                    Origine = new Origine
                    {
                        IndirizzoTelematico = mittentiDestinatari.GetIndirizzoTelematico(),
                        Mittente = mittentiDestinatari.GetMittente()
                    },
                    Destinazione = mittentiDestinatari.GetDestinatari()
                }
            };

            if (inviaPec)
            {
                segnatura.Intestazione.InvioEmail = new InvioEmail { Text = new string[] { "si" } };
                segnatura.Intestazione.CorpoEmail = new CorpoEmail { Text = new string[] { _protoIn.Oggetto.Replace(Environment.NewLine, " ") } };
            }

            var docs = DocumentiFactory.Create(_protoIn.Allegati);

            if (docs == null)
            {
                segnatura.Descrizione = new Descrizione { Item = new TestoDelMessaggio() };
                return segnatura;
            }

            segnatura.Descrizione = new Descrizione { Item = docs.DocPrincipale };

            if (docs.Allegati != null)
                segnatura.Descrizione.Allegati = new Allegati { Items = docs.Allegati };

            return segnatura;
        }
    }
}
