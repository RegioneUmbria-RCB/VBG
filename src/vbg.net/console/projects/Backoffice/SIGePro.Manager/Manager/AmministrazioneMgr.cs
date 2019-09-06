using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{

    public class AmministrazioniMgr : BaseManager
    {
        public AmministrazioniMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Amministrazioni GetById(String idComune, int codiceAmministrazione)
        {
            Amministrazioni retVal = new Amministrazioni();
            retVal.IDCOMUNE = idComune;
            retVal.CODICEAMMINISTRAZIONE = codiceAmministrazione.ToString();

            return (Amministrazioni)db.GetClass(retVal);
        }

        public Amministrazioni GetByIdProtocollo(string idComune, int codiceAmministrazione, string software, string codiceComune)
        {
            var amm = this.GetById(idComune, codiceAmministrazione);

            var ammProtoMgr = new AmministrazioniProtocolloMgr(db);
            var ammProto = ammProtoMgr.GetById(idComune, codiceAmministrazione, software, codiceComune);

            if (ammProto != null)
            {
                amm.PROT_UO = ammProto.ProtUo;
                amm.PROT_RUOLO = ammProto.ProtRuolo;
            }

            if (!String.IsNullOrEmpty(amm.CITTA))
            {
                var comMgr = new ComuniMgr(db);
                var comune = comMgr.GetByComune(amm.CITTA);
                amm.ComuneResidenza = comune;
            }

            return amm;
        }

        public string GetCodiceFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var amm = this.GetFromAlberoProcProtocollo(idIntervento, idComune, software, codiceComune);
            if (amm != null)
                return amm.CODICEAMMINISTRAZIONE;

            return "";
        }

        public string GetDescrizioneFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var amm = this.GetFromAlberoProcProtocollo(idIntervento, idComune, software, codiceComune);
            if (amm != null)
                return amm.AMMINISTRAZIONE;

            return "";
        }

        public Amministrazioni GetFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var alberoMgr = new AlberoProcMgr(db);
            var albero = alberoMgr.GetById(idIntervento, idComune, codiceComune);

            if (albero.CodiceAmministrazione.HasValue)
                return this.GetByIdProtocollo(idComune, albero.CodiceAmministrazione.Value, software, codiceComune);

            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = alberoMgr.GetByScCodice(idComune, software, el, codiceComune);

                if (albero.CodiceAmministrazione.HasValue)
                    return this.GetByIdProtocollo(idComune, albero.CodiceAmministrazione.Value, software, codiceComune);
            }

            return null;
        }        

        public ArrayList GetList(Amministrazioni p_class)
        {
            return this.GetList(p_class, null);
        }

        public ArrayList GetList(Amministrazioni p_class, Amministrazioni p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false);
        }
        #endregion
    }
}