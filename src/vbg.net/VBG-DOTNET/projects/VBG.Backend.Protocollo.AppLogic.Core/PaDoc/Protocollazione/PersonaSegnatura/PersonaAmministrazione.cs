using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.PersonaSegnatura
{
    public class PersonaAmministrazione : IPersona
    {
        private readonly ProtocolloAmministrazioni _amm;

        public PersonaAmministrazione(ProtocolloAmministrazioni amm)
        {
            this._amm = amm;
        }

        public object[] GetPersona()
        {
            var list = new List<object>();

            list.Add(new Denominazione { Text = new string[] { this._amm.AMMINISTRAZIONE } });

            if (!String.IsNullOrEmpty(this._amm.PARTITAIVA))
                list.Add(new CodiceFiscale { Text = new string[] { this._amm.PARTITAIVA } });

            return list.ToArray();
        }

        public string Denominazione
        {
            get { return this._amm.AMMINISTRAZIONE; }
        }


        public IndirizzoPostale GetIndirizzoPostale()
        {
            return new IndirizzoPostale
            {
                Item = new Indirizzo
                {
                    CAP = new CAP { Text = new string[] { this._amm.CAP } },
                    Civico = new Civico { Text = new string[] { "" } },
                    Comune = new Comune { Text = new string[] { this._amm.CITTA } },
                    Provincia = new Provincia { Text = new string[] { this._amm.PROVINCIA } },
                    Toponimo = new Toponimo { dug = "", Text = new string[] { this._amm.INDIRIZZO } }
                }
            };
        }

        public IndirizzoTelematico IndirizzoTelematico
        {
            get { return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = new string[] { this._amm.PEC } }; }
        }
    }
}
