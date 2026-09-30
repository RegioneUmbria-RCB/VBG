using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SigedoSegnaturaAmministrazioniBuilder
    {
        private readonly ProtocolloAmministrazioni _amministrazioneVbg;
        private readonly string _codiceAmministrazione;

        public readonly Amministrazione SegnaturaAmministrazione;

        public SigedoSegnaturaAmministrazioniBuilder(ProtocolloAmministrazioni amministrazioneVbg, string codiceAmministrazione)
        {
            this._amministrazioneVbg = amministrazioneVbg;
            this._codiceAmministrazione = codiceAmministrazione;

            this.SegnaturaAmministrazione = this.GetAmministrazione();
        }

        private Amministrazione GetAmministrazione()
        {

            var amministrazione = new Amministrazione();
            /*amministrazione.Denominazione = _amministrazioneVbg.AMMINISTRAZIONE;*/
            amministrazione.CodiceAmministrazione = this._codiceAmministrazione;

            amministrazione.IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { this._amministrazioneVbg.EMAIL } };
            amministrazione.Items = new object[] { new UnitaOrganizzativa { id = this._amministrazioneVbg.PROT_UO } };
            amministrazione.ItemElementName = new ItemChoiceType[] { ItemChoiceType.UnitaOrganizzativa };

            return amministrazione;
        }
    }
}
