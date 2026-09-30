using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Manager
{
    public partial class ProtOggettiMgr
    {
        public string GetContentType(ProtOggetti oggetto)
        {
            int startIdx = oggetto.Nomefile.LastIndexOf(".");

            if (startIdx == -1)
                return "";

            string ext = oggetto.Nomefile.Substring(startIdx + 1);

            ContentTypes ct = new ContentTypes();
            ct.OthersWhereClause.Add("ct_extension like '%;" + ext.ToLower() + ";%'");

            var coll = db.GetClassList(ct);

            if (coll.Count == 0) return "";

            return ((ContentTypes)coll[0]).CT_MIMETYPE;
        }

        public string GetExtension(ProtOggetti oggetto)
        {
            int startIdx = oggetto.Nomefile.LastIndexOf(".");

            if (startIdx == -1)
                return "";

            string ext = oggetto.Nomefile.Substring(startIdx + 1);
            return ext;
        }
    }
}
