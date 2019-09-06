using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.ComponentModel;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per TipiMovimentoMgr.\n	/// </summary>
	public partial class TipiMovimentoMgr//: BaseManager
	{

		private TipiMovimento DataIntegrations(TipiMovimento cls)
		{
			TipiMovimento retVal = ( TipiMovimento ) cls.Clone();

            if (retVal.FlagCds.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagCds = 0;

            if (retVal.FlagEnmail.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagEnmail = 0;

            if (retVal.FlagEnmostra.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagEnmostra = 0;

            if (retVal.FlagInterruzione.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagInterruzione = 0;

            if (retVal.FlagNoamminterna.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagNoamminterna = 0;

            if (retVal.FlagNonoperante.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagNonoperante = 0;

            if (retVal.FlagOperante.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagOperante = 0;

            if (retVal.FlagProroga.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagProroga = 0;

            if (retVal.FlagPubblicamovimento.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagPubblicamovimento = 1;

            if (retVal.FlagPubblicaparere.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagPubblicaparere = 1;

            if (retVal.FlagRegistro.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagRegistro = 0;

            if (retVal.FlagRichiestaintegrazione.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagRichiestaintegrazione = 0;

            if (retVal.FlagUsadalprotocollo.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.FlagUsadalprotocollo = 0;

            if (retVal.Ggproroga.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.Ggproroga = 0;

            if (retVal.Sistema.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.Sistema = 0;

            if (retVal.Tipologiaesito.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.Tipologiaesito = 0;

            if (retVal.Tutteleamministrazioni.GetValueOrDefault(int.MinValue) == int.MinValue)
				retVal.Tutteleamministrazioni = 0;

			return retVal;
		}


		private void Validate(TipiMovimento cls, AmbitoValidazione ambitoValidazione)
		{
			if (cls.FlagCds != 0 && cls.FlagCds != 1)
				throw new IncongruentDataException("TIPIMOVIMENTO.FLAG_CDS = " + cls.FlagCds);

			if (cls.FlagEnmail != 0 && cls.FlagEnmail != 1)
				throw new IncongruentDataException("TIPIMOVIMENTO.FLAG_ENMAIL = " + cls.FlagEnmail);

			if ( cls.FlagInterruzione != 0 && cls.FlagInterruzione != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagInterruzione = " + cls.FlagInterruzione);

			if ( cls.FlagNoamminterna != 0 && cls.FlagNoamminterna != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagNoamminterna = " + cls.FlagNoamminterna);

			if ( cls.FlagNonoperante != 0 && cls.FlagNonoperante != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagNonoperante = " + cls.FlagNonoperante);

			if ( cls.FlagOperante != 0 && cls.FlagOperante != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagOperante = " + cls.FlagOperante);

			if ( cls.FlagProroga != 0 && cls.FlagProroga != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagProroga = " + cls.FlagProroga);

			if ( cls.FlagPubblicamovimento != 0 && cls.FlagPubblicamovimento != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagPubblicamovimento = " + cls.FlagPubblicamovimento);

			if ( cls.FlagPubblicaparere != 0 && cls.FlagPubblicaparere != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagPubblicaparere = " + cls.FlagPubblicaparere);

			if ( cls.FlagRegistro != 0 && cls.FlagRegistro != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagRegistro = " + cls.FlagRegistro);

			if ( cls.FlagRichiestaintegrazione != 0 && cls.FlagRichiestaintegrazione != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagRichiestaintegrazione = " + cls.FlagRichiestaintegrazione);

			if ( cls.FlagUsadalprotocollo != 0 && cls.FlagUsadalprotocollo != 1 )
				throw new IncongruentDataException("TIPIMOVIMENTO.FlagUsadalprotocollo = " + cls.FlagUsadalprotocollo);			
			
			RequiredFieldValidate( cls , ambitoValidazione);

			ForeignValidate( cls );
		}


		private void ForeignValidate ( TipiMovimento cls )
		{
			#region TIPIMOVIMENTO.FKIDREGISTRO
            if (cls.Fkidregistro.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				if ( this.recordCount("TIPOLOGIAREGISTRI","TR_ID","WHERE IDCOMUNE = '" + cls.Idcomune + "' AND TR_ID = " + cls.Fkidregistro ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO.FKIDREGISTRO (" + cls.Fkidregistro + ") non trovato nella tabella TIPOLOGIAREGISTRI"));
				}
			}
			#endregion

			#region TIPIMOVIMENTO.CODICELETTERA
            if (cls.Codicelettera.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				if ( this.recordCount("LETTERETIPO","CODICELETTERA","WHERE IDCOMUNE = '" + cls.Idcomune + "' AND CODICELETTERA = " + cls.Codicelettera ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO.CODICELETTERA (" + cls.Codicelettera + ") non trovato nella tabella LETTERETIPO"));
				}
			}
			#endregion
		}



        internal IEnumerable<Dyn2ModelliT> GetSchedeDinamicheConfigurate(string idComune, string idTipomovimento)
        {
            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica(@"SELECT 
														dyn2_modellit.* 
													FROM 
														dyn2_modellit,
														tipimovimentidyn2modellit
													WHERE
														dyn2_modellit.idcomune = tipimovimentidyn2modellit.idcomune AND
														dyn2_modellit.id = tipimovimentidyn2modellit.fk_d2mt_id AND
														tipimovimentidyn2modellit.idcomune = {0} AND
														tipimovimentidyn2modellit.tipoMovimento = {1}",
                                                    "idComune",
                                                    "tipoMovimento");

                using (var cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("tipoMovimento", idTipomovimento));

                    return db.GetClassList<Dyn2ModelliT>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }
    }
}