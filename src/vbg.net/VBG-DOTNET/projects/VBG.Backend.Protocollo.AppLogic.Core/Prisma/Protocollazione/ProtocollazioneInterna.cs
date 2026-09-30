using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione
{
    public class ProtocollazioneInterna : IProtocollazionePrisma
    {
        private readonly ProtocolloAmministrazioni _mittente;
        private readonly string _codiceEnte;
        private readonly string _uoMittente;
        private readonly string _uoDestinatario;
        private readonly string _ruoloMittente;

        public ProtocollazioneInterna(ProtocolloAmministrazioni ammMittente, string uoDestinatario, string codiceEnte)
        {
            this._mittente = ammMittente;
            this._codiceEnte = codiceEnte;
            this._uoMittente = ammMittente.PROT_UO;
            this._uoDestinatario = uoDestinatario;
            this._ruoloMittente = ammMittente.PROT_RUOLO;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO_DOCAREA; }
        }

        public string Uo => this._uoMittente;

        public string Smistamento => this._ruoloMittente;

        public Destinatario[] GetDestinatario()
        {
            return new Destinatario[]
            {
                new Destinatario
                {
                    Items = new object[]
                    {
                        new Amministrazione
                        {
                            CodiceAmministrazione = this._codiceEnte,
                            IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[] { "" } },
                            Items = new object[] { new UnitaOrganizzativa { id = this._uoDestinatario } },
                            ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }
                        }
                    }
                }
            };
        }

        public Mittente[] GetMittente()
        {

            if (String.IsNullOrEmpty(this._mittente.PROT_RUOLO))
            {
                return null;
            }

            return new Mittente[]
{
                new Mittente
                {
                    Items = new object[]
                    {
                        new Amministrazione
                        {
                            Denominazione = this._mittente.AMMINISTRAZIONE,
                            CodiceAmministrazione = this._codiceEnte,
                            IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[] { "" } },
                            Items = new object[] { new UnitaOrganizzativa { id = this._mittente.PROT_RUOLO } },
                            ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa }
                        }
                    }
                }
            };
        }
    }
}
