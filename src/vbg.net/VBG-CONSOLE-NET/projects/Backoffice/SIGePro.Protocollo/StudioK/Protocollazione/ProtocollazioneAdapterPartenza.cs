using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public class ProtocollazioneAdapterPartenza : IProtocollazioneAdapter
    {
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        VerticalizzazioniWrapper _vert;
        string _ufficioMittente;
        string _classifica;

        public ProtocollazioneAdapterPartenza(IEnumerable<IAnagraficaAmministrazione> destinatari, VerticalizzazioniWrapper vert, string ufficioMittente, string classifica)
        {
            _destinatari = destinatari;
            _vert = vert;
            _ufficioMittente = ufficioMittente;
            _classifica = classifica;
        }

        public Destinazione[] GetDestinatari()
        {

            var senzaCodFiscalePartitaIva = _destinatari.Where(x => String.IsNullOrEmpty(x.CodiceFiscalePartitaIva));
            if (senzaCodFiscalePartitaIva.Count() > 0)
            {
                var errore = senzaCodFiscalePartitaIva.Select(x => String.Format("IL DESTINATARIO {0} E' SPROVVISTO DI PARTITA IVA", x.Denominazione));
                throw new Exception(String.Join("\r\n", errore.ToArray()));
            }


            var destinazione = new Destinazione[]
            { 
                new Destinazione 
                {
                    Destinatario = _destinatari.Select(x => new Destinatario
                    {
                        Items = new object[] 
                        { 
                            new Persona 
                            { 
                                id = x.CodiceFiscalePartitaIva,
                                Identificativo = new Identificativo{ Text = new string[]{ x.CodiceFiscalePartitaIva }},
                                Items = new object[]
                                { 
                                    new Nome{ Text = new string[]{ x.Nome } },
                                    new Cognome{ Text = new string[]{ x.Cognome } },
                                    new Denominazione{ Text = new string[]{ x.NomeCognome }},
                                    new CodiceFiscale{ Text = new string[]{ x.CodiceFiscalePartitaIva } }
                                } 
                            } 
                        },
                        IndirizzoTelematico = new IndirizzoTelematico
                        {
                            Text = new string[] { x.Pec },
                            tipo = IndirizzoTelematicoTipo.smtp
                        },
                        IndirizzoPostale = new IndirizzoPostale
                        {
                            Item = new Indirizzo
                            {
                                CAP = new CAP { Text = new string[] { x.Cap } },
                                Comune = new Comune { Text = new string[] { x.Comune } },
                                Provincia = new Provincia { Text = new string[] { x.Provincia } },
                                Toponimo = new Toponimo { Text = new string[] { x.Indirizzo } }
                            }
                        }
                    }).ToArray()
                }
            };

            return destinazione;

        }

        public Origine GetMittente()
        {
            return new Origine{ Mittente = new Mittente() };
        }

        public CustomMetadata GetCustomMetadata()
        {
            var classe = "";
            var categoria = "";

            if (!String.IsNullOrEmpty(_classifica))
            {
                var classificaSplittata = _classifica.Split('.');
                if (classificaSplittata.Length == 2)
                {
                    classe = classificaSplittata[0];
                    categoria = classificaSplittata[1];
                }
            }

            var customMetadata = new CustomMetadata
            {
                Intestazione = new CustomMetadataIntestazione
                {
                    Flusso = CustomMetadataIntestazioneFlusso.U,
                    FlussoSpecified = true,
                    Classificazione = new CustomMetadataIntestazioneClassificazione[]
                    { 
                        new CustomMetadataIntestazioneClassificazione
                        { 
                            Classe = classe, 
                            Categoria = categoria 
                        } 
                    },
                    eMail = CustomMetadataIntestazioneEMail.S,
                    Accompagnatoria = CustomMetadataIntestazioneAccompagnatoria.S,
                    AccompagnatoriaSpecified = true,
                    eMailSpecified = true
                }
            };

            return customMetadata;
        }
    }
}
