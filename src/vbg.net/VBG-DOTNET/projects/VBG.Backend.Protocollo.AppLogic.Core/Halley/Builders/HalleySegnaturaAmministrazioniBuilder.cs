using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders
{
    public class HalleySegnaturaAmministrazioniBuilder
    {
        private readonly ProtocolloAmministrazioni _amministrazioneVbg;
        private readonly string _indirizzoTelematico = String.Empty;
        private readonly string _codiceAmministrazione;

        public HalleySegnaturaAmministrazioniBuilder(ProtocolloAmministrazioni amministrazioneVbg, string codiceAmministrazione, string indirizzoTelematico)
        {
            this._amministrazioneVbg = amministrazioneVbg;
            this._codiceAmministrazione = codiceAmministrazione;
            this._indirizzoTelematico = indirizzoTelematico;
            //SegnaturaAmministrazione = GetAmministrazione();
        }

        public Amministrazione GetAmministrazione()
        {

            var amministrazione = new Amministrazione();
            amministrazione.Denominazione = this._amministrazioneVbg.AMMINISTRAZIONE;
            amministrazione.CodiceAmministrazione = this._codiceAmministrazione;

            amministrazione.IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { this._indirizzoTelematico } };
            amministrazione.Items = new object[] { new UnitaOrganizzativa { id = this._amministrazioneVbg.PROT_UO } };
            amministrazione.ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa };

            return amministrazione;
        }
    }
}
