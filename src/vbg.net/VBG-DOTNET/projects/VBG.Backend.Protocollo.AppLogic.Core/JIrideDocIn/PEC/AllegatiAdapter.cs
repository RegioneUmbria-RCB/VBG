
namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.PEC
{
    public class Allegati
    {
        public string AllegatoPrincipale { get; private set; }
        public string AllegatiSecondari { get; private set; }

        public Allegati(string principale, string allegati)
        {
            AllegatoPrincipale = String.IsNullOrEmpty(principale) ? "" : principale;
            AllegatiSecondari = String.IsNullOrEmpty(allegati) ? "" : allegati;
        }
    }

    public class AllegatiAdapter
    {
        public AllegatiAdapter()
        {

        }

        public Allegati Adatta(IEnumerable<string> seriali)
        {
            if (seriali == null || seriali.Count() == 0)
                throw new Exception("ALLEGATI NON PRESENTI");

            var allegatoPrincipale = seriali.First();
            string allegatiSecondari = "";

            if (seriali.Count() > 1)
                allegatiSecondari = String.Join("|", seriali.Skip(1));

            return new Allegati(allegatoPrincipale, allegatiSecondari);
        }
    }
}
