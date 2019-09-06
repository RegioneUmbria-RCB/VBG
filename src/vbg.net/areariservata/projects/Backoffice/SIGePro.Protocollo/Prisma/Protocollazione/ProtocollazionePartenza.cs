using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        IEnumerable<IAnagraficaAmministrazione> _destinatari;
        ParametriRegoleWrapper _vert;
        string _uo;
        string _ruolo;

        public ProtocollazionePartenza(IEnumerable<IAnagraficaAmministrazione> _destinatari, ParametriRegoleWrapper vert, string uo, string ruolo)
        {
            this._destinatari = _destinatari;
            this._vert = vert;
            this._uo = uo;
            this._ruolo = ruolo;
        }

        public string Flusso
        {
            get
            {
                return ProtocolloConstants.COD_PARTENZA_DOCAREA;
            }
        }

        public string Uo => this._uo;

        public string Smistamento => this._ruolo;

        public Destinatario[] GetDestinatario()
        {
            return _destinatari.Select(x => new Destinatario
            {
                Items = new object[]
                {
                    new Persona
                    {
                        id = x.CodiceFiscalePartitaIva,
                        CodiceFiscale = x.CodiceFiscalePartitaIva,
                        Cognome = x.Tipo == "G" ? x.Denominazione : x.Cognome,
                        Denominazione = x.Denominazione,
                        Nome = String.IsNullOrEmpty(x.Nome) ? "." : x.Nome,
                        IndirizzoTelematico = new IndirizzoTelematico
                        {
                            Text = new string[] { x.Pec },
                            tipo = "smtp"
                        },
                    }
                }
            }).ToArray();
        }

        public Mittente[] GetMittente()
        {
            return new Mittente[]
            {
                new Mittente
                {
                    Items = new object[]
                    {
                        new Amministrazione
                        {
                            Denominazione = this._vert.DenominazioneEnte,
                            CodiceAmministrazione = this._vert.CodiceEnte,
                            IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[] { "" } },
                        },
                        new AOO
                        {
                            CodiceAOO = this._vert.CodiceAoo
                        }
                    }
                }
            };
        }
    }
}
