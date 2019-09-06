using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        public string Flusso { get { return ProtocolloConstants.COD_ARRIVO_DOCAREA; } }

        public string Uo
        {
            get { return this._codiceUo; }
        }

        public string Smistamento
        {
            get { return this._codiceUo; }
        }

        IEnumerable<IAnagraficaAmministrazione> _mittenti;
        readonly string _codiceUo;
        readonly string _codiceRuolo;

        public ProtocollazioneArrivo(IEnumerable<IAnagraficaAmministrazione> mittenti, string codiceUo, string codiceRuolo)
        {
            this._mittenti = mittenti;
            this._codiceUo = codiceUo;
            this._codiceRuolo = codiceRuolo;
        }

        public Destinatario[] GetDestinatario()
        {
            return null;
            //La logica che prevedeva la compilazione del nodo Destinatario è stata spostata sul parametro Smistamento 
            //del nodo ApplicativoProtocollo (vedere la classe ProtocollazioneInAdapter), compilare il nodo Destinatario o 
            //il parametro Smistamento è la stessa cosa.
            //return new Destinatario[]
            //{
            //    new Destinatario
            //    {
            //        Items = new object[]
            //        {
            //            new Amministrazione
            //            {
            //                Denominazione = this._denominazioneUo,
            //                CodiceAmministrazione = this._codiceEnte,
            //                IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[] { "" } },
            //                Items = new object[] { new UnitaOrganizzativa { id = this._codiceUo } },
            //                ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }
            //            }
            //        }
            //    }
            //};
        }

        public Mittente[] GetMittente()
        {
            return _mittenti.Select(x => new Mittente
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
    }
}
