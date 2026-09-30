using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
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

            return this.db.GetClass(retVal);
        }

        public List<Amministrazioni> GetByCodiceComune(string idComune, string codiceComune)
        {
            Amministrazioni filtro = new Amministrazioni
            {
                IDCOMUNE = idComune,
                CodiceComune = codiceComune
            };

            return this.GetList(filtro);
        }

        public List<Amministrazioni> GetByProtUO(string idComune, string codiceUO)
        {
            var sql = $@"select 
                            amministrazioni.* 
                        from 
                            amministrazioni 
                                inner join amministr_protocollo on 
                                    amministrazioni.idcomune = amministr_protocollo.idcomune and 
                                    amministrazioni.codiceamministrazione = amministr_protocollo.codiceamministrazione and
                                    amministr_protocollo.prot_uo = {this.db.Specifics.QueryParameterName("codiceUO")}
                        where
                            amministrazioni.idcomune={this.db.Specifics.QueryParameterName("idcomune")}";

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("codiceUO", codiceUO));
                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));

                return this.db.GetClassList<Amministrazioni>(cmd);
            }
        }
        public Amministrazioni GetByIdProtocollo(string idComune, int codiceAmministrazione, string software, string codiceComune)
        {
            var amm = this.GetById(idComune, codiceAmministrazione);

            var ammProtoMgr = new AmministrazioniProtocolloMgr(this.db);
            var ammProto = ammProtoMgr.GetById(idComune, codiceAmministrazione, software, codiceComune);

            if (ammProto != null)
            {
                amm.PROT_UO = ammProto.ProtUo;
                amm.PROT_RUOLO = ammProto.ProtRuolo;
            }

            if (!String.IsNullOrEmpty(amm.CITTA))
            {
                var comMgr = new ComuniMgr(this.db);
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
            var alberoMgr = new AlberoProcMgr(this.db);
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

        public List<Amministrazioni> GetList(Amministrazioni p_class)
        {
            return this.db.GetClassList(p_class);
        }

        #endregion
    }
}