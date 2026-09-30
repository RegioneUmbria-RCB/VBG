using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using PersonalLib2.Data;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using Init.SIGePro.Manager.Manager;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders
{
	public class Dyn2DataAccessProvider : IDyn2DataAccessProvider
	{
		protected DataBase Database { get; private set; }

		public Dyn2DataAccessProvider(DataBase database)
		{
			Database = database;
		}


		#region IDyn2DataAccessProvider Members

		public IDyn2ProprietaCampiManager GetProprietaCampiManager()
		{
			return new Dyn2CampiProprietaMgr(Database);
		}

		public IDyn2ModelliManager GetModelliManager()
		{
			return new Dyn2ModelliTMgr(Database);
		}

		public IDyn2DettagliModelloManager GetDettagliModelloManager()
		{
			return new Dyn2ModelliDMgr(Database);
		}

		public IDyn2TestoModelloManager GetTestoModelloManager()
		{
			return new Dyn2ModelliDTestiMgr(Database);
		}

		public IDyn2CampiManager GetCampiManager()
		{
			return new Dyn2CampiMgr(Database);
		}

		public IDyn2ScriptCampiManager GetScriptCampiManager()
		{
			return new Dyn2CampiScriptMgr(Database);
		}

		public IDyn2ScriptModelloManager GetScriptModelliManager()
		{
			return new Dyn2ModelliScriptMgr(Database);
		}

		public IIstanzeDyn2DatiManager GetIstanzeDyn2DatiManager()
		{
            throw new NotImplementedException();
		}

		public IIstanzeDyn2DatiStoricoManager GetIstanzeDyn2DatiStoricoManager()
		{
            throw new NotImplementedException();
		}

		public IIstanzeManager GetIstanzeManager()
		{
			return new IstanzeMgr(Database);
		}

		public IAnagrafeDyn2DatiManager GetAnagrafeDyn2DatiManager()
		{
            throw new NotImplementedException();
        }

		public IAnagrafeDyn2DatiStoricoManager GetAnagrafeDyn2DatiStoricoManager()
		{
            throw new NotImplementedException();
        }

		public IAnagrafeManager GetAnagrafeManager()
		{
			return new AnagrafeMgr(Database);
		}

		public IIAttivitaDyn2DatiManager GetAttivitaDyn2DatiManager()
		{
            throw new NotImplementedException();
        }

		public IIAttivitaDyn2DatiStoricoManager GetAttivitaDyn2DatiStoricoManager()
		{
            throw new NotImplementedException();
        }

		public IIAttivitaManager GetAttivitaManager()
		{
			return new IAttivitaMgr(Database);
		}

		public IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
		{
			return new QuerySigepro(Database);
		}

		public string GetToken()
		{
			return Database.ConnectionDetails.Token;
		}

		#endregion


		public virtual IQueryLocalizzazioni GetQueryLocalizzazioni()
		{
			throw new NotImplementedException();
		}
	}
}
