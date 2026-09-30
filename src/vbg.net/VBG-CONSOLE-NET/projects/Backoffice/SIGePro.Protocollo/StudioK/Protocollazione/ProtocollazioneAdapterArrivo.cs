using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK.Protocollazione
{
    public class ProtocollazioneAdapterArrivo : IProtocollazioneAdapter
    {
        IAnagraficaAmministrazione _mittente;
        VerticalizzazioniWrapper _vert;
        string _assegnatoA;
        string _classifica;

        public ProtocollazioneAdapterArrivo(IAnagraficaAmministrazione mittente, VerticalizzazioniWrapper vert, string assegnatoA, string classifica)
        {
            _mittente = mittente;
            _vert = vert;
            _assegnatoA = assegnatoA;
            _classifica = classifica;
        }



        public Origine GetMittente()
        {
            if (String.IsNullOrEmpty(_mittente.CodiceFiscalePartitaIva))
                throw new Exception(String.Format("IL MITTENTE {0} E' SPROVVISTO DI CODICE FISCALE / PARTITA IVA", _mittente.NomeCognome));

            var persona = new Persona[] 
            { 
                new Persona 
                { 
                    id = _mittente.CodiceFiscale, 
                    Identificativo = new Identificativo { Text = new string[] { _mittente.CodiceFiscale } }, 
                    Items = new object[] 
                    { 
                        new CodiceFiscale{ Text = new string[]{ _mittente.CodiceFiscalePartitaIva } },
                        new Nome{ Text = new string[]{ _mittente.Nome } },
                        new Cognome{ Text = new string[]{ _mittente.Cognome } },
                        new Denominazione{ Text = new string[]{ _mittente.Denominazione } }
                    }
                } 
            };

            var origine = new Origine
            {
                IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[]{ _mittente.Pec }},
                Mittente = new Mittente
                {
                    Amministrazione = new Amministrazione
                    {
                        Denominazione = new Denominazione { Text = new string[] { _mittente.NomeCognome } },
                        //CodiceAmministrazione = new CodiceAmministrazione { Text = new string[] { _vert.CodiceAmministrazione } },
                        Items = new Persona[] 
                        { 
                            new Persona 
                            { 
                                id = _mittente.CodiceFiscale, 
                                Identificativo = new Identificativo { Text = new string[] { _mittente.CodiceFiscale } }, 
                                Items = new object[] 
                                { 
                                    new CodiceFiscale{ Text = new string[]{ _mittente.CodiceFiscalePartitaIva } },
                                    new Nome{ Text = new string[]{ _mittente.Nome } },
                                    new Cognome{ Text = new string[]{ _mittente.Cognome } },
                                    new Denominazione{ Text = new string[]{ _mittente.NomeCognome } }
                                }
                            } 
                        }
                    },
                    AOO = new AOO 
                    {
                        Denominazione = new Denominazione { Text = new string[] { _vert.CodiceAoo } },
                        CodiceAOO = new CodiceAOO { Text = new string[] { _vert.CodiceAoo } } 
                    }
                }
            };

            return origine;
        }

        public Destinazione[] GetDestinatari()
        {

            var destinazione = new Destinazione[]
            { 
                new Destinazione
                { 
                    Destinatario = new Destinatario[]
                    { 
                        new Destinatario
                        { 
                            Items = new object[]
                            { 
                                new Amministrazione
                                { 
                                    Denominazione = new Denominazione{ Text = new string[]{ _vert.DenominazioneEnte } },
                                    //CodiceAmministrazione = new CodiceAmministrazione{ Text = new string[]{ _assegnatoA } }, 
                                    Items = new object[]
                                    { 
                                        new UnitaOrganizzativa
                                        { 
                                            Denominazione = new Denominazione{ Text = new string[]{ _vert.DenominazioneEnte  } },
                                            //Identificativo = new Identificativo{ Text = new string[]{ _assegnatoA  } }
                                        } 
                                    }
                                } 
                            } 
                        }
                    } 
                } 
            };
            return destinazione;
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
                    Flusso = CustomMetadataIntestazioneFlusso.E,
                    FlussoSpecified = true,
                    Classificazione = new CustomMetadataIntestazioneClassificazione[]
                    { 
                        new CustomMetadataIntestazioneClassificazione
                        { 
                            Classe = classe, 
                            Categoria = categoria 
                        } 
                    },
                    Assegnazione = new CustomMetadataIntestazioneAssegnazione[] 
                    { 
                        new CustomMetadataIntestazioneAssegnazione 
                        { 
                            AssegnatoA = _assegnatoA, 
                            AssegnatoDa = _vert.AssegnatoDa
                        } 
                    }
                }
            };

            return customMetadata;
        }
    }
}
