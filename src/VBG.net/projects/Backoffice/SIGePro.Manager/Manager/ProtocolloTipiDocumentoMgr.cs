using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using System.Linq;

namespace Init.SIGePro.Manager
{
	public partial class ProtocolloTipiDocumentoMgr : BaseProtocolloManager
	{
		public ProtocolloTipiDocumentoMgr(DataBase dataBase) : base(dataBase) { }

        public IEnumerable<ProtocolloTipiDocumento> GetBySoftwareCodiceComune(string idcomune, string software = "TT", string codiceComune = "")
        {
            var c = new ProtocolloTipiDocumento { Idcomune = idcomune };
            return this.GetByClassProtocollo<ProtocolloTipiDocumento>(c, software, codiceComune);
        }

		public ProtocolloTipiDocumento GetById(string idcomune, string codice, string software = "TT", string codiceComune = "")
		{
            var c = new ProtocolloTipiDocumento { Idcomune = idcomune, Codice = codice };
            return this.GetByIdProtocollo<ProtocolloTipiDocumento>(c, software, codiceComune);
		}

        public string GetCodiceFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var tipiDoc = this.GetFromAlberoProcProtocollo(idIntervento, idComune, software, codiceComune);
            if (tipiDoc != null)
                return tipiDoc.Codice;

            return "";
        }

        public string GetDescrizioneFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var tipiDoc = this.GetFromAlberoProcProtocollo(idIntervento, idComune, software, codiceComune);
            if (tipiDoc != null)
                return tipiDoc.Descrizione;

            return "";
        }

        public ProtocolloTipiDocumento GetFromAlberoProcProtocollo(int idIntervento, string idComune, string software, string codiceComune)
        {
            var alberoMgr = new AlberoProcMgr(db);
            var albero = alberoMgr.GetById(idIntervento, idComune, codiceComune);

            if (!String.IsNullOrEmpty(albero.TipoDocumentoProtocollazione))
                return this.GetById(idComune, albero.TipoDocumentoProtocollazione, software, codiceComune);
            
            var range = albero.GetListaScCodice()
							  .ToArray()
							  .Reverse()
							  .Skip(1);

            foreach (var el in range)
            {
                albero = alberoMgr.GetByScCodice(idComune, software, el, codiceComune);

                if(!String.IsNullOrEmpty(albero.TipoDocumentoProtocollazione))
                    return this.GetById(idComune, albero.TipoDocumentoProtocollazione, software, codiceComune);
            }

            return null;
        }

		public List<ProtocolloTipiDocumento> GetList(ProtocolloTipiDocumento filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtocolloTipiDocumento>();
		}
	}
}
			
			
			