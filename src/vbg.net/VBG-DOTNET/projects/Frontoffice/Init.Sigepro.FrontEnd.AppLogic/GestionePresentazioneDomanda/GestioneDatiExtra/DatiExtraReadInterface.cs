// -----------------------------------------------------------------------
// <copyright file="DatiExtraReadInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra
{
    using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
    using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
    using System.Collections.Generic;
    using System.Linq;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatiExtraReadInterface : IDatiExtraReadInterface
    {
        private PresentazioneIstanzaDbV2 _db;

        public DatiExtraReadInterface(PresentazioneIstanzaDbV2 db)
        {
            _db = db;
        }


        public string GetValoreDato(string chiave)
        {
            var datiXml = _db.DatiExtra.FindByChiave(chiave);

            if (datiXml == null)
                return null;

            return datiXml.Valore;
        }

        public T Get<T>(string chiave) where T : class
        {
            return GetValoreDato(chiave)?.DeserializeXML<T>();
        }

        public IEnumerable<string> Keys
        {
            get { return _db.DatiExtra.Select(x => x.Chiave); }
        }
    }
}
