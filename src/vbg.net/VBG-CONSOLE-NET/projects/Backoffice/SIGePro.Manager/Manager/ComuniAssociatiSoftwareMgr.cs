
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class ComuniAssociatiSoftwareMgr
    {
        public ComuniAssociatiSoftware GetByCodiceComuneSoftwareIdComune(string idcomune, string software, string codiceComune)
        {
            var c = new ComuniAssociatiSoftware { Idcomune = idcomune, Software = software, Codicecomune = codiceComune };
            return (ComuniAssociatiSoftware)db.GetClass(c);
        }


		public string GetIndirizzoPecComuneAssociato(string idComune, string idComuneAssociato, string software)
		{
			var filtro = new ComuniAssociatiSoftware
			{
				Idcomune = idComune,
				Codicecomune = idComuneAssociato,
				Software = software
			};

			var cls = (ComuniAssociatiSoftware)db.GetClass(filtro);

			if(cls != null && !String.IsNullOrEmpty(cls.Mailpec))
				return cls.Mailpec;

			filtro.Software = "TT";

			cls = (ComuniAssociatiSoftware)db.GetClass(filtro);

			if (cls != null && !String.IsNullOrEmpty(cls.Mailpec))
				return cls.Mailpec;

			var cfg = new ConfigurazioneMgr(db).GetById(idComune, software);

			if(cfg != null && !String.IsNullOrEmpty( cfg.EmailResponsabilePec ))
				return cfg.EmailResponsabilePec;

			cfg = new ConfigurazioneMgr(db).GetById(idComune, "TT");

			if (cfg != null && !String.IsNullOrEmpty(cfg.EmailResponsabilePec))
				return cfg.EmailResponsabilePec;

			return String.Empty;
		}
	}
}
				