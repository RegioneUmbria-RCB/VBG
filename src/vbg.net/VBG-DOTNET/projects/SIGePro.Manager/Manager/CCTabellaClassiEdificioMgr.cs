using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCTabellaClassiEdificioMgr
    {
        private void VerificaRecordCollegati(CCTabellaClassiEdificio cls)
        {
            var sql = $@"SELECT 
	                        COUNT(*)
                        FROM
	                        CC_ICALCOLI
                        WHERE 
	                        IDCOMUNE={this.db.QueryParameter("idcomune")} AND 
	                        FK_CCTCE_ID={this.db.QueryParameter("id")}";

            var count = this.db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idcomune", cls.Idcomune);
                mp.AddParameter("id", cls.Id);
            });


            if (count > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLI");
        }

        public CCTabellaClassiEdificio GetClasseEdificio(string idComune, string software, decimal percentuale)
        {
            //TODO: query
            FormattableString sql = $@"SELECT
								Id
							FROM 
								cc_tabella_classiedificio
							WHERE
								cc_tabella_classiedificio.IdComune = {idComune} AND
								cc_tabella_classiedificio.Software = {software} AND
								cc_tabella_classiedificio.Da < {percentuale} AND
								cc_tabella_classiedificio.A >= {percentuale}";

            var id = this.db.ExecuteScalar(sql, -1);

            if (id < 0)
            {
                throw new InvalidOperationException("Non è stato possibile determinare la classe dificio per la percentuale " + percentuale.ToString());
            }

            return this.GetById(idComune, id);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCTabellaClassiEdificio> Find(string token, int? id, string descrizione, string software, string sortExpression)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCTabellaClassiEdificio filtro = new CCTabellaClassiEdificio();
            CCTabellaClassiEdificio filtroCompare = new CCTabellaClassiEdificio();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;

            filtroCompare.Descrizione = "LIKE";

            List<CCTabellaClassiEdificio> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCTabellaClassiEdificio>();

            ListSortManager<CCTabellaClassiEdificio>.Sort(list, sortExpression);

            return list;

        }
    }
}
