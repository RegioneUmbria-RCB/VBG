using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.Documenti;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione
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

                var corpoMail = string.IsNullOrEmpty(_protoIn.CorpoMail) ? _protoIn.Oggetto.Replace(Environment.NewLine, " ") : _protoIn.CorpoMail;

                segnatura.Intestazione.CorpoEmail = new CorpoEmail { Text = new string[] { corpoMail } };
            }

            var docs = DocumentiFactory.Create(_protoIn.RecuperaAllegati());

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
